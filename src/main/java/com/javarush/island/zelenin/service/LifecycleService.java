package com.javarush.island.zelenin.service;

import com.javarush.island.zelenin.entity.map.Location;
import com.javarush.island.zelenin.service.auxilary.EatingService;
import com.javarush.island.zelenin.service.auxilary.ReproductionService;
import com.javarush.island.zelenin.service.auxilary.VitalityService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LifecycleService {
    private final EatingService eatingService;
    private final VitalityService vitalityService;
    private final ReproductionService reproductionService;

    public void processLife(Location location) {
        vitalityService.decreaseSatiety(location);
        eatingService.processEating(location);
        vitalityService.removeDead(location);
        reproductionService.processReproduction(location);
    }
}
