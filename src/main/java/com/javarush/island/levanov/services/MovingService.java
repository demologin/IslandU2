package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.Game;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.entity.organisms.animals.Animal;
import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.concurrent.locks.Lock;

public class MovingService implements Runnable {
    private final Game game;

    public MovingService(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Map<Cell, Set<Organism>> residentsToMove = new HashMap<>();
        Arrays.stream(game.getGameMap().cells)
                .flatMap(Arrays::stream)
                .forEach(cell -> residentsToMove.put(cell, cell.getResidentsSet()));

        for (Map.Entry<Cell, Set<Organism>> cellSetEntry : residentsToMove.entrySet()) {
            for (Organism organism : cellSetEntry.getValue()) {
                organism.safeMove(cellSetEntry.getKey());
            }
        }
    }
}