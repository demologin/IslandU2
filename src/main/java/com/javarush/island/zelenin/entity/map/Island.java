package com.javarush.island.zelenin.entity.map;

import java.util.ArrayList;
import java.util.List;

public class Island {
    private final Location[][] locations;
    private final List<Location> cachedLocations;

    public Island(int rows, int cols) {
        locations = new Location[rows][cols];
        List<Location> temp = new ArrayList<>();

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                locations[row][col] = new Location(row, col);
                temp.add(locations[row][col]);
            }
        }
        cachedLocations = List.copyOf(temp);
    }

    public int getRows() {
        return locations.length;
    }

    public int getCols() {
        return locations[0].length;
    }

    public Location getLocation(int row, int col) {
        return locations[row][col];
    }

    //TODO remove later?
    public Location[][] getGrid() {
        return locations;
    }

    public List<Location> getLocations() {
        return cachedLocations;
    }

    public List<Location> getReachableFrom(Location location, int radius) {
        List<Location> locations = new ArrayList<>();
        int minRow = Math.max(location.getRow() - radius, 0);
        int minCol = Math.max(location.getCol() - radius, 0);
        int maxRow = Math.min(location.getRow() + radius, getRows() - 1);
        int maxCol = Math.min(location.getCol() + radius, getCols() - 1);

        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                int rowDistance = Math.abs(row - location.getRow());
                int colDistance = Math.abs(col - location.getCol());
                if (rowDistance + colDistance <= radius) {
                    locations.add(this.locations[row][col]);
                }
            }
        }
        return locations;
    }
}
