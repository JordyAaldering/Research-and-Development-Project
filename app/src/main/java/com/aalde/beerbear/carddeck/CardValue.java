package com.aalde.beerbear.carddeck;

import android.annotation.SuppressLint;

import java.util.HashMap;

public enum CardValue {
    JOKER    (0),
    ACE_LOW  (1),
    TWO      (2),
    THREE    (3),
    FOUR     (4),
    FIVE     (5),
    SIX      (6),
    SEVEN    (7),
    EIGHT    (8),
    NINE     (9),
    TEN      (10),
    JACK     (11),
    QUEEN    (12),
    KING     (13),
    ACE_HIGH (14);

    @SuppressLint("UseSparseArrays")
    private static final HashMap<Integer, CardValue> lookup = new HashMap<>();

    static {
        for (CardValue card : CardValue.values()) {
            lookup.put(card.getValue(), card);
        }
    }

    private final int value;

    CardValue(int value) {
        this.value = value;
    }

    public static CardValue getCard(int dayValue) {
        return lookup.get(dayValue);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value >= 2 && value <= 10) {
            return String.valueOf(value);
        } else {
            switch (value) {
                case 0:
                    return "Joker";
                case 11:
                    return "Jack";
                case 12:
                    return "Queen";
                case 13:
                    return "King";
                default:
                    return "Ace";
            }
        }
    }
}
