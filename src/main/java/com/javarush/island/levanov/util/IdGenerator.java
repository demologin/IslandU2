package com.javarush.island.levanov.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final AtomicLong counter = new AtomicLong();

    private IdGenerator() {
    }

    public static long generateId(){
        return counter.incrementAndGet();
    }
}