package com.javarush.island.makhmudov.config;

import com.javarush.island.makhmudov.api.annotation.DataType;
import com.javarush.island.makhmudov.entity.organism.Limit;
import com.javarush.island.makhmudov.entity.organism.Organism;
import com.javarush.island.makhmudov.exception.GameException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class EntityScanner {

    private EntityScanner() {
    }

    public static Organism[] createPrototypes(Class<?>[] TYPES) {
        Organism[] organisms = new Organism[TYPES.length];
        int index = 0;
        for (Class<?> type : TYPES) {
            if (type.isAnnotationPresent(DataType.class)) {
                DataType dataType = type.getAnnotation(DataType.class);
                String name = dataType.name();
                String icon = dataType.icon();
                int flockSize = dataType.flockSize();
                Limit limit = new Limit(
                        dataType.maxCountInCell() / flockSize,
                        dataType.maxWeight() * flockSize,
                        dataType.maxSpeed(),
                        dataType.maxFood() * flockSize,
                        flockSize
                );
                organisms[index++] = generatePrototype(type, name, icon, limit);
            }
        }
        return organisms;
    }

    private static Organism generatePrototype(Class<?> type, String name, String icon, Limit limit) {
        try {
            Constructor<?> constructor = type.getConstructor(String.class, String.class, Limit.class);
            return (Organism) constructor.newInstance(name, icon, limit);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new GameException("not found Entity constructor", e);
        }
    }
}
