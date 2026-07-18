package com.javarush.island.sharifullina.entity.map;

import com.javarush.island.sharifullina.entity.OrganizmFactory;
import com.javarush.island.sharifullina.entity.Organizms;
import com.javarush.island.sharifullina.entity.TypeOrganizmsCreator;
import com.javarush.island.sharifullina.entity.organizm.Organizm;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Cell {
    private int x;
    private int y;
    private List<Cell> neighbors;
    private Island island;
    private Map<String,Set<Organizm>> organizmsMap = new HashMap<>();

    public Cell(int x, int y, Island island) {
        this.x = x;
        this.y = y;
        this.island = island;
    }

    public void getNeighbors() {
        if (x > 0) {
            neighbors.add(island.getCell(x-1,y));
        }
        if (y > 0) {
            neighbors.add(island.getCell(x,y-1));
        }
        if (x < island.getWidth() - 1) {
            neighbors.add(island.getCell(x+1,y));
        }
        if (y < island.getHeight() - 1) {
            neighbors.add(island.getCell(x, y + 1));
        }
    }

    private void setTypeOrganizms(String type, Set<Organizm> organizmsSet) {
        organizmsMap.put(type,organizmsSet);
    }

    public void initCell() throws InterruptedException, ExecutionException {
        try (ExecutorService executorService = Executors.newFixedThreadPool(4)) {
            for (String type : Organizms.getOrganizmRegistry().keySet()) {
                OrganizmFactory organizmFactory = new OrganizmFactory(type);
                TypeOrganizmsCreator typeOrganizmsCreator = new TypeOrganizmsCreator(organizmFactory);
                Future<Set<Organizm>> future = executorService.submit(typeOrganizmsCreator);
                Set<Organizm> organizms = future.get();
                if (organizms != null) {
                    setTypeOrganizms(type, organizms);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        organizmsMap.forEach((type,organizms) -> {sb.append(" ".repeat(18)+type + ": " + organizms.size()).append("\n");});
        return "Cell["+x+","+y+"] Organizms: \n"+ sb.toString();
    }
}
