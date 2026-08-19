package com.javarush.island.makhmudov.api.entity;

import com.javarush.island.makhmudov.entity.map.Cell;

@FunctionalInterface
public interface Reproducible {

    @SuppressWarnings("UnusedReturnValue")
    boolean spawn(Cell currentCell);
}
