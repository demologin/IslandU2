package com.javarush.island.levanov.entity.organisms;

import com.javarush.island.levanov.entity.map.Cell;

public interface Movable {
    void safeMove(Cell cell);
}
