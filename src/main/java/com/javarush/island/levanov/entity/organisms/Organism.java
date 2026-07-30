package com.javarush.island.levanov.entity.organisms;

import com.javarush.island.levanov.entity.EntityFactory;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.util.Constants;
import com.javarush.island.levanov.util.IdGenerator;
import com.javarush.island.levanov.util.Randomizer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public abstract class Organism implements Reproducible {
    private final OrganismParams params;
    private final long id;
    protected double weight;

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

    public abstract void safeReproduce(Cell cell, EntityFactory entityFactory);

    public boolean safeAddToCell(Cell cell) {
        boolean isAdded = false;
        Lock lock = cell.getLock();
        lock.lock();
        try {
            String className = this.getClass().getName();
            Map<String, Set<Organism>> cellResidents = cell.getResidentsMap();
            Set<Organism> cellResidentsOfThisType = cellResidents.get(className);
            if (cellResidentsOfThisType.add(this)) {
                isAdded = true;
            }
            cellResidents.put(className, cellResidentsOfThisType);
            cell.setResidentsMap(cellResidents);
        } finally {
            lock.unlock();
        }
        return isAdded;
    }

    public void safeDeleteFromCell(Cell cell) {
        Lock lock = cell.getLock();
        lock.lock();
        try {
            String className = this.getClass().getName();
            Map<String, Set<Organism>> cellResidents = cell.getResidentsMap();
            Set<Organism> cellResidentsOfThisType = cellResidents.get(className);
            cellResidentsOfThisType.remove(this);
            cellResidents.put(className, cellResidentsOfThisType);
            cell.setResidentsMap(cellResidents);
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