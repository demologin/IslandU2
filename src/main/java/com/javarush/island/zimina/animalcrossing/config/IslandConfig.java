package com.javarush.island.zimina.animalcrossing.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class IslandConfig {
    private Map<String, AnimalSpeciesConfig> animals;
    private PlantConfig plant;
}
