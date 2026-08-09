package com.javarush.island.tikhonova.entity.organizm.animals.herbivores;

import com.javarush.island.tikhonova.WriteInFile;
import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.map.StatisticOrganizm;
import com.javarush.island.tikhonova.entity.organizm.Limit;
import com.javarush.island.tikhonova.entity.organizm.Organizm;
import com.javarush.island.tikhonova.entity.organizm.animals.Animal;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Mouse extends Herbivore {
    private static String ADDRESS_FOR_LOG = "src/main/java/com/javarush/island/tikhonova/logEatingMouse.txt";
    public static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter(ADDRESS_FOR_LOG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Mouse(double weight, int countMouse) {

        super("mouse", weight, "\uD83D\uDC01", new Limit(1, 0.01,
                Map.weightMaxForInstance[6], Map.weightMinForInstance[6], Map.maxNumbersInstanceInCell[6]), countMouse);
    }

    @Override
    public void eat(CellMap cellMap, String name) {
        if (cellMap.getOrganizmsInSectionWithKey().get(name).getWeight() < cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms() * cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMaxWeight()) {
            eatPredatorOrganizm(cellMap, name);
        }
    }

    private void eatPredatorOrganizm(CellMap cellMap, String name) {
        Organizm animal = cellMap.getOrganizmsInSectionWithKey().get(name);
        Organizm eatingOrganizm = null;
        // for (int i = 0; i < Map.ATTEMPTS_TO_GET_ENOUGH; i++) {
        String extractionName = randomEat(name, cellMap);

        eatingOrganizm = cellMap.getOrganizmsInSectionWithKey().get(extractionName);
        double countEat = amountOfFood(animal, eatingOrganizm);
        if (countEat > 0.0) {
            cellMap.addOrganizmInCell(name, animal.getWeight() + countEat, animal.getCountOrganizms());

            int indexEat = 0;
            for (int j = 0; j < Map.nameOrganizms.length; j++) {
                if (Map.nameOrganizms[j].equals(extractionName)) {
                    indexEat = j;
                    break;
                }
            }

            int declinePopulation = 0;
            double auxiliary = countEat;
            while (auxiliary >= eatingOrganizm.getLimit().getMaxWeight() && declinePopulation < eatingOrganizm.getCountOrganizms()) {
                auxiliary -= eatingOrganizm.getLimit().getMaxWeight();
                declinePopulation++;
            }
            if (declinePopulation >= 1) {
                synchronized (StatisticOrganizm.class) {
                    StatisticOrganizm.countOrganizmStatistic[indexEat] -= declinePopulation;
                }
            }

            cellMap.addOrganizmInCell(eatingOrganizm.getName(), eatingOrganizm.getWeight() - countEat, eatingOrganizm.getCountOrganizms() - declinePopulation);

            // break;
            try {
                fileWriter.write("x = " + cellMap.getIndexX() + " y = " + cellMap.getIndexX() + " " + name + " съел " + extractionName + " " + declinePopulation + " особей, осталось: " + cellMap.getOrganizmsInSectionWithKey().get(extractionName).getCountOrganizms() + " их вес составляет: " + cellMap.getOrganizmsInSectionWithKey().get(extractionName).getWeight() + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private double amountOfFood(Organizm animal, Organizm eatingAnimal) {
        if (animal.getWeight() < animal.getLimit().getMaxWeight() * animal.getCountOrganizms() && eatingAnimal.getWeight() > 0 && eatingAnimal.getCountOrganizms() > 0) {
            double test = animal.getLimit().getMaxWeight() * animal.getCountOrganizms() - animal.getWeight();
            if (eatingAnimal.getWeight() <= test && eatingAnimal.getWeight() > 0) {
                return eatingAnimal.getWeight() * 50 / 100;
            } else {
                return test * 50 / 100;
            }
        }
        return 0.0;
    }

    private String randomEat(String name, CellMap cellMap) {
        int indexName = 0;
        for (int i = 0; i < Map.nameOrganizms.length; i++) {
            if (Map.nameOrganizms[i].equals(name)) {
                indexName = i;
                break;
            }
        }
        int[] animalEatingVariable = Map.probabilitiesOfEating[indexName];
        ArrayList<String> listOfFoodOptions = new ArrayList<>();
        for (int i = 0; i < animalEatingVariable.length; i++) {
            if (animalEatingVariable[i] != 0 && cellMap.getOrganizmsInSectionWithKey().containsKey(Map.nameOrganizms[i]) && cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms() > 0 && cellMap.getOrganizmsInSectionWithKey().get(name).getWeight() > 0.0) {
                String animalForEat = Map.nameOrganizms[i];
                int countVariable = animalEatingVariable[i];
                for (int j = 0; j < countVariable; j++) {
                    listOfFoodOptions.add(animalForEat);
                }
            }
        }
        Random rand = new Random();
        Collections.shuffle(listOfFoodOptions);
        int indexAnimalForEat = rand.nextInt(listOfFoodOptions.size());
        String extractionName = listOfFoodOptions.get(indexAnimalForEat);
        return extractionName;
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

    @Override
    public String toString() {
        return
                "'" + this.getSymbols() + '\'' + " " +
                        this.getCountOrganizms();
    }

}
