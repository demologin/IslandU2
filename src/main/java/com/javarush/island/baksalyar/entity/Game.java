package com.javarush.island.baksalyar.entity;

import com.javarush.island.baksalyar.config.Initialization;
import com.javarush.island.baksalyar.config.View;
import com.javarush.island.baksalyar.entity.map.GameMap;
import lombok.Getter;
import lombok.Setter;


/**
 * The central controller class for the game logic and state management.
 * <p>
 * This class holds the game map ({@link GameMap}), the entity factory used
 * for creating organisms ({@link Initialization}), and a reference to the
 * UI view ({@link View}) for updating the display. It manages the game's
 * lifecycle state, including whether the game is currently paused or finished.
 * </p>
 * <p>
 * The game is typically created during the initialization phase (see
 * {@link GameController#gameStarted()}) and is passed to the service processor
 * ({@link GameServiceProcessor}) that executes the simulation steps (movement,
 * eating, reproduction, etc.) in a loop. The {@link View} interface allows
 * the game to request UI updates without depending on concrete JavaFX components.
 * </p>
 * <p>
 * The pause and finished flags control the execution flow:
 * <ul>
 *   <li>{@code isPause} – when true, the simulation services should skip updates
 *       (used for the "pause" button).</li>
 *   <li>{@code isFinished} – when true, the simulation should terminate completely
 *       (used when navigating back to the home screen or closing the window).</li>
 * </ul>
 * </p>
 *
 * @see GameMap
 * @see Initialization
 * @see View
 * @see GameServiceProcessor
 * @since 1.0
 */
@Getter
@Setter
public class Game {

    private final GameMap gameMap;
    private final Initialization entityFactory;
    private View view;
    private boolean isFinished = false;
    private boolean isPause = false;

    /**
     * Constructs a new Game instance with the specified map, factory, and view.
     *
     * @param gameMap        the game map containing the grid and organisms
     * @param entityFactory  the factory used for organism creation and cell filling
     * @param view           the view interface for updating the user interface
     */
    public Game(GameMap gameMap, Initialization entityFactory, View view) {
        this.gameMap = gameMap;
        this.entityFactory = entityFactory;
        this.view = view;
    }
}
