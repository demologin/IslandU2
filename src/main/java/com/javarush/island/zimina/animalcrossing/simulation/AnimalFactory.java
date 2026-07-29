package com.javarush.island.zimina.animalcrossing.simulation;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;
import com.javarush.island.zimina.animalcrossing.model.animals.herbivores.*;
import com.javarush.island.zimina.animalcrossing.model.animals.omnivores.Boar;
import com.javarush.island.zimina.animalcrossing.model.animals.omnivores.Duck;
import com.javarush.island.zimina.animalcrossing.model.animals.omnivores.Mouse;
import com.javarush.island.zimina.animalcrossing.model.animals.predators.*;

import java.util.Map;

public class AnimalFactory {

    private final Map<String, TriFunction<Integer, Integer, AnimalSpeciesConfig, Animal>> animals = Map.ofEntries(
            Map.entry("Rabbit", Rabbit::new),
            Map.entry("Wolf", Wolf::new),
            Map.entry("Horse", Horse::new),
            Map.entry("Deer", Deer::new),
            Map.entry("Fox", Fox::new),
            Map.entry("Boa", Boa::new),
            Map.entry("Bear", Bear::new),
            Map.entry("Eagle", Eagle::new),
            Map.entry("Mouse", Mouse::new),
            Map.entry("Goat", Goat::new),
            Map.entry("Sheep", Sheep::new),
            Map.entry("Boar", Boar::new),
            Map.entry("Buffalo", Buffalo::new),
            Map.entry("Duck", Duck::new),
            Map.entry("Caterpillar", Caterpillar::new)
    );

    public Animal createAnimal(String type, int x, int y, AnimalSpeciesConfig config) {
        if (!animals.containsKey(type)) {
            return null;
        }
        return animals.get(type).apply(x, y, config);
    }
}
