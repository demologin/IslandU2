package com.javarush.island.tikhonova.entity.map;

public interface Map {
    int HEIGHT_MAP = 120;
    int  WIDTH_MAP = 70;
    int NUMBER_OF_MENTIONS = 5;

    int ATTEMPTS_TO_GET_ENOUGH = 10;
    static final int[][] probabilitiesOfEating = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 90, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 90, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 90, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {20, 0, 80, 10, 70, 40, 90, 80, 70, 50, 0, 80, 0, 0, 0, 0},
            {0, 0, 0, 10, 0, 0, 40, 20, 0, 0, 0, 0, 0, 15, 0, 0},
            {0, 0, 0, 80, 0, 0, 90, 90, 0, 0, 0, 0, 0, 10, 0, 0},
            {0, 40, 0, 60, 0, 0, 90, 70, 0, 0, 0, 0, 0, 0, 0, 0},
            {10, 0, 15, 40, 60, 10, 80, 60, 70, 15, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };



    static final String[] nameOrganizms = new String[]{
        "buffalo", "caterpillar", "deer", "duck", "goat", "horse","mouse", "rabbit","sheep", "wild boar",
                "bear", "boa constrictor", "eagle", "fox", "wolf", "grass"
    };

    /*
    this.maxWeight = maxWeight;
        this.minWeight = minWeight;
        this.maxNumbersOfOrganizmInCell = maxNumbersOfOrganizmInCell;
     */

    static final double[] weightMaxForInstance = new double[]{
        700, 0.01, 300, 1, 60, 400, 0.05, 2, 70, 400, 500, 15, 6, 8, 50, 1
    };
    static final double[] weightMinForInstance = new double[]{
        350, 0.009, 150, 0.5, 30, 200, 0.04, 1, 35, 200, 250, 7, 3, 4, 25, 0
    };

    static final int[] maxNumbersInstanceInCell = new int[]{
        10, 1000, 20, 200, 140, 60, 500, 150, 140, 50, 5, 30, 20, 30, 30, 200
    };

}
