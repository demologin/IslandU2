package com.javarush.island.sharifullina.services;

import com.javarush.island.sharifullina.entity.TypePopulation;

import java.util.concurrent.Callable;

public class TypePopulationCreatorService implements Callable<TypePopulation> {
    private String type;

    public TypePopulationCreatorService(String type) {
        this.type =  type;
    }

    @Override
    public TypePopulation call() {
        TypePopulation typePopulation = new TypePopulation(type);
        return typePopulation;
    }
}
