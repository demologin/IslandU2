package com.javarush.island.sharifullina.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FoodChances {
    @Getter
    private static Map<String, Map<String, Integer>> foodChances = getFromYaml();

    public static Map<String, Integer> getFoodChance(String eaterType) {
        return foodChances.get(eaterType);
    }

    private static Map<String, Map<String, Integer>> getFromYaml() {
        Map<String, Map<String, Integer>> map;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            map = mapper.readValue(
                    new File(Constants.PATH_FOOD_CHANCE_FILE),
                    new TypeReference<>() {
                    }
            );
            return map;
        } catch (Exception e) {
            System.out.println("Error reading yaml file " + Constants.PATH_FOOD_CHANCE_FILE);
        }
        return new HashMap<>();
    }
}
