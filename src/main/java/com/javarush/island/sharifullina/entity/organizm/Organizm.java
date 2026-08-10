package com.javarush.island.sharifullina.entity.organizm;

import com.javarush.island.sharifullina.api.entity.*;
import com.javarush.island.sharifullina.entity.Limit;
import com.javarush.island.sharifullina.entity.Limits;
import com.javarush.island.sharifullina.entity.TypePopulation;
import com.javarush.island.sharifullina.execption.GameException;
import com.javarush.island.sharifullina.repository.OrganizmFactory;
import com.javarush.island.sharifullina.entity.map.Cell;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;


public abstract class Organizm implements Reproducible, Dieable {
    private final static AtomicLong idCounter = new AtomicLong(System.currentTimeMillis());

    private long id = idCounter.getAndIncrement();
    @Getter
    protected volatile double weight;

    protected Limit limit;
    protected String type;
    protected OrganizmFactory organizmFactory;
    @Getter
    protected String icon;

    public Organizm(double weight) {
        this.weight = weight;
        type = this.getClass().getSimpleName();
        limit = Limits.getLimit(type);
        organizmFactory = new OrganizmFactory(type);
    }

    @Override
    public boolean safeReproduce(Cell cell) {
        boolean result = false;
        cell.getLock().lock();
        try {
            TypePopulation typePopulation = cell.getPopulation(type);
            int pairs = getCount() / 2;
            for (int i = 0; i < pairs; i++) {
                if (typePopulation.getCount() >= limit.getMaxCount()) {
                   break;
                }
                int rndBorning = ThreadLocalRandom.current().nextInt(100);
                if (rndBorning > limit.getChanceToBorn()) {
                    continue;
                }
                if(!changeWeight(cell, limit.getMaxWeight())){
                    break;
                }
                result = true;
            }
        } catch (Exception e) {
            throw new GameException("Reproduce error" + e.getMessage());
        } finally {
            cell.getLock().unlock();
        }
        return result;
    }

    public boolean changeWeight(Cell cell, double deltaWeight) {
            if (cell.getPopulation(type).getWeight() + deltaWeight <= limit.getMaxCount() * limit.getMaxWeight()) {
                weight += deltaWeight;
                if (weight > limit.getMaxWeight() * limit.getFlockSize()) {
                    separateFlock(cell);
                }
                return true;
            }
            return false;
    }

    protected void separateFlock(Cell cell) {
        int count = (int) Math.ceil(weight / limit.getMaxWeight());
        double newWeight = limit.getMaxWeight() * (count / 2);
        double newFlockWeight = weight - newWeight;
        weight = newWeight;
        try {
            Object instance = organizmFactory.createInstance(newFlockWeight);
            if (instance instanceof Organizm organizm) {
                cell.safeAddOrganizm(organizm);
            }
        } catch (Exception e) {
            throw new GameException(e.getMessage());
        }
    }

    @Override
    public boolean die(Cell cell) {
        weight = 0;
        return cell.safeRemoveOrganizm(this);
    }

    public int getCount() {
        double count = weight / limit.getMaxWeight();
        return (int) Math.ceil(count);
    }

    @Override
    public String toString() {
        return type + "[id=" + id + ", weight=" + weight + " kg, count:" + getCount() +"]";
    }
}
