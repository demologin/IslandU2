package com.javarush.island.rukavanov.simulation;

import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.renderer.ConsoleRenderer;
import com.javarush.island.rukavanov.renderer.Renderer;
import com.javarush.island.rukavanov.services.InitializationService;
import com.javarush.island.rukavanov.services.LifeCycleService;
import com.javarush.island.rukavanov.services.PlantGrowthService;
import com.javarush.island.rukavanov.services.StatisticsService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private final Island island = new Island();

    private final InitializationService initializationService =

            new InitializationService();

    private final PlantGrowthService plantGrowthService =

            new PlantGrowthService();

    private final LifeCycleService lifeCycleService =

            new LifeCycleService();

    private final Renderer renderer =

            new ConsoleRenderer();

    private final StatisticsService statisticsService =

            new StatisticsService(renderer);

    private final ScheduledExecutorService scheduler =

            Executors.newSingleThreadScheduledExecutor();

    public void start() {

        initializationService.initialize(island);

        scheduler.scheduleAtFixedRate(

                this::tick,

                0,

                1,

                TimeUnit.SECONDS

        );

    }

    private void tick() {

        try {

            plantGrowthService.process(island);

            lifeCycleService.process(island);

            statisticsService.process(island);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void stop() {

        scheduler.shutdownNow();

    }

}
