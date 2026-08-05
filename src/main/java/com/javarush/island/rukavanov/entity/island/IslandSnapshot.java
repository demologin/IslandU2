package com.javarush.island.rukavanov.entity.island;

import com.javarush.island.rukavanov.config.OrganismType;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.Map;

@Getter
@Setter
public class IslandSnapshot {
    private final EnumMap<OrganismType, Integer> statistics =

            new EnumMap<>(OrganismType.class);

    private long tick;

    public void put(OrganismType type, int count) {
        statistics.put(type, count);
    }
}
