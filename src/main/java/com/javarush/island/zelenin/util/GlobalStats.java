package com.javarush.island.zelenin.util;

import com.javarush.island.zelenin.entity.organism.OrganismType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class GlobalStats {
    private static final Map<OrganismType, LongAdder> COUNTERS;

    @Getter private static long totalCarnivores;
    @Getter private static long totalHerbivores;
    @Getter private static long totalPlants;

    static {
        COUNTERS = new ConcurrentHashMap<>();
        for (OrganismType type : OrganismType.values()) {
            COUNTERS.put(type, new LongAdder());
        }
    }

    private GlobalStats() {
        throw new UnsupportedOperationException(); //TODO message
    }

    public static void resetTotals() {
        totalCarnivores = 0;
        totalHerbivores = 0;
        totalPlants = 0;
    }

    public static void updateTotals(OrganismType type, long count) {
        if (type.isCarnivore()) {
            totalCarnivores += count;
        } else if (type.isHerbivore()) {
            totalHerbivores += count;
        } else {
            totalPlants += count;
        }
    }

    public static void increment(OrganismType type) {
        COUNTERS.get(type).increment();
    }

    public static void decrement(OrganismType type) {
        COUNTERS.get(type).decrement();
    }

    public static Map<OrganismType, Long> snapshot() {
        Map<OrganismType, Long> snapshot = new HashMap<>();
        COUNTERS.forEach((type, adder) -> snapshot.put(type, adder.sum()));
        return snapshot;
    }
}
