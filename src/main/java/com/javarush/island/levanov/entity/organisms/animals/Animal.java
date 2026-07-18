package com.javarush.island.levanov.entity.organisms.animals;

import com.javarush.island.levanov.entity.organisms.Eating;
import com.javarush.island.levanov.entity.organisms.Limit;
import com.javarush.island.levanov.entity.organisms.Movable;
import com.javarush.island.levanov.entity.organisms.Organism;

public abstract class Animal extends Organism implements Movable, Eating {


    @Override
    public void move() {

    }

    @Override
    public void eat() {

    }

}