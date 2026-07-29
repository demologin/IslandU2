package com.javarush.island.zimina.animalcrossing.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Контейнер данных об одном виде животного, загружаемых из config.yaml.
 * Поля соответствуют ключам в структуре YAML-файла.
 */
@Getter
@Setter
public class AnimalSpeciesConfig {
    private double weight;
    private int maxPerCell;
    private int speed;
    private double foodNeeded;
    private int initialCount;
}