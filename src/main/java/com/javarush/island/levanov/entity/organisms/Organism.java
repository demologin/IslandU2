package com.javarush.island.levanov.entity.organisms;

import com.javarush.island.levanov.util.Constants;
import com.javarush.island.levanov.util.IdGenerator;
import com.javarush.island.levanov.util.Randomizer;

public abstract class Organism implements Reproducible {

    //    private static AtomicLong idCounter;
    public OrganismParams params;
    private long id;
    private double weight;

    public Organism() {
        Class<?> clazz = this.getClass();
        this.params = Constants.PARAMS.get(clazz);
        this.weight = (double) Math.round(params.maxWeight() * Randomizer.getRandomProbability() * 100) / 100;
        this.id = IdGenerator.generateId();
    }

    public double getWeight() {
        return weight;
    }

    public String getIcon() {
        return params.icon();
    }

    public int getVelocity(){
        return params.velocity();
    }

    public int getMaxNumberInCell(){
        return params.maxNumberInCell();
    }

    @Override
    public void produce() {

    }

    @Override
    public String toString() {
        return this.params.icon() + " " +
                "id=" + id +
                ", weight=" + weight;
    }
}