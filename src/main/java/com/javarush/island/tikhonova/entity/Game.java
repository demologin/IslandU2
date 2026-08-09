package com.javarush.island.tikhonova.entity;

import com.javarush.island.tikhonova.entity.map.GameMap;

public class Game {
    private final GameMap gameMap;
    //private final Initialization entityFactory;
    private boolean isFinished = false;

    public Game(GameMap gameMap) {//, Initialization entityFactory
        this.gameMap = gameMap;
        //this.entityFactory = entityFactory;
    }

//    public Initialization getEntityFactory() {
//        return entityFactory;
//    }

    public GameMap getGameMap() {
        return gameMap;
    }
}
