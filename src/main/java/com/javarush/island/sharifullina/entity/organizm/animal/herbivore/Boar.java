package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Boar")
public class Boar extends Herbivore{

    public Boar(double weight) {
        super(weight);
        icon = "🐗";
    }
}
