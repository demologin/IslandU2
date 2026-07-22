package com.javarush.island.rukavanov.entity;


import com.javarush.island.rukavanov.config.OrganismType;

public abstract class Organism {

    public abstract OrganismType getData();

    public double getWeight() {
        return getData().getWeight();
    }

    public String getIcon() {
        return getData().getIcon();
    }

}
