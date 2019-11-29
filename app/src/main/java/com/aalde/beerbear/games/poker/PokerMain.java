package com.aalde.beerbear.games.poker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aalde.beerbear.R;

public class PokerMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_poker_main);
        Button btnStart = findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Start();
            }
        });
    }

    private void Start() {
        EditText playerEdit = findViewById(R.id.editTextPlayers);
        int players;

        if (playerEdit.getText().toString().isEmpty()) {
            players = 1;
        } else {
            players = Integer.parseInt(playerEdit.getText().toString());
            if (players >= 5) {
                players = 1;
            }
        }

        Intent intent = new Intent(this, PokerRound.class);
        intent.putExtra("PLAYERS", players);
        startActivity(intent);
        finish();
    }
}
