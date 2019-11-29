package com.aalde.beerbear.carddeck;

public class PlayingCard {
    private final CardSuit  suit;
    private final CardValue value;

    PlayingCard(CardSuit suit, CardValue value) {
        this.suit  = suit;
        this.value = value;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public int getValue() {
        return value.getValue();
    }

    public boolean isBlack() {
        return suit == CardSuit.CLUBS || suit == CardSuit.SPADES;
    }

    public boolean isRed() {
        return !isBlack();
    }

    @Override
    public String toString() {
        return suit.toString() + "\n" + value.toString();
    }
}
