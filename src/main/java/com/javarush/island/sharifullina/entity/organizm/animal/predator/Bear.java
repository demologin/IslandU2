package com.javarush.island.sharifullina.entity.organizm.animal.predator;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;
import com.javarush.island.sharifullina.entity.map.Cell;
import com.javarush.island.sharifullina.entity.organizm.Organizm;
import com.javarush.island.sharifullina.entity.organizm.TypePopulation;
import com.javarush.island.sharifullina.execption.GameException;

import java.util.concurrent.ThreadLocalRandom;

@TypedOrganizm(type = "Bear")
public class Bear extends Predator {
    public Bear(double weight) {
        super(weight);
        icon = "\uD83D\uDC3B";
    }

    @Override
    public boolean safeReproduce(Cell cell) {
        boolean result = false;
        cell.getLock().lock();
        try {
            TypePopulation typePopulation = cell.getPopulation(type);
            int count = typePopulation.getCount();
            if (count >= limit.getMaxCount()) {
                return false;
            }
            int pairs = count / 2;
            for (int i = 0; i < pairs; i++) {

                int rndBorning = ThreadLocalRandom.current().nextInt(100);
                if (rndBorning > limit.getChanceToBorn()) {
                    continue;
                }
                Object instance = organizmFactory.createInstance(limit.getMaxWeight());
                if (instance instanceof Organizm organizm && cell.safeAddOrganizm(organizm)) {
                    result = true;
                }
            }
        } catch (Exception e) {
            throw new GameException(e.getMessage());
        } finally {
            cell.getLock().unlock();
        }
        return result;
    }
}
