package com.javarush.island.sharifullina.services;

import com.javarush.island.sharifullina.entity.Settings;
import com.javarush.island.sharifullina.entity.map.Game;
import com.javarush.island.sharifullina.entity.TypePopulation;
import com.javarush.island.sharifullina.entity.Organizms;
import com.javarush.island.sharifullina.entity.map.Cell;
import com.javarush.island.sharifullina.entity.organizm.animal.Animal;
import com.javarush.island.sharifullina.entity.organizm.plant.Plant;

import java.util.Map;
import java.util.concurrent.*;


public class ScheduleService implements Runnable {
    private final Game game;
    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private ScheduledExecutorService mainPool;

    public ScheduleService(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        mainPool = Executors.newSingleThreadScheduledExecutor();
        mainPool.scheduleWithFixedDelay(this::doOneStepGame, 0, Settings.PERIOD, TimeUnit.MILLISECONDS);
    }

    private void doOneStepGame() {
        if (game.isGameOver()) {
            mainPool.shutdown();
            return;
        }
        try (ExecutorService executorService = Executors.newFixedThreadPool(CORE_POOL_SIZE)) {
            for (String type : Organizms.getOrganizmRegistry().keySet()) {
                Map<Cell, TypePopulation> organizmsByType = game.getIsland().getOrganizmsByType(type);
                Class<?> clazz = Organizms.getOrganizmRegistry().get(type);
                if (clazz == null) {
                    continue;
                }
                if (Animal.class.isAssignableFrom(clazz)) {
                    LifeService service = new AnimalLifeService(organizmsByType);
                    executorService.submit(service);
                }
                if (Plant.class.isAssignableFrom(clazz)) {
                    LifeService service = new PlantLifeService(organizmsByType);
                    executorService.submit(service);
                }
            }
        }
        ViewService viewService = new ViewService(game);
        new Thread(viewService).start();
    }
}










