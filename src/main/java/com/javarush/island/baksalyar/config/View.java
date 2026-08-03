package com.javarush.island.baksalyar.config;
/**
 * Contract for the game's user interface.
 * <p>
 * the game map (organisms on the grid) and the statistics (population counts).
 * All methods are intended to be called from the simulation loop to keep the
 * UI in sync with the game state.
 * </p>
 */
public interface View {
    /**
     * Performs a full UI update, typically by calling both
     * {@link #showMap()} and {@link #showStatistics()}.
     * Implementations may skip updates if the game is paused.
     */
    void show();
    /**
     * Updates the statistics display (e.g., population counts per organism type).
     */
    void showStatistics();
    /**
     * Updates the visual representation of the game grid,
     * showing organism icons in each cell.
     */
    void showMap();
}
