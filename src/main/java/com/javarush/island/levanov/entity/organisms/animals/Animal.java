package com.javarush.island.levanov.entity.organisms.animals;

import com.javarush.island.levanov.entity.EntityFactory;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.util.Constants;
import com.javarush.island.levanov.util.Randomizer;

import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

public abstract class Animal extends Organism implements Movable, Eating {
    public Animal(double weight) {
        super(weight);
    }

    public void safeMove(Cell startCell) {
        if (this.isHere(startCell)) {
            Cell middleCell = startCell;
            for (int i = 0; i < this.getVelocity(); i++) {
                Cell nextCell = middleCell.getNextCell();
                Lock nextCellLock = nextCell.getLock();
                Lock middleCellLock = middleCell.getLock();
                nextCellLock.lock();
                try {
                    middleCellLock.lock();
                    try {
                        if (!nextCell.isCellFull(this)) {
                            if (this.safeAddToCell(nextCell)) {
                                this.safeDeleteFromCell(middleCell);
                                middleCell = nextCell;
                            }
                        }
                    } finally {
                        middleCellLock.unlock();
                    }
                } finally {
                    nextCellLock.unlock();
                }
            }
        }
    }

    private boolean isHere(Cell cell) {
        return cell.getResidentsSet().contains(this);
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

    public void safeLooseWeight(Cell cell) {
        Lock lock = cell.getLock();
        double weight = this.getWeight();
        lock.lock();
        try {
            weight = weight - this.getMaxWeight() * Constants.LOSE_WEIGHT_VELOCITY;
            if (weight < 0) {
                this.safeDeleteFromCell(cell);
            } else {
                this.setWeight(weight);
            }
        } finally {
            lock.unlock();
        }
    }

    public void safeEat(Cell cell) {
        Lock lock = cell.getLock();
        lock.lock();
        try {
            double appetite = this.getAppetite();
            if (appetite > 0) {
                Set<Organism> food = safeFindFood(cell);
                for (Organism organism : food) {
                    if (appetite > organism.getWeight()) {
                        weight += organism.getWeight();
                        organism.safeDeleteFromCell(cell);
                    } else {
                        weight += this.getFoodMaxWeight();
                        organism.setWeight(organism.getWeight() - appetite);
                    }
                    appetite = Math.max(0, appetite - organism.getWeight());
                    if (appetite == 0) break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private double getAppetite() {
        return Math.min(this.getFoodMaxWeight(), this.getMaxWeight() - weight);
    }

    public Set<Organism> safeFindFood(Cell cell) {
        Lock lock = cell.getLock();
        lock.lock();
        try {
            return cell.getResidentsSet().stream()
                    .filter(organism -> organism.isCaught(this))
                    .collect(Collectors.toSet());
        } finally {
            lock.unlock();
        }
    }
}
