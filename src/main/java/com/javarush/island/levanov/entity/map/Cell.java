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
        Map<String, Set<Organism>> cellResidents = this.getResidentsMap();
        Set<Organism> allOrganisms = new HashSet<>();
        for (Map.Entry<String, Set<Organism>> stringSetEntry : cellResidents.entrySet()) {
            allOrganisms.addAll(stringSetEntry.getValue());
        }
        return allOrganisms;
    }

    public int getResidentsNumber(Cell cell){
        return cell.getResidentsSet().size();
    }

    public Cell getNextCell() {
        List<Cell> availableCells = this.getAvailableNextCells();
        int size = availableCells.size();
        return availableCells.get(Randomizer.getRandomNextCell(size));
    }

    public boolean isCellFull(Organism organism) {
        Map<String, Set<Organism>> residents = this.getResidentsMap();
        Set<Organism> residentsOfThisType = residents.get(organism.getClass().getName());
        return residentsOfThisType.size() == organism.getMaxNumberInCell();
    }

    // ходят только в 4 стороны
    private List<Cell> getAvailableNextCells() {
        List<Cell> availableCells = new ArrayList<>();
        Cell[][] cells = this.gameMap.cells;
        if (this.xCoordinate == 0) {
            availableCells.add(cells[this.xCoordinate + 1][this.yCoordinate]);
        } else if (this.xCoordinate == Constants.COLS - 1) {
            availableCells.add(cells[this.xCoordinate - 1][this.yCoordinate]);
        } else {
            availableCells.add(cells[this.xCoordinate + 1][this.yCoordinate]);
            availableCells.add(cells[this.xCoordinate - 1][this.yCoordinate]);
        }
        if (this.yCoordinate == 0) {
            availableCells.add(cells[this.xCoordinate][this.yCoordinate + 1]);
        } else if (this.yCoordinate == Constants.ROWS - 1) {
            availableCells.add(cells[this.xCoordinate][this.yCoordinate - 1]);
        } else {
            availableCells.add(cells[this.xCoordinate][this.yCoordinate + 1]);
            availableCells.add(cells[this.xCoordinate][this.yCoordinate - 1]);
        }
        return availableCells;
    }
}
