package com.javarush.island.baksalyar.services;

import com.javarush.island.baksalyar.entity.Game;
import com.javarush.island.baksalyar.entity.interfaces.Reproducible;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.organizm.Organism;

import java.util.Arrays;

/**
 * Service responsible for executing the reproduction behavior for all organisms
 * in every cell of the game map.
 * <p>
 * This service iterates over all cells in the game grid and, for each cell,
 * applies the {@link Reproducible#spawn(Cell)} method to every organism residing in
 * </p>
 * <p>
 * The reproduction logic typically involves checking whether the organism has sufficient
 * weight (energy) to reproduce, ensuring that the cell has not exceeded its maximum
 * capacity and then creating a new offspring
 * (clone) added to the same cell. The specific behavior is defined by the implementation
 * </p>
 * <p>
 * This service is intended to be run as part of the simulation loop managed by
 * {@link GameServiceProcessor}. In the typical execution order (defined in
 * {@linkGameController#gameStarted()}), it runs after movement and eating,
 * allowing organisms to have moved to new locations and replenished energy before
 * reproducing.
 * </p>
 */
public class ReproducingService extends AbstractService {

    /**
     * Constructs a new ReproducingService for the specified game.
     *
     * @param game the game instance containing the map and state
     */
    public ReproducingService(Game game) {
        super(game);
    }

    /**
     * Executes the reproduction process for all cells in the game map.
     * <p>
     * The method streams over all rows and cells of the game map, and for each
     * cell invokes  with a consumer that
     * calls {@link Organism#spawn(Cell)} on every organism in that cell.
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
                .forEach(cell -> processOneCell(cell, o -> o.spawn(cell)));
    }
}
