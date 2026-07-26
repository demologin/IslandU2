package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.Game;

import java.util.Arrays;

public class EatingService extends AbstractService implements Runnable {
    private final Game game;

    public EatingService(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Arrays.stream(game.getGameMap().cells)
                .flatMap(Arrays::stream)
                .forEach(cell -> this.processOneCell(cell, organism -> organism.safeEat(cell)));
    }
}
