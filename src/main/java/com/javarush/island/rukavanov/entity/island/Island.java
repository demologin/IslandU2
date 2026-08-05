package com.javarush.island.rukavanov.entity.island;

import com.javarush.island.rukavanov.config.Settings;

public class Island {
    private final Location[][] locations;

    public Island() {

        locations = new Location[Settings.ISLAND_HEIGHT][Settings.ISLAND_WIDTH];
        int id = 0;
        for (int y = 0; y < Settings.ISLAND_HEIGHT; y++) {
            for (int x = 0; x < Settings.ISLAND_WIDTH; x++) {
                locations[y][x] = new Location(id++, x, y);
            }

        }

    }

    public Location get(int x, int y) {
        return locations[y][x];
    }

    public int getWidth() {
        return Settings.ISLAND_WIDTH;
    }

    public int getHeight() {
        return Settings.ISLAND_HEIGHT;
    }

}
