package com.javarush.island.zelenin.repository;

import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.util.Const;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class GameStats {
    private static final Map<OrganismType, LongAdder> counters;

    static {
        counters = new ConcurrentHashMap<>();
        for (OrganismType type : OrganismType.values()) {
            counters.put(type, new LongAdder());
        }
    }

    public static void add(OrganismType type, int count) {
        counters.get(type).add(count);
    }

    public static void subtract(OrganismType type, int count) {
        counters.get(type).add(-count);
    }

    public static Map<OrganismType, Long> snapshot() {
        Map<OrganismType, Long> snapshot = new HashMap<>();
        counters.forEach((type, adder) -> snapshot.put(type, adder.sum()));
        return snapshot;
    }

    private GameStats() {
        throw new UnsupportedOperationException(Const.UNSUPPORTED_OPERATION);
    }
}
