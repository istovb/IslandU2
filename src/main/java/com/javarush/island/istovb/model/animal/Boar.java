package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.config.SimulationConfig;

public class Boar extends Herbivore {
    @Override public String getSpeciesName() { return "Кабан"; }
    @Override public char getSymbol() { return 'V'; } //
    @Override public double getBaseWeight() { return 400; }
    @Override public int getMaxPerCell() { return 50; }
    @Override public int getSpeed() { return 2; }
    @Override public double getFoodRequired() { return 50; }
    @Override public int getMoveChancePercent() { return SimulationConfig.MOVE_CHANCE_BOAR; }
    @Override public int getOffspringCount() { return SimulationConfig.OFFSPRING_BOAR; }
    @Override public Animal createOffspring() { return new Boar(); }

    @Override
    protected void eatAnimals() {
        // Кабан всеядный: пытается съесть мышь, а если не вышло — гусеницу
        if (tryEatAnimal(Mouse.class, 50)) return;
        tryEatAnimal(Caterpillar.class, 90);
    }
}
