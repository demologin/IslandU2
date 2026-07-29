package com.javarush.island.zimina.animalcrossing.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalSpeciesConfig {
    private double weight;
    private int maxPerCell;
    private int speed;
    private double foodNeeded;
    private int initialCount;
}