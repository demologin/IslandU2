package com.javarush.island.levanov.entity.organisms;

public record OrganismParams(String name,
                             String icon,
                             double maxWeight,
                             int maxNumberInCell,
                             int velocity,
                             double maxFootWeight) {
}
