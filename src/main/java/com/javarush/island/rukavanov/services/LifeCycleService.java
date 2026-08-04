package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class LifeCycleService {

    private final HungerService hungerService = new HungerService();

    private final EatService eatService = new EatService();

    private final MoveService moveService = new MoveService();

    private final ReproduceService reproduceService = new ReproduceService();
    private final PlantGrowthService plantGrowthService = new PlantGrowthService();

    public void process(Island island) {

        ExecutorService executor =

                Executors.newFixedThreadPool(

                        Runtime.getRuntime().availableProcessors()

                );

        for (int y = 0; y < island.getHeight(); y++) {

            for (int x = 0; x < island.getWidth(); x++) {

                Location location = island.get(x, y);

                executor.submit(() -> processLocation(location, island));

            }

        }

        executor.shutdown();

        try {

            executor.awaitTermination(1, TimeUnit.HOURS);
            plantGrowthService.process(island);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }

    private void processLocation(Location location,
                                 Island island) {

        location.lock();

        try {

            hungerService.process(location, island);

            eatService.process(location, island);

            reproduceService.process(location, island);

            moveService.process(location, island);

        } finally {

            location.unlock();

        }

    }
}
