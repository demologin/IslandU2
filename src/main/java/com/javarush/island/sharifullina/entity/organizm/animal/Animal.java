package com.javarush.island.sharifullina.entity.organizm.animal;

import com.javarush.island.sharifullina.entity.organizm.Settings;
import com.javarush.island.sharifullina.entity.map.Cell;
import com.javarush.island.sharifullina.entity.organizm.FoodChances;
import com.javarush.island.sharifullina.entity.organizm.Organizm;
import com.javarush.island.sharifullina.api.entity.Eating;
import com.javarush.island.sharifullina.api.entity.Movable;
import com.javarush.island.sharifullina.api.entity.WeightLosable;
import com.javarush.island.sharifullina.entity.organizm.TypePopulation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Organizm implements Eating, Movable, WeightLosable {
    public Animal(double weight) {
        super(weight);
    }

    @Override
    public void loseWeight(Cell cell) {
        double delta = limit.getMaxWeight() * getCount() * Settings.PERCENT_LOSING_WEIGHT / 100;
        if (weight <= delta) {
            die(cell);
        } else {
            weight -= delta;
        }
    }

    @Override
    public boolean eat(Cell cell) {
        cell.getLock().lock();
        Map<String, Integer> foodChance = FoodChances.getFoodChance(type);
        if (foodChance == null || foodChance.isEmpty()) {
            return false;
        }
//        if(weight == 0) {  // Костыль №1: Попытка исправить редкие случаи осуществления питания мертвым животным -> программа зависает
//            return false;             // Если ставлю true, тоже зависает
//        }
        Set<String> foodNames = foodChance.keySet();

        double maxFood = mostPossibleFoodWeightForEating(cell);
        double eatenFood = 0;
        int trying = 1;
        while (eatenFood < maxFood && trying > 0) {
            String foodName = mostCommonFood(cell, foodNames);
            if (foodName == null) {
                break;
            }
            int chance = foodChance.get(foodName);
            int rnd = ThreadLocalRandom.current().nextInt(0, 100);
            if (rnd <= chance) {
                eatenFood += tryEat(cell, foodName, maxFood - eatenFood);
            }
            trying--;
        }
        cell.getLock().unlock();
        return eatenFood > 0;
    }

    public double tryEat(Cell cell, String foodName, double requiredWeightForEating) {
        if (weight == 0) { //Костыль №2 : Мертвое животное не ест.
            return 0;   //Почему мертвое животное вообще хочет есть не поняла((( Хочется поставить lock на сам Organizm
        }               //П.С.: последние 20 раз мертвые перестали есть (магия:))
        TypePopulation foodPopulation = cell.getPopulation().getPopulationMap().get(foodName);
        if (foodPopulation == null) {
            return 0;
        }
        Set<Organizm> foodSet = foodPopulation.getOrganizms();
        Organizm foodOrganizm = foodSet.iterator().next();
        double foodWeight = foodOrganizm.getWeight();
        double foodToEat = Math.min(foodWeight, requiredWeightForEating);

        if (changeWeight(cell, foodToEat)) {
//            System.out.println(foodOrganizm + " was eaten by " + this + ": " + foodToEat); //delete after
            if (foodWeight <= requiredWeightForEating) {
                foodOrganizm.die(cell);
            } else {
                foodOrganizm.changeWeight(cell, -requiredWeightForEating);
            }
            return foodToEat;
        }
        return 0;
    }

    public String mostCommonFood(Cell cell, Set<String> foodNames) {
        return cell.getPopulation().getPopulationMap().entrySet().stream()
                .filter(entry -> foodNames.contains(entry.getKey()))
                .filter(entry -> entry.getValue().getOrganizms() != null && !entry.getValue().getOrganizms().isEmpty())
                .max((e1, e2) -> Double.compare(
                        e1.getValue().getWeight() * FoodChances.getFoodChance(type).get(e1.getKey()),
                        e2.getValue().getWeight() * FoodChances.getFoodChance(type).get(e2.getKey())
                ))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private double mostPossibleFoodWeightForEating(Cell cell) {
        double weightToFullCell = limit.getMaxCount() * limit.getMaxWeight() - cell.getPopulation(type).getWeight();
        double maxFood = limit.getMaxFood() * getCount();
        double weightToFullOfganizmWeight = getCount() * limit.getMaxWeight() - weight;
        if (cell.getPopulation(type).getCount() < limit.getMaxCount()) {
            return Math.min(maxFood, weightToFullCell);
        }
        return Math.min(maxFood, weightToFullOfganizmWeight);
    }

    @Override
    public boolean move(Cell cell) {
        Cell rndTarget = getRndTargetCell(cell);
        if (rndTarget == null) {
            return false;
        }
        if (rndTarget.safeAddOrganizm(this)) {
            if (cell.safeRemoveOrganizm(this)) {
                return true;
            } else {
                rndTarget.safeRemoveOrganizm(this);
            }
        }
        return false;
    }

    private Cell getRndTargetCell(Cell current) {
        HashSet<Cell> leftCells = new HashSet<>();
        leftCells.add(current);
        Cell target;
        int trying = 0;
        do {
            target = current.getRandomNeighbor();
            if (leftCells.contains(target) || !canMoveToCell(target)) {
                trying++;
                continue;
            }
            leftCells.add(target);
            current = target;
            trying = 0;
        } while (leftCells.size() <= limit.getMaxSpeed() && trying < Settings.MAX_COUNT_ERROR_MOVING);
        return trying > Settings.MAX_COUNT_ERROR_MOVING ? null : target;
    }

    private boolean canMoveToCell(Cell cell) {
        int count = getCount();
        return cell.getPopulation().getPopulationMap().get(type).canAddOrganizm(count);
    }
}
