package com.javarush.island.levanov.entity;

import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.util.Constants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class EntityFactory {

    // причесать эксепшены
    // добавить случайность в рождении
    public Map<String, Set<Organism>> getInitialCellResidents() {
        Map<String, Set<Organism>> initialCellResidents = new LinkedHashMap<>();
        for (Class<?> type : Constants.TYPES) {
            Set<Organism> initialCellResidentsOfType = new HashSet<>();
            Constructor<?> constructor = null;
            try {
                constructor = type.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            try {
                initialCellResidentsOfType.add((Organism) constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            initialCellResidents.put(type.getName(), initialCellResidentsOfType);
        }
        return initialCellResidents;
    }
}
