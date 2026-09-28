package com.javarush.island.istovb.model.animal;

import com.javarush.island.istovb.model.Island;
import com.javarush.island.istovb.model.Location;
import com.javarush.island.istovb.model.plant.AbstractPlant;
import com.javarush.island.istovb.model.plant.Berry;
import com.javarush.island.istovb.util.RandomUtil;

import java.util.List;

public abstract class Herbivore extends Animal {
    @Override
    public void eat(Island island) {
        if (!alive || location == null) return;

        eatPlants();
        // Если травы не хватило, а животное всеядное (мышь, белка, кабан, утка), оно пытается съесть насекомых
        if (eatenFood < getFoodRequired()) {
            eatAnimals();
        }
        if (eatenFood >= getFoodRequired()) {
            ticksWithoutFood = 0;
        }
    }

    protected void eatPlants() {
        Location here = this.location;
        List<AbstractPlant> plants = here.getPlants();

        while (!plants.isEmpty() && eatenFood < getFoodRequired()) {
            AbstractPlant plant = null;
            try {
                if (!plants.isEmpty()) {
                    plant = plants.remove(0);
                }
            } catch (IndexOutOfBoundsException e) {
                    break;
            }

            if (plant != null) {
                eatenFood += (plant instanceof Berry) ? 0.5 : 1.0;
            }
        }
    }

    protected void eatAnimals() { }
    protected boolean tryEatAnimal(Class<? extends Animal> preyClass, int percent) {
        Location here = this.location;
        if (here == null) return false;

        List<Animal> neighbors = here.getAnimals();
        for (int i = 0; i < neighbors.size(); i++) {
            if (i >= neighbors.size()) break;

            Animal prey = neighbors.get(i);
            if (prey == this || !prey.isAlive() || !preyClass.isInstance(prey)) {
                continue;
            }
            if (RandomUtil.chance(percent)) {
                eatenFood += prey.getWeight();
                prey.kill();
                here.removeAnimal(prey);
                return true;
            }
        }
        return false;
    }
    @Override
    public int getEatProbability(String prey) {
         return 0;
    }
}
