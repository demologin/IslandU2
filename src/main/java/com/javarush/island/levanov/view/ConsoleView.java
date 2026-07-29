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
        System.out.println("STEP #:" + GameServiceProcessor.step);
        System.out.print("Residents: " + getTotalResidents());
        System.out.print(";   Grass weight: " + getTotalGrassWeight());
        System.out.println(";   Animals weight: " + getTotalAnimalWeight());
        System.out.println(statistics);
    }

    private Map<String, Set<Organism>> getMapResidents() {
        Map<String, Set<Organism>> mapResidents = new LinkedHashMap<>();
        Arrays.stream(gameMap.cells)
                .flatMap(Arrays::stream)
                .map(Cell::getResidentsMap)
                .map(Map::entrySet)
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

    public void safeShowCellResidents(Cell cell) {
        System.out.print("(" + cell.xCoordinate + "," + cell.yCoordinate + ")");
        Lock lock = cell.getLock();
        lock.lock();
        try {
            Map<String, Set<Organism>> cellResidents = cell.getResidentsMap();
            int cellResidentsCounter = cell.getResidentsSet().size();
            Map<Character, Double> visibleMap = new LinkedHashMap<>();
            for (Map.Entry<String, Set<Organism>> stringSetEntry : cellResidents.entrySet()) {
                try {
                    Class<?> clazz = Class.forName(stringSetEntry.getKey());
                    char label = Constants.PARAMS.get(clazz).label();
                    double part = (double) stringSetEntry.getValue().size() / cellResidentsCounter;
                    visibleMap.put(label, part);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            visibleMap.entrySet().stream()
                    .sorted(Map.Entry.<Character, Double>comparingByValue().reversed())
                    .limit(Constants.CELL_SHOW_LENGTH)
                    .forEach(e -> System.out.print(getColor(e.getValue()) + e.getKey() + Constants.RESET));
            System.out.print(" ");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void showMap() {
        for (int i = 0; i < Constants.ROWS_SHOW; i++) {
            for (int j = 0; j < Constants.COLS_SHOW; j++) {
                safeShowCellResidents(gameMap.cells[i][j]);
            }
            System.out.println();
        }
    }

    public void showLabels() {
        for (Class<?> type : Constants.TYPES) {
            String icon = Constants.PARAMS.get(type).icon();
            char label = Constants.PARAMS.get(type).label();
            System.out.print(icon + label + " ");
        }
        System.out.println();
    }

    public void showScale() {
        for (int i = 1; i <= 10; i++) {
            System.out.print(getColor((double) i / 10) + i * 10 + "% " + Constants.RESET);
        }
        System.out.println();
    }

    private String getColor(double part) {
        if (part > 0.9) return Constants.RED;
        if (part > 0.8) return Constants.PURPLE;
        if (part > 0.7) return Constants.BRIGHT_PURPLE;
        if (part > 0.6) return Constants.BLUE;
        if (part > 0.5) return Constants.BRIGHT_BLUE;
        if (part > 0.4) return Constants.GREEN;
        if (part > 0.3) return Constants.BRIGHT_GREEN;
        if (part > 0.2) return Constants.YELLOW;
        if (part > 0.1) return Constants.BRIGHT_YELLOW;
        return Constants.WHITE;
    }
}