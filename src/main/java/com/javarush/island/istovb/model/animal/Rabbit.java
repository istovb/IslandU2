package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Rabbit extends Herbivore {
    @Override public String getSpeciesName() { return "Кролик"; }
    @Override public char getSymbol() { return 'R'; }
    @Override public double getBaseWeight() { return 2; }
    @Override public int getMaxPerCell() { return 150; }
    @Override public int getSpeed() { return 2; }
    @Override public double getFoodRequired() { return 0.45; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_RABBIT; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_RABBIT; }
    @Override public Animal createOffspring() { return new Rabbit(); }
    @Override public int getEatProbability(String prey) { return 0; }
}