package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Squirrel extends Herbivore {
    @Override public String getSpeciesName() { return "Белка"; }
    @Override public char getSymbol() { return 'q'; }
    @Override public double getBaseWeight() { return 0.5; }
    @Override public int getMaxPerCell() { return 100; }
    @Override public int getSpeed() { return 3; }
    @Override public double getFoodRequired() { return 0.2; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_SQUIRREL; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_SQUIRREL; }
    @Override public Animal createOffspring() { return new Squirrel(); }

    @Override
    public int getEatProbability(String prey) {
        return "Гусеница".equals(prey) ? 80 : 0;
    }
    @Override
    protected void eatAnimals() {
        tryEatAnimal(Caterpillar.class, 80);
    }
}
