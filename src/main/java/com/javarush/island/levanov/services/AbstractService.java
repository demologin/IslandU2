package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Organism;

import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

public abstract class AbstractService implements Runnable{

    public void processOneCell(Cell cell, Consumer<Organism> action) {
//        Lock lock = cell.getLock();
//        lock.lock();
//        try {
            cell.getResidentsSet().forEach(action);
//        }
//        finally {
//            lock.unlock();
//        }
    }
}
