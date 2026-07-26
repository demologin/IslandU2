package com.javarush.island.levanov.entity;

import com.javarush.island.levanov.entity.map.GameMap;
import com.javarush.island.levanov.view.View;

public class Game {
    private final GameMap gameMap;
    private final EntityFactory entityFactory;
    private final View view;
    private boolean isFinished = false;

    public Game(GameMap gameMap, EntityFactory entityFactory, View view) {
        this.gameMap = gameMap;
        this.entityFactory = entityFactory;
        this.view = view;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public View getView() {
        return view;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
