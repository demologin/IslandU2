package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.map.GameMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MovingService implements Runnable{
    private final GameMap gameMap;
    private Map<String, Cell> movingAnimals = new HashMap<>();

    public MovingService(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void run() {
//        Arrays.stream(gameMap.cells)
//                .flatMap(cell -> Arrays.stream(cell))
//                .forEach(cell -> moveAllResidents(cell));
    }
}
