package com.javarush.island.zelenin.entity.organism;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Organism {
    private final OrganismType type;
    private boolean isEaten;

}
