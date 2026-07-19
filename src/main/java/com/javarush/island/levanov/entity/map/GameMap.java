package com.javarush.island.levanov.entity.map;

import com.javarush.island.levanov.util.Constants;

public class GameMap {
    public final Cell[][] cells = new Cell[Constants.ROWS][Constants.COLS];

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int xCoordinate, int yCoordinate) {
        return cells[xCoordinate][yCoordinate];
    }
}
