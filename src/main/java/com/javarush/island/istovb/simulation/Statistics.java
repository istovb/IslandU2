package com.javarush.island.istovb.simulation;

import com.javarush.island.istovb.model.Island;
import com.javarush.island.istovb.model.Location;
import com.javarush.island.istovb.model.animal.Animal;
import com.javarush.island.istovb.model.plant.AbstractPlant;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Statistics {

    private final AtomicInteger grownPlants = new AtomicInteger(0);
    private final AtomicLong totalTicks = new AtomicLong(0);

    private final Map<String, Integer> animalsBySpecies = new ConcurrentHashMap<>();
    private final Map<String, Integer> plantsBySpecies = new ConcurrentHashMap<>();

      private volatile int fillPercent = 0;

    private static final String RESET      = "\u001B[0m";
    private static final String BOLD       = "\u001B[1m";
    private static final String DIM        = "\u001B[2m";
    private static final String FG_WHITE   = "\u001B[37m";
    private static final String FG_YELLOW  = "\u001B[33m";
    private static final String FG_MAGENTA = "\u001B[35m";
    private static final String FG_RED     = "\u001B[31m";
    private static final String FG_GRAY    = "\u001B[90m";

    public void addGrownPlants(int count) {
        grownPlants.addAndGet(count);
    }

    public long getTotalAnimals() {
        return animalsBySpecies.values().stream().mapToLong(Integer::longValue).sum();
    }

    public int getFillPercent() {
        return fillPercent;
    }

    /** Вызывается в конце каждого такта. */
    public void registerTick(int tick, Island island) {
        totalTicks.incrementAndGet();

        Map<String, Integer> animals = new LinkedHashMap<>();
        Map<String, Integer> plants = new LinkedHashMap<>();

        int occupiedCells = 0;
        int totalCells = 0;

        for (Location loc : island.allLocations()) {
            totalCells++;

            boolean occupied = !loc.getAnimals().isEmpty() || !loc.getPlants().isEmpty();
            if (occupied) occupiedCells++;

            for (Animal a : loc.getAnimals()) {
                animals.merge(a.getSpeciesName(), 1, Integer::sum);
            }
            for (AbstractPlant p : loc.getPlants()) {
                plants.merge(p.getName(), 1, Integer::sum);
            }
        }

        animalsBySpecies.clear();
        animalsBySpecies.putAll(animals);

        plantsBySpecies.clear();
        plantsBySpecies.putAll(plants);

        // % занятых клеток
        if (totalCells > 0) {
            fillPercent = (int) Math.round(occupiedCells * 100.0 / totalCells);
        } else {
            fillPercent = 0;
        }
    }

    public void printSummary(int tick) {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("  ШАГ " + tick);
        System.out.println("==========================================================");

        StringBuilder animalsLine = new StringBuilder();
        for (Map.Entry<String, Integer> e : animalsBySpecies.entrySet()) {
            animalsLine.append(symbolFor(e.getKey()))
                    .append("=").append(e.getValue()).append("  ");
        }
        System.out.println("  " + animalsLine.toString().trim());

        StringBuilder plantsLine = new StringBuilder();
        for (Map.Entry<String, Integer> e : plantsBySpecies.entrySet()) {
            plantsLine.append(symbolForPlant(e.getKey()))
                    .append("=").append(e.getValue()).append("  ");
        }
        System.out.println("  " + plantsLine.toString().trim());

        System.out.println("  Всего животных: " + getTotalAnimals());
        System.out.println("  Выросло растений (всего): " + grownPlants.get());

        printScale();

        System.out.println("==========================================================");
    }

    private void printScale() {
        System.out.print("  " + BOLD + "Прогресс: " + RESET);
        int[] percents = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

        for (int p : percents) {
            boolean active = p <= fillPercent;
            if (active) {
                System.out.print(colorForPercent(p) + p + "% " + RESET);
            } else {
                System.out.print(DIM + FG_GRAY + p + "% " + RESET);
            }
        }

        System.out.print("  " + BOLD + "→ " + fillPercent + "%" + RESET);
        System.out.println();
    }

    private String colorForPercent(int p) {
        if (p <= 30) return FG_WHITE;
        if (p <= 60) return FG_YELLOW;
        if (p <= 80) return FG_MAGENTA;
        return FG_RED;
    }

    private String symbolFor(String species) {
        switch (species) {
            case "Волк":      return "🐺Волк";
            case "Удав":      return "🐍Удав";
            case "Лиса":      return "🦊Лиса";
            case "Медведь":   return "🐻Медведь";
            case "Орёл":      return "🦅Орёл";
            case "Лошадь":    return "🐴Лошадь";
            case "Олень":     return "🦌Олень";
            case "Кролик":    return "🐰Кролик";
            case "Мышь":      return "🐭Мышь";
            case "Коза":      return "🐐Коза";
            case "Овца":      return "🐑Овца";
            case "Кабан":     return "🐗Кабан";
            case "Буйвол":    return "🐃Буйвол";
            case "Утка":      return "🦆Утка";
            case "Гусеница":  return "🐛Гусеница";
            case "Белка":     return " \uD83D\uDC3F\uFE0FБелка";
            default:          return "?";
        }
    }

    private String symbolForPlant(String name) {
        if ("Ягоды".equals(name)) return "🍓";
        if ("Трава".equals(name)) return "🌿";
        return "?";
    }
}