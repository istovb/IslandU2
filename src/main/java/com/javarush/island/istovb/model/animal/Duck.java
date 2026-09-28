package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Duck extends Herbivore {
    @Override public String getSpeciesName() { return "Утка"; }
    @Override public char getSymbol() { return 'd'; }
    @Override public double getBaseWeight() { return 1; }
    @Override public int getMaxPerCell() { return 200; }
    @Override public int getSpeed() { return 4; }
    @Override public double getFoodRequired() { return 0.15; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_DUCK; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_DUCK; }
    @Override public Animal createOffspring() { return new Duck(); }

    @Override public boolean canSwim() { return true; }

    @Override
    protected void eatAnimals() {
        tryEatAnimal(Caterpillar.class, 90);
    }
}
