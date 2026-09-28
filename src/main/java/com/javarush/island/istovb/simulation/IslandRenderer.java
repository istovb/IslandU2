package com.javarush.island.istovb.simulation;

import com.javarush.island.istovb.model.Island;
import com.javarush.island.istovb.model.Location;
import com.javarush.island.istovb.model.Terrain;
import com.javarush.island.istovb.model.animal.Animal;
import com.javarush.island.istovb.model.animal.Predator;
import com.javarush.island.istovb.model.plant.AbstractPlant;

public class IslandRenderer {
    private final Island island;
    private static final int CELL_WIDTH = 2;

    private static final String RESET      = "\u001B[0m";
    private static final String BOLD       = "\u001B[1m";
    private static final String FG_RED     = "\u001B[31m";
    private static final String FG_YELLOW  = "\u001B[33m";
    private static final String FG_GREEN   = "\u001B[32m";
    private static final String FG_CYAN    = "\u001B[36m";
    private static final String FG_MAGENTA = "\u001B[35m";

    public IslandRenderer(Island island) {
        this.island = island;
    }

    public void render(int tick) {
        StringBuilder sb = new StringBuilder();
        int cols = island.getCols();
        int rows = island.getRows();
        sb.append('┌');
        for (int c = 0; c < cols; c++) {
            sb.append(repeat('─', CELL_WIDTH));
            sb.append(c < cols - 1 ? '┬' : '┐');
        }
        sb.append('\n');

        for (int r = 0; r < rows; r++) {
            sb.append('│');
            for (int c = 0; c < cols; c++) {
                sb.append(renderCell(island.getLocation(r, c)));
                sb.append('│');
            }
            sb.append('\n');

            if (r < rows - 1) {
                sb.append('├');
                for (int c = 0; c < cols; c++) {
                    sb.append(repeat('─', CELL_WIDTH));
                    sb.append(c < cols - 1 ? '┼' : '┤');
                }
                sb.append('\n');
            }
        }

        sb.append('└');
        for (int c = 0; c < cols; c++) {
            sb.append(repeat('─', CELL_WIDTH));
            sb.append(c < cols - 1 ? '┴' : '┘');
        }
        sb.append('\n');

        long totalAnimals = 0;
        if (island.allLocations() != null) {
            for (Location loc : island.allLocations()) {
                totalAnimals += loc.getAnimals().size();
            }
        }

        sb.append(BOLD).append("Шаг симуляции: ").append(RESET).append(tick).append("\n");
        sb.append("Животных на карте: ").append(totalAnimals).append("\n");
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print(sb.toString());
    }


    private String renderCell(Location location) {
        if (location == null) {
            return repeat(' ', CELL_WIDTH);
        }

        String icon = " ";
        String color = RESET;


        if (location.getAnimals() != null && !location.getAnimals().isEmpty()) {
            Animal animal = location.getAnimals().get(0);
            icon = getAnimalAbbreviation(animal.getSpeciesName());
            color = (animal instanceof Predator) ? FG_RED : FG_YELLOW;
        } else if (location.getPlants() != null && !location.getPlants().isEmpty()) {
            AbstractPlant plant = location.getPlants().get(0);
            icon = getPlantAbbreviation(plant.getName());
            // ИСПРАВЛЕНО: Проверяем русскую "Я", чтобы ягоды правильно подсвечивались пурпурным цветом
            color = "Я".equals(icon) ? FG_MAGENTA : FG_GREEN;
        } else if (location.getTerrain() == Terrain.RIVER) {
            icon = String.valueOf(Terrain.RIVER.getSymbol()); // Подставляем '~'
            color = FG_CYAN;
        }
        return color + padRight(icon, CELL_WIDTH) + RESET;
    }


    private String padRight(String str, int targetWidth) {
        if (str == null) str = "";
        int paddingNeeded = targetWidth - str.length();
        if (paddingNeeded <= 0) {
            return str;
        }
        return str + repeat(' ', paddingNeeded);
    }
    private String repeat(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    private String getAnimalAbbreviation(String species) {
        if (species == null) return " ";
        switch (species) {
                 case "Волк":      return "W";
            case "Удав":      return "B";
            case "Лиса":      return "F";
            case "Медведь":   return "b";
            case "Орёл":      return "E";
            case "Лошадь":    return "H";
            case "Олень":     return "D";
            case "Кролик":    return "R";
            case "Мышь":      return "m";
            case "Коза":      return "G";
            case "Овца":      return "s";
            case "Кабан":     return "V";
            case "Буйвол":    return "U";
            case "Утка":      return "d";
            case "Гусеница":  return "c";
            case "Белка":     return "q";
            default:          return "?";
        }
    }
    private String getPlantAbbreviation(String name) {
        if ("Berry".equals(name) || "Ягоды".equals(name)) return "Я"; // Berry (пурпурный цвет)
        if ("Grass".equals(name) || "Трава".equals(name)) return "Т"; // Grass (зеленый цвет)
        return "?";
    }
}
