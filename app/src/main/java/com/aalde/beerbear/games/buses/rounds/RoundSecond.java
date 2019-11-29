package com.aalde.beerbear.games.buses.rounds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.aalde.beerbear.R;
import com.aalde.beerbear.carddeck.CardDeck;
import com.aalde.beerbear.carddeck.CardImage;
import com.aalde.beerbear.carddeck.PlayingCard;
import com.aalde.beerbear.carddeck.UserDeck;
import com.aalde.beerbear.games.buses.handlers.PlayerHandler;
import com.aalde.beerbear.main.NewIntent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

public class RoundSecond extends AppCompatActivity {
    private final Random random = new Random();

    private ImageView image1_1, image2_1, image2_2, image3_1, image3_2, image3_3,
                      image4_1, image4_2, image4_3, image4_4, image5_1, image5_2, image5_3, image5_4, image5_5;
    private Button    btnTurnCard, btnPlaceCard, btnNext;

    private CardDeck      cardDeck;
    private PlayingCard   currCard;
    private PlayerHandler playerHandler;

    private Rounds round;
    private int    players, turned = 0, giveSips, userEditing;
    private ArrayList<Integer> playerHasCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_round_second);
        getBundle();
        setView();
        setButtons();
    }

    private void getBundle() {
        Bundle extras  = getIntent().getExtras();
        assert extras != null;

        String jsonMyObject = extras.getString("CARD_DECK");
        cardDeck      = new Gson().fromJson(jsonMyObject, CardDeck.class);
        jsonMyObject  = extras.getString("PLAYER_HANDLER");
        playerHandler = new Gson().fromJson(jsonMyObject, PlayerHandler.class);

        round   = (Rounds) extras.get("ROUND");
        players = (int) extras.get("PLAYERS");
    }

    private void setView() {
        image1_1 = findViewById(R.id.imageView1_1);
        image2_1 = findViewById(R.id.imageView2_1);
        image2_2 = findViewById(R.id.imageView2_2);
        image3_1 = findViewById(R.id.imageView3_1);
        image3_2 = findViewById(R.id.imageView3_2);
        image3_3 = findViewById(R.id.imageView3_3);
        image4_1 = findViewById(R.id.imageView4_1);
        image4_2 = findViewById(R.id.imageView4_2);
        image4_3 = findViewById(R.id.imageView4_3);
        image4_4 = findViewById(R.id.imageView4_4);
        image5_1 = findViewById(R.id.imageView5_1);
        image5_2 = findViewById(R.id.imageView5_2);
        image5_3 = findViewById(R.id.imageView5_3);
        image5_4 = findViewById(R.id.imageView5_4);
        image5_5 = findViewById(R.id.imageView5_5);

        btnNext      = findViewById(R.id.buttonNext);
        btnTurnCard  = findViewById(R.id.buttonTurn);
        btnPlaceCard = findViewById(R.id.buttonPlace);

        btnNext     .setVisibility(View.INVISIBLE);
        btnPlaceCard.setVisibility(View.INVISIBLE);
    }

    private void setButtons() {
        btnTurnCard .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                turnCardSwitch();
            }
        });
        btnPlaceCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                placeCard();
            }
        });
        btnNext     .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startRoundThree();
            }
        });
    }

    private void turnCardSwitch() {
        getCard();
        switch (turned) {
            case 0:
                int id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image1_1.setImageResource(id);
                giveSips = 1;
                break;
            case 1:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image2_1.setImageResource(id);
                giveSips = 2;
                break;
            case 2:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image2_2.setImageResource(id);
                giveSips = 2;
                break;
            case 3:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image3_1.setImageResource(id);
                giveSips = 3;
                break;
            case 4:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image3_2.setImageResource(id);
                giveSips = 3;
                break;
            case 5:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image3_3.setImageResource(id);
                giveSips = 3;
                break;
            case 6:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image4_1.setImageResource(id);
                giveSips = 4;
                break;
            case 7:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image4_2.setImageResource(id);
                giveSips = 4;
                break;
            case 8:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image4_3.setImageResource(id);
                giveSips = 4;
                break;
            case 9:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image4_4.setImageResource(id);
                giveSips = 4;
                break;
            case 10:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image5_1.setImageResource(id);
                giveSips = 5;
                break;
            case 11:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image5_2.setImageResource(id);
                giveSips = 5;
                break;
            case 12:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image5_3.setImageResource(id);
                giveSips = 5;
                break;
            case 13:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image5_4.setImageResource(id);
                giveSips = 5;
                break;
            case 14:
                id = CardImage.get(currCard.getSuit(), currCard.getValue());
                image5_5.setImageResource(id);
                btnTurnCard.setVisibility(View.INVISIBLE);
                if (!playerCanPlace()) {
                    btnNext.setVisibility(View.VISIBLE);
                }
                giveSips = 5;
                break;
        }

        turned++;
        if (playerCanPlace()) {
            btnTurnCard.setVisibility(View.INVISIBLE);
            btnPlaceCard.setVisibility(View.VISIBLE);
        }
    }

    private void getCard() {
        int index = random.nextInt(cardDeck.getDeck().size());
        currCard  = cardDeck.getPlayingCard(index);
        cardDeck.pop(index);
    }

    private boolean playerCanPlace() {
        playerHasCard = new ArrayList<>();
        ArrayList<UserDeck> cardsList = playerHandler.getCards();

        boolean someoneHas = false;
        for (int i = 0; i < players; i++) {
            if (cardsList.get(i).hasValue(currCard.getValue())) {
                someoneHas = true;
                playerHasCard.add(i);
            }
        }
        return someoneHas;
    }

    private void placeCard() {
        for (int index : playerHasCard) {
            userEditing = index;
            startPlayerPlace();
        }

        btnPlaceCard.setVisibility(View.INVISIBLE);
        if (turned < 15) {
            btnTurnCard.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
        }
    }

    private void startPlayerPlace() {
        Intent intent = new Intent(this, PlayerPlace.class);
        intent.putExtra("GIVE_SIPS", giveSips);
        intent.putExtra("CURR_PLAYER", userEditing);
        intent.putExtra("CURR_CARD", new Gson().toJson(currCard));
        intent.putExtra("USER_DECK", new Gson().toJson(playerHandler.getCards().get(userEditing)));
        NewIntent.roundSecond(intent, cardDeck, playerHandler, round, players);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String jsonMyObject = data.getStringExtra("NEW_USER_DECK");
            playerHandler.set(userEditing, new Gson().fromJson(jsonMyObject, UserDeck.class));
        }
    }

    private void startRoundThree() {
        int mostCards = 0, playerLost = 0, cardValue = 0;
        for (UserDeck player : playerHandler.getCards()) {
            if (player.size() > mostCards) {
                mostCards = player.size();
            }
        }

        for (int i = 0; i < players; i++) {
            if (playerHandler.get(i).size() == mostCards) {
                if (playerHandler.get(i).getDeckValue() > cardValue) {
                    cardValue = playerHandler.get(i).getDeckValue();
                    playerLost = i;
                }
            }
        }

        Intent intent = new Intent(this, RoundThird.class);
        intent.putExtra("PLAYER_LOST", playerLost);
        startActivity(intent);
        finish();
    }
}
