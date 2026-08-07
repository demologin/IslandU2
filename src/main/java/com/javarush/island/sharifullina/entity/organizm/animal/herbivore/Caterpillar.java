package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;
import com.javarush.island.sharifullina.entity.map.Cell;
import com.javarush.island.sharifullina.entity.organizm.TypePopulation;
import com.javarush.island.sharifullina.execption.GameException;

import java.util.concurrent.ThreadLocalRandom;

@TypedOrganizm(type = "Caterpillar")
public class Caterpillar extends Herbivore{

    public Caterpillar(double weight) {
        super(weight);
        icon = "\uD83D\uDC1B";
    }
    @Override
    public boolean move(Cell cell) {
        return false;
    }

    @Override
    public boolean eat(Cell cell) {
        return true;
    }

    @Override
    public boolean safeReproduce(Cell cell) {
        cell.getLock().lock();
        boolean result = changeWeight(cell, weight);
            cell.getLock().unlock();
        return result;
    }
}
