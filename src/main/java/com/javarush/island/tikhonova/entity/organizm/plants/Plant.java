package com.javarush.island.tikhonova.entity.organizm.plants;


import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.StatisticOrganizm;
import com.javarush.island.tikhonova.entity.organizm.FunctionDead;
import com.javarush.island.tikhonova.entity.organizm.FunctionReproduction;
import com.javarush.island.tikhonova.entity.organizm.Limit;
import com.javarush.island.tikhonova.entity.organizm.Organizm;


import java.util.Random;

public abstract class Plant extends Organizm {
    public Plant(String name, double weight, String symbols, Limit limit, int countPlant) {
        super(name, weight, symbols, limit, countPlant);
    }


    @Override
    public double getWeight() {
        return super.getWeight();
    }

    @Override
    public void setWeight(double weight) {
        super.setWeight(weight);
    }

    @Override
    public boolean dying(CellMap cellMap, String name) {
        if (cellMap.getOrganizmsInSectionWithKey().get(name).getWeight() < cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMinWeight() * cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms()) {
            return true;
        }
        return false;
    }

    @Override
    public void reproduction(CellMap cellMap, String name) {
        int countOrganizmInCell = cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms();
        int maxCountOrganizmInCell = cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMaxNumbersOfOrganizmInCell();
        if (countOrganizmInCell < maxCountOrganizmInCell) {
            int countAvailableSeatsCell = maxCountOrganizmInCell - countOrganizmInCell;
            int countKid = countOrganizmInCell * 30 / 100;

            if (countKid > countAvailableSeatsCell) {
                countKid = countAvailableSeatsCell;
            }

            for (int i = 0; i < countKid; i++) {
                cellMap.addOrganizmInCell(name,
                        cellMap.getOrganizmsInSectionWithKey().get(name).getWeight() + 1,
                        countOrganizmInCell + 1);
            }
            StatisticOrganizm.countOrganizmStatistic[15] += countKid;
        }
    }


    @Override
    public String toString() {
        return
                "'" + this.getSymbols() + '\'' + " " +
                        this.getCountOrganizms();
    }
}
