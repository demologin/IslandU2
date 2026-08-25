package com.javarush.island.zelenin.service;

import com.javarush.island.zelenin.entity.map.Location;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LifecycleService {
    private final EatingService eatingService;
    private final VitalityService vitalityService;
    private final ReproductionService reproductionService;

    public void processLife(Location location) {
        vitalityService.decreaseSatiety(location);
        eatingService.executeEating(location);
        vitalityService.removeDead(location);
        reproductionService.executeReproduction(location);
    }
}
