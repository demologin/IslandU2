package com.javarush.island.levanov.entity.organisms;

public record OrganismParams(String name,
                             char label,
                             String icon,
                             double maxWeight,
                             int maxNumberInCell,
                             int velocity,
                             double maxFoodWeight) { }
