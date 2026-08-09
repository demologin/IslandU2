package com.javarush.island.tikhonova.services;

import com.javarush.island.tikhonova.entity.Game;
import com.javarush.island.tikhonova.entity.map.GameMap;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameServiceProcessor extends Thread {
    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final Game game;
    private final List<Runnable> services;
    private int PERIOD = 1000;
    private ScheduledExecutorService mainPool;


    public GameServiceProcessor(Game game, List<Runnable> list) {
        this.game = game;
        this.services = list;
    }

    @Override
    public void run() {
        mainPool = Executors.newSingleThreadScheduledExecutor();
        mainPool.scheduleAtFixedRate(this::doOneStepGame, 0, PERIOD, TimeUnit.MILLISECONDS);
    }

    private void doOneStepGame() {
        if (!game.getGameMap().liveGame()) {
            try (ExecutorService servicePool = Executors.newFixedThreadPool(CORE_POOL_SIZE)) {
                services.forEach(servicePool::submit);
                servicePool.shutdown();
            }
        } else {
            mainPool.shutdown();
        }
    }

}
