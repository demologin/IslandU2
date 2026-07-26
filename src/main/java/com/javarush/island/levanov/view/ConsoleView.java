package com.javarush.island.levanov.view;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.map.GameMap;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.entity.organisms.animals.Animal;
import com.javarush.island.levanov.entity.organisms.plants.Grass;
import com.javarush.island.levanov.services.GameServiceProcessor;
import com.javarush.island.levanov.util.Constants;

import java.util.*;
import java.util.concurrent.locks.Lock;

public class ConsoleView implements View {
    private final GameMap gameMap;

    public ConsoleView(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void showStatistics() {
        Map<String, Set<Organism>> mapResidents = getMapResidents();
        Map<String, Integer> statistics = getStatistics(mapResidents);
        System.out.println("STEP #: " + GameServiceProcessor.step);
        System.out.println("Total residents: " + getTotalResidents());
        System.out.println("Total grass weight: " + getTotalGrassWeight());
        System.out.println("Total animals weight: " + getTotalAnimalWeight());
        System.out.println("steps forward: " + Organism.stepForward);
        System.out.println("steps from: " + Organism.stepFrom);
        System.out.println("fullCellCounter: " + Organism.fullCellCounter);
        System.out.println(statistics);
    }

    private Map<String, Set<Organism>> getMapResidents() {
        Map<String, Set<Organism>> mapResidents = new LinkedHashMap<>();
        Arrays.stream(gameMap.cells)
                .flatMap(Arrays::stream)
                .map(cell -> cell.getResidentsMap())
                .map(map -> map.entrySet())
                .forEach(set -> {
                    for (Map.Entry<String, Set<Organism>> stringSetEntry : set) {
                        mapResidents.merge(stringSetEntry.getKey(), new HashSet<>(stringSetEntry.getValue()), (set1, set2) -> {
                                    set1.addAll(set2);
                                    return set1;
                                }
                        );
                    }
                });
        return mapResidents;
    }

    // подумать как написать на стримах красивее
    private Map<String, Integer> getStatistics(Map<String, Set<Organism>> mapResidents) {
        Map<String, Integer> statistics = new LinkedHashMap<>();
        for (Map.Entry<String, Set<Organism>> stringSetEntry : mapResidents.entrySet()) {
            try {
                Class<?> clazz = Class.forName(stringSetEntry.getKey());
                String icon = Constants.PARAMS.get(clazz).icon();
                statistics.put(icon, stringSetEntry.getValue().size());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return statistics;
    }

    // дает неточное число потому что берет все ячейки по очереди, в это время все перебегают между ячейками
    private int getTotalResidents() {
        return Arrays.stream(gameMap.cells)
                .flatMap(Arrays::stream)
                .map(cell -> cell.getResidentsSet().size())
                .reduce(0, Integer::sum);
    }

    double getTotalGrassWeight() {
        double totalGrassWeight = Arrays.stream(gameMap.cells)
                .flatMap(Arrays::stream)
                .map(Cell::getResidentsSet)
                .map(set -> set
                        .stream()
                        .filter(o -> o instanceof Grass)
                        .map(Organism::getWeight)
                        .reduce(0.0, Double::sum))
                .reduce(0.0, Double::sum);
        totalGrassWeight = (double) Math.round(totalGrassWeight * 100) / 100;
        return totalGrassWeight;
    }

    double getTotalAnimalWeight() {
        double totalAnimalWeight = Arrays.stream(gameMap.cells)
                .flatMap(Arrays::stream)
                .map(Cell::getResidentsSet)
                .map(set -> set
                        .stream()
                        .filter(o -> o instanceof Animal)
                        .map(Organism::getWeight)
                        .reduce(0.0, Double::sum))
                .reduce(0.0, Double::sum);
        totalAnimalWeight = (double) Math.round(totalAnimalWeight * 100) / 100;
        return totalAnimalWeight;
    }

    @Override
    public void safeShowCellResidents(Cell cell) {
        System.out.print("x=" + cell.xCoordinate + " ,y=" + cell.yCoordinate + "   ");
        Lock lock = cell.getLock();
        lock.lock();
        try {
            Map<String, Set<Organism>> cellResidents = cell.getResidentsMap();
            for (Map.Entry<String, Set<Organism>> stringSetEntry : cellResidents.entrySet()) {
                try {
                    Class<?> clazz = Class.forName(stringSetEntry.getKey());
                    String icon = Constants.PARAMS.get(clazz).icon();
                    int counter = stringSetEntry.getValue().size();
                    System.out.print(icon + counter + " ");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            lock.unlock();
        }
        System.out.println();
    }

    @Override
    public void showMap() {
        for (int i = 0; i < gameMap.cells.length; i++) {
            for (int j = 0; j < gameMap.cells[i].length; j++) {
                safeShowCellResidents(gameMap.cells[i][j]);
            }
        }
    }
}