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
            if (!animal.isEaten() && animal.getSatiety() > 0) return false;
            int packSize = config.getPackSize(animal.getType());
            GameStats.subtract(animal.getType(), packSize);
            return true;
        });
        location.getPlants().removeIf(plant -> {
            if (!plant.isEaten()) return false;
            int packSize = config.getPackSize(plant.getType());
            GameStats.subtract(plant.getType(), packSize);
            return true;
        });
    }
}
