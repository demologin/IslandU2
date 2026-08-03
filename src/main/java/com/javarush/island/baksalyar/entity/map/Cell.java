package com.javarush.island.baksalyar.entity.map;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a single cell in the game grid, containing a collection of organisms
 * ({@link Residents}) and a reference to all neighboring cells.
 * <p>
 * Each cell maintains a thread-safe lock ({@link ReentrantLock}) to synchronize
 * concurrent access to its residents, as the simulation may update cells from
 * multiple threads (e.g., moving, eating, reproducing).
 * </p>
 * <p>
 * The {@link #nextCell} list stores all adjacent cells in the 8 cardinal directions
 * (including diagonals), but does not include the cell itself. This structure
 * enables organisms to move to neighboring cells during the simulation.
 * </p>
 */
@Getter
public class Cell {

    private final List<Cell> nextCell = new ArrayList<>();
    private final Lock lock = new ReentrantLock(true);
    private final Residents residents = new Residents();

    /**
     * Updates the {@link #nextCell} list with all neighboring cells based on the
     * provided game map and the cell's position (row, col).
     * <p>
     * The method checks all 8 directions (up, down, left, right, and diagonals)
     * within the grid boundaries and adds the corresponding {@link Cell} references
     * to the list. The order of neighbors is not guaranteed and may vary.
     * </p>
     *
     * @param map the game map containing the grid of cells
     * @param row the row index of this cell
     * @param col the column index of this cell
     */
    public void updateNextCell(GameMap map, int row, int col) {
        Cell[][] cells = map.getCells();
        int colMax = map.getCols() - 1;
        if (row > 0) {
            nextCell.add(cells[row - 1][col]);
            if (col + 1 <= colMax) { nextCell.add(cells[row - 1][col + 1]); }
            if (col - 1 >= 0) { nextCell.add(cells[row - 1][col - 1]); }
        }
        if (col > 0) { nextCell.add(cells[row][col - 1]); }
        if (row < map.getRows() - 1) {
            nextCell.add(cells[row + 1][col]);
            if (col + 1 <= colMax) { nextCell.add(cells[row + 1][col + 1]); }
            if (col - 1 >= 0) { nextCell.add(cells[row + 1][col - 1]); }
        }
        if (col < map.getCols() - 1) { nextCell.add(cells[row][col + 1]); }
    }
}
