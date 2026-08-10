package com.javarush.island.sharifullina.entity.organizm.animal.predator;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Wolf")
public class Wolf extends Predator {
    public Wolf(double weight) {
        super(weight);
        icon = "\uD83D\uDC3A";
    }
}
