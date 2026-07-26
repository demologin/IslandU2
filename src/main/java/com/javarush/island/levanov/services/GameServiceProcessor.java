package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.Game;
import com.javarush.island.levanov.util.Constants;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameServiceProcessor implements Runnable {
    private final Game game;
    private final List<Runnable> services;
    private ScheduledExecutorService mainPool;
    public static int step = 0;

    public GameServiceProcessor(Game game, List<Runnable> services) {
        this.game = game;
        this.services = services;
    }

    @Override
    public void run() {
        mainPool = Executors.newSingleThreadScheduledExecutor();
        mainPool.scheduleWithFixedDelay(this::doOneStep, 0, Constants.PERIOD, TimeUnit.MILLISECONDS);
    }

    private void doOneStep() {
        if (!game.isFinished()) {
            try (ExecutorService servicePool = Executors.newFixedThreadPool(Constants.NUMBER_OF_PROCESSORS)) {
                for (Runnable service : services) {
                    servicePool.submit(service);
                }
                step++;
                servicePool.shutdown();
            }
        } else {
            mainPool.shutdown();
        }
    }
}
