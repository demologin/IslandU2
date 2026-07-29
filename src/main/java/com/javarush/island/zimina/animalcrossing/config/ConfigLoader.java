package com.javarush.island.zimina.animalcrossing.config;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

/**
 * Загружает конфигурацию острова (характеристики видов животных и растений)
 * из файла config.yaml, лежащего в ресурсах проекта.
 * Использует SnakeYAML с явным описанием типов, чтобы вложенная карта
 * "имя вида -> характеристики" превращалась в объекты AnimalSpeciesConfig,
 * а не в безликие структуры Map.
 */
public class ConfigLoader {

    /**
     * Читает config.yaml и превращает его в объект IslandConfig
     * со всеми характеристиками видов животных и растения.
     *
     * @return заполненный объект конфигурации острова
     */
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
