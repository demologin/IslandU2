package com.javarush.island.zimina.animalcrossing.model.animals.herbivores;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;

public class Horse extends Herbivore {

    /**
     * Создаёт лошадь с характеристиками, загруженными из конфигурации вида.
     *
     * @param x      координата x на острове
     * @param y      координата y на острове
     * @param config конфигурация вида (вес, скорость, потребность в еде)
     */
    public Horse (int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

}
