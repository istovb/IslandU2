package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Caterpillar extends Herbivore {
    @Override public String getSpeciesName() { return "Гусеница"; }
    @Override public char getSymbol() { return 'c'; }
    @Override public double getBaseWeight() { return 0.01; }
    @Override public int getMaxPerCell() { return 1000; }
    @Override public int getSpeed() { return 0; }
    @Override public double getFoodRequired() { return 0; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_CATERPILLAR; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_CATERPILLAR; }
    @Override public Animal createOffspring() { return new Caterpillar(); }
}
