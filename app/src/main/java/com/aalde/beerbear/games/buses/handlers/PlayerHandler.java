package com.aalde.beerbear.games.buses.handlers;

import com.aalde.beerbear.carddeck.UserDeck;

import java.util.ArrayList;

public class PlayerHandler {
    private final ArrayList<UserDeck> playerCards;

    public PlayerHandler(int players) {
        playerCards = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            playerCards.add(new UserDeck());
        }
    }

    public void set(int index, UserDeck cards) {
        playerCards.set(index, cards);
    }

    public UserDeck get(int index) {
        return playerCards.get(index);
    }

    public ArrayList<UserDeck> getCards() {
        return playerCards;
    }
}
