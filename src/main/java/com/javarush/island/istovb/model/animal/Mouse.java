package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Mouse extends Herbivore {
    @Override public String getSpeciesName() { return "Мышь"; }
    @Override public char getSymbol() { return 'm'; }
    @Override public double getBaseWeight() { return 0.05; }
    @Override public int getMaxPerCell() { return 500; }
    @Override public int getSpeed() { return 1; }
    @Override public double getFoodRequired() { return 0.01; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_MOUSE; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_MOUSE; }
    @Override public Animal createOffspring() { return new Mouse(); }

    @Override
    public int getEatProbability(String prey) {
        return "Гусеница".equals(prey) ? 90 : 0;
    }

    @Override
    protected void eatAnimals() {
        tryEatAnimal(Caterpillar.class, 90);
    }
}