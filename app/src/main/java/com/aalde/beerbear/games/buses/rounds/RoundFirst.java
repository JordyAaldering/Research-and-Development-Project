package com.aalde.beerbear.games.buses.rounds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aalde.beerbear.R;
import com.aalde.beerbear.carddeck.CardDeck;
import com.aalde.beerbear.carddeck.CardImage;
import com.aalde.beerbear.carddeck.UserDeck;
import com.aalde.beerbear.games.buses.handlers.PlayerHandler;
import com.aalde.beerbear.main.NewIntent;
import com.google.gson.Gson;

import java.util.Random;

public class RoundFirst extends AppCompatActivity {
    private final Random random = new Random();

    private TextView  textAction, textPlayer;
    private ImageView imageCard1, imageCard2, imageCard3, imageCard4;
    private Button    btnOption1, btnOption2, btnOption3, btnNext;

    private CardDeck cardDeck;
    private UserDeck userDeck;
    private PlayerHandler playerHandler;

    private Rounds round;
    private int    sipsAmount, sips, players, currPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_round_first);
        getBundle();
        setView();
        setText();
        setButtons();
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
        players    = (int) extras.get("PLAYERS");
    }

    private void setView() {
        textPlayer = findViewById(R.id.textViewPlayer);
        textAction = findViewById(R.id.textViewAction);

        imageCard1 = findViewById(R.id.imageViewCard1);
        imageCard2 = findViewById(R.id.imageViewCard2);
        imageCard3 = findViewById(R.id.imageViewCard3);
        imageCard4 = findViewById(R.id.imageViewCard4);

        btnOption1 = findViewById(R.id.buttonOption1);
        btnOption2 = findViewById(R.id.buttonOption2);
        btnOption3 = findViewById(R.id.buttonOption3);
        btnNext    = findViewById(R.id.buttonNext);
    }

    private void setButtons() {
        btnOption1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked("btnOption1");
                resetVisibility();
            }
        });
        btnOption2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked("btnOption2");
                resetVisibility();
            }
        });
        btnOption3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked("btnOption3");
                resetVisibility();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonNext();
            }
        });
    }

    private void setText() {
        String text = getString(R.string.current_player) + " " + String.valueOf(currPlayer + 1);
        textPlayer.setText(text);
        btnNext.setVisibility(View.INVISIBLE);
        btnOption3.setVisibility(View.INVISIBLE);

        switch (round) {
            case BLACK_RED:
                textAction.setText(getString(R.string.black_or_red));
                btnOption1.setText(getString(R.string.black));
                btnOption2.setText(getString(R.string.red));
                break;
            case HIGHER_LOWER:
                textAction.setText(getString(R.string.higher_or_lower));
                btnOption1.setText(getString(R.string.higher));
                btnOption2.setText(getString(R.string.lower));
                btnOption3.setText(getString(R.string.post));
                btnOption3.setVisibility(View.VISIBLE);
                int id = CardImage.get(userDeck.get(0).getSuit(), userDeck.get(0).getValue());
                imageCard1.setImageResource(id);
                break;
            case BETWEEN_OUTSIDE:
                textAction.setText(getString(R.string.inside_or_outside));
                btnOption1.setText(getString(R.string.inside));
                btnOption2.setText(getString(R.string.outside));
                id = CardImage.get(userDeck.get(0).getSuit(), userDeck.get(0).getValue());
                imageCard1.setImageResource(id);
                id = CardImage.get(userDeck.get(1).getSuit(), userDeck.get(1).getValue());
                imageCard2.setImageResource(id);
                break;
            case HAVE_DONT_HAVE:
                textAction.setText(getString(R.string.do_you_have_the_suit));
                btnOption1.setText(getString(R.string.have));
                btnOption2.setText(getString(R.string.dont_have));
                id = CardImage.get(userDeck.get(0).getSuit(), userDeck.get(0).getValue());
                imageCard1.setImageResource(id);
                id = CardImage.get(userDeck.get(1).getSuit(), userDeck.get(1).getValue());
                imageCard2.setImageResource(id);
                id = CardImage.get(userDeck.get(2).getSuit(), userDeck.get(2).getValue());
                imageCard3.setImageResource(id);
                if (differentSuits()) {
                    btnOption3.setText(R.string.rainbow);
                    btnOption3.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void resetVisibility() {
        btnOption1.setVisibility(View.INVISIBLE);
        btnOption2.setVisibility(View.INVISIBLE);
        btnOption3.setVisibility(View.INVISIBLE);
        btnNext   .setVisibility(View.VISIBLE);
    }

    private boolean differentSuits() {
        return userDeck.get(0).getSuit() != userDeck.get(1).getSuit()
                && userDeck.get(0).getSuit() != userDeck.get(2).getSuit()
                && userDeck.get(1).getSuit() != userDeck.get(2).getSuit();
    }

    private void buttonClicked(String button) {
        newCard();
        switch (round) {
            case BLACK_RED:
                int id = CardImage.get(userDeck.get(0).getSuit(), userDeck.get(0).getValue());
                imageCard1.setImageResource(id);
                if (!checkColor(button)) {
                    sips = sipsAmount;
                }
                break;
            case HIGHER_LOWER:
                if (!checkNumber(button)) {
                    sips = sipsAmount;
                }
                id = CardImage.get(userDeck.get(1).getSuit(), userDeck.get(1).getValue());
                imageCard2.setImageResource(id);
                break;
            case BETWEEN_OUTSIDE:
                if (!checkPosition(button)) {
                    sips = sipsAmount;
                }
                id = CardImage.get(userDeck.get(2).getSuit(), userDeck.get(2).getValue());
                imageCard3.setImageResource(id);
                break;
            case HAVE_DONT_HAVE:
                if (!checkHas(button)) {
                    sips = sipsAmount;
                }
                id = CardImage.get(userDeck.get(3).getSuit(), userDeck.get(3).getValue());
                imageCard4.setImageResource(id);
                break;
        }
    }

    private void newCard() {
        int index = random.nextInt(cardDeck.getDeck().size());
        userDeck.add(cardDeck.getPlayingCard(index));
        cardDeck.pop(index);
    }

    private boolean checkColor(String button) {
        return button.equals("btnOption1") ? userDeck.get(0).isBlack() : userDeck.get(0).isRed();
    }

    private boolean checkNumber(String button) {
        int value0 = userDeck.get(0).getValue(),
                value1 = userDeck.get(1).getValue();

        switch (button) {
            case "btnOption1":
                return value1 > value0;
            case "btnOption2":
                return value1 < value0;
            case "btnOption3":
                return value0 == value1;
            default:
                return false;
        }
    }

    private boolean checkPosition(String button) {
        int value0 = userDeck.get(0).getValue(),
                value1 = userDeck.get(1).getValue(),
                value2 = userDeck.get(2).getValue();

        if (button.equals("btnOption1")) {
            return value2 > value0 && value2 < value1;
        } else {
            return value2 < value0 && value2 > value1;
        }
    }

    private boolean checkHas(String button) {
        String suit0 = userDeck.get(0).getSuit().toString(),
                suit1 = userDeck.get(1).getSuit().toString(),
                suit2 = userDeck.get(2).getSuit().toString(),
                suit3 = userDeck.get(3).getSuit().toString();
        switch (button) {
            case "btnOption1":
                return suit3.equals(suit0) || suit3.equals(suit1) || suit3.equals(suit2);
            case "btnOption2":
                return !(suit3.equals(suit0) || suit3.equals(suit1) || suit3.equals(suit2));
            case "btnOption3":
                return differentSuits() && (!(suit3.equals(suit0) || suit3.equals(suit1) || suit3.equals(suit2)));
            default:
                return false;
        }
    }

    private void buttonNext() {
        Intent intent = new Intent(this, NextPlayer.class);
        intent.putExtra("TAKE_SIPS", sips);
        NewIntent.roundFirst(intent, cardDeck, userDeck, playerHandler,
                round, sipsAmount, players, currPlayer);
        startActivity(intent);
        finish();
    }

}
