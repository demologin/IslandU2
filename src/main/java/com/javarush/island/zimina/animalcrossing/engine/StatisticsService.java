package com.javarush.island.zimina.animalcrossing.engine;

import com.javarush.island.zimina.animalcrossing.config.SimulationConfig;
import com.javarush.island.zimina.animalcrossing.map.Island;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;

import java.util.Map;
import java.util.TreeMap;

/**
 * Собирает статистику о текущем состоянии острова —
 * количество живых особей каждого вида и количество растений.
 */
public class StatisticsService {

    /**
     * Считает количество живых особей каждого вида животных на острове.
     *
     * @param island остров, для которого считается статистика
     * @return карта "имя вида -> количество особей", отсортированная по имени вида
     */
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

    /**
     * Считает общее количество растений на острове.
     *
     * @param island остров, для которого считается статистика
     * @return количество растений
     */
    public int countPlants(Island island) {
        int total = 0;
        for (int x = 0; x < SimulationConfig.ISLAND_WIDTH; x++) {
            for (int y = 0; y < SimulationConfig.ISLAND_HEIGHT; y++) {
                total += island.getLocation(x, y).getPlantsCount();
            }
        }
        return total;
    }

    /**
     * Выводит текущую статистику острова в консоль.
     *
     * @param island остров, для которого выводится статистика
     */
    public void printStatistics(Island island) {
        System.out.println("--- Статистика острова ---");
        countAnimals(island).forEach((type, count) -> System.out.println(type + ": " + count));
        System.out.println("Plant: " + countPlants(island));
    }
}