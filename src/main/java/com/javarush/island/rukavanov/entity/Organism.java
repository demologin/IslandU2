package com.javarush.island.rukavanov.entity;


import com.javarush.island.rukavanov.config.AnimalData;

public abstract class Organism {

    public abstract AnimalData getData();

    public double getWeight() {
        return getData().getWeight();
    }

    public String getIcon() {
        return getData().getIcon();
    }

}
