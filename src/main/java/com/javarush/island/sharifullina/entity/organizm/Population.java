package com.javarush.island.sharifullina.entity.organizm;

import com.javarush.island.sharifullina.execption.GameException;
import com.javarush.island.sharifullina.services.TypePopulationCreatorService;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Population {
    @Getter
    private final Map<String, TypePopulation> populationMap = new ConcurrentHashMap<>();

    public Population() {
        initPopulationMap();
    }

    private void initPopulationMap() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(4)) {
            for (String type : Organizms.getOrganizmRegistry().keySet()) {
                TypePopulationCreatorService typeOrganizmCreator = new TypePopulationCreatorService(type);
                Future<TypePopulation> future = executorService.submit(typeOrganizmCreator);
                TypePopulation typePopulation = future.get();
                if (typePopulation != null) {
                    populationMap.put(type, typePopulation);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new GameException("Error initialization of population " + e.getMessage());
        }
    }

    public boolean addOrganizm(Organizm organizm) {
        String type = organizm.getClass().getSimpleName();
        return populationMap.get(type).addOrganizm(organizm);
    }

    public boolean removeOrganizm(Organizm organizm) {
        String type = organizm.getClass().getSimpleName();
        return populationMap.get(type).removeOrganizm(organizm);
    }

    public Map<String, Integer> getTopStatInPercentMap() {
        return populationMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null
                        && entry.getValue().getPercent() !=0
                        && entry.getKey() != null
                        && !entry.getKey().isEmpty()
                )
                .sorted(Comparator.comparing((Map.Entry<String, TypePopulation> entry) -> entry.getValue().getPercent()).reversed())
                .limit(Settings.TOP_ORGANIZM_COUNT)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getPercent(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    @Nonnull
    public Map<String, Integer> getCountOfOrganizmsMap() {
        return populationMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getCount()
                ));
    }

    public boolean canAddOrganizm(String type, int count) {
        return populationMap.get(type).canAddOrganizm(count);
    }

    public boolean canEat(String type,double weight) {
        return populationMap.get(type).canEat(weight);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Population{");

        populationMap.entrySet().stream().forEach(
                entry -> {
                    sb.append("\n\t").append(entry.getKey()).append(" ");
                    sb.append(entry.getValue().getPercent()).append("%: ");
                    sb.append(entry.getValue().toString());
                }
        );
        sb.append("}");
        return sb.toString();
    }
}
