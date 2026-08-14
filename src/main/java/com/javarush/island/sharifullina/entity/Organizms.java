package com.javarush.island.sharifullina.entity;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;
import com.javarush.island.sharifullina.execption.GameException;
import org.reflections.Reflections;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class Organizms {
    private static final String PACKAGE_NAME = "com.javarush.island.sharifullina.entity";

    private static final Map<String, Class<?>> organizmRegistry = new ConcurrentHashMap<>();

    private Organizms() {
    }

    static {
        Reflections reflections = new Reflections(PACKAGE_NAME);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(TypedOrganizm.class);

        for (Class<?> clazz : annotated) {
            TypedOrganizm annotation = clazz.getAnnotation(TypedOrganizm.class);
            if (annotation != null) {
                String type = annotation.type();
                if (organizmRegistry.containsKey(type)) {
                    throw new GameException(String.format(
                            "Duplicate organism type '%s' found! Classes: %s and %s",
                            type, organizmRegistry.get(type).getName(), clazz.getName()
                    ));
                }
                organizmRegistry.put(type, clazz);
            }
        }
    }

    public static Map<String, Class<?>> getOrganizmRegistry() {
        return Collections.unmodifiableMap(organizmRegistry);
    }

    public static Class<?> getType(String name) {
        return organizmRegistry.get(name);
    }
}
