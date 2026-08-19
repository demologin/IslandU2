package com.javarush.island.makhmudov.api.entity;

import com.javarush.island.makhmudov.entity.map.Cell;

@FunctionalInterface
public interface Movable {

    @SuppressWarnings("UnusedReturnValue")
    boolean move(Cell startCell);
}
