package com.javarush.island.levanov;

import com.javarush.island.levanov.entity.Creator;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.map.GameMap;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.entity.organisms.animals.Animal;
import com.javarush.island.levanov.entity.organisms.animals.predators.Wolf;
import com.javarush.island.levanov.util.Randomizer;
import com.javarush.island.levanov.view.ConsoleView;
import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConsoleRunner {
    public static void main(String[] args) {
        Creator creator = new Creator();
        GameMap gameMap = creator.createGameMap();
        ConsoleView console = new ConsoleView(gameMap);
        console.showStatistics();

//        Cell cell = gameMap.getCell(0, 0);
//        Map<String, Set<Organism>> residents = cell.getResidents();
//        System.out.println(residents);

// волк куда-то сходил
        Cell cell = gameMap.getCell(1, 1);
        Map<String, Set<Organism>> residents = cell.getResidents();
        Set<Organism> organismSet = residents.get(Wolf.class.getName());
        Animal animal = (Animal) organismSet.iterator().next();
        animal.move(cell);

        console.showCellResidents(gameMap.getCell(0,0));
        console.showCellResidents(gameMap.getCell(0,1));
        console.showCellResidents(gameMap.getCell(1,0));
        console.showCellResidents(gameMap.getCell(1,1));

//        System.out.println("Available cells:");
//        for (Cell availableCell : cell.getAvailableCells()) {
//            System.out.println("x=" + availableCell.xCoordinate + ", y=" + availableCell.yCoordinate);
//        }
//
//        System.out.println();
//
//        List<Cell> availableCells = cell.getAvailableCells();
//        int size = availableCells.size();
//        Cell nextCell = availableCells.get(Randomizer.getRandomNextCell(size));
//        System.out.println("Next cell:");
//        System.out.println("x=" + nextCell.xCoordinate + ", y=" + nextCell.yCoordinate);
    }
}
