package com.javarush.island.tikhonova.entity.organizm;

public class Limit {
    private int maxStep;
    private double maxWeightExtraction;
    private double maxWeight;
    private double minWeight;
    private int maxNumbersOfOrganizmInCell;


    public Limit(int maxStep, double maxWeightExtraction, double maxWeight, double minWeight, int maxNumbersOfOrganizmInCell) {
        this.maxStep = maxStep;
        this.maxWeightExtraction = maxWeightExtraction;
        this.maxWeight = maxWeight;
        this.minWeight = minWeight;
        this.maxNumbersOfOrganizmInCell = maxNumbersOfOrganizmInCell;
    }

    public int getMaxStep() {
        return maxStep;
    }

    public void setMaxStep(int maxStep) {
        this.maxStep = maxStep;
    }

    public double getMaxWeightExtraction() {
        return maxWeightExtraction;
    }

    public void setMaxWeightExtraction(double maxWeightExtraction) {
        this.maxWeightExtraction = maxWeightExtraction;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(double minWeight) {
        this.minWeight = minWeight;
    }

    public int getMaxNumbersOfOrganizmInCell() {
        return maxNumbersOfOrganizmInCell;
    }

    public void setMaxNumbersOfOrganizmInCell(int maxNumbersOfOrganizmInCell) {
        this.maxNumbersOfOrganizmInCell = maxNumbersOfOrganizmInCell;
    }
}
