package com.javarush.island.baksalyar.services;


import com.javarush.island.baksalyar.entity.Game;

/**
 * Service responsible for updating the game's user interface.
 * <p>
 * This service updates the game statistics and triggers a refresh of the
 * visual display. It is executed as part of the simulation loop managed by
 * {@link GameServiceProcessor} and typically runs once per simulation step.
 * </p>
 * <p>
 * The service performs two main operations:
 * Unlike other services (movement, eating, reproduction), this service does
 * not modify the game state; it only reads data and updates the presentation.
 * The actual UI updates are performed on the JavaFX Application
 * </p>
 */
public class ViewService implements Runnable {

    private final Game game;

    /**
     * Constructs a new ViewService for the specified game.
     *
     * @param game the game instance to update the view for
     */
    public ViewService(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.getGameMap().updateStatistics();
        game.getView().show();
    }
}

