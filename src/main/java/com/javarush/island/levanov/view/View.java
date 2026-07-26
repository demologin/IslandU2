package com.javarush.island.levanov.view;

import com.javarush.island.levanov.entity.map.Cell;

public interface View {
    void showStatistics();

    void safeShowCellResidents(Cell cell);

    void showMap();
}
