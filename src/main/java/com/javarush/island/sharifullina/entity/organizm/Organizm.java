package com.javarush.island.sharifullina.entity.organizm;

import com.javarush.island.sharifullina.entity.organizm.behaviour.Reproducible;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;


public abstract class Organizm implements Reproducible {
    private final static AtomicLong idCounter = new AtomicLong(System.currentTimeMillis());

    private long id = idCounter.getAndIncrement();
    @Getter
    private double weight;

    public Organizm(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[id=" + id + ", weight=" + weight + " kg]";
    }

    public void changeWeight (double weight) {
        this.weight += weight;
    }

}
