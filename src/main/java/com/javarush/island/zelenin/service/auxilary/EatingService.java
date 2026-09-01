package com.javarush.island.zelenin.service.auxilary;

import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.map.Location;
import com.javarush.island.zelenin.entity.organism.Organism;
import com.javarush.island.zelenin.entity.organism.animal.Animal;
import com.javarush.island.zelenin.entity.organism.plant.Plant;
import com.javarush.island.zelenin.util.RandomUtils;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

@RequiredArgsConstructor
public class EatingService {
    private final GameConfig config;

    public void processEating(Location location) {
        Collections.shuffle(location.getAnimals(), RandomUtils.instance());
        Collections.shuffle(location.getPlants(), RandomUtils.instance());

        for (Animal animal : location.getAnimals()) {
            if (animal.isEaten()) continue;

            double maxSatiety = config.getMaxSatiety(animal);
            if (animal.getSatiety() >= maxSatiety) continue;
            Organism food = findFood(animal, location);
            if (food == null) continue;

            if (RandomUtils.rollChance(config.getEatChance(animal, food))) {
                food.setEaten(true);

                double totalWeight = config.getWeight(food) * config.getPackSize(food);
                animal.increaseSatiety(totalWeight / config.getPackSize(animal));

                if (animal.getSatiety() > maxSatiety) {
                    animal.setSatiety(maxSatiety);
                }
            }
        }
    }

    private Organism findFood(Animal animal, Location location) {
        for (Plant plant : location.getPlants()) {
            if (!plant.isEaten() && config.getEatChance(animal, plant) > 0) {
                return plant;
            }
        }
        for (Animal prey : location.getAnimals()) {
            if (!prey.isEaten() && prey != animal && config.getEatChance(animal, prey) > 0) {
                return prey;
            }
        }
        return null;
    }
}
