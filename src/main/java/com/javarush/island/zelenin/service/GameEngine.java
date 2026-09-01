package com.javarush.island.zelenin.service;

import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.map.Island;
import com.javarush.island.zelenin.entity.map.Location;
import com.javarush.island.zelenin.view.View;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class GameEngine implements Runnable {
    private final GameConfig config;
    private final Island island;
    private final View view;
    private final LifecycleService lifecycleService;
    private final MovementService movementService;
    private ExecutorService renderPool;
    private ScheduledExecutorService schedulerPool;

    @Getter
    private final AtomicInteger tickCounter = new AtomicInteger(0);

    @Override
    public void run() {
        renderPool = Executors.newSingleThreadExecutor();
        schedulerPool = Executors.newSingleThreadScheduledExecutor();
        schedulerPool.scheduleAtFixedRate(this::runOneTick, 0,
                config.getTickDelay(), TimeUnit.MILLISECONDS);
    }

    /**
     * Runs one day of the simulation which is split into 4 consecutive phases:
     * <ol>
     *   <li><b><i>Lifecycle phase.</i></b> Includes eating, reproduction and dying.</li>
     *   <li><b><i>Preparation phase.</i></b> Resets counters needed for the next phases.</li>
     *   <li><b><i>Departure phase.</i></b> Outgoing animals are put into staging queues.</li>
     *   <li><b><i>Arrival phase.</i></b> Incoming animals waiting in queues are processed.</li>
     * </ol>
     * Phases are split on a basis of them requiring access to multiple locations or not.
     * For example, the whole lifecycle phase can be isolated to one location, meanwhile
     * the departure phase requires access to two locations (original and target).
     * <br><br>Rendering is done asynchronously in its own separate pool.
     */
    public void runOneTick() {
        long startTime = System.nanoTime();
        int tick = tickCounter.incrementAndGet();
        CompletableFuture.runAsync(() -> view.render(tick), renderPool);

        List<Location> locations = island.getLocations();
        locations.parallelStream().forEach(lifecycleService::processLife);
        locations.parallelStream().forEach(movementService::prepareReservations);
        locations.parallelStream().forEach(movementService::planDepartures);
        locations.parallelStream().forEach(movementService::processArrivals);

        long duration = (System.nanoTime() - startTime) / 1000000;
        System.out.printf("Next day took %d ms to compute.\n", duration);
    }
}
