package com.javarush.island.sharifullina.services;

import com.javarush.island.sharifullina.entity.organizm.TypePopulation;
import com.javarush.island.sharifullina.entity.map.Cell;
import com.javarush.island.sharifullina.entity.organizm.plant.Plant;
import com.javarush.island.sharifullina.execption.GameException;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PlantLifeService extends LifeService {


    public PlantLifeService(Map<Cell, TypePopulation> organizms) {
        super(organizms);
    }

    @Override
    public void run() {
        organizms.entrySet().stream().forEach(
                entry -> {
                    Cell cell = entry.getKey();
                    String type = entry.getValue().getType();
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    TypePopulationCreatorService typeOrganizmCreator = new TypePopulationCreatorService(type);
                    Future<TypePopulation> future = executor.submit(typeOrganizmCreator);
                    TypePopulation typePopulation = null;
                    try {
                        typePopulation = future.get();
                    }  catch (Exception e) {
                        throw new GameException("Reproduse init error " + e);
                    }
                    if (typePopulation != null) {
                        cell.getPopulation().getPopulationMap().put(type, typePopulation);
                    }
                });
    }
//    @Override
//    public void run() {
//        organizms.entrySet().stream().forEach(
//                entry -> {
//                    entry.getValue()
//                            .getOrganizms()
//                            .stream()
//                            .map(organizm -> (Plant) organizm)
//                            .forEach(plant -> {
//                                Cell cell = entry.getKey();
//                                plant.safeReproduce(cell);
//                            });
//                });
//    }
}
