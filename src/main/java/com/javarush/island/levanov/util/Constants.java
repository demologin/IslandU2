package com.javarush.island.levanov.util;

import com.javarush.island.levanov.entity.organisms.OrganismParams;
import com.javarush.island.levanov.entity.organisms.animals.herbivores.*;
import com.javarush.island.levanov.entity.organisms.animals.plants.Grass;
import com.javarush.island.levanov.entity.organisms.animals.predators.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {
    public static final int ROWS = 2;
    public static final int COLS = 2;

    public static final Class<?>[] TYPES = {
            Wolf.class, Boa.class, Fox.class, Bear.class, Eagle.class,
            Horse.class, Dear.class, Rabbit.class, Mouse.class, Goat.class, Sheep.class, Boar.class, Buffalo.class, Duck.class, Caterpillar.class,
            Grass.class
    };

    public static final Map<Class<?>, OrganismParams> PARAMS = new LinkedHashMap<>();

    static {
        PARAMS.put(Wolf.class, new OrganismParams("Wolf", "\uD83D\uDC3A", 50, 30, 3, 8));
        PARAMS.put(Boa.class, new OrganismParams("Boa", "\uD83D\uDC0D", 15, 30, 1, 3));
        PARAMS.put(Fox.class, new OrganismParams("Fox", "\uD83E\uDD8A", 8, 30, 2, 2));
        PARAMS.put(Bear.class, new OrganismParams("Bear", "\uD83D\uDC3B", 500, 5, 2, 80));
        PARAMS.put(Eagle.class, new OrganismParams("Eagle", "\uD83E\uDD85", 6, 20, 3, 1));
        PARAMS.put(Horse.class, new OrganismParams("Horse", "\uD83D\uDC0E", 400, 20, 4, 60));
        PARAMS.put(Dear.class, new OrganismParams("Dear", "\uD83E\uDD8C", 300, 20, 4, 50));
        PARAMS.put(Rabbit.class, new OrganismParams("Rabbit", "\uD83D\uDC07", 2, 150, 2, 0.45));
        PARAMS.put(Mouse.class, new OrganismParams("Mouse", "\uD83D\uDC01", 0.05, 500, 1, 0.01));
        PARAMS.put(Goat.class, new OrganismParams("Goat", "\uD83D\uDC10", 60, 140, 3, 10));
        PARAMS.put(Sheep.class, new OrganismParams("Sheep", "\uD83D\uDC11", 70, 140, 3, 15));
        PARAMS.put(Boar.class, new OrganismParams("Boar", "\uD83D\uDC17", 400, 50, 2, 50));
        PARAMS.put(Buffalo.class, new OrganismParams("Buffalo", "\uD83D\uDC03", 700, 10, 3, 100));
        PARAMS.put(Duck.class, new OrganismParams("Duck", "\uD83E\uDD86", 1, 200, 4, 0.15));
        PARAMS.put(Caterpillar.class, new OrganismParams("Caterpillar", "\uD83D\uDC1B", 0.01, 1000, 0, 0));
        PARAMS.put(Grass.class, new OrganismParams("Grass", "\uD83C\uDF3F", 1, 200, 0, 0));
    }
}
