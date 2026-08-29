package com.javarush.island.zelenin.service;

import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.map.Location;
import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.entity.organism.animal.Animal;
import com.javarush.island.zelenin.entity.organism.plant.Plant;
import com.javarush.island.zelenin.util.GlobalStats;
import com.javarush.island.zelenin.util.LifeCreator;
import com.javarush.island.zelenin.util.RandomUtils;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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
                    int count = parents.size();

                    for (int i = 0; i < parents.size() / 2; i++) {
                        if (count >= config.getMaxCount(type)) break;
                        if (RandomUtils.rollChance(config.getBirthChance(type))) {
                            newborns.add(creator.createAnimal(type));
                            GlobalStats.increment(type);
                            count++;
                        }
                    }
                });
        location.getAnimals().addAll(newborns);
    }

    private void growPlants(Location location) {
        List<Plant> grownPlants = new ArrayList<>();

        for (OrganismType type : OrganismType.getPlants()) {
            long count = location.getPlants().stream()
                    .filter(plant -> plant.getType() == type).count();
            if (count <= 0) continue;

            for (int i = 0; i < config.getGrowthRate(type); i++) {
                if (count++ >= config.getMaxCount(type)) break;
                grownPlants.add(creator.createPlant(type));
                GlobalStats.increment(type);
            }
        }
        location.getPlants().addAll(grownPlants);
    }
}
