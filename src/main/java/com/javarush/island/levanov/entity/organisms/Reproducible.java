package com.javarush.island.levanov.entity.organisms;

import com.javarush.island.levanov.entity.EntityFactory;
import com.javarush.island.levanov.entity.map.Cell;

public interface Reproducible {
    void safeReproduce(Cell cell, EntityFactory entityFactory);
}
