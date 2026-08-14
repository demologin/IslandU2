package com.javarush.island.sharifullina.entity.organizm.plant;

import com.javarush.island.sharifullina.entity.map.Cell;
import com.javarush.island.sharifullina.entity.organizm.Organizm;

public abstract class Plant extends Organizm {
    public Plant(double weight) {
        super(weight);
    }

    @Override
    public boolean safeReproduce(Cell cell) {
        increaseWeight();
        return true;
    }

    @Override
    public boolean changeWeight(Cell cell, double deltaWeight) {
        if (cell.getPopulation(type).getWeight() + deltaWeight <= limit.getMaxCount() * limit.getMaxWeight()) {
            weight += deltaWeight;
            if (weight > limit.getMaxWeight() * limit.getFlockSize()) {
                separateFlock(cell);
            }
            return true;
        }
        if(cell.getPopulation(type).getWeight() < limit.getMaxCount() * limit.getMaxWeight()){
            weight = limit.getMaxCount() * limit.getMaxWeight();
            return true;
        }
        return false;
    }

    public void increaseWeight(){
        weight = limit.getMaxWeight() * limit.getFlockSize();
    }
}
