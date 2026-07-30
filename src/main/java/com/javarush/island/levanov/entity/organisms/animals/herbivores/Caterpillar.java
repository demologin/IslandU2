package com.javarush.island.levanov.entity.organisms.animals.herbivores;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.util.Constants;

import java.util.concurrent.locks.Lock;

public class Caterpillar extends Herbivore {
    public Caterpillar(double weight) {
        super(weight);
    }

    @Override
    public void safeEat(Cell cell) {
        Lock lock = cell.getLock();
        lock.lock();
        try {
            double weight = this.getWeight();
            double maxWeight = this.getMaxWeight();
            if (!safeFindFood(cell).isEmpty() && weight < maxWeight) {
                this.setWeight(Math.min(maxWeight, weight + Constants.GROW_VELOCITY * maxWeight));
            }
        } finally {
            lock.unlock();
        }
    }
}
