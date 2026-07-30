package com.javarush.island.levanov.entity;

import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.map.GameMap;

public class Creator {
    private final EntityFactory entityFactory;

    public Creator(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public GameMap createGameMap() {
        GameMap gameMap = new GameMap();
        for (int i = 0; i < gameMap.cells.length; i++) {
            for (int j = 0; j < gameMap.cells[i].length; j++) {
                gameMap.cells[i][j] = new Cell(i, j, gameMap);
                gameMap.cells[i][j].setResidentsMap(entityFactory.getInitialCellResidents());
            }
        }
        return gameMap;
    }
}
