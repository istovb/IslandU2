package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Buffalo extends Herbivore {
    @Override public String getSpeciesName() { return "Буйвол"; }
    @Override public char getSymbol() { return 'U'; }
    @Override public double getBaseWeight() { return 700; }
    @Override public int getMaxPerCell() { return 10; }
    @Override public int getSpeed() { return 3; }
    @Override public double getFoodRequired() { return 100; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_BUFFALO; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_BUFFALO; }
    @Override public Animal createOffspring() { return new Buffalo(); }

    /** Буйволы отлично плавают и могут пересекать реки */
    @Override public boolean canSwim() { return true; }
}
