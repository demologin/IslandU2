package com.javarush.island.levanov.view;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.map.GameMap;

public interface View {
    void showStatistics();

    void showCellResidents(Cell cell);

    void showMap();
}
