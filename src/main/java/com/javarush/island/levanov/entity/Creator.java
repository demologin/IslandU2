package com.javarush.island.levanov.entity;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.map.GameMap;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.util.Constants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Creator {
    public GameMap createGameMap() {
        GameMap gameMap = new GameMap();
        for (int i = 0; i < gameMap.cells.length; i++) {
            for (int j = 0; j < gameMap.cells[i].length; j++) {
                gameMap.cells[i][j] = new Cell(i, j, gameMap);
                gameMap.cells[i][j].setResidents(getInitialCellResidents());
            }
        }
        return gameMap;
    }

    // причесать эксепшены
    private Map<String, Set<Organism>> getInitialCellResidents() {
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
