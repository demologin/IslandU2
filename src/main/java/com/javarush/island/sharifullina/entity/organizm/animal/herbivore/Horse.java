package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Horse")
public class Horse extends Herbivore{
    public Horse(double weight) {
        super(weight);
        icon = "\uD83D\uDC0E";
    }
}
