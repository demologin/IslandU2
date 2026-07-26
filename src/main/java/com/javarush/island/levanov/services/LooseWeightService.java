package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.Game;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.entity.organisms.animals.Animal;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

public class LooseWeightService extends AbstractService implements Runnable {
    private final Game game;

    public LooseWeightService(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Arrays.stream(game.getGameMap().cells)
                .flatMap(Arrays::stream)
                .forEach(cell-> this.processOneCell(cell, organism -> organism.safeLooseWeight(cell)));
    }
}

