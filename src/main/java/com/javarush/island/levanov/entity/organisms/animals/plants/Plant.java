package com.javarush.island.levanov.entity.organisms.animals.plants;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.util.Constants;

import java.util.concurrent.locks.Lock;

public abstract class Plant extends Organism implements Growable {

    @Override
    public void safeGrow(Cell cell) {
        Lock lock = cell.getLock();
        double weight = this.getWeight();
        double maxWeight = this.getMaxWeight();
        lock.lock();
        try{
            if (weight < maxWeight) {
                double weightAfterGrow = weight * (1 + Constants.GROW_VELOCITY);
                this.setWeight(Math.min(weightAfterGrow, maxWeight));
            }
        } finally {
            lock.unlock();
        }
    }
}
