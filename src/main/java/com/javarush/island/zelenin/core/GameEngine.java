package com.javarush.island.zelenin.core;

import com.javarush.island.zelenin.entity.map.Island;
import com.javarush.island.zelenin.entity.map.Location;
import com.javarush.island.zelenin.service.LifecycleService;
import com.javarush.island.zelenin.service.MovementService;
import com.javarush.island.zelenin.view.View;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class GameEngine implements Runnable {
    private final Island island;
    private final LifecycleService lifecycleService;
    private final MovementService movementService;
    private ExecutorService workerPool;
    private ExecutorService renderPool;

    private final View view;

    @Getter
    private final AtomicInteger tickCounter = new AtomicInteger(0);

    @Override
    public void run() {
        workerPool = Executors.newVirtualThreadPerTaskExecutor();
        renderPool = Executors.newSingleThreadExecutor();
        ScheduledExecutorService schedulerPool = Executors.newSingleThreadScheduledExecutor(); //TODO <- this
        schedulerPool.scheduleWithFixedDelay(this::runOneTick, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void runOneTick() {
        long startTime = System.nanoTime();
        int tick = tickCounter.incrementAndGet();
        CompletableFuture.runAsync(() -> view.render(tick), renderPool);

        dispatchAsyncTasks(lifecycleService::processLife)
                .thenRunAsync(() -> runParallelStream(movementService::prepareReservations))
                .thenCompose(v -> dispatchAsyncTasks(movementService::planDepartures))
                .thenAccept(v -> runParallelStream(movementService::processArrivals))
                .join();

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.printf("Next day took %.0f ms to compute.\n", duration);
    }

    private CompletableFuture<Void> dispatchAsyncTasks(Consumer<Location> action) {
        List<Location> locations = island.getLocations();
        CompletableFuture<?>[] futures = new CompletableFuture[locations.size()];

        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            Runnable task = () -> action.accept(location);
            futures[i] = CompletableFuture.runAsync(task, workerPool);
        }
        return CompletableFuture.allOf(futures);
    }

    private void runParallelStream(Consumer<Location> action) {
        island.getLocations().parallelStream().forEach(action);
    }
}
