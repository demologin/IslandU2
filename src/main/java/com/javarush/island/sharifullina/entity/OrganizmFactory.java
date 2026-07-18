package com.javarush.island.sharifullina.entity;

import com.javarush.island.sharifullina.entity.organizm.Limit;
import com.javarush.island.sharifullina.entity.organizm.Limits;
import com.javarush.island.sharifullina.entity.organizm.Organizm;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class OrganizmFactory {
    String type;

    public OrganizmFactory(String type){
        this.type=type;
    }

    private <T> T createInstance(String type, double weight) throws Exception { //изменияла c Object
        Class<?> clazz = Organizms.getType(type);
        if (clazz == null) {
            throw new IllegalArgumentException("Класс с именем '" + type + "' не найден!");
        }
        return (T) clazz.getConstructor(double.class).newInstance(weight); //ПЕРЕПИСАТЬ КОНСТРУКТОР С ПАРАМЕТРАМИ
    }

    @Nullable
    public Set<Organizm> getOrganizmTypeSet() throws Exception {
        Set<Organizm> organizmsTypeSet = new HashSet<>();
        Map<String, Class<?>> organizmRegistry = Organizms.getOrganizmRegistry();
        if (!organizmRegistry.containsKey(type)) {
            System.out.printf("No such organizm %s\n", type);
            return null;
        }
        if (!Limits.getLimits().containsKey(type)) {
            System.out.printf("No limit for such organizm %s\n", type);
            return null;
        }
        Limit typeLimit = Limits.getLimit(type);
        int maxCount = typeLimit.getMaxCount(); //подумать над выносом в отдельный метод
        double maxWeight = typeLimit.getMaxWeight();
        int bound = ThreadLocalRandom.current().nextInt(maxCount);
        for (int i = 0; i < bound; i++) {
            double weight = ThreadLocalRandom.current().nextDouble(maxWeight);
            Object instance = createInstance(type, weight);
            checkAndAdd(instance, organizmsTypeSet);
        }
        return organizmsTypeSet;
    }

    private static void checkAndAdd(Object instance, Set<Organizm> organizms) {
        if (instance instanceof Organizm organizm) {
                organizms.add(organizm);
        }
    }
}
