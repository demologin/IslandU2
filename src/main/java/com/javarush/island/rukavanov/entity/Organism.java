package com.javarush.island.rukavanov.entity;

import com.javarush.island.rukavanov.config.OrganismType;

public abstract class Organism {

    private final OrganismType data;

    protected Organism(OrganismType data) {
        this.data = data;
    }

    public OrganismType getData() {
        return data;
    }

    public double getWeight() {
        return data.getWeight();
    }

    public String getIcon() {
        return data.getIcon();
    }

}
