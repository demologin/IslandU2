package com.javarush.island.sharifullina.entity;

import java.util.Arrays;
import java.util.List;

public interface Constants {
    List<String> TYPES_OF_ORGANIZM = Arrays.asList("Wolf", "Boa", "Fox", "Bear", "Eagle", "Horse", // delete
            "Deer", "Rabbit", "Mouse", "Goat", "Sheep", "Boar", "Buffalo", "Duck", "Caterpillar", "Grass");

    String PACKAGE_NAME = "com.javarush.island.sharifullina.entity";
    String PATH_LIMIT_FILE = "src/main/resources/sharifullina/limit.yaml";
    String PATH_FOOD_CHANCE_FILE = "src/main/resources/sharifullina/foodChance.yaml";
}
