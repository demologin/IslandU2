package com.javarush.island.zimina.animalcrossing.simulation;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;
import com.javarush.island.zimina.animalcrossing.model.animals.herbivores.*;
import com.javarush.island.zimina.animalcrossing.model.animals.omnivores.Boar;
import com.javarush.island.zimina.animalcrossing.model.animals.omnivores.Duck;
import com.javarush.island.zimina.animalcrossing.model.animals.omnivores.Mouse;
import com.javarush.island.zimina.animalcrossing.model.animals.predators.*;

import java.util.Map;

/**
 * Фабрика для создания животных различных видов по их названию.
 * Хранит соответствие между именем вида и способом создания
 * конкретного объекта (ссылкой на конструктор), что позволяет
 * заселять остров без дублирования кода для каждого вида.
 */
public class AnimalFactory {

    /** Карта соответствия названия вида и функции для создания его экземпляра. */
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

    /**
     * Создаёт животное указанного вида в заданных координатах
     * с характеристиками из конфигурации вида.
     *
     * @param type   тип животного (ключ из карты фабрик, например "Rabbit")
     * @param x      координата x на острове
     * @param y      координата y на острове
     * @param config конфигурация вида (вес, скорость, потребность в еде)
     * @return созданный объект животного
     */
    public Animal createAnimal(String type, int x, int y, AnimalSpeciesConfig config) {
        if (!animals.containsKey(type)) {
            return null;
        }
        return animals.get(type).apply(x, y, config);
    }
}
