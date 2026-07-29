package com.javarush.island.rukavanov.services;


import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class AbstractIslandService {

    protected final ExecutorService executor = ExecutorManager.getExecutor();

    public final void process(Island island) {

        List<Future<?>> futures = new ArrayList<>();

        for (int y = 0; y < island.getHeight(); y++) {

            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.get(x, y);
                futures.add(executor.submit(() -> processLocation(location, island))
                );
            }
        }
        waitAll(futures);
    }

    private void waitAll(List<Future<?>> futures) {

        for (Future<?> future : futures) {

            try {
                future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processLocationSafe(Location location, Island island) {

        location.lock();

        try {
            processLocation(location, island);
        } finally {
            location.unlock();
        }

    }

    protected abstract void processLocation(Location location, Island island);

}
