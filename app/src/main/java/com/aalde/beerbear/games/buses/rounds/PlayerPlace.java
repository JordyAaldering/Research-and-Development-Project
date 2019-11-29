package com.aalde.beerbear.games.buses.rounds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aalde.beerbear.R;
import com.aalde.beerbear.carddeck.CardImage;
import com.aalde.beerbear.carddeck.PlayingCard;
import com.aalde.beerbear.carddeck.UserDeck;
import com.google.gson.Gson;

public class PlayerPlace extends AppCompatActivity {
    private Button   btn1, btn2, btn3, btn4, btnSkip, btnCont;
    private TextView txtGive;

    private UserDeck    userDeck;
    private PlayingCard playingCard;
    private int player, giveSips, occurrences = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_player_place);
        getBundle();
        setView();
        setVisibility();
        setButtons();
    }

    private void getBundle() {
        Bundle extras  = getIntent().getExtras();
        assert extras != null;

        String jsonMyObject = extras.getString("USER_DECK");
        userDeck     = new Gson().fromJson(jsonMyObject, UserDeck.class);
        jsonMyObject = extras.getString("CURR_CARD");
        playingCard  = new Gson().fromJson(jsonMyObject, PlayingCard.class);

        player   = (int) extras.get("CURR_PLAYER");
        giveSips = (int) extras.get("GIVE_SIPS");
    }

    private void setView() {
        TextView txtPlayer = findViewById(R.id.textViewPass);
        String text = getString(R.string.pass_phone_to_player) + " " + String.valueOf(player + 1);
        txtPlayer.setText(text);
        txtGive = findViewById(R.id.textViewGive);

        btn1 = findViewById(R.id.buttonPlace1);
        btn2 = findViewById(R.id.buttonPlace2);
        btn3 = findViewById(R.id.buttonPlace3);
        btn4 = findViewById(R.id.buttonPlace4);
        btnSkip = findViewById(R.id.buttonSkip);
        btnCont = findViewById(R.id.buttonContinue);

        ImageView imageCard = findViewById(R.id.imageViewCard);
        int id = CardImage.get(playingCard.getSuit(), playingCard.getValue());
        imageCard.setImageResource(id);
    }

    private void setVisibility() {
        for (int i = 0; i < userDeck.size(); i++) {
            if (userDeck.get(i).getValue() == playingCard.getValue()) {
                occurrences++;
            }
        }
        switch (occurrences) {
            case 4:
                btn4.setVisibility(View.VISIBLE);
            case 3:
                btn3.setVisibility(View.VISIBLE);
            case 2:
                btn2.setVisibility(View.VISIBLE);
            default:
                btn1.setVisibility(View.VISIBLE);
        }
    }

    private void setButtons() {
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                place(1);
                setGiveText(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                place(2);
                setGiveText(2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                place(3);
                setGiveText(3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                place(4);
                setGiveText(4);
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goBack();
            }
        });
        btnCont.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void place(int amount) {
        for (int i = 0; i < amount; i++) {
            int index = userDeck.getIndex(playingCard);
            if (index >= 0) {
                userDeck.pop(index);
            }
        }
    }

    private void setGiveText(int mult) {
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        btnSkip.setVisibility(View.INVISIBLE);
        btnCont.setVisibility(View.VISIBLE);

        String text = getString(R.string.give) + " " + String.valueOf(giveSips * mult) + " " + getString(R.string.sips_to_player);
        txtGive.setText(text);
    }

    private void goBack() {
        Intent intent = new Intent();
        intent.putExtra("NEW_USER_DECK", new Gson().toJson(userDeck));
        setResult(RESULT_OK, intent);
        finish();
    }
}
