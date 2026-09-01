package com.javarush.island.zelenin.view.console;

import com.javarush.island.zelenin.entity.organism.OrganismType;
import lombok.Getter;

import java.util.Map;

public class ReportBuffer {
    private Map<OrganismType, Long> currentSnapshot = Map.of();
    private Map<OrganismType, Long> lastSnapshot = Map.of();

    @Getter private long carnivores;
    @Getter private long herbivores;
    @Getter private long plants;

    public void update(Map<OrganismType, Long> newSnapshot) {
        lastSnapshot = currentSnapshot;
        currentSnapshot = newSnapshot;

        carnivores = 0;
        herbivores = 0;
        plants = 0;

        for (OrganismType type : OrganismType.values()) {
            long count = getCount(type);
            if (type.isCarnivore()) carnivores += count;
            else if (type.isHerbivore()) herbivores += count;
            else plants += count;
        }
    }

    public long getCount(OrganismType type) {
        return currentSnapshot.getOrDefault(type, 0L);
    }

    public long getTrend(OrganismType type) {
        return getCount(type) - lastSnapshot.getOrDefault(type, getCount(type));
    }
}
