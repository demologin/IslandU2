package com.javarush.island.makhmudov.api.entity;

import com.javarush.island.makhmudov.entity.map.Cell;

@SuppressWarnings("unused")
@FunctionalInterface
public interface Eating {

    boolean eat(Cell currentCell);
}
