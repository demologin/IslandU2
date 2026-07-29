package com.javarush.island.zimina.animalcrossing.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Organism {

    @Setter(AccessLevel.PROTECTED)
    private int x;

    @Setter(AccessLevel.PROTECTED)
    private int y;

    protected double weight;

    private boolean alive;

    public Organism(int x, int y, double weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.alive = true;
    }

    public void die() {
        this.alive = false;
    }
}