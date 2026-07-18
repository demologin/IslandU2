package com.javarush.island.sharifullina.entity.organizm.plant;

import com.javarush.island.sharifullina.entity.NamedOrganizm;

@NamedOrganizm(type = "Grass")
public class Grass extends Plant{

    public Grass(double weight) {
        super(weight);
    }

    @Override
    public void reproduce() {

    }
}
