package com.javarush.island.zimina.animalcrossing.simulation;

@FunctionalInterface
public interface TriFunction<A, B, C, R> {

    R apply(A a, B b, C c);
}