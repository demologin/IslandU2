package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Deer")
public class Deer extends Herbivore{
    public Deer(double weight) {
        super(weight);
        icon = "\uD83E\uDD8C";
    }
}
