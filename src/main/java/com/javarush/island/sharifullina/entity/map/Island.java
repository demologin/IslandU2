package com.javarush.island.sharifullina.entity.map;


import lombok.Getter;
import java.util.concurrent.ExecutionException;

public class Island {
    @Getter
    private int width;
    @Getter
    private int height;

    private Cell[][] cells;

    public Island(int width, int height) throws ExecutionException, InterruptedException {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        initiateCells();
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void initiateCells() throws ExecutionException, InterruptedException {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y, this);
                cells[x][y].initCell();
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                sb.append(cells[x][y].toString());
            }
        }
        return sb.toString();
    }
}
