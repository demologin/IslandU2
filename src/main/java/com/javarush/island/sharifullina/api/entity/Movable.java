package com.javarush.island.sharifullina.api.entity;

import com.javarush.island.sharifullina.entity.map.Cell;

public interface Movable {
    boolean move(Cell current);
}
