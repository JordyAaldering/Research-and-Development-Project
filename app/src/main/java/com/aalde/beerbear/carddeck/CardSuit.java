package com.aalde.beerbear.carddeck;

public enum CardSuit {
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    CLUBS("Clubs"),
    SPADES("Spades");

    private final String name;

    CardSuit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
