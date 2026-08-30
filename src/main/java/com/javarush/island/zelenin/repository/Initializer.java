package com.javarush.island.zelenin.repository;

import com.javarush.island.khmelov.exception.GameException;
import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.map.Island;
import com.javarush.island.zelenin.entity.map.Location;
import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.entity.organism.animal.Animal;
import com.javarush.island.zelenin.entity.organism.plant.Plant;
import com.javarush.island.zelenin.util.Const;
import com.javarush.island.zelenin.util.RandomUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Initializer {
    private final GameConfig config;
    private final LifeCreator creator;

    public Island initializeIsland() {
        Island island = new Island(config.getRows(), config.getCols());
        island.getLocations().forEach(this::populateLocation);
        return island;
    }

    private void populateLocation(Location location) {
        for (OrganismType type : OrganismType.values()) {
            int count = RandomUtils.nextInt(0, config.getMaxCount(type) + 1);

            for (int i = 0; i < count; i++) {
                switch (creator.createFrom(type)) {
                    case Animal animal -> location.getAnimals().add(animal);
                    case Plant plant -> location.getPlants().add(plant);
                    default -> throw new GameException(Const.UNKNOWN_TYPE);
                }
                GameStats.increment(type);
            }
        }
    }
}
