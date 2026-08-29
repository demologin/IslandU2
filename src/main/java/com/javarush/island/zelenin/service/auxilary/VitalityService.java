package com.javarush.island.zelenin.service.auxilary;

import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.map.Location;
import com.javarush.island.zelenin.repository.GameStats;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VitalityService {
    private final GameConfig config;

    public void decreaseSatiety(Location location) {
        location.getAnimals().forEach(animal ->
                animal.decreaseSatiety(config.getSatietyLoss(animal)));
    }

    public void removeDead(Location location) {
        location.getAnimals().removeIf(animal -> {
            if (!(animal.isEaten() || animal.getSatiety() <= 0)) return false;
            GameStats.decrement(animal.getType());
            return true;
        });
        location.getPlants().removeIf(plant -> {
            if (!plant.isEaten()) return false;
            GameStats.decrement(plant.getType());
            return true;
        });
    }
}
