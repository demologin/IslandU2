package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.Game;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.entity.organisms.animals.Animal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class MovingService implements Runnable {
    private final Game game;
    private Map<String, Cell> movingAnimals = new HashMap<>();

    public MovingService(Game game) {
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
                if (organism instanceof Animal) {
                    ((Animal) organism).safeMove(cell);
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
