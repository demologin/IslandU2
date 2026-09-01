package com.javarush.island.zelenin.service.auxilary;

import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.map.Location;
import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.entity.organism.animal.Animal;
import com.javarush.island.zelenin.entity.organism.plant.Plant;
import com.javarush.island.zelenin.repository.GameStats;
import com.javarush.island.zelenin.repository.LifeCreator;
import com.javarush.island.zelenin.util.RandomUtils;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReproductionService {
    private final GameConfig config;
    private final LifeCreator creator;

    public void processReproduction(Location location) {
        createAnimals(location);
        growPlants(location);
    }

    private void createAnimals(Location location) {
        List<Animal> newborns = new ArrayList<>();

        location.getAnimals().stream()
                .collect(Collectors.groupingBy(Animal::getType))
                .forEach((type, parents) -> {
                    if (parents.size() < 2) return;

                    int packSize = config.getPackSize(type);
                    int maxPacks = config.getMaxCount(type) / packSize;
                    int currentPacks = parents.size();

                    for (int i = 0; i < parents.size() / 2; i++) {
                        if (currentPacks >= maxPacks) break;

                        if (RandomUtils.rollChance(config.getBirthChance(type))) {
                            newborns.add(creator.createAnimal(type));
                            GameStats.add(type, packSize);
                            currentPacks++;
                        }
                    }
                });
        location.getAnimals().addAll(newborns);
    }

    private void growPlants(Location location) {
        List<Plant> grownPlants = new ArrayList<>();

        Map<OrganismType, Long> counts = location.getPlants().stream()
                .collect(Collectors.groupingBy(Plant::getType, Collectors.counting()));

        for (OrganismType type : OrganismType.getPlants()) {
            long currentPacks = counts.getOrDefault(type, 0L);
            if (currentPacks <= 0) continue;

            int packSize = config.getPackSize(type);
            int maxPacks = config.getMaxCount(type) / packSize;
            int packsToGrow = config.getGrowthRate(type) / packSize;

            for (int i = 0; i < packsToGrow; i++) {
                if (currentPacks >= maxPacks) break;

                grownPlants.add(creator.createPlant(type));
                GameStats.add(type, packSize);
                currentPacks++;
            }
        }
        location.getPlants().addAll(grownPlants);
    }
}
