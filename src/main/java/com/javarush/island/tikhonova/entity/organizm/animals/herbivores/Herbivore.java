package com.javarush.island.tikhonova.entity.organizm.animals.herbivores;

import com.javarush.island.tikhonova.WriteInFile;
import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.map.StatisticOrganizm;
import com.javarush.island.tikhonova.entity.organizm.Limit;
import com.javarush.island.tikhonova.entity.organizm.Organizm;
import com.javarush.island.tikhonova.entity.organizm.animals.Animal;
import com.javarush.island.tikhonova.entity.organizm.plants.Grass;
import com.javarush.island.tikhonova.entity.organizm.plants.Plant;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public abstract class Herbivore extends Animal {
    private static String ADDRESS_FOR_LOG = "src/main/java/com/javarush/island/tikhonova/logEatingHerbivore.txt";
    public static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter(ADDRESS_FOR_LOG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Herbivore(String name, double weight, String symbols, Limit limit, int countHerbivore) {
        super(name, weight, symbols, limit, countHerbivore);
    }

    @Override
    public String toString() {
        return
                "'" + this.getSymbols() + '\'' + " " +
                        this.getCountOrganizms();
    }

    @Override
    public void eat(CellMap cellMap, String name) {
        if (cellMap.getOrganizmsInSectionWithKey().get(name).getWeight() < cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms() * cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMaxWeight() && cellMap.getOrganizmsInSectionWithKey().containsKey("grass")) {
            eatHerbivore(cellMap, name);
        }
    }

    private void eatHerbivore(CellMap cellMap, String name) {
        if (cellMap.getOrganizmsInSectionWithKey().get("grass").getWeight() > 0 && cellMap.getOrganizmsInSectionWithKey().get("grass").getCountOrganizms() > 0) {
            Organizm animal = cellMap.getOrganizmsInSectionWithKey().get(name);
            Organizm plant = cellMap.getOrganizmsInSectionWithKey().get("grass");

            double countEat = amountOfFood(animal, plant);
            cellMap.addOrganizmInCell(name, animal.getWeight() + countEat, animal.getCountOrganizms());
            int declinePopulation = 0;
            double auxiliary = countEat;
            while (auxiliary > plant.getLimit().getMaxWeight()) {
                auxiliary -= plant.getLimit().getMaxWeight();
                declinePopulation++;
            }
            if (declinePopulation >= 1) {
                synchronized (StatisticOrganizm.class) {
                    StatisticOrganizm.countOrganizmStatistic[15] -= declinePopulation;
                }
            }

            cellMap.addOrganizmInCell("grass", plant.getWeight() - countEat, plant.getCountOrganizms() - declinePopulation);
            try {
                fileWriter.write("x= " + cellMap.getIndexX() + " y= " + cellMap.getIndexY() + " " + name + " съел травы " + countEat + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private double amountOfFood(Organizm animal, Organizm grass) {
        double test = (animal.getLimit().getMaxWeight() * animal.getCountOrganizms()) - animal.getWeight();
        if (grass.getWeight() <= test) {
            return grass.getWeight();
        } else {
            return test;
        }
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
        super.reproduction(cellMap, name);
    }

    @Override
    public void going(GameMap gameMap, CellMap cellMap, String name) {
        super.going(gameMap, cellMap, name);
    }
}
