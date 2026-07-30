package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.Game;
import com.javarush.island.levanov.entity.organisms.plants.Plant;

import java.util.Arrays;

public class PlantGrowingService extends AbstractService implements Runnable {
    private final Game game;

    public PlantGrowingService(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Arrays.stream(game.getGameMap().cells)
                .flatMap(Arrays::stream)
                .forEach(cell -> this.processOneCell(cell, organism ->
                {
                    if (organism instanceof Plant) {
                        ((Plant) organism).safeGrow(cell);
                    }
                }));
    }
}
