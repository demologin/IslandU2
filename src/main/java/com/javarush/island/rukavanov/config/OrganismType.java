package com.javarush.island.rukavanov.config;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum OrganismType {
    WOLF("🐺", 50, 30, 3, 8),
    SNAKE("🐍", 15, 30, 1, 3),
    FOX("🦊", 8, 30, 2, 2),
    BEAR("🐻", 500, 5, 2, 80),
    EAGLE("🦅", 6, 20, 3, 1),
    HORSE("🐎", 400, 20, 4, 60),
    DEER("🦌", 300, 20, 4, 50),
    RABBIT("🐇", 2, 150, 2, 0.45),
    MOUSE("🐁", 0.05, 500, 1, 0.01),
    GOAT("🐐", 60, 140, 3, 10),
    SHEEP("🐑", 70, 140, 3, 15),
    BOAR("🐗", 400, 50, 2, 50),
    BUFFALO("🐃", 700, 10, 3, 100),
    DUCK("🦆", 1, 200, 4, 0.15),
    CATERPILLAR("🐛", 0.01, 1000, 0, 0),
    PLANT("🪴", 1, 200, 0, 0);

    private final String icon;
    private final double weight;
    private final int maxCount;
    private final int speed;
    private final double foodNorm;

    OrganismType(String icon,

                 double weight,
                 int maxCount,
                 int speed,
                 double foodNorm) {

        this.icon = icon;
        this.weight = weight;
        this.maxCount = maxCount;
        this.speed = speed;
        this.foodNorm = foodNorm;

    }

    private static final List<OrganismType> ANIMALS =
            Arrays.stream(values())
                    .filter(type -> type != PLANT)
                    .toList();

    public static List<OrganismType> animals() {
        return ANIMALS;
    }

}
