package com.javarush.island.sharifullina.entity.organizm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.island.sharifullina.entity.Constants;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Limits {
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
        try {
            map = mapper.readValue(
                    new File(Constants.PATH_LIMIT_FILE),
                    new TypeReference<>(){}
            );
            return map;
        } catch (Exception e) {
            System.out.println("Error reading yaml file " + Constants.PATH_LIMIT_FILE);
        }
        return new HashMap<>();
    }
}
