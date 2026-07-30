package com.javarush.island.levanov.entity.map;

import com.javarush.island.levanov.util.Constants;

public class GameMap {
    public final Cell[][] cells = new Cell[Constants.ROWS][Constants.COLS];
    private int step = 0;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
