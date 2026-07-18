package com.javarush.island.sharifullina.entity;

import com.javarush.island.sharifullina.entity.organizm.Organizm;

import java.util.Set;
import java.util.concurrent.Callable;

public class TypeOrganizmsCreator implements Callable<Set<Organizm>> {
    OrganizmFactory factory;

    public TypeOrganizmsCreator(OrganizmFactory factory) {
        this.factory = factory;
    }

    @Override
    public Set<Organizm> call() throws Exception {
        Set<Organizm> organizmTypeSet = factory.getOrganizmTypeSet();
        return organizmTypeSet;
    }
}
