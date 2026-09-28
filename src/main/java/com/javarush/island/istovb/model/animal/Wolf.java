package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;
import com.javarush.island.istovb.model.Island;
import com.javarush.island.istovb.model.Location;
import com.javarush.island.istovb.util.RandomUtil;

public class Wolf extends Predator {

    private static final int SENSE_RADIUS = 5;

    @Override public String getSpeciesName() { return "Волк"; }
    @Override public char getSymbol() { return 'W'; }
    @Override public double getBaseWeight() { return 50; }
    @Override public int getMaxPerCell() { return 30; }
    @Override public int getSpeed() { return 3; }
    @Override public double getFoodRequired() { return 8; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_WOLF; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_WOLF; }
    @Override public Animal createOffspring() { return new Wolf(); }

    @Override
    public int[] chooseDirection(Island island) {
        Location here = location;
        if (here == null) return super.chooseDirection(island);

        Animal prey = findNearest(island, here, new AnimalFilter() {
            @Override public boolean accept(Animal a) { return a instanceof Herbivore; }
        });
        if (prey != null) return stepTowards(here, prey.getLocation());

           if (RandomUtil.chance(70)) {
            Animal mate = findNearest(island, here, new AnimalFilter() {
                @Override public boolean accept(Animal a) {
                    return a != Wolf.this && a instanceof Wolf;
                }
            });
            if (mate != null) return stepTowards(here, mate.getLocation());
        }

        return super.chooseDirection(island);
    }

    private Animal findNearest(Island island, Location from, AnimalFilter filter) {
        Animal best = null;
        int bestDist = Integer.MAX_VALUE;
        for (int dr = -SENSE_RADIUS; dr <= SENSE_RADIUS; dr++) {
            for (int dc = -SENSE_RADIUS; dc <= SENSE_RADIUS; dc++) {
                int r = from.getRow() + dr;
                int c = from.getCol() + dc;
                if (!island.inBounds(r, c)) continue;
                for (Animal a : island.getLocation(r, c).getAnimals()) {
                    if (a == null || !a.isAlive() || !filter.accept(a)) continue;
                    int dist = Math.abs(dr) + Math.abs(dc);
                    if (dist < bestDist) {
                        bestDist = dist;
                        best = a;
                    }
                }
            }
        }
        return best;
    }

    private int[] stepTowards(Location from, Location target) {
        int dr = Integer.compare(target.getRow() - from.getRow(), 0);
        int dc = Integer.compare(target.getCol() - from.getCol(), 0);


        if (RandomUtil.chance(5)) {
            dr += RandomUtil.between(-1, 1);
            dc += RandomUtil.between(-1, 1);
            dr = Integer.compare(dr, 0);
            dc = Integer.compare(dc, 0);
        }
        return new int[]{dr, dc};
    }

    @FunctionalInterface
    private interface AnimalFilter {
        boolean accept(Animal animal);
    }
}
