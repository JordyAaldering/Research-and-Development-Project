package com.aalde.beerbear.main;

import android.content.Intent;

import com.aalde.beerbear.carddeck.CardDeck;
import com.aalde.beerbear.carddeck.UserDeck;
import com.aalde.beerbear.games.buses.handlers.PlayerHandler;
import com.aalde.beerbear.games.buses.rounds.Rounds;
import com.google.gson.Gson;

public class NewIntent {
    public static void roundFirst(Intent intent, CardDeck deck, UserDeck cards,
                                  PlayerHandler playerHandler, Rounds round,
                                  int sipsAmount, int players, int currPlayer) {

        intent.putExtra("ROUND",       round);
        intent.putExtra("PLAYERS",     players);
        intent.putExtra("SIPS_AMOUNT", sipsAmount);
        intent.putExtra("PLAYER_TURN", currPlayer);

        intent.putExtra("CARD_DECK",      new Gson().toJson(deck));
        intent.putExtra("USER_DECK",      new Gson().toJson(cards));
        intent.putExtra("PLAYER_HANDLER", new Gson().toJson(playerHandler));
    }

    public static void roundSecond(Intent intent, CardDeck deck,
                                   PlayerHandler playerHandler,
                                   Rounds round, int players) {

        intent.putExtra("ROUND",   round);
        intent.putExtra("PLAYERS", players);

        intent.putExtra("CARD_DECK",      new Gson().toJson(deck));
        intent.putExtra("PLAYER_HANDLER", new Gson().toJson(playerHandler));
    }
}
