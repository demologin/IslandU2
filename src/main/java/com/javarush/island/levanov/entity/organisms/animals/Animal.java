package com.javarush.island.levanov.entity.organisms.animals;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Eating;
import com.javarush.island.levanov.entity.organisms.Movable;
import com.javarush.island.levanov.entity.organisms.Organism;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public abstract class Animal extends Organism implements Movable, Eating {


    @Override
    public void safeMove(Cell startCell) {
        // сделать потокобезопасным и подумать надо ли блокировать обе ячейки если я и так блокирую обе ячейки по очереди
        // добавить рекурсию
        Cell middleCell = startCell;
        for (int i = 0; i < this.getVelocity(); i++) {
            Cell nextCell = middleCell.getNextCell();
//            Lock lockNextCell = nextCell.getLock();
//            Lock lockMiddleCell = middleCell.getLock();
//            lockNextCell.lock();
//            try {
//                lockMiddleCell.lock();
//                try {
                    if (!nextCell.isCellFull(this)) {
                        this.safeAddToNextCell(nextCell);
                        this.safeDeleteFromStartCell(middleCell);
                    }
//                } finally {
//                    lockMiddleCell.unlock();
//                }
//            } finally {
//                lockNextCell.unlock();
//            }
            middleCell = nextCell;
        }
    }

    // переписать на лямбду
    private void safeAddToNextCell(Cell nextCell) {
        Lock lock = nextCell.getLock();
        lock.lock();
        try {
            String className = this.getClass().getName();
            Map<String, Set<Organism>> nextCellResidents = nextCell.getResidentsMap();
            Set<Organism> nextCellResidentsOfThisType = nextCellResidents.get(className);
            nextCellResidentsOfThisType.add(this);
            nextCellResidents.put(className, nextCellResidentsOfThisType);
            nextCell.setResidentsMap(nextCellResidents);
        } finally {
            lock.unlock();
        }
    }

    private void safeDeleteFromStartCell(Cell startCell) {
        Lock lock = startCell.getLock();
        lock.lock();
        try {
            String className = this.getClass().getName();
            Map<String, Set<Organism>> startCellResidents = startCell.getResidentsMap();
            Set<Organism> startCellResidentsOfThisType = startCellResidents.get(className);
            startCellResidentsOfThisType.remove(this);
            startCellResidents.put(className, startCellResidentsOfThisType);
            startCell.setResidentsMap(startCellResidents);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void eat() {

    }
}