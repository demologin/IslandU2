package com.javarush.island.baksalyar.services;


import com.javarush.island.baksalyar.controllers.GameController;
import com.javarush.island.baksalyar.entity.Game;
import com.javarush.island.baksalyar.entity.interfaces.Movement;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import com.javarush.island.baksalyar.entity.organizm.animals.Animal;

import java.util.Arrays;
import java.util.function.Consumer;


/**
 * Service responsible for executing the movement behavior for all organisms
 * in every cell of the game map.
 * <p>
 * This service iterates over all cells in the game grid and, for each cell,
 * applies the {@link Movement#move(Cell)} method to every organism residing in
 * that cell. The operation is performed via {@link AbstractService#processOneCell(Cell, Consumer)}
 * which ensures thread-safe access and respects the game's pause state.
 * </p>
 * <p>
 * The movement logic typically involves selecting a neighboring cell (from the
 * {@link Cell#getNextCell()} list) and transferring the organism there, provided
 * the target cell has available capacity. The specific behavior is defined by the
 * implementation of {@code move(Cell)} in each organism subclass (e.g., {@link Animal}).
 * </p>
 * <p>
 * This service is intended to be run as part of the simulation loop managed by
 * {@link GameServiceProcessor}. In the typical execution order (defined in
 * {@link GameController}), it runs after the view update and
 * before eating and reproduction, ensuring that organisms move to new cells
 * before consuming food or spawning offspring.
 * </p>
 */
public class MovingService extends AbstractService {

    /**
     * Constructs a new MovingService for the specified game.
     *
     * @param game the game instance containing the map and state
     */
    public MovingService(Game game) {
        super(game);
    }

    /**
     * Executes the movement process for all cells in the game map.
     * <p>
     * The method streams over all rows and cells of the game map, and for each
     * cell invokes {@link #processOneCell(Cell, Consumer)} with a consumer that
     * calls {@link Organism#move(Cell)} on every organism in that cell.
     * </p>
     * <p>
     * If the game is paused, the processing is skipped entirely (due to the check
     * inside {@code processOneCell}). The operation is thread-safe as each cell
     * is locked during the read of its residents.
     * </p>
     */
    @Override
    public void run() {
        Arrays.stream(game.getGameMap().getCells()) // rows
                .flatMap(Arrays::stream)             // cells
                .forEach(cell -> processOneCell(cell, o -> o.move(cell)));
    }
}

