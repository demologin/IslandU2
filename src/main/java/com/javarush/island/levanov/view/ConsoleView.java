package com.javarush.island.levanov.view;

import com.javarush.island.levanov.entity.map.GameMap;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.util.Constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ConsoleView implements View {
    private final GameMap gameMap;

    public ConsoleView(GameMap gameMap) {
        this.gameMap = gameMap;
    }


    @Override
    public void showStatistics() {
        // собираем статистику по всей карте
        Map<String, Set<Organism>> mapResidents = new LinkedHashMap<>();
        for (int i = 0; i < gameMap.cells.length; i++) {
            for (int j = 0; j < gameMap.cells[i].length; j++) {
                Map<String, Set<Organism>> cellResidents = gameMap.cells[i][j].getResidents();
                for (Map.Entry<String, Set<Organism>> stringSetEntry : cellResidents.entrySet()) {
                    mapResidents.merge(stringSetEntry.getKey(), stringSetEntry.getValue(), (set1, set2) -> {
                            set1.addAll(set2);
                            return set1;
                        });
                }
            }
        }

        // подумать как написать на стримах красивее
        // формируем статистику
        Map<String, Integer> statistics = new LinkedHashMap<>();
        for (Map.Entry<String, Set<Organism>> stringSetEntry : mapResidents.entrySet()) {
//            statistics.put(stringSetEntry.getKey(), stringSetEntry.getValue().size());
            try {
                Class<?> clazz = Class.forName(stringSetEntry.getKey());
                String icon = Constants.PARAMS.get(clazz).icon();
                statistics.put(icon, stringSetEntry.getValue().size());

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(statistics);
    }
}
