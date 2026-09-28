package com.javarush.island.istovb.model.plant;

import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractPlant {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();
    private final long id = ID_GENERATOR.incrementAndGet();

    public long getId() { return id; }

    public abstract char getSymbol();
    public abstract String getName();
}