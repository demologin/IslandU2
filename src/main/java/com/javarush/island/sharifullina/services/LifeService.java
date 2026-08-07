package com.javarush.island.sharifullina.services;

import com.javarush.island.sharifullina.entity.organizm.TypePopulation;
import com.javarush.island.sharifullina.entity.map.Cell;

import java.util.Map;

public abstract class LifeService implements Runnable {
    protected Map<Cell, TypePopulation> organizms;

    public LifeService(Map<Cell, TypePopulation> organizms) {
        this.organizms = organizms;
    }
}
