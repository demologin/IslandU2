package com.javarush.island.sharifullina.entity.map;


import com.javarush.island.sharifullina.entity.organizm.TypePopulation;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Island {
    @Getter
    private int column;
    @Getter
    private int row;
    @Getter
    private Cell[][] cells;

    public Island(int row, int column){
        this.column = column;
        this.row = row;
        cells = new Cell[row][column];
        initiateCells();
        initCellsNeighbours();
    }

    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    private void initiateCells() {
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                cells[x][y] = new Cell(x, y, this);
            }
        }
    }

    private void initCellsNeighbours(){
        Arrays.stream(cells).flatMap(Arrays::stream).forEach(cell -> {
            cell.initNeighbors();
        });
    }

    public Map<String, Integer> getStatistics() {
        return Arrays.stream(cells).flatMap(Arrays::stream)
                .map(cell -> cell
                        .getCountMap())
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1 + v2
                ));
    }

    public Map<Cell, TypePopulation> getOrganizmsByType(String type) {
        ConcurrentHashMap<Cell, TypePopulation> map = new ConcurrentHashMap<>();
        Arrays.stream(cells).flatMap(Arrays::stream)
                .forEach(cell -> {
                    map.put(cell, cell.getPopulation(type));
                });
        return map;
    }

    public double getCommonWeight() {
        double commonWeight = Arrays.stream(cells).flatMap(Arrays::stream)
                .map(cell ->
                        cell.getPopulation()
                                .getPopulationMap()
                                .entrySet().stream()
                                .map(Map.Entry::getValue)
                                .map(TypePopulation::getWeight)
                                .mapToDouble(Double::doubleValue)
                                .sum()
                ).reduce((aDouble, aDouble2) -> aDouble + aDouble2).get();
        return commonWeight;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                sb.append(cells[x][y].toString());
            }
        }
        return sb.toString();
    }
}
