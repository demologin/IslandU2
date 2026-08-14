package com.javarush.island.sharifullina.repository;

import com.javarush.island.sharifullina.entity.Organizms;
import com.javarush.island.sharifullina.entity.Limit;
import com.javarush.island.sharifullina.entity.Limits;
import com.javarush.island.sharifullina.entity.organizm.Organizm;
import com.javarush.island.sharifullina.entity.organizm.plant.Plant;
import com.javarush.island.sharifullina.execption.GameException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public final class OrganizmFactory {
    private String type;

    public OrganizmFactory(String type) {
        this.type = type;
    }

    public <T> T createInstance(double weight) {
        Class<?> clazz = Organizms.getType(type);
        if (clazz == null) {
            throw new GameException("Класс с именем '" + type + "' не найден!");
        }
        try {
            return (T) clazz.getConstructor(double.class).newInstance(weight);
        } catch (Exception e) {
            throw new GameException("Reflection error during creation" + e);
        }
    }

    public Set<Organizm> getOrganizmTypeSet() {
        Set<Organizm> organizmsTypeSet  = ConcurrentHashMap.newKeySet();;
        Map<String, Class<?>> organizmRegistry = Organizms.getOrganizmRegistry();
        if (!organizmRegistry.containsKey(type)) {
            throw new GameException(String.format("No such organizm %s" +System.lineSeparator(), type));
        }
        if (!Limits.getLimits().containsKey(type)) {
            throw new GameException(String.format("No limit for such organizm %s" +System.lineSeparator(), type));
        }
        Limit typeLimit = Limits.getLimit(type);
        int flockSize = typeLimit.getFlockSize();
        int maxFlockCount = typeLimit.getMaxCount() / flockSize;
        double maxWeight = typeLimit.getMaxWeight() * flockSize;
        int bound = ThreadLocalRandom.current().nextInt(maxFlockCount);
        try {
            for (int i = 0; i < bound; i++) {
                double weight = ThreadLocalRandom.current().nextDouble(maxWeight);
                Object instance = createInstance(weight);
                checkAndAdd(instance, organizmsTypeSet);
            }
            return organizmsTypeSet;
        } catch (Exception e) {
            throw new GameException("Population generation error" + e.getMessage());
        }
    }

    private static void checkAndAdd(Object instance, Set<Organizm> organizms) {
        if(instance instanceof Plant plant){
            plant.increaseWeight();
            organizms.add(plant);
        }
        if (instance instanceof Organizm organizm) {
            organizms.add(organizm);
        }
    }
}
