package com.javarush.island.istovb.model;

import com.javarush.island.istovb.model.animal.Animal;
import com.javarush.island.istovb.model.plant.AbstractPlant;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {

    private final int row;
    private final int col;
    private final Terrain terrain;

    private final List<AbstractPlant> plants = new CopyOnWriteArrayList<>();
    private final List<Animal> animals = new CopyOnWriteArrayList<>();

    public Location(int row, int col, Terrain terrain) {
        this.row = row;
        this.col = col;
        this.terrain = terrain;
    }
    public int getRow() { return row; }

    public int getCol() { return col; }

    public Terrain getTerrain() { return terrain; }

    public List<AbstractPlant> getPlants() { return plants; }

    public List<Animal> getAnimals() { return animals; }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

      public long countAnimals(Class<? extends Animal> type) {
        return animals.stream().filter(type::isInstance).count();
    }

    public long countPlants(Class<? extends AbstractPlant> type) {
        return plants.stream().filter(type::isInstance).count();
    }
}