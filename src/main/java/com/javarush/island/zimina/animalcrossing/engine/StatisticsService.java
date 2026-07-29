package com.javarush.island.zimina.animalcrossing.engine;

import com.javarush.island.zimina.animalcrossing.config.SimulationConfig;
import com.javarush.island.zimina.animalcrossing.map.Island;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;

import java.util.Map;
import java.util.TreeMap;

public class StatisticsService {

    public Map<String, Integer> countAnimals(Island island) {
        Map<String, Integer> counts = new TreeMap<>();
        for (int x = 0; x < SimulationConfig.ISLAND_WIDTH; x++) {
            for (int y = 0; y < SimulationConfig.ISLAND_HEIGHT; y++) {
                for (Animal animal : island.getLocation(x, y).getAnimals()) {
                    if (animal.isAlive()) {
                        String type = animal.getClass().getSimpleName();
                        counts.merge(type, 1, Integer::sum);
                    }
                }
            }
        }
        return counts;
    }

    public int countPlants(Island island) {
        int total = 0;
        for (int x = 0; x < SimulationConfig.ISLAND_WIDTH; x++) {
            for (int y = 0; y < SimulationConfig.ISLAND_HEIGHT; y++) {
                total += island.getLocation(x, y).getPlantsCount();
            }
        }
        return total;
    }

    public void printStatistics(Island island) {
        System.out.println("--- Статистика острова ---");
        countAnimals(island).forEach((type, count) -> System.out.println(type + ": " + count));
        System.out.println("Plant: " + countPlants(island));
    }
}