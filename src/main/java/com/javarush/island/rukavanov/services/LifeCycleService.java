package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;
import com.javarush.island.rukavanov.processor.EatProcessor;
import com.javarush.island.rukavanov.processor.HungerProcessor;
import com.javarush.island.rukavanov.processor.MoveProcessor;
import com.javarush.island.rukavanov.processor.ReproduceProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class LifeCycleService {

    private final HungerProcessor hungerProcessor = new HungerProcessor();

    private final EatProcessor eatProcessor = new EatProcessor();

    private final MoveProcessor moveProcessor = new MoveProcessor();

    private final ReproduceProcessor reproduceProcessor = new ReproduceProcessor();

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

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }

    private void processLocation(Location location,

                                 Island island) {

        location.lock();

        try {

            hungerProcessor.process(location, island);

            eatProcessor.process(location, island);

            moveProcessor.process(location, island);

            reproduceProcessor.process(location, island);

        } finally {

            location.unlock();

        }

    }
}
