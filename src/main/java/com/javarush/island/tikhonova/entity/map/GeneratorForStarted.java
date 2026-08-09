package com.javarush.island.tikhonova.entity.map;

import com.javarush.island.tikhonova.entity.organizm.Organizm;

import java.util.Random;

public class GeneratorForStarted {
    public CellMap fillCell(int x, int y) {
        CellMap filledIncell = new CellMap(x, y);
        Random rand = new Random();
        for (int i = 0; i < Map.nameOrganizms.length - 1; i++) {
            int temporaryVariable = Map.maxNumbersInstanceInCell[i] * 70 / 100;
            double weight = 0.0;
            for (int j = 0; j < temporaryVariable; j++) {
                weight += rand.nextDouble(Map.weightMinForInstance[i], Map.weightMaxForInstance[i] + 1);
            }
            filledIncell.addOrganizmInCell(Map.nameOrganizms[i], weight, temporaryVariable);
            StatisticOrganizm.countOrganizmStatistic[i] += temporaryVariable;
        }
        filledIncell.addOrganizmInCell(Map.nameOrganizms[15], (double) Map.weightMaxForInstance[15] * Map.maxNumbersInstanceInCell[15], Map.maxNumbersInstanceInCell[15]);
        StatisticOrganizm.countOrganizmStatistic[15] += Map.maxNumbersInstanceInCell[15];
        return filledIncell;
    }

}
