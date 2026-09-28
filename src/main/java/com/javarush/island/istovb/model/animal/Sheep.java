package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Sheep extends Herbivore {
    @Override public String getSpeciesName() { return "Овца"; }
    @Override public char getSymbol() { return 's'; }
    @Override public double getBaseWeight() { return 70; }
    @Override public int getMaxPerCell() { return 140; }
    @Override public int getSpeed() { return 3; }
    @Override public double getFoodRequired() { return 15; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_SHEEP; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_SHEEP; }
    @Override public Animal createOffspring() { return new Sheep(); }
    @Override public int getEatProbability(String prey) { return 0; }
}