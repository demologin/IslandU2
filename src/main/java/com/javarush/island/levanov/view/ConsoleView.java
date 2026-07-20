package com.javarush.island.levanov.view;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.map.GameMap;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.util.Constants;

import java.util.*;

public class ConsoleView implements View {
    private final GameMap gameMap;

    public ConsoleView(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void showStatistics() {
        Map<String, Set<Organism>> mapResidents = getMapResidents();
        Map<String, Integer> statistics = getStatistics(mapResidents);
        System.out.println("Total residents: " + getTotalResidents());
        System.out.println(statistics);
    }

    private Map<String, Set<Organism>> getMapResidents() {
        Map<String, Set<Organism>> mapResidents = new LinkedHashMap<>();
        for (int i = 0; i < gameMap.cells.length; i++) {
            for (int j = 0; j < gameMap.cells[i].length; j++) {
                Map<String, Set<Organism>> cellResidents = gameMap.cells[i][j].getResidentsMap();
                for (Map.Entry<String, Set<Organism>> stringSetEntry : cellResidents.entrySet()) {
                    mapResidents.merge(stringSetEntry.getKey(), new HashSet<>(stringSetEntry.getValue()), (set1, set2) -> {
                        set1.addAll(set2);
                        return set1;
                    });
                }
            }
        }
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

    private int getTotalResidents() {
        return Arrays.stream(gameMap.cells)
                .flatMap(Arrays::stream)
                .map(cell -> cell.getResidentsSet().size())
                .reduce(0, Integer::sum);
//
//        int total = 0;
//        for (int i = 0; i < gameMap.cells.length; i++) {
//            for (int j = 0; j < gameMap.cells[i].length; j++) {
//                Map<String, Set<Organism>> cellResidents = gameMap.cells[i][j].getResidentsMap();
//                for (Map.Entry<String, Set<Organism>> stringSetEntry : cellResidents.entrySet()) {
//                    total += stringSetEntry.getValue().size();
//                }
//            }
//        }
//        return total;
    }


    @Override
    public void showCellResidents(Cell cell) {
        System.out.print("x=" + cell.xCoordinate + " ,y=" + cell.yCoordinate + "   ");
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
        System.out.println();
    }

    @Override
    public void showMap() {
        for (int i = 0; i < gameMap.cells.length; i++) {
            for (int j = 0; j < gameMap.cells[i].length; j++) {
                showCellResidents(gameMap.cells[i][j]);
            }
        }
    }
}

