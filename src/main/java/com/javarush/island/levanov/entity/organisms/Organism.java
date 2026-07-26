package com.javarush.island.levanov.entity.organisms;

import com.javarush.island.levanov.entity.EntityFactory;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.util.Constants;
import com.javarush.island.levanov.util.IdGenerator;
import com.javarush.island.levanov.util.Randomizer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

public abstract class Organism implements Reproducible, Eating {

    public OrganismParams params;
    private final long id;
    private double weight;
    public static int stepForward = 0;
    public static int stepFrom = 0;
    public static int fullCellCounter = 0;

    public Organism(double weight) {
        Class<?> clazz = this.getClass();
        this.params = Constants.PARAMS.get(clazz);
        this.weight = weight;
        this.id = IdGenerator.generateId();
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public String getIcon() {
        return params.icon();
    }

    public int getVelocity() {
        return params.velocity();
    }

    public int getMaxNumberInCell() {
        return params.maxNumberInCell();
    }

    public double getMaxWeight() {
        return params.maxWeight();
    }

    public double getFoodMaxWeight() {
        return params.maxFoodWeight();
    }

    @Override
    public abstract void safeReproduce(Cell cell, EntityFactory entityFactory);

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
//                    System.out.println("Moving " + this + " from " + middleCell.xCoordinate + "," + middleCell.yCoordinate + " -> " + nextCell.xCoordinate + "," + nextCell.yCoordinate);
                            }
                        } else {
                            fullCellCounter++;
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

    public boolean safeAddToCell(Cell cell) {
        boolean isAdded = false;
        Lock lock = cell.getLock();
        lock.lock();
        try {
            String className = this.getClass().getName();
            Map<String, Set<Organism>> cellResidents = cell.getResidentsMap();
            Set<Organism> cellResidentsOfThisType = cellResidents.get(className);
            if (cellResidentsOfThisType.add(this)) {
                stepForward++;
                isAdded = true;
            }
            cellResidents.put(className, cellResidentsOfThisType);
            cell.setResidentsMap(cellResidents);
        } finally {
            lock.unlock();
        }
        return isAdded;
    }

    private boolean safeDeleteFromCell(Cell cell) {
        boolean result = false;
        Lock lock = cell.getLock();
        lock.lock();
        try {
            String className = this.getClass().getName();
            Map<String, Set<Organism>> cellResidents = cell.getResidentsMap();
            Set<Organism> cellResidentsOfThisType = cellResidents.get(className);
            if (cellResidentsOfThisType.remove(this)) {
                stepFrom++;
                result = true;
            }
            cellResidents.put(className, cellResidentsOfThisType);
            cell.setResidentsMap(cellResidents);
        } finally {
            lock.unlock();
        }
        return result;
    }

    private boolean isHere(Cell cell) {
        return cell.getResidentsSet().contains(this);
    }

    @Override
    public void safeEat(Cell cell) {
        Lock lock = cell.getLock();
        lock.lock();
        try {
            double appetite = this.getAppetite();
            if (appetite > 0) {
                Set<Organism> food = safeFindFood(cell);
                for (Organism organism : food) {
                    if (appetite > organism.weight) {
                        weight += organism.weight;
                        organism.safeDeleteFromCell(cell);
//                        System.out.println(organism + "has been eaten");
                    } else {
                        weight += this.getFoodMaxWeight();
                        organism.weight -= appetite;
                    }
                    appetite = Math.max(0, appetite - organism.weight);
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

    public void safeGrow(Cell cell) {
    }

    public Set<Organism> safeFindFood(Cell cell) {
        Lock lock = cell.getLock();
        lock.lock();
        try {
            Set<Organism> food = cell.getResidentsSet().stream()
                    .filter(organism -> organism.isCaught(this))
                    .collect(Collectors.toSet());
            return food;
        } finally {
            lock.unlock();
        }
    }

    public boolean isCaught(Organism catcher) {
        boolean isCaught = false;
        Double probability;
        Map<Class<?>, Double> map = Constants.PROBABILITY_BEING_CAUGHT.get(catcher.getClass());
        if (map == null) {
            return false;
        }
        probability = map.get(this.getClass());
        if (probability == null) {
            return false;
        }
        if (probability >= Randomizer.getRandomProbability()) {
            isCaught = true;
//            System.out.println(this + " is caught by " + catcher);
        }
        return isCaught;
    }

    @Override
    public String toString() {
        return this.params.icon() + " " +
                "id=" + id +
                ", weight=" + weight;
    }
}