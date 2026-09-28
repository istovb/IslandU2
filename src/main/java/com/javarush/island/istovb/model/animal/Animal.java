package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;
import com.javarush.island.istovb.model.Island;
import com.javarush.island.istovb.model.Location;
import com.javarush.island.istovb.model.Terrain;
import com.javarush.island.istovb.util.RandomUtil;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Animal {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    protected final long id = ID_GENERATOR.incrementAndGet();
    protected Location location;
    protected double eatenFood = 0;
    protected int ticksWithoutFood = 0;
    protected volatile boolean alive = true;


    protected boolean isNewborn = false;

    protected double currentWeight;


    public abstract String getSpeciesName();
    public abstract char getSymbol();

    public abstract double getBaseWeight();


    public double getWeight() {
        return currentWeight;
    }
    public abstract int getMaxPerCell();
    public abstract int getSpeed();
    public abstract double getFoodRequired();
    public abstract int getMoveChancePercent();
    public abstract int getOffspringCount();
    public abstract int getEatProbability(String preySpecies);
    public abstract Animal createOffspring();

    public long getId() { return id; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public boolean isAlive() { return alive; }
    public void kill() { alive = false; }

    public boolean canSwim() { return false; }

    public void applyInitialWeight() {
        double loss = SimulationConfig.INITIAL_WEIGHT_LOSS_PERCENT / 100.0;
        this.currentWeight = getBaseWeight() * (1.0 - loss);
    }

    public void tick(Island island) {
        if (!alive) return;

        // Детёныши пропускают свой самый первый такт действий, чтобы симуляция не взрывалась
        if (isNewborn) {
            isNewborn = false;
            return;
        }
        eat(island);
        breed(island);
        move(island);
        applyHunger();
    }

    public abstract void eat(Island island);

    /**
     * Размножение. ВАЖНО: размножаются только СЫТЫЕ особи.
     */
    public void breed(Island island) {
        if (!alive || location == null) return;
        if (eatenFood < getFoodRequired()) return;
        synchronized (location) {
            long same = location.countAnimals(this.getClass());
            if (same < 2) return;
            if (same >= getMaxPerCell()) return;
            if (!RandomUtil.chance(SimulationConfig.BREED_CHANCE_PERCENT)) return;

            int children = getOffspringCount();
            for (int i = 0; i < children; i++) {
                if (location.countAnimals(this.getClass()) >= getMaxPerCell()) break;

                Animal child = createOffspring();
                child.applyInitialWeight();
                child.isNewborn = true; // Защита: помечаем новорождённого
                child.setLocation(location);
                location.addAnimal(child);
            }
        }
    }

    public void move(Island island) {
        if (!alive || location == null) return;
        if (getSpeed() <= 0) return;
        if (!RandomUtil.chance(getMoveChancePercent())) return;

        int steps = RandomUtil.between(1, getSpeed());
        for (int i = 0; i < steps && alive; i++) {
            moveOneStep(island);
        }
    }

    protected void moveOneStep(Island island) {
        if (location == null) return;

        int[] d = chooseDirection(island);
        int nr = location.getRow() + d[0];
        int nc = location.getCol() + d[1];
        if (!island.inBounds(nr, nc)) return;

        Location next = island.getLocation(nr, nc);
        if (next == location || !canEnter(next)) return;

        Location firstLock = this.location.hashCode() < next.hashCode() ? this.location : next;
        Location secondLock = firstLock == this.location ? next : this.location;

        synchronized (firstLock) {
            synchronized (secondLock) {
                // Двойная проверка условий после захвата мониторов ячеек
                if (!alive || this.location != firstLock && this.location != secondLock) return;
                if (next.countAnimals(this.getClass()) >= getMaxPerCell()) return;

                location.removeAnimal(this);
                next.addAnimal(this);
                this.location = next;
            }
        }
    }
    public int[] chooseDirection(Island island) {
        int[][] dirs = {
                {-1,-1},{-1,0},{-1,1},
                { 0,-1},        { 0,1},
                { 1,-1},{ 1,0},{ 1,1}
        };
        return dirs[RandomUtil.nextInt(dirs.length)];
    }
    protected boolean canEnter(Location target) {
        if (target.getTerrain() == Terrain.RIVER) return canSwim();
        return true;
    }
    protected void applyHunger() {
        if (eatenFood >= getFoodRequired()) {
            eatenFood = 0;
            ticksWithoutFood = 0;
            return;
        }
        ticksWithoutFood++;
        int maxTicksWithoutFood = this instanceof Predator
                ? SimulationConfig.PREDATOR_TICKS_WITHOUT_FOOD_TO_DIE
                : SimulationConfig.TICKS_WITHOUT_FOOD_TO_DIE;

        if (ticksWithoutFood >= maxTicksWithoutFood) {
            alive = false;
        }
    }
    @Override
    public String toString() { return getSymbol() + "#" + id; }
}
