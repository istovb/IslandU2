package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Horse extends Herbivore {
    @Override public String getSpeciesName() { return "Лошадь"; }
    @Override public char getSymbol() { return 'H'; }
    @Override public double getBaseWeight() { return 400; }
    @Override public int getMaxPerCell() { return 20; }
    @Override public int getSpeed() { return 4; }
    @Override public double getFoodRequired() { return 60; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_HORSE; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_HORSE; }
    @Override public Animal createOffspring() { return new Horse(); }
    @Override public int getEatProbability(String prey) { return 0; }
}