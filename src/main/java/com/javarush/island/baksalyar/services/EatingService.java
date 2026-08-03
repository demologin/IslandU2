package com.javarush.island.baksalyar.services;

import com.javarush.island.baksalyar.entity.Game;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import com.javarush.island.baksalyar.entity.organizm.animals.Animal;

import java.util.Arrays;


/**
 * Service responsible for executing the eating behavior for all organisms
 * in every cell of the game map.
 * <p>
 * This service iterates over all cells in the game grid and, for each cell,
 * applies the  method to every organism residing in
 * that cell. The operation is performed via
 * which ensures thread-safe access and respects the game's pause state.
 * </p>
 * <p>
 * The eating logic may involve locating food, consuming it, and updating the
 * organism's weight (energy). The specific behavior is defined by the implementation
 * of {@code eat(Cell)} in each organism subclass (e.g., {@link Animal}).
 * </p>
 * <p>
 * This service is intended to be run as part of the simulation loop managed by
 * {@link GameServiceProcessor}, and it is typically executed after movement
 * and before reproduction in each simulation tick.
 * </p>
 */
public class EatingService extends AbstractService {

    /**
     * Constructs a new EatingService for the specified game.
     *
     * @param game the game instance containing the map and state
     */
    public EatingService(Game game) {
        super(game);
    }

    /**
     * Executes the eating process for all cells in the game map.
     * <p>
     * The method streams over all rows and cells of the game map, and for each
     * cell invokes  with a consumer that
     * calls {@link Organism#eat(Cell)} on every organism in that cell.
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
                .forEach(cell -> processOneCell(cell, o -> o.eat(cell)));
    }
}
