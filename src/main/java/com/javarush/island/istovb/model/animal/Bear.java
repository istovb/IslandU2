package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Bear extends Predator {
    @Override public String getSpeciesName() { return "Медведь"; }
    @Override public char getSymbol() { return 'b'; }
    @Override public double getBaseWeight() { return 500; }
    @Override public int getMaxPerCell() { return 5; }
    @Override public int getSpeed() { return 2; }
    @Override public double getFoodRequired() { return 80; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_BEAR; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_BEAR; }
    @Override public Animal createOffspring() { return new Bear(); }

    @Override public boolean canSwim() { return true; }

}
