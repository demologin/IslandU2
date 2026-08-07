package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Goat")
public class Goat extends Herbivore{
    public Goat(double weight) {
        super(weight);
        icon = "\uD83D\uDC10";
    }
}
