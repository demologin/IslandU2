package com.javarush.island.zimina.animalcrossing;

import com.javarush.island.zimina.animalcrossing.config.SimulationConfig;
import com.javarush.island.zimina.animalcrossing.engine.SimulationTick;
import com.javarush.island.zimina.animalcrossing.engine.StatisticsService;
import com.javarush.island.zimina.animalcrossing.map.Island;
import com.javarush.island.zimina.animalcrossing.simulation.IslandPopulator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Точка входа для запуска симуляции острова.
 * Такты выполняются по расписанию через ScheduledExecutorService,
 * с периодом SimulationConfig.TICK_DURATION_MS.
 * Симуляция останавливается по достижении MAX_TICKS тактов
 * или при полном вымирании животных на острове.
 */
public class Main {

    private static final int MAX_TICKS = 100;

    public static void main(String[] args) {
        Island island = new Island();
        IslandPopulator populator = new IslandPopulator();
        populator.populate(island);

        SimulationTick tick = new SimulationTick();
        StatisticsService statistics = new StatisticsService();

        System.out.println("=== После заселения ===");
        statistics.printStatistics(island);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        AtomicInteger tickNumber = new AtomicInteger(0);

        scheduler.scheduleWithFixedDelay(() -> {
            int currentTick = tickNumber.incrementAndGet();

            long startTime = System.currentTimeMillis();
            tick.run(island);
            long tickDuration = System.currentTimeMillis() - startTime;

            boolean allDead = statistics.countAnimals(island).isEmpty();

            if (currentTick % 10 == 0 || allDead || currentTick >= MAX_TICKS) {
                System.out.println("=== Такт " + currentTick + " (заняло " + tickDuration + " мс) ===");
                statistics.printStatistics(island);
            } else {
                System.out.println("Такт " + currentTick + ": " + tickDuration + " мс");
            }

            if (allDead || currentTick >= MAX_TICKS) {
                System.out.println(allDead ? "Все животные вымерли, симуляция остановлена." : "Достигнут лимит тактов, симуляция остановлена.");
                scheduler.shutdown();
                tick.shutdown();
            }
        }, SimulationConfig.TICK_DURATION_MS, SimulationConfig.TICK_DURATION_MS, TimeUnit.MILLISECONDS);
    }
}