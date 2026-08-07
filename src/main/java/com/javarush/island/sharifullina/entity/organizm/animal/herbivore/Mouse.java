package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;
import com.javarush.island.sharifullina.entity.map.Cell;
import com.javarush.island.sharifullina.entity.organizm.TypePopulation;
import com.javarush.island.sharifullina.execption.GameException;

import java.util.concurrent.ThreadLocalRandom;

@TypedOrganizm(type = "Mouse")
public class Mouse extends Herbivore{
    public Mouse(double weight) {
        super(weight);
        icon = "\uD83D\uDC01";
    }

    @Override
    public boolean safeReproduce(Cell cell) {
        boolean result = false;
        cell.getLock().lock();
        try {
            TypePopulation typePopulation = cell.getPopulation(type);
            int pairs = getCount() / 2;
            for (int i = 0; i < pairs; i++) {
                if (typePopulation.getCount() >= limit.getMaxCount()) {
                    break;
                }
                int rndBorning = ThreadLocalRandom.current().nextInt(100);
                if (rndBorning > limit.getChanceToBorn()) {
                    continue;
                }
                for (int n = 0; n < 3; n++) {
                    if (!changeWeight(cell, limit.getMaxWeight())) {
                        break;
                    }
                }
                result = true;
            }
        } catch (Exception e) {
            throw new GameException("Reproduce error" + e.getMessage());
        } finally {
            cell.getLock().unlock();
        }
        return result;
    }
}
