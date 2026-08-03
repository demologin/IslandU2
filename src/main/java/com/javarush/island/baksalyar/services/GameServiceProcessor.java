package com.javarush.island.baksalyar.services;

import com.javarush.island.baksalyar.entity.Game;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * The main simulation loop orchestrator that executes game services periodically.
 * <p>
 * This class runs as a separate thread and schedules the execution of all
 * game services (view update, movement, eating, reproduction) at fixed intervals.
 * It uses a {@link ScheduledExecutorService} to trigger the simulation steps
 * and a thread pool ({@link Executors#newFixedThreadPool}) to run the services
 * in parallel, leveraging the available CPU cores for performance.
 * </p>
 * <p>
 * The simulation step (method {@link #doOneStepGame()}) submits all services
 * to the thread pool. Each service is responsible for a specific aspect of
 * the game logic (e.g., {@link MovingService}, {@link EatingService},
 * {@link ReproducingService}, {@link ViewService}). The pool is shut down
 * after each step to release resources.
 * </p>
 * <p>
 * The loop continues until {@link Game#isFinished()} returns {@code true},
 * at which point the scheduled executor is terminated. The pause state
 * ({@link Game#isPause()}) is respected inside each service to skip updates
 * when the game is paused.
 * </p>
 */
public class GameServiceProcessor extends Thread {

    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final List<Runnable> services;
    private final Game game;
    private final int PERIOD = 1000;
    private ScheduledExecutorService mainPool;

    /**
     * Constructs a new GameServiceProcessor with the given services and game.
     *
     * @param services the collection of services to run each simulation step
     * @param game     the game instance controlling the simulation state
     */
    public GameServiceProcessor(List<Runnable> services, Game game) {
        this.services = services;
        this.game = game;
    }

    /**
     * Starts the simulation loop by scheduling {@link #doOneStepGame()} to run
     * <p>
     * The method is called when the thread is started (via {@link Thread#start()})
     * and creates a single-threaded scheduled executor that will execute the
     * simulation step at the specified rate. The executor runs in the background
     * and will be shut down when the game finishes.
     * </p>
     */
    @Override
    public void run() {
        mainPool = Executors.newSingleThreadScheduledExecutor();
        mainPool.scheduleWithFixedDelay(this::doOneStepGame, 0, PERIOD, TimeUnit.MILLISECONDS);
    }

    /**
     * Executes one full simulation step.
     * <p>
     * If the game is not finished, it creates a fixed thread pool with
     * {@link #CORE_POOL_SIZE} threads and submits all services to it.
     * The services are executed in parallel, and the pool is shut down
     * immediately after all tasks are submitted to free resources.
     * If the game is finished, the main scheduler is terminated.
     * </p>
     * <p>
     * Each service's {@link Runnable#run()} method will check the pause state
     * internally and skip processing if the game is paused.
     * </p>
     */
    private void doOneStepGame() {
        if (!game.isFinished()) {
            try (ExecutorService servicePool = Executors.newFixedThreadPool(CORE_POOL_SIZE)) {
                services.forEach(servicePool::submit);
                servicePool.shutdown();
            }
        } else {
            mainPool.shutdown();
        }
    }
}

