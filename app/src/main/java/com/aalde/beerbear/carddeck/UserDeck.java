package com.aalde.beerbear.carddeck;

import java.util.ArrayList;

public class UserDeck {
    private final ArrayList<PlayingCard> deck;

    public UserDeck() {
        this.deck = new ArrayList<>();
    }

    public void add(PlayingCard card) {
        deck.add(card);
    }

    public PlayingCard get(int index) {
        return deck.get(index);
    }

    public void set(int index, PlayingCard card) {
        deck.set(index, card);
    }

    public void pop(int index) {
        deck.remove(index);
    }

    public int size() {
        return deck.size();
    }

    public int getIndex(PlayingCard playingCard) {
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getValue() == playingCard.getValue()) {
                return i;
            }
        }
        return -1;
    }

    public boolean hasValue(int value) {
        for (PlayingCard card : deck) {
            if (card.getValue() == value) {
                return true;
            }
        }
        return false;
    }

    public int getDeckValue() {
        int value = 0;
        for (PlayingCard card : deck) {
            value += card.getValue();
        }
        return value;
    }
}
