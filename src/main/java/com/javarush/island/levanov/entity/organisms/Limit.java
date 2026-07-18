package com.javarush.island.levanov.entity.organisms;

public class Limit {
    private final int maxWeight;
    private final int maxNumberInCell;
    private final int velocity;
    private final int maxFoofWeight;

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getMaxNumberInCell() {
        return maxNumberInCell;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getMaxFoofWeight() {
        return maxFoofWeight;
    }

    public Limit(int maxWeight, int maxNumberInCell, int velocity, int maxFoofWeight) {
        this.maxWeight = maxWeight;
        this.maxNumberInCell = maxNumberInCell;
        this.velocity = velocity;
        this.maxFoofWeight = maxFoofWeight;
    }
}
