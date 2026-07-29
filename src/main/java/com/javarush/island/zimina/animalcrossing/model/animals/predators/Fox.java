package com.javarush.island.zimina.animalcrossing.model.animals.predators;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;

import java.util.Map;

public class Fox extends Predator {


    /**
     * Создаёт лису с характеристиками, загруженными из конфигурации вида.
     *
     * @param x      координата x на острове
     * @param y      координата y на острове
     * @param config конфигурация вида (вес, скорость, потребность в еде)
     */
    public Fox(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

    @Override
    public  Map<String, Integer> getFoodProbabilities() {
        return Map.of("Rabbit", 70, "Mouse", 90, "Duck", 10, "Caterpillar", 40);
    }

}
