package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.Game;

import java.util.Arrays;

public class ReproduceService extends AbstractService{
    private final Game game;

    public ReproduceService(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Arrays.stream(game.getGameMap().cells)
                .flatMap(Arrays::stream)
                .forEach(cell -> this.processOneCell(cell, organism -> organism.safeReproduce(cell, game.getEntityFactory())));
    }
}
