package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.util.RandomUtil;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    private static final Direction[] VALUES = values();

    public static Direction random() {

        return VALUES[RandomUtil.random(VALUES.length)];

    }
}
