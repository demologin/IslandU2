package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.entity.map.Cell;
import com.javarush.island.sharifullina.entity.organizm.animal.Animal;

public abstract class Herbivore extends Animal {
    public Herbivore(double weight) {
        super(weight);
    }

    @Override
    public boolean safeReproduce(Cell cell) {
        return super.safeReproduce(cell);
    }
}
