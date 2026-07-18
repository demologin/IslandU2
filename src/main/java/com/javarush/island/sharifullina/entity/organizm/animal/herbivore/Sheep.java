package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.entity.NamedOrganizm;

@NamedOrganizm(type = "Sheep")
public class Sheep extends Herbivore{
    public Sheep(double weight) {
        super(weight);
    }

    @Override
    public double eat() {
        return 0;
    }

    @Override
    public void move() {

    }

    @Override
    public void reproduce() {

    }
}
