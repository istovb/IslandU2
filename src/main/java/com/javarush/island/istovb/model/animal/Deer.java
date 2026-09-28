package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Deer extends Herbivore {
    @Override public String getSpeciesName() { return "Олень"; }
    @Override public char getSymbol() { return 'D'; }
    @Override public double getBaseWeight() { return 300; }
    @Override public int getMaxPerCell() { return 20; }
    @Override public int getSpeed() { return 4; }
    @Override public double getFoodRequired() { return 50; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_DEER; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_DEER; }
    @Override public Animal createOffspring() { return new Deer(); }
    @Override public int getEatProbability(String prey) { return 0; }
}