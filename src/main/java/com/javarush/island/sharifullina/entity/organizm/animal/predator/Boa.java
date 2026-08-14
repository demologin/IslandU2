package com.javarush.island.sharifullina.entity.organizm.animal.predator;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Boa")
public class Boa extends Predator {
    public Boa(double weight) {
        super(weight);
        icon = "\uD83D\uDC0D";
    }
}
