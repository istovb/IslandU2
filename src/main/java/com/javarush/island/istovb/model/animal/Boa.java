package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Boa extends Predator {
    @Override public String getSpeciesName() { return "Удав"; }
    @Override public char getSymbol() { return 'B'; }
    @Override public double getBaseWeight() { return 15; }
    @Override public int getMaxPerCell() { return 30; }
    @Override public int getSpeed() { return 1; }
    @Override public double getFoodRequired() { return 3; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_BOA; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_BOA; }
    @Override public Animal createOffspring() { return new Boa(); }


}
