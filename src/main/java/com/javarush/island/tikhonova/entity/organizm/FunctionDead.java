package com.javarush.island.tikhonova.entity.organizm;

import com.javarush.island.tikhonova.entity.map.CellMap;

public interface FunctionDead {
    boolean dying(CellMap cellMap, String name);
}
