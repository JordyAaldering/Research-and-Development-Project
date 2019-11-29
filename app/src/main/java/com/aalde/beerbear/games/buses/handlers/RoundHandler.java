package com.aalde.beerbear.games.buses.handlers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aalde.beerbear.carddeck.CardDeck;
import com.aalde.beerbear.carddeck.UserDeck;
import com.aalde.beerbear.games.buses.rounds.RoundFirst;
import com.aalde.beerbear.games.buses.rounds.RoundSecond;
import com.aalde.beerbear.games.buses.rounds.Rounds;
import com.aalde.beerbear.main.NewIntent;
import com.google.gson.Gson;

public class RoundHandler extends AppCompatActivity {
    private PlayerHandler playerHandler;
    private UserDeck userDeck;
    private CardDeck cardDeck;

    private int sipsAmount, players, currPlayer, nextPlayer;
    private Rounds currRound, nextRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_buses_round_handler);
        getBundle();
        playerHandler();
        nextStage();
    }

    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        assert extras != null;

        String jsonMyObject = extras.getString("CARD_DECK");
        cardDeck = new Gson().fromJson(jsonMyObject, CardDeck.class);
        jsonMyObject = extras.getString("USER_DECK");
        userDeck = new Gson().fromJson(jsonMyObject, UserDeck.class);
        jsonMyObject = extras.getString("PLAYER_HANDLER");
        playerHandler = new Gson().fromJson(jsonMyObject, PlayerHandler.class);

        currRound = (Rounds) extras.get("ROUND");
        sipsAmount = (int) extras.get("SIPS_AMOUNT");
        players = (int) extras.get("PLAYERS");
        currPlayer = (int) extras.get("PLAYER_TURN");
    }

    private void playerHandler() {
        playerHandler.set(currPlayer, userDeck);
        if (currPlayer >= players - 1) {
            nextPlayer = 0;
            switch (currRound) {
                case BLACK_RED:
                    nextRound = Rounds.HIGHER_LOWER;
                    break;
                case HIGHER_LOWER:
                    nextRound = Rounds.BETWEEN_OUTSIDE;
                    break;
                case BETWEEN_OUTSIDE:
                    nextRound = Rounds.HAVE_DONT_HAVE;
                    break;
                case HAVE_DONT_HAVE:
                    nextRound = Rounds.ROUND_TWO_MAIN;
                    break;
            }
        } else {
            nextPlayer = currPlayer + 1;
            nextRound = currRound;
        }
    }

    private void nextStage() {
        if (nextRound == Rounds.ROUND_TWO_MAIN) {
            Intent intent = new Intent(this, RoundSecond.class);
            NewIntent.roundSecond(intent, cardDeck, playerHandler, nextRound, players);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, RoundFirst.class);
            UserDeck nextCards = playerHandler.get(nextPlayer);

            NewIntent.roundFirst(intent, cardDeck, nextCards, playerHandler,
                    nextRound, sipsAmount, players, nextPlayer);
            startActivity(intent);
        }
        finish();
    }
}
