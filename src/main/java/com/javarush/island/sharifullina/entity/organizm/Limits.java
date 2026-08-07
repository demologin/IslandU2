package com.javarush.island.sharifullina.entity.organizm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.island.sharifullina.execption.GameException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Limits {
    private static final String PATH_LIMIT_FILE = "sharifullina/limit.yaml";

    private static Map<String, Limit> limits =  getFromYaml();

    public static Map<String, Limit> getLimits() {
        return limits;
    }

    public static Limit getLimit(String type) {
        return limits.get(type);
    }

    private static Map<String, Limit> getFromYaml() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<String, Limit> map;
        try (InputStream inputStream = FoodChances.class.getClassLoader().getResourceAsStream(PATH_LIMIT_FILE)) {
            if (inputStream == null) {
                throw new GameException("Failed to load limits file");
            }
            map = mapper.readValue(
                    inputStream,
                    new TypeReference<>() {
                    }
            );

            if (map == null || map.isEmpty()) {
                throw new GameException(PATH_LIMIT_FILE + "is empty");
            }
            return map;
        } catch (MismatchedInputException e) {
            throw new GameException("Wrong YAML structure" + e.getMessage());
        } catch (IOException e) {
            throw new GameException("Something went wrong while reading yaml file" + e.getMessage());
        }
    }
}
