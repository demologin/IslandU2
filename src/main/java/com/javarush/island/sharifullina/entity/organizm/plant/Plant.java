package com.javarush.island.sharifullina.entity.organizm.plant;

import com.javarush.island.sharifullina.entity.organizm.Organizm;

public abstract class Plant extends Organizm {
    public Plant(double weight) {
        super(weight);
    }

//    public Plant reproduce(Organizm organizm){
//        return new Plant() {
//            @Override
//            public void reproduce() {
//
//            }
//        };
//    }
}
