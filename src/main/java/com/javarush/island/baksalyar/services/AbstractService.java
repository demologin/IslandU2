package com.javarush.island.baksalyar.services;

import com.javarush.island.baksalyar.entity.Game;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import com.javarush.island.baksalyar.entity.organizm.Organisms;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Abstract base class for all game services that run asynchronously on the game map.
 * <p>
 * This class implements {@link Runnable} and provides common functionality for
 * processing organisms within a cell. It encapsulates the thread-safe reading of
 * all organisms in a cell using a lock, and applies a given action to each organism.
 * The {@link #processOneCell(Cell, Consumer)} method is the core utility for
 * subclasses to perform operations on all organisms in a cell without manually
 * handling locking and iteration.
 * </p>
 * <p>
 * Subclasses typically represent specific simulation steps such as movement,
 * feeding, reproduction, or view updates. They override {@link #run()} to define
 * their main loop, iterating over all cells and applying the appropriate action
 * via {@code processOneCell}.
 * </p>
 * <p>
 * The class respects the game's pause state: if {@link Game#isPause()} returns
 * {@code true}, the processing is skipped to avoid modifying the game state
 * during a pause.
 * </p>
 */
abstract class AbstractService implements Runnable {

    /** The game instance containing the map and state. */
    protected final Game game;

    /**
     * Constructs a new AbstractService with the given game.
     *
     * @param game the game instance to operate on
     */
    protected AbstractService(Game game) {
        this.game = game;
    }

    /**
     * Processes all organisms residing in the specified cell by applying the
     * given action to each organism.
     * <p>
     * The method first checks if the game is paused; if so, it returns immediately
     * without performing any action. Otherwise, it safely reads all organisms from
     * the cell using a lock (to ensure thread-safe access), and then applies the
     * provided {@link Consumer} to each organism.
     * </p>
     * <p>
     * The action may be any operation, such as moving, eating, reproducing, or
     * updating view data. Subclasses can call this method for each cell in their
     * {@code run()} implementation.
     * </p>
     *
     * @param cell   the cell whose organisms are to be processed
     * @param action the operation to perform on each organism
     */
    protected void processOneCell(Cell cell, Consumer<Organism> action) {
        if (game.isPause()) {
            return;
        }
        // Obtain all organisms in the cell (thread-safe)
        Set<Organism> all = safeReadAll(cell);
        // Apply the action to each organism
        all.forEach(action);
    }

    /**
     * Safely reads all organisms present in the given cell, acquiring the cell's
     * lock to ensure that the read is consistent and thread-safe.
     *
     * @param cell the cell to read organisms from
     * @return an immutable set of all organisms in the cell (at the time of reading)
     */
    private Set<Organism> safeReadAll(Cell cell) {
        cell.getLock().lock();
        try {
            return cell.getResidents()
                    .values()
                    .stream()
                    .flatMap(Organisms::stream)
                    .collect(Collectors.toSet());
        } finally {
            cell.getLock().unlock();
        }
    }
}
