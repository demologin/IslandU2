package com.javarush.island.tikhonova.services;

import com.javarush.island.tikhonova.entity.Game;
import com.javarush.island.tikhonova.entity.map.GameMap;

public abstract class AbstractService implements Runnable {
    protected final Game game;

    public AbstractService(Game game) {
        this.game = game;
    }
}
