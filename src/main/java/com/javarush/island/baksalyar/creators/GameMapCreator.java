package com.javarush.island.baksalyar.creators;


import com.javarush.island.baksalyar.config.Initialization;
import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.map.GameMap;

/**
 * Responsible for creating and populating a game map with organisms.
 * <p>
 * This class uses an {@link Initialization} factory to fill individual cells
 * with organisms based on a given probability. After filling all cells, it
 * updates each cell's neighboring cell references via
 * {@link Cell#updateNextCell(GameMap, int, int)} to enable movement and
 * interaction logic during the simulation.
 * </p>
 * <p>
 * The map dimensions are determined by the current settings
 * ({@link Setting#getRows()} and {@link Setting#getCols()}).
 * </p>
 */
public class GameMapCreator {

    private final Initialization entityFactory;

    /**
     * Constructs a new GameMapCreator with the specified entity factory.
     *
     * @param entityFactory the factory responsible for filling cells with organisms
     */
    public GameMapCreator(Initialization entityFactory) {
        this.entityFactory = entityFactory;
    }

    /**
     * Creates a new {@link GameMap} with all cells populated.
     * <p>
     * For each cell, the {@link Initialization#fill(Cell, double)} method is called
     * with the provided probability to add organisms. After all cells are filled,
     * each cell's {@link Cell#updateNextCell(GameMap, int, int)} method is invoked
     * to establish connections to neighboring cells for use in the simulation.
     * </p>
     *
     * @param percentProbably the probability (0.0–1.0) that a particular organism
     *                        type will appear in a cell during filling
     * @return a fully populated {@link GameMap} instance
     */
    public GameMap createRandomFilledGameMap(double percentProbably) {
        GameMap gameMap = new GameMap();
        Cell[][] cells = gameMap.getCells();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = new Cell();
                entityFactory.fill(cell, percentProbably);
                cells[row][col] = cell;
            }
        }
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[row][col].updateNextCell(gameMap, row, col);
            }
        }

        return gameMap;
    }
}
