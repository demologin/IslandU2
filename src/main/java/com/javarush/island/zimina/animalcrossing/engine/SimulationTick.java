package com.javarush.island.zimina.animalcrossing.engine;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;
import com.javarush.island.zimina.animalcrossing.config.ConfigLoader;
import com.javarush.island.zimina.animalcrossing.config.IslandConfig;
import com.javarush.island.zimina.animalcrossing.config.SimulationConfig;
import com.javarush.island.zimina.animalcrossing.map.Island;
import com.javarush.island.zimina.animalcrossing.map.Location;
import com.javarush.island.zimina.animalcrossing.model.Organism;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;
import com.javarush.island.zimina.animalcrossing.model.plant.Plant;
import com.javarush.island.zimina.animalcrossing.simulation.AnimalFactory;
import com.javarush.island.zimina.animalcrossing.util.ProbabilityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Выполняет один такт симуляции: последовательно проходит фазы
 * (перемещение, голод, еда, размножение, рост растений).
 * Внутри каждой фазы клетки острова обрабатываются параллельно
 * (по строкам), синхронизация обеспечивается через Location.getLock().
 */
public class SimulationTick {

    private static final int SEED_CHANCE = 40;
    private static final int CATERPILLAR_SEED_CHANCE = 40;
    private static final int REPRODUCTION_ATTEMPTS_PER_CELL = 2;

    private final AnimalFactory animalFactory = new AnimalFactory();
    private final IslandConfig islandConfig = ConfigLoader.loadConfig();
    private final ExecutorService executor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Выполняет один такт симуляции для всего острова.
     *
     * @param island остров, для которого выполняется такт
     */
    public void run(Island island) {
        try {
            runPhaseParallel(island, location -> movementPhase(location, island));
            runPhaseParallel(island, this::starvationPhase);
            runPhaseParallel(island, this::eatingPhase);
            runPhaseParallel(island, this::reproductionPhase);
            runPhaseParallel(island, this::growthPhase);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void runPhaseParallel(Island island, Consumer<Location> action) throws InterruptedException {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int y = 0; y < SimulationConfig.ISLAND_HEIGHT; y++) {
            int row = y;
            tasks.add(() -> {
                for (int x = 0; x < SimulationConfig.ISLAND_WIDTH; x++) {
                    action.accept(island.getLocation(x, row));
                }
                return null;
            });
        }
        executor.invokeAll(tasks);
    }

    public void shutdown() {
        executor.shutdown();
    }

    private void movementPhase(Location location, Island island) {
        new ArrayList<>(location.getAnimals()).stream()
                .filter(Organism::isAlive)
                .forEach(animal -> animal.move(island, location));
    }

    private void starvationPhase(Location location) {
        new ArrayList<>(location.getAnimals()).stream()
                .filter(Organism::isAlive)
                .forEach(animal -> animal.starve(location));
    }

    private void eatingPhase(Location location) {
        new ArrayList<>(location.getAnimals()).stream()
                .filter(Organism::isAlive)
                .forEach(animal -> animal.eat(location));
    }

    private void reproductionPhase(Location location) {
        Map<String, List<Animal>> animalsByType = new ArrayList<>(location.getAnimals()).stream()
                .filter(Organism::isAlive)
                .collect(Collectors.groupingBy(animal -> animal.getClass().getSimpleName()));

        animalsByType.forEach((type, group) -> {
            AnimalSpeciesConfig config = islandConfig.getAnimals().get(type);
            int attempts = Math.min(group.size() / 2, REPRODUCTION_ATTEMPTS_PER_CELL);
            for (int i = 0; i < attempts; i++) {
                group.get(i).reproduce(location, animalFactory, config);
            }
        });
    }

    private void growthPhase(Location location) {
        new ArrayList<>(location.getPlants())
                .forEach(plant -> plant.grow(location, islandConfig.getPlant()));

        if (location.getPlants().isEmpty() && ProbabilityUtils.rollChance(SEED_CHANCE)) {
            location.getLock().lock();
            try {
                Plant seed = new Plant(0, 0, islandConfig.getPlant().getWeight());
                location.getPlants().add(seed);
            } finally {
                location.getLock().unlock();
            }
        }

        if (location.countAnimalsOfType("Caterpillar") == 0
                && ProbabilityUtils.rollChance(CATERPILLAR_SEED_CHANCE)) {
            location.getLock().lock();
            try {
                AnimalSpeciesConfig config = islandConfig.getAnimals().get("Caterpillar");
                Animal caterpillar = animalFactory.createAnimal("Caterpillar", 0, 0, config);
                if (caterpillar != null) {
                    location.getAnimals().add(caterpillar);
                }
            } finally {
                location.getLock().unlock();
            }
        }
    }
}