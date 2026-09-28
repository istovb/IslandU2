package com.javarush.island.istovb.model;

import com.javarush.island.istovb.config.SimulationConfig;

import java.util.ArrayList;
import java.util.List;

public class Island {

    private final int rows;
    private final int cols;
    private final Location[][] grid;

    public Island(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Location[rows][cols];
        initLocations();
    }


    private void initLocations() {
        // Заранее рассчитываем центральную координату реки по конфигу
        int riverCenter = cols / 2 + SimulationConfig.RIVER_COLUMN_OFFSET;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Terrain terrain = Terrain.PLAIN;

                // Проверяем, попадает ли текущая колонка под границы реки
                if (SimulationConfig.RIVER_ENABLED) {
                    if (c >= riverCenter && c < (riverCenter + SimulationConfig.RIVER_WIDTH)) {
                        terrain = Terrain.RIVER;
                    }
                }

                // Создаем локацию один раз и на всю симуляцию
                grid[r][c] = new Location(r, c, terrain);
            }
        }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public boolean inBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public Location getLocation(int row, int col) {
        return grid[row][col];
    }

    public List<Location> allLocations() {
        List<Location> list = new ArrayList<>(rows * cols);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                list.add(grid[r][c]);
            }
        }
        return list;
    }
}
