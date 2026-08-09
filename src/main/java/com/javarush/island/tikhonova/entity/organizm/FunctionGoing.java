package com.javarush.island.tikhonova.entity.organizm;

import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;

public interface FunctionGoing {
    void going(GameMap gameMap, CellMap cell, String name);
}
