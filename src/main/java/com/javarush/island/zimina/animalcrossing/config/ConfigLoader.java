package com.javarush.island.zimina.animalcrossing.config;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ConfigLoader {

    public static IslandConfig loadConfig() {
        Constructor constructor = new Constructor(IslandConfig.class, new org.yaml.snakeyaml.LoaderOptions());

        TypeDescription islandConfigDescription = new TypeDescription(IslandConfig.class);
        islandConfigDescription.addPropertyParameters("animals", String.class, AnimalSpeciesConfig.class);
        constructor.addTypeDescription(islandConfigDescription);

        Yaml yaml = new Yaml(constructor);

        InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.yaml");
        return yaml.load(input);
    }
}
