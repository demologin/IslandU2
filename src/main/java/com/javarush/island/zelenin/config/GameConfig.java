package com.javarush.island.zelenin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.island.zelenin.entity.organism.Organism;
import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.exception.GameException;
import com.javarush.island.zelenin.util.Const;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

@Getter
public class GameConfig {
    private IslandProps island;
    private Map<OrganismType, OrganismProps> organisms;
    private Map<OrganismType, Map<OrganismType, Integer>> eatingMatrix;

    public record IslandProps(int rows, int cols, int tickDelay) {}
    public record OrganismProps(String name, String icon, double weight, int maxCount,
                                int maxSpeed, double maxSatiety, double satietyLoss,
                                double birthChance, int growthRate) {}

    public GameConfig() {
        loadDefaults();
        loadFromYaml();
    }

    private void loadDefaults() {
        island = new IslandProps(Default.ROWS, Default.COLS, Default.TICK_DELAY);
        organisms = new EnumMap<>(Default.ORGANISMS);
        eatingMatrix = new EnumMap<>(Default.EATING_MATRIX);
    }

    private void loadFromYaml() {
        URL resource = GameConfig.class.getResource(Const.PROPERTIES_YAML);
        if (resource == null) return;

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ObjectReader reader = mapper.readerForUpdating(this);
        try (InputStream stream = resource.openStream()) {
            reader.readValue(stream);
        } catch (IOException e) {
            throw new GameException(e.getMessage());
        }
    }

    public int getRows() {
        return island.rows;
    }

    public int getCols() {
        return island.cols;
    }

    public int getTickDelay() {
        return island.tickDelay;
    }

    public String getName(OrganismType type) {
        return organisms.get(type).name;
    }

    public String getName(Organism organism) {
        return getName(organism.getType());
    }

    public String getIcon(OrganismType type) {
        return organisms.get(type).icon;
    }

    public String getIcon(Organism organism) {
        return getIcon(organism.getType());
    }

    public double getWeight(OrganismType type) {
        return organisms.get(type).weight;
    }

    public double getWeight(Organism organism) {
        return getWeight(organism.getType());
    }

    public int getMaxCount(OrganismType type) {
        return organisms.get(type).maxCount;
    }

    public int getMaxCount(Organism organism) {
        return getMaxCount(organism.getType());
    }

    public int getMaxSpeed(OrganismType type) {
        return organisms.get(type).maxSpeed;
    }

    public int getMaxSpeed(Organism organism) {
        return getMaxSpeed(organism.getType());
    }

    public double getMaxSatiety(OrganismType type) {
        return organisms.get(type).maxSatiety;
    }

    public double getMaxSatiety(Organism organism) {
        return getMaxSatiety(organism.getType());
    }

    public double getSatietyLoss(OrganismType type) {
        return organisms.get(type).satietyLoss;
    }

    public double getSatietyLoss(Organism organism) {
        return getSatietyLoss(organism.getType());
    }

    public double getBirthChance(OrganismType type) {
        return organisms.get(type).birthChance;
    }

    public double getBirthChance(Organism organism) {
        return getBirthChance(organism.getType());
    }

    public int getGrowthRate(OrganismType type) {
        return organisms.get(type).growthRate;
    }

    public int getGrowthRate(Organism organism) {
        return getGrowthRate(organism.getType());
    }

    public int getEatChance(OrganismType animal, OrganismType food) {
        return eatingMatrix.getOrDefault(animal, Map.of()).getOrDefault(food, 0);
    }

    public int getEatChance(Organism animal, Organism food) {
        return getEatChance(animal.getType(), food.getType());
    }
}
