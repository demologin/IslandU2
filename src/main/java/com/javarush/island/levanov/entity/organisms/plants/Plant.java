package com.javarush.island.levanov.entity.organisms.plants;

import com.javarush.island.levanov.entity.EntityFactory;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.util.Constants;

import java.util.Set;
import java.util.concurrent.locks.Lock;

public abstract class Plant extends Organism {

    public Plant(double weight) {
        super(weight);
    }

    @Override
    public Set<Organism> safeFindFood(Cell cell){return null;}

    @Override
    public void safeEat(Cell cell){}

    @Override
    public void safeGrow(Cell cell) {
        Lock lock = cell.getLock();
        double weight = this.getWeight();
        double maxWeight = this.getMaxWeight();
        lock.lock();
        try {
            if (weight < maxWeight) {
                double weightAfterGrow = weight + maxWeight * Constants.GROW_VELOCITY;
                this.setWeight(Math.min(weightAfterGrow, maxWeight));
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void safeLooseWeight(Cell cell) {}

    @Override
    public void safeReproduce(Cell cell, EntityFactory entityFactory) {

    }
}
