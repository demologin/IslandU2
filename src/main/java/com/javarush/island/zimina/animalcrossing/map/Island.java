package com.javarush.island.zimina.animalcrossing.map;

import com.javarush.island.zimina.animalcrossing.config.SimulationConfig;

/**
 * Остров — сетка клеток фиксированного размера.
 * Хранит все локации острова и предоставляет доступ к ним.
 */
public class Island {
    private final Location[][] grid;

    public Island() {
        grid = new Location[SimulationConfig.ISLAND_WIDTH][SimulationConfig.ISLAND_HEIGHT];
        for (int x = 0; x < SimulationConfig.ISLAND_WIDTH; x++) {
            for (int y = 0; y < SimulationConfig.ISLAND_HEIGHT; y++) {
                grid[x][y] = new Location();
            }
        }
    }

    /**
     * Возвращает клетку острова по указанным координатам.
     *
     * @param x координата по горизонтали
     * @param y координата по вертикали
     * @return клетка острова в данной позиции
     */
    public Location getLocation(int x, int y) {
        return grid[x][y];
    }
}
