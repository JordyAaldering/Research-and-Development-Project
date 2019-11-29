package com.aalde.beerbear.games.buses.rounds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aalde.beerbear.R;
import com.aalde.beerbear.carddeck.CardDeck;
import com.aalde.beerbear.carddeck.UserDeck;
import com.aalde.beerbear.games.buses.handlers.PlayerHandler;
import com.aalde.beerbear.main.NewIntent;

public class BusesMain extends AppCompatActivity {
    private EditText txtSips, txtPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_main);
        Button btnStart = findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start();
            }
        });
    }

    private void start() {
        txtSips     = findViewById(R.id.editTextSips);
        txtPlayers  = findViewById(R.id.editTextPlayers);
        int sips    = setSips();
        int players = setPlayers();

        Intent intent    = new Intent(getBaseContext(), RoundFirst.class);
        CardDeck deck    = new CardDeck("normalHigh");
        UserDeck cards   = new UserDeck();
        PlayerHandler ph = new PlayerHandler(players);
        Rounds round     = Rounds.BLACK_RED;

        NewIntent.roundFirst(intent, deck, cards, ph,
                round, sips, players, 0);
        startActivity(intent);
        finish();
    }

    private int setSips() {
        int sips = 5;
        if (!txtSips.getText().toString().isEmpty()) {
            int input = Integer.parseInt(txtSips.getText().toString());
            if (input >= 1 && input <= 50) {
                sips = input;
            }
        }
        return sips;
    }

    private int setPlayers() {
        int players = 2;
        if (!txtPlayers.getText().toString().isEmpty()) {
            int input = Integer.parseInt(txtPlayers.getText().toString());
            if (input >= 1 && input <= 5) {
                players = input;
            }
        }
        return players;
    }
}
