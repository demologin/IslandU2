package com.javarush.island.sharifullina.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.island.sharifullina.execption.GameException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FoodChances {
    private static final String PATH_FOOD_CHANCE_FILE = "sharifullina/foodChance.yaml";

    private static Map<String, Map<String, Integer>> foodChances = getFromYaml();

    public static Map<String, Integer> getFoodChance(String eaterType) {
        return foodChances.get(eaterType);
    }

    private static Map<String, Map<String, Integer>> getFromYaml() {
        Map<String, Map<String, Integer>> map;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream inputStream = FoodChances.class.getClassLoader().getResourceAsStream(PATH_FOOD_CHANCE_FILE)) {
            if (inputStream == null) {
                throw new GameException("Failed to load food chances file");
            }
            map = mapper.readValue(
                    inputStream,
                    new TypeReference<>() {
                    }
            );

            if (map == null || map.isEmpty()) {
                throw new GameException(PATH_FOOD_CHANCE_FILE + "is empty");
            }
            return map;
        } catch (MismatchedInputException e) {
            throw new GameException("Wrong YAML structure" + e.getMessage());
        } catch (IOException e) {
            throw new GameException("Something went wrong while reading yaml file" + e.getMessage());
        }
    }
}
