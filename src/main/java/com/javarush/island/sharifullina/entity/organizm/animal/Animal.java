package com.javarush.island.sharifullina.entity.organizm.animal;

import com.javarush.island.sharifullina.entity.organizm.Organizm;
import com.javarush.island.sharifullina.entity.organizm.behaviour.Eating;
import com.javarush.island.sharifullina.entity.organizm.behaviour.Movable;

public abstract class Animal extends Organizm implements Eating, Movable {
    public Animal(double weight) {
        super(weight);
    }
}
