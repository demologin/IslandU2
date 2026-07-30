package com.javarush.island.levanov.entity.map;

import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.util.Constants;
import com.javarush.island.levanov.util.Randomizer;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
    private final GameMap gameMap;
    private Map<String, Set<Organism>> residents;
    public final int xCoordinate;
    public final int yCoordinate;
    private final Lock lock = new ReentrantLock(true);

    public Cell(int xCoordinate, int yCoordinate, GameMap gameMap) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.gameMap = gameMap;
    }

    public void setResidentsMap(Map<String, Set<Organism>> residents) {
        this.residents = residents;
    }

    public Lock getLock() {
        return lock;
    }

    public Map<String, Set<Organism>> getResidentsMap() {
        return residents;
    }

    public Set<Organism> getResidentsSet() {
        lock.lock();
        Set<Organism> allOrganisms = new HashSet<>();
        try {
            Map<String, Set<Organism>> cellResidents = this.getResidentsMap();
            for (Map.Entry<String, Set<Organism>> stringSetEntry : cellResidents.entrySet()) {
                allOrganisms.addAll(stringSetEntry.getValue());
            }
        } finally {
            lock.unlock();
        }
        return allOrganisms;
    }

    public boolean haveCouple(Organism organism) {
        lock.lock();
        boolean haveCouple = false;
        try {
            Map<String, Set<Organism>> cellResidents = this.getResidentsMap();
            if (cellResidents.get(organism.getClass().getName()).size() > 1) {
                haveCouple = true;
            }

        } finally {
            lock.unlock();
        }
        return haveCouple;
    }

    public boolean isCellFull(Organism organism) {
        Map<String, Set<Organism>> residents = this.getResidentsMap();
        Set<Organism> residentsOfThisType = residents.get(organism.getClass().getName());
        return residentsOfThisType.size() == organism.getMaxNumberInCell();
    }

    public Cell getNextCell() {
        List<Cell> availableCells = this.getAvailableNextCells();
        int size = availableCells.size();
        return availableCells.get(Randomizer.getRandomNextCell(size));
    }

    private List<Cell> getAvailableNextCells() {
        List<Cell> availableCells = new ArrayList<>();
        Cell[][] cells = this.gameMap.cells;
        switch (this.xCoordinate) {
            case 0 -> availableCells.add(cells[this.xCoordinate + 1][this.yCoordinate]);
            case Constants.ROWS - 1 -> availableCells.add(cells[this.xCoordinate - 1][this.yCoordinate]);
            default -> {
                availableCells.add(cells[this.xCoordinate + 1][this.yCoordinate]);
                availableCells.add(cells[this.xCoordinate - 1][this.yCoordinate]);
            }
        }
        switch (this.yCoordinate) {
            case 0 -> availableCells.add(cells[this.xCoordinate][this.yCoordinate + 1]);
            case Constants.COLS - 1 -> availableCells.add(cells[this.xCoordinate][this.yCoordinate - 1]);
            default -> {
                availableCells.add(cells[this.xCoordinate][this.yCoordinate + 1]);
                availableCells.add(cells[this.xCoordinate][this.yCoordinate - 1]);
            }
        }
        return availableCells;
    }
}
