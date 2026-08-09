package com.javarush.island.tikhonova.entity.organizm;

public abstract class Organizm implements FunctionEating, FunctionDead, FunctionReproduction, FunctionGoing {
    private String name;
    private double weight;
    private String symbols;
    private Limit limit;
    private int countOrganizms;

    public Organizm(String name, double weight, String symbols, Limit limit, int countOrganizms) {
        this.name = name;
        this.weight = weight;
        this.symbols = symbols;
        this.limit = limit;
        this.countOrganizms = countOrganizms;
    }


    public int getCountOrganizms() {
        return countOrganizms;
    }


    public void setCountOrganizms(int countOrganizms) {
        this.countOrganizms = countOrganizms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return
                "'" + this.getSymbols() + '\'' + " " +
                        this.getCountOrganizms();
    }
}
