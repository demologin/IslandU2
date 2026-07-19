package com.javarush.island.levanov.entity.organisms.animals;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Eating;
import com.javarush.island.levanov.entity.organisms.Limit;
import com.javarush.island.levanov.entity.organisms.Movable;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.util.Randomizer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Animal extends Organism implements Movable, Eating {


    @Override
    public void move(Cell startCell) {
        // сделать потокобезопасным
        // добавить рекурсию
        int velocity = this.getVelocity();
        Cell middleCell = startCell;
        for (int i = 0; i < velocity; i++) {
            Cell nextCell = middleCell.getNextCell();
            if (!nextCell.isCellFull(this)) {
                this.addToNextCell(nextCell);
                this.deleteFromStartCell(middleCell);
                System.out.println("move from "
                        + middleCell.xCoordinate + "," + middleCell.yCoordinate +
                        " to " +
                        +nextCell.xCoordinate + "," + nextCell.yCoordinate);
            } else {
                System.out.println("Can't move");
            }
            middleCell = nextCell;
        }
    }

    // переписать на лямбду
    private void addToNextCell(Cell nextCell) {
        String className = this.getClass().getName();
        Map<String, Set<Organism>> nextCellResidents = nextCell.getResidents();
        Set<Organism> nextCellResidentsOfThisType = nextCellResidents.get(className);
        nextCellResidentsOfThisType.add(this);
        nextCellResidents.put(className, nextCellResidentsOfThisType);
        nextCell.setResidents(nextCellResidents);
    }

    private void deleteFromStartCell(Cell startCell) {
        String className = this.getClass().getName();
        Map<String, Set<Organism>> startCellResidents = startCell.getResidents();
        Set<Organism> startCellResidentsOfThisType = startCellResidents.get(className);
        startCellResidentsOfThisType.remove(this);
        startCellResidents.put(className, startCellResidentsOfThisType);
        startCell.setResidents(startCellResidents);

    }

    @Override
    public void eat() {

    }
}