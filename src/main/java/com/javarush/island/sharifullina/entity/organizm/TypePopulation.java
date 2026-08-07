package com.javarush.island.sharifullina.entity.organizm;

import com.javarush.island.sharifullina.repository.OrganizmFactory;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class TypePopulation {
    @Getter
    private Set<Organizm> organizms;
    @Getter
    private final String type;

    public TypePopulation(String type) {
        this.type = type;
        organizms = new HashSet<>();
        initResidence();
    }

    public String getIcon() {
        return organizms.stream()
                .map(organizm -> organizm.getIcon())
                .findAny()
                .orElse("x");
    }

    private void initResidence() {
        OrganizmFactory organizmFactory = new OrganizmFactory(type);
        organizms = organizmFactory.getOrganizmTypeSet();
    }

    public int getCount() {
        return organizms.stream().
                map(organizm -> (int) Math.ceil(organizm.getWeight() / Limits.getLimit(type).getMaxWeight()))
                .reduce(0, (x, y) -> x + y);
    }

    public int getPercent() {
        return getCount() * 100 / Limits.getLimit(type).getMaxCount();
    }

    public double getWeight() {
        return organizms.stream()
                .map(Organizm::getWeight)
                .reduce(0.0, (x, y) -> x + y);
    }

    public boolean addOrganizm(Organizm organizm) {
        int countInFlock = organizm.getCount();
        if (!canAddOrganizm(countInFlock)) {
            return organizms.add(organizm);
        }
        return false;
    }

    public boolean removeOrganizm(Organizm organizm) {
        return organizms.remove(organizm);
    }

    public boolean canAddOrganizm(int count) {
        return count + getCount() > Limits.getLimit(type).getMaxCount();
    }

    public boolean canEat(double weight) {
        return weight + getWeight() < Limits.getLimit(type).getMaxWeight() * Limits.getLimit(type).getMaxCount();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(" ".repeat(8) + "{\n");
        for (Organizm organizm : organizms) {
            sb.append(" ".repeat(8)).append(organizm.toString());
            sb.append("\n");
        }
        sb.append(" ".repeat(8)).append("}\n");
        return sb.toString();
    }
}
