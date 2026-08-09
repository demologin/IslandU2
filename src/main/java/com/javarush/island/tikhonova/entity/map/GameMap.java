package com.javarush.island.tikhonova.entity.map;

import com.javarush.island.tikhonova.WriteInFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class GameMap implements Map {

    //создать двумерный массив treeMap(?), в каждом должен быть набор рандомных животных и растений
    public CellMap[][] map = new CellMap[Map.HEIGHT_MAP][Map.WIDTH_MAP];

    public void InitialStateField() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new GeneratorForStarted().fillCell(i, j);
            }
        }
    }

    public Stream<CellMap> getStreamCells() {
        return Arrays.stream(map).flatMap(Arrays::stream);
    }

    public CellMap getCellMap(int row, int col) {
        return map[row][col];
    }

    public boolean liveGame() {
        int count = 0;
        boolean gameOver = false;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].getOrganizmsInSectionWithKey().size() < 2) {
                    count++;
                }
            }
        }
        if (count == Map.HEIGHT_MAP * Map.WIDTH_MAP) {
            gameOver = true;
        }
        for (int i = 0; i < StatisticOrganizm.countOrganizmStatistic.length; i++) {
            System.out.print(StatisticOrganizm.symbols[i] + " - " + StatisticOrganizm.countOrganizmStatistic[i] + " | ");
        }
        System.out.println();
        try {
            WriteInFile.closeWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gameOver;
    }
}
