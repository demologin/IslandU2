package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.Game;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Organism;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class PlantGrowingService implements Runnable {
    private final Game game;

    public PlantGrowingService(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        Arrays.stream(game.getGameMap().cells)
                .flatMap(Arrays::stream)
                .forEach(this::processOneCell);
    }

    public void processOneCell(Cell cell) {
        Lock lock = cell.getLock();
        lock.lock();
        try {
            Set<Organism> allOrganisms = cell.getResidentsSet();
            for (Organism organism : allOrganisms) {
                organism.safeGrow(cell);
            }
        } finally {
            lock.unlock();
        }
    }
}
