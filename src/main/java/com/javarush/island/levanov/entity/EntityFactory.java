package com.javarush.island.levanov.entity;

import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.util.Constants;
import com.javarush.island.levanov.util.Randomizer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EntityFactory {

    public Map<String, Set<Organism>> getInitialCellResidents() {
        Map<String, Set<Organism>> initialCellResidents = new LinkedHashMap<>();
        for (Class<?> clazz : Constants.TYPES) {
            Set<Organism> initialCellResidentsOfType = ConcurrentHashMap.newKeySet();
            Constructor<?> constructor;
            try {
                constructor = clazz.getConstructor(double.class);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            double maxWeight = Constants.PARAMS.get(clazz).maxWeight();
            int counter = Randomizer.getRandomInt(Constants.PARAMS.get(clazz).maxNumberInCell() / Constants.INITIAL_CELL_EMPTINESS + 1);
            for (int i = 0; i < counter; i++) {
                double weight = (double) Math.round(maxWeight * Randomizer.getRandomProbability() * 100) / 100;
                try {
                    initialCellResidentsOfType.add((Organism) constructor.newInstance(weight));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
            initialCellResidents.put(clazz.getName(), initialCellResidentsOfType);
        }
        return initialCellResidents;
    }

    public Organism getNewBorn(Class<?> clazz, double weight) {
        try {
            Constructor<?> constructor = clazz.getConstructor(double.class);
            return (Organism) constructor.newInstance(weight);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
