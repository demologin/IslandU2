package com.javarush.island.levanov.util;

import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.entity.organisms.animals.herbivores.Herbivore;
import com.javarush.island.levanov.entity.organisms.animals.plants.Plant;
import com.javarush.island.levanov.entity.organisms.animals.predators.Predator;

public class BornProbabilityGenerator {
    private double bornProbability;

    public double getBornProbability(Organism organism) {
        bornProbability = 1.0;

        // вынести в константы коэффициенты рождения
        Class<?> clazz = organism.getClass();
        if (Predator.class.isAssignableFrom(clazz)) {
            bornProbability *= 0.3;
        } else if (Herbivore.class.isAssignableFrom(clazz)) {
            bornProbability *= 1.0;
        } else if (Plant.class.isAssignableFrom(clazz)) {
            bornProbability *= 3.0;
        }

        // добавить учет веса и прожерливости

        return bornProbability;
    }


}
