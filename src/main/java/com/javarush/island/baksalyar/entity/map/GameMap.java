package com.javarush.island.baksalyar.entity.map;


import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the game grid containing all {@link Cell} objects and maintains
 * statistics about the organisms living in them.
 * <p>
 * The map dimensions are determined by the global {@link Setting} configuration.
 * Statistics are updated by counting organisms in each cell and aggregating them
 * by organism type (using the icon as a key, then mapping to the actual
 * {@link Organism} prototype). The statistics are stored in a {@link LinkedHashMap}
 * to preserve insertion order.
 * </p>
 * <p>
 * This class is thread-safe regarding statistics updates, as it reads from
 * cell residents which are protected by their own locks.
 * </p>
 */
@Getter
public class GameMap {

    private final Cell[][] cells;
    private final Map<Organism, Long> statistics;

    /**
     * Constructs a new GameMap with dimensions obtained from {@link Setting#SETTING}.
     * Statistics are initialized as an empty {@link LinkedHashMap}.
     */
    public GameMap() {
        this.cells = new Cell[Setting.SETTING.getRows()][Setting.SETTING.getCols()];
        statistics = new LinkedHashMap<>();
    }

    /**
     * Returns the number of rows in the game map.
     *
     * @return the row count
     */
    public int getRows() {
        return cells.length;
    }

    /**
     * Returns the number of columns in the game map.
     *
     * @return the column count
     */
    public int getCols() {
        return cells[0].length;
    }

    /**
     * Updates the statistics map by counting all organisms currently present in every cell.
     * <p>
     * The method iterates over all cells, retrieves their residents, and accumulates
     * the total count per organism icon. Then it maps these counts back to the
     * corresponding {@link Organism} prototypes (using the icon to match).
     * Only organisms with a positive count are kept in the {@code statistics} map.
     * </p>
     * <p>
     * This method should be called periodically (e.g., each simulation tick) to
     * keep the statistics up-to-date.
     * </p>
     */
    public void updateStatistics() {
        Map<String, Double> rawStatistics = new HashMap<>();
        Cell[][] cells = getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                Residents residents = cell.getResidents();
                if (Objects.nonNull(residents)) {
                    residents.values().stream()
                            .filter(organisms -> !organisms.isEmpty())
                            .forEach(organisms -> {
                                String icon = organisms.getIcon();
                                double count = organisms.calculateSize();
                                rawStatistics.put(icon, rawStatistics.getOrDefault(icon, 0D) + count);
                            });
                }
            }
        }
        for (Organism organism : Setting.PROTOTYPES) {
            long count = (long) Math.ceil(rawStatistics.getOrDefault(organism.getIcon(), 0d));
            if (count > 0) {
                statistics.put(organism, count);
            } else {
                statistics.remove(organism);
            }
        }
    }
}
