package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Eagle extends Predator {
    @Override public String getSpeciesName() { return "Орёл"; }
    @Override public char getSymbol() { return 'E'; }
    @Override public double getBaseWeight() { return 6; }
    @Override public int getMaxPerCell() { return 20; }
    @Override public int getSpeed() { return 3; }
    @Override public double getFoodRequired() { return 1; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_EAGLE; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_EAGLE; }
    @Override public Animal createOffspring() { return new Eagle(); }

    @Override public boolean canSwim() { return true; }

}
