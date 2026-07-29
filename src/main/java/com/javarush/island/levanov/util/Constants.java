package com.javarush.island.levanov.util;

import com.javarush.island.levanov.entity.organisms.OrganismParams;
import com.javarush.island.levanov.entity.organisms.animals.herbivores.*;
import com.javarush.island.levanov.entity.organisms.plants.Grass;
import com.javarush.island.levanov.entity.organisms.animals.predators.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {
    public static final int COLS = 20;
    public static final int ROWS = 100;
    public static final int PERIOD = 10;
    public static final int NUMBER_OF_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static final int INITIAL_CELL_EMPTINESS = 12;
    public static final double REPRODUCE_PROBABILITY = 0.1;
    public static final double CHILD_WEIGHT = 0.25;
    public static final double GROW_VELOCITY = 0.9;
    public static final double LOSE_WEIGHT_VELOCITY = 0.05;

    public static final long CELL_SHOW_LENGTH = 5;
    public static final long COLS_SHOW = 10;
    public static final long ROWS_SHOW = 5;

    public static final Class<?>[] TYPES = {
            Wolf.class, Boa.class, Fox.class, Bear.class, Eagle.class,
            Horse.class, Dear.class, Rabbit.class, Mouse.class, Goat.class, Sheep.class, Boar.class, Buffalo.class, Duck.class, Caterpillar.class,
            Grass.class
    };

    public static final Map<Class<?>, OrganismParams> PARAMS = new LinkedHashMap<>();

    static {
        PARAMS.put(Wolf.class, new OrganismParams("Wolf", 'W', "\uD83D\uDC3A", 50, 30, 3, 8));
        PARAMS.put(Boa.class, new OrganismParams("Boa", '&', "\uD83D\uDC0D", 15, 30, 1, 3));
        PARAMS.put(Fox.class, new OrganismParams("Fox", 'F', "\uD83E\uDD8A", 8, 30, 2, 2));
        PARAMS.put(Bear.class, new OrganismParams("Bear", 'B', "\uD83D\uDC3B", 500, 5, 2, 80));
        PARAMS.put(Eagle.class, new OrganismParams("Eagle", 'E', "\uD83E\uDD85", 6, 20, 3, 1));
        PARAMS.put(Horse.class, new OrganismParams("Horse", 'H', "\uD83D\uDC0E", 400, 20, 4, 60));
        PARAMS.put(Dear.class, new OrganismParams("Dear", 'D', "\uD83E\uDD8C", 300, 20, 4, 50));
        PARAMS.put(Rabbit.class, new OrganismParams("Rabbit", 'R', "\uD83D\uDC07", 2, 150, 2, 0.45));
        PARAMS.put(Mouse.class, new OrganismParams("Mouse", 'M', "\uD83D\uDC01", 0.05, 500, 1, 0.01));
        PARAMS.put(Goat.class, new OrganismParams("Goat", 'G', "\uD83D\uDC10", 60, 140, 3, 10));
        PARAMS.put(Sheep.class, new OrganismParams("Sheep", 'S', "\uD83D\uDC11", 70, 140, 3, 15));
        PARAMS.put(Boar.class, new OrganismParams("Boar", 'b', "\uD83D\uDC17", 400, 50, 2, 50));
        PARAMS.put(Buffalo.class, new OrganismParams("Buffalo", 'U', "\uD83D\uDC03", 700, 10, 3, 100));
        PARAMS.put(Duck.class, new OrganismParams("Duck", 'D', "\uD83E\uDD86", 1, 200, 4, 0.15));
        PARAMS.put(Caterpillar.class, new OrganismParams("Caterpillar", 'C', "\uD83D\uDC1B", 0.01, 1000, 0, 0));
        PARAMS.put(Grass.class, new OrganismParams("Grass", 'g', "\uD83C\uDF3F", 1, 200, 0, 0));
    }

    public static final Map<Class<?>, Map<Class<?>, Double>> PROBABILITY_BEING_CAUGHT = new LinkedHashMap<>();

    static {
        PROBABILITY_BEING_CAUGHT.put(Wolf.class, new HashMap<>(Map.ofEntries(
                Map.entry(Horse.class, 0.1),
                Map.entry(Dear.class, 0.15),
                Map.entry(Rabbit.class, 0.6),
                Map.entry(Mouse.class, 0.8),
                Map.entry(Goat.class, 0.6),
                Map.entry(Sheep.class, 0.7),
                Map.entry(Boar.class, 0.15),
                Map.entry(Buffalo.class, 0.1),
                Map.entry(Duck.class, 0.4)
        )));
        PROBABILITY_BEING_CAUGHT.put(Boa.class, new HashMap<>(Map.ofEntries(
                Map.entry(Fox.class, 0.15),
                Map.entry(Rabbit.class, 0.2),
                Map.entry(Mouse.class, 0.4),
                Map.entry(Duck.class, 0.1)
        )));
        PROBABILITY_BEING_CAUGHT.put(Fox.class, new HashMap<>(Map.ofEntries(
                Map.entry(Rabbit.class, 0.7),
                Map.entry(Mouse.class, 0.9),
                Map.entry(Duck.class, 0.6),
                Map.entry(Caterpillar.class, 0.4)
        )));
        PROBABILITY_BEING_CAUGHT.put(Bear.class, new HashMap<>(Map.ofEntries(
                Map.entry(Boa.class, 0.8),
                Map.entry(Horse.class, 0.4),
                Map.entry(Dear.class, 0.8),
                Map.entry(Rabbit.class, 0.8),
                Map.entry(Mouse.class, 0.9),
                Map.entry(Goat.class, 0.7),
                Map.entry(Sheep.class, 0.7),
                Map.entry(Boar.class, 0.5),
                Map.entry(Buffalo.class, 0.2),
                Map.entry(Duck.class, 0.1)
        )));
        PROBABILITY_BEING_CAUGHT.put(Eagle.class, new HashMap<>(Map.ofEntries(
                Map.entry(Fox.class, 0.8),
                Map.entry(Rabbit.class, 0.9),
                Map.entry(Mouse.class, 0.9),
                Map.entry(Duck.class, 0.8)
        )));
        PROBABILITY_BEING_CAUGHT.put(Horse.class, new HashMap<>(Map.ofEntries(
                Map.entry(Grass.class, 1.0)
        )));
        PROBABILITY_BEING_CAUGHT.put(Dear.class, new HashMap<>(Map.ofEntries(
                Map.entry(Grass.class, 1.0)
        )));
        PROBABILITY_BEING_CAUGHT.put(Rabbit.class, new HashMap<>(Map.ofEntries(
                Map.entry(Grass.class, 1.0)
        )));
        PROBABILITY_BEING_CAUGHT.put(Mouse.class, new HashMap<>(Map.ofEntries(
                Map.entry(Grass.class, 1.0),
                Map.entry(Caterpillar.class, 0.9)
        )));
        PROBABILITY_BEING_CAUGHT.put(Goat.class, new HashMap<>(Map.ofEntries(
                Map.entry(Grass.class, 1.0)
        )));
        PROBABILITY_BEING_CAUGHT.put(Sheep.class, new HashMap<>(Map.ofEntries(
                Map.entry(Grass.class, 1.0)
        )));
        PROBABILITY_BEING_CAUGHT.put(Boar.class, new HashMap<>(Map.ofEntries(
                Map.entry(Mouse.class, 0.5),
                Map.entry(Caterpillar.class, 0.9),
                Map.entry(Grass.class, 1.0)
        )));
        PROBABILITY_BEING_CAUGHT.put(Buffalo.class, new HashMap<>(Map.ofEntries(
                Map.entry(Grass.class, 1.0)
        )));
        PROBABILITY_BEING_CAUGHT.put(Duck.class, new HashMap<>(Map.ofEntries(
                Map.entry(Caterpillar.class, 0.9),
                Map.entry(Grass.class, 1.0)
        )));
        PROBABILITY_BEING_CAUGHT.put(Caterpillar.class, new HashMap<>(Map.ofEntries(
                Map.entry(Grass.class, 1.0)
        )));
    }

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
}
