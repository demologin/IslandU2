package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Organism;

import java.util.function.Consumer;

public abstract class AbstractService implements Runnable {

    public void processOneCell(Cell cell, Consumer<Organism> action) {
        cell.getResidentsSet().forEach(action);
    }
}
