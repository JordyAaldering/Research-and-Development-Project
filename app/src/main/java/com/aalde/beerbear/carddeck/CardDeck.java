package com.aalde.beerbear.carddeck;

import java.util.ArrayList;
import java.util.Arrays;

public class CardDeck {
    private ArrayList<PlayingCard> deck;

    public CardDeck(String deckType) {
        switch (deckType) {
            case "normalHigh":
                makeDeck(new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)));
                break;
            case "normalLow":
                makeDeck(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)));
                break;
        }
    }

    private void makeDeck(ArrayList<Integer> cardValues) {
        deck = new ArrayList<>();
        for (CardSuit suit : CardSuit.values()) {
            for (Integer value : cardValues) {
                deck.add(new PlayingCard(suit, CardValue.getCard(value)));
            }
        }
    }

    public ArrayList<PlayingCard> getDeck() {
        return deck;
    }

    public PlayingCard getPlayingCard(int index) {
        return deck.get(index);
    }

    public void pop(int index) {
        deck.remove(index);
    }
}
