package com.javarush.island.istovb.model;

public enum Terrain {
    PLAIN(' '),
    RIVER('~');
    private final char symbol;
    Terrain(char symbol) { this.symbol = symbol; }
    public char getSymbol() { return symbol; }
}