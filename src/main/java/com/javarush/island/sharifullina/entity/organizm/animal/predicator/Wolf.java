package com.javarush.island.sharifullina.entity.organizm.animal.predicator;

import com.javarush.island.sharifullina.entity.NamedOrganizm;

@NamedOrganizm(type = "Wolf")
public class Wolf extends Predicator{
    public Wolf(double weight) {
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
