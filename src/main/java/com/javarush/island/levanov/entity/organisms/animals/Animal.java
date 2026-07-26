package com.javarush.island.levanov.entity.organisms.animals;

import com.javarush.island.levanov.entity.EntityFactory;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Eating;
import com.javarush.island.levanov.entity.organisms.Movable;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.entity.organisms.Reproducible;
import com.javarush.island.levanov.entity.organisms.plants.Grass;
import com.javarush.island.levanov.util.Constants;
import com.javarush.island.levanov.util.Randomizer;

import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

public abstract class Animal extends Organism implements Movable, Eating, Reproducible {
    public Animal(double weight) {
        super(weight);
    }

    @Override
    public void safeReproduce(Cell cell, EntityFactory entityFactory) {
        Lock lock = cell.getLock();
        lock.lock();
        try {
            if (cell.haveCouple(this) && Randomizer.getRandomProbability() < Constants.REPRODUCE_PROBABILITY) {
                Organism child = entityFactory.getNewBorn(this.getClass(), this.getWeight() * Constants.CHILD_WEIGHT);
                child.safeAddToCell(cell);
                this.setWeight(this.getWeight() * (1 - Constants.CHILD_WEIGHT));
            }
        } finally {
            lock.unlock();
        }
    }

}
