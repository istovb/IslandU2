package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Goat extends Herbivore {
    @Override public String getSpeciesName() { return "Коза"; }
    @Override public char getSymbol() { return 'G'; }
    @Override public double getBaseWeight() { return 60; }
    @Override public int getMaxPerCell() { return 140; }
    @Override public int getSpeed() { return 3; }
    @Override public double getFoodRequired() { return 10; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_GOAT; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_GOAT; }
    @Override public Animal createOffspring() { return new Goat(); }
    @Override public int getEatProbability(String prey) { return 0; }
}