package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Fox extends Predator {
    @Override public String getSpeciesName() { return "Лиса"; }
    @Override public char getSymbol() { return 'F'; }
    @Override public double getBaseWeight() { return 8; }
    @Override public int getMaxPerCell() { return 30; }
    @Override public int getSpeed() { return 2; }
    @Override public double getFoodRequired() { return 2; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_FOX; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_FOX; }
    @Override public Animal createOffspring() { return new Fox(); }

}
