package com.javarush.island.rukavanov.services;


import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class AbstractIslandService implements IslandService {

    @Override

    public void process(Island island) {

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int y = 0; y < island.getHeight(); y++) {

            for (int x = 0; x < island.getWidth(); x++) {

                Location location = island.get(x, y);
                executor.submit(() -> {
                    location.lock();

                    try {
                        processLocation(location, island);
                    } finally {
                        location.unlock();
                    }
                });
            }
        }
        executor.shutdown();
        try {

            executor.awaitTermination(1, TimeUnit.HOURS);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }

    protected abstract void processLocation(Location location, Island island
    );
}