package com.javarush.island.sharifullina.entity;

import lombok.Getter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Organizms {
    @Getter
    private static final Map<String, Class<?>> organizmRegistry = new HashMap<>();

    static{
        Reflections reflections = new Reflections(Constants.PACKAGE_NAME);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(NamedOrganizm.class);

        for (Class<?> clazz : annotated) {
            NamedOrganizm annotation = clazz.getAnnotation(NamedOrganizm.class);
            organizmRegistry.put(annotation.type(), clazz);
        }
    }

    public static Class<?> getType(String name){
        return organizmRegistry.get(name);
    }
}
