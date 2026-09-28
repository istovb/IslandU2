package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.EatTable;
import com.javarush.island.istovb.model.Island;
import com.javarush.island.istovb.model.Location;
import com.javarush.island.istovb.util.RandomUtil;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public abstract class Predator extends Animal {

    private static final int SENSE_RADIUS = 4;

    @Override
    public int getEatProbability(String prey) {
        return EatTable.PROBABILITIES
                .getOrDefault(this.getClass().getSimpleName(), Map.of())
                .getOrDefault(prey, 0);
    }

    @Override
    public void eat(Island island) {
        if (!alive || location == null) return;

        Animal prey = findNearest(island,
                a -> !a.getClass().equals(this.getClass()) && !(a instanceof Predator));

        if (prey != null) {
            Location targetLoc = prey.getLocation();
            if (targetLoc != this.location) {
                moveTo(island, targetLoc);
            }

            if (this.location == prey.getLocation()) {
                int chance = getEatProbability(prey.getSpeciesName());
                if (chance > 0 && RandomUtil.chance(chance)) {
                    eatenFood += Math.max(prey.getWeight(), getFoodRequired() * 0.5);
                    prey.kill();
                    this.location.removeAnimal(prey);

                    if (eatenFood >= getFoodRequired()) {
                        ticksWithoutFood = 0;
                        return;
                    }
                }
            }
        }

        List<Animal> neighbors = location.getAnimals();
        for (int i = 0; i < neighbors.size(); i++) {
            if (i >= neighbors.size()) break;

            Animal n = neighbors.get(i);
            if (n == this || !n.isAlive() || n instanceof Predator) continue;

            int c = getEatProbability(n.getSpeciesName());
            if (c <= 0 || !RandomUtil.chance(c)) continue;

            eatenFood += Math.max(n.getWeight(), getFoodRequired() * 0.5);
            n.kill();
            location.removeAnimal(n);
            i--;

            if (eatenFood >= getFoodRequired()) {
                ticksWithoutFood = 0;
                break;
            }
        }
    }

    protected Animal findNearest(Island island, Predicate<Animal> filter) {
        int r = location.getRow();
        int c = location.getCol();

        for (int dr = -SENSE_RADIUS; dr <= SENSE_RADIUS; dr++) {
            for (int dc = -SENSE_RADIUS; dc <= SENSE_RADIUS; dc++) {
                int nr = r + dr;
                int nc = c + dc;
                if (!island.inBounds(nr, nc))
                    continue;

                List<Animal> cellAnimals = island.getLocation(nr, nc).getAnimals();
                for (Animal a : cellAnimals) {
                    if (a != null && a.isAlive() && filter.test(a)) {
                        return a;
                    }
                }
            }
        }
        return null;
    }

    protected boolean moveTo(Island island, Location target) {
        Location currentSource = this.location;
        if (currentSource == null || target == currentSource)
            return false;

        int[] d = stepTowards(currentSource, target);
        int nr = currentSource.getRow() + d[0];
        int nc = currentSource.getCol() + d[1];

        if (!island.inBounds(nr, nc))
            return false;

        Location next = island.getLocation(nr, nc);
        if (!canEnter(next))
            return false;

        boolean currentFirst = (currentSource.getRow() < next.getRow()) ||
                (currentSource.getRow() == next.getRow() && currentSource.getCol() < next.getCol());

        Location firstLock = currentFirst ? currentSource : next;
        Location secondLock = currentFirst ? next : currentSource;

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (!alive || this.location != currentSource) return false;
                if (next.countAnimals(getClass()) >= getMaxPerCell()) return false;

                currentSource.removeAnimal(this);
                next.addAnimal(this);
                setLocation(next);
                return true;
            }
        }
    }

    private static int[] stepTowards(Location from, Location to) {
        int dr = Integer.compare(to.getRow() - from.getRow(), 0);
        int dc = Integer.compare(to.getCol() - from.getCol(), 0);
        return new int[]{dr, dc};
    }
}
