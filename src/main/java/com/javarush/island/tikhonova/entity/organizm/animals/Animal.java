package com.javarush.island.tikhonova.entity.organizm.animals;

import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.map.StatisticOrganizm;
import com.javarush.island.tikhonova.entity.organizm.*;

import java.util.Random;

public abstract class Animal extends Organizm implements FunctionGoing, FunctionEating, FunctionDead, FunctionReproduction {
    public Animal(String name, double weight, String symbols, Limit limit, int countAnimal) {

        super(name, weight, symbols, limit, countAnimal);
    }


    @Override
    public void eat(CellMap cellMap, String name) {
    }


    @Override
    public boolean dying(CellMap cellMap, String name) {
        if (cellMap.getOrganizmsInSectionWithKey().get(name).getWeight() < cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMinWeight()) {
            return true;
        }
        return false;
    }

    @Override
    public void reproduction(CellMap cellMap, String name) {
        Random random = new Random();
        if (cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms() <
                cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMaxNumbersOfOrganizmInCell()
                && cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms() > 2) {
            if (cellMap.getOrganizmsInSectionWithKey().get(name).getWeight() > cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMinWeight() * 2) {
                int countOfPairs = random.nextInt(0,
                        cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMaxNumbersOfOrganizmInCell() - cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms());
                for (int i = 0; i < countOfPairs; i++) {
                    double randomWeightKid = random.nextDouble(cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMinWeight(),
                            cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMaxWeight());
                    cellMap.addOrganizmInCell(name,
                            cellMap.getOrganizmsInSectionWithKey().get(name).getWeight() + randomWeightKid,
                            cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms() + 1);

                }
                for (int i = 0; i < Map.nameOrganizms.length; i++) {
                    if (Map.nameOrganizms[i].equals(name)) {
                        synchronized (StatisticOrganizm.class) {
                            StatisticOrganizm.countOrganizmStatistic[i] += countOfPairs;
                        }
                    }
                }

            }
        }

    }

    @Override
    public void going(GameMap gameMap, CellMap cellMap, String name) { //где-то рандомно выбираем направление, тут рандомно выбираем количество шагов
        Random random = new Random();
        int countAnimalInCell = cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms();
        int averageNumberAnimal = cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMaxNumbersOfOrganizmInCell() * 50 / 100;
        int indexEat = 0;
        for (int i = 0; i < Map.nameOrganizms.length; i++) {
            if (Map.nameOrganizms[i].equals(name)) {
                indexEat = i;
                break;
            }
        }
        boolean thereIsFood = false;
        int[] eat = Map.probabilitiesOfEating[indexEat];
        for (int i = 0; i < eat.length; i++) {
            if (eat[i] != 0) {
                String nameFood = Map.nameOrganizms[i];
                if (cellMap.getOrganizmsInSectionWithKey().containsKey(nameFood)) {
                    thereIsFood = true;
                    break;
                }
            }
        }

        if (countAnimalInCell < averageNumberAnimal || thereIsFood == false) {
            int indexX = cellMap.getIndexX();
            int indexY = cellMap.getIndexY();
            int stepMax = cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMaxStep();


            int north = indexX;
            int soutn = Map.HEIGHT_MAP - indexX - 1;
            int west = indexY;
            int east = Map.WIDTH_MAP - indexY - 1;
            int direction = 0;
            String directionNSWE = "";

            for (int i = 0; i < 10; i++) {
                direction = random.nextInt(4);

                switch (direction) {
                    case 0 -> {
                        direction = north;
                        directionNSWE = "n";
                    }
                    case 1 -> {
                        direction = soutn;
                        directionNSWE = "s";
                    }
                    case 2 -> {
                        direction = west;
                        directionNSWE = "w";
                    }
                    case 3 -> {
                        direction = east;
                        directionNSWE = "e";
                    }
                }
                if (direction != 0) {
                    break;
                }
            }

            int countStepReal = 0;
            for (int i = 0; i < stepMax; i++) {
                if (direction < stepMax) {
                    countStepReal = random.nextInt(direction);
                } else if (direction >= stepMax) {
                    countStepReal = random.nextInt(stepMax);
                }
                int disadvantadeAnimal = gameMap.map[indexX - countStepReal][indexY].getOrganizmsInSectionWithKey()
                        .get(name).getLimit().getMaxNumbersOfOrganizmInCell()
                        - gameMap.map[indexX - countStepReal][indexY].getOrganizmsInSectionWithKey()
                        .get(name).getCountOrganizms();
                if (disadvantadeAnimal >= cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms()) {
                    double weightFuture;
                    int countAnimalsFuture;

                    if (directionNSWE.equals("n")) {
                        //cellFuture

                        if (!gameMap.map[indexX - countStepReal][indexY].getOrganizmsInSectionWithKey().containsKey(name)) {
                            weightFuture = cellMap
                                    .getOrganizmsInSectionWithKey().get(name).getWeight();
                            countAnimalsFuture = cellMap
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms();
                        } else {
                            weightFuture = gameMap.map[indexX - countStepReal][indexY]
                                    .getOrganizmsInSectionWithKey().get(name).getWeight() + cellMap
                                    .getOrganizmsInSectionWithKey().get(name).getWeight();
                            countAnimalsFuture = gameMap.map[indexX - countStepReal][indexY]
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms() + cellMap
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms();
                        }
                        gameMap.map[indexX - countStepReal][indexY]
                                .addOrganizmInCell(name, weightFuture, countAnimalsFuture);
                        cellMap.deleteOrganizm(name);
                    } else if (directionNSWE.equals("s")) {

                        if (!gameMap.map[indexX + countStepReal][indexY].getOrganizmsInSectionWithKey().containsKey(name)) {
                            weightFuture = cellMap
                                    .getOrganizmsInSectionWithKey().get(name).getWeight();
                            countAnimalsFuture = cellMap
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms();
                        } else {

                            weightFuture = gameMap.map[indexX + countStepReal][indexY]
                                    .getOrganizmsInSectionWithKey().get(name).getWeight() + cellMap
                                    .getOrganizmsInSectionWithKey().get(name).getWeight();
                            countAnimalsFuture = gameMap.map[indexX + countStepReal][indexY]
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms() + cellMap
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms();
                        }
                        gameMap.map[indexX + countStepReal][indexY]
                                .addOrganizmInCell(name, weightFuture, countAnimalsFuture);
                        cellMap.deleteOrganizm(name);

                    } else if (directionNSWE.equals("w")) {

                        if (!gameMap.map[indexX][indexY - countStepReal].getOrganizmsInSectionWithKey().containsKey(name)) {
                            weightFuture = cellMap
                                    .getOrganizmsInSectionWithKey().get(name).getWeight();
                            countAnimalsFuture = cellMap
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms();
                        } else {
                            weightFuture = gameMap.map[indexX][indexY - countStepReal]
                                    .getOrganizmsInSectionWithKey().get(name).getWeight() + cellMap
                                    .getOrganizmsInSectionWithKey().get(name).getWeight();
                            countAnimalsFuture = gameMap.map[indexX][indexY - countStepReal]
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms() + cellMap
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms();
                        }
                        gameMap.map[indexX][indexY - countStepReal]
                                .addOrganizmInCell(name, weightFuture, countAnimalsFuture);
                        cellMap.deleteOrganizm(name);
                    } else {

                        if (gameMap.map[indexX][indexY + countStepReal].getOrganizmsInSectionWithKey().containsKey(name)) {
                            weightFuture = cellMap
                                    .getOrganizmsInSectionWithKey().get(name).getWeight();
                            countAnimalsFuture = cellMap
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms();
                        } else {
                            weightFuture = gameMap.map[indexX][indexY + countStepReal]
                                    .getOrganizmsInSectionWithKey().get(name).getWeight() + cellMap
                                    .getOrganizmsInSectionWithKey().get(name).getWeight();
                            countAnimalsFuture = gameMap.map[indexX][indexY + countStepReal]
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms() + cellMap
                                    .getOrganizmsInSectionWithKey().get(name)
                                    .getCountOrganizms();
                        }
                        gameMap.map[indexX][indexY + countStepReal]
                                .addOrganizmInCell(name, weightFuture, countAnimalsFuture);
                        cellMap.deleteOrganizm(name);
                    }

                    break;
                }

            }

        }
    }

    @Override
    public String toString() {
        return
                "'" + this.getSymbols() + '\'' + " " +
                        this.getCountOrganizms();
    }
}
