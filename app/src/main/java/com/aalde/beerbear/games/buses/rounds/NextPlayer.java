package com.aalde.beerbear.games.buses.rounds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aalde.beerbear.R;
import com.aalde.beerbear.carddeck.CardDeck;
import com.aalde.beerbear.carddeck.UserDeck;
import com.aalde.beerbear.games.buses.handlers.PlayerHandler;
import com.aalde.beerbear.games.buses.handlers.RoundHandler;
import com.aalde.beerbear.main.NewIntent;
import com.google.gson.Gson;

public class NextPlayer extends AppCompatActivity {
    private CardDeck cardDeck;
    private UserDeck userDeck;
    private PlayerHandler playerHandler;

    private Rounds round;
    private int    sipsAmount, sips, players, currPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_next_player);
        getBundle();
        setButton();
        setText();
    }

    private void getBundle() {
        Bundle extras  = getIntent().getExtras();
        assert extras != null;

        String jsonMyObject = extras.getString("CARD_DECK");
        cardDeck      = new Gson().fromJson(jsonMyObject, CardDeck.class);
        jsonMyObject  = extras.getString("USER_DECK");
        userDeck      = new Gson().fromJson(jsonMyObject, UserDeck.class);
        jsonMyObject  = extras.getString("PLAYER_HANDLER");
        playerHandler = new Gson().fromJson(jsonMyObject, PlayerHandler.class);

        round      = (Rounds) extras.get("ROUND");
        sipsAmount = (int) extras.get("SIPS_AMOUNT");
        currPlayer = (int) extras.get("PLAYER_TURN");
        sips       = (int) extras.get("TAKE_SIPS");
        players    = (int) extras.get("PLAYERS");
    }

    private void setButton() {
        Button btnNext = findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextPlayer();
            }
        });
    }

    private void setText() {
        TextView textWrongRight = findViewById(R.id.textViewAnswer);
        TextView textSips       = findViewById(R.id.textViewSips);
        TextView textPlayer     = findViewById(R.id.textViewPlayer);

        if (sips > 0) {
            textWrongRight.setText(R.string.that_answer_is_wrong);
        } else {
            textWrongRight.setText(R.string.that_answer_is_correct);
        }
        String text = getString(R.string.you_have_to_take) + " " + String.valueOf(sips) + " " + getString(R.string.sips);
        textSips.setText(text);

        int nextPlayer = 1;
        if (currPlayer < players - 1) {
            nextPlayer = currPlayer + 2;
        }
        text = getString(R.string.the_next_player_is) + " " + String.valueOf(nextPlayer);
        textPlayer.setText(text);
    }

    private void nextPlayer() {
        Intent intent = new Intent(this, RoundHandler.class);
        NewIntent.roundFirst(intent, cardDeck, userDeck, playerHandler,
                round, sipsAmount, players, currPlayer);
        startActivity(intent);
        finish();
    }
}
