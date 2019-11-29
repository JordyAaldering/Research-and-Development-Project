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
import com.aalde.beerbear.carddeck.PlayingCard;
import com.aalde.beerbear.carddeck.UserDeck;

import java.util.Random;

public class RoundThird extends AppCompatActivity {
    private final Random random = new Random();

    private ImageView imageCard1, imageCard2, imageCard3, imageCard4,
                      imageCard5, imageCard6, imageCard7, imageCardPrev;
    private TextView  txtPlayersBus, txtRightWrong, txtSips, txtPrevious;
    private Button    btnHigher, btnPost, btnLower;

    private final UserDeck TableCards = new UserDeck();
    private CardDeck cardDeck;
    private PlayingCard currCard, nextCard;
    private int currIndex = 0, prevIndex, playerLost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_round_third);
        getBundle();
        setView();
        setStartText();
        setDeck();
        setButtons();
        resetCards();
        imageCard1.setAlpha(255);
    }

    private void getBundle() {
        Bundle extras  = getIntent().getExtras();
        assert extras != null;

        playerLost = (int) extras.get("PLAYER_LOST");
        cardDeck   = new CardDeck("normalHigh");
    }

    private void setView() {
        imageCard1 = findViewById(R.id.imageViewCard1);
        imageCard2 = findViewById(R.id.imageViewCard2);
        imageCard3 = findViewById(R.id.imageViewCard3);
        imageCard4 = findViewById(R.id.imageViewCard4);
        imageCard5 = findViewById(R.id.imageViewCard5);
        imageCard6 = findViewById(R.id.imageViewCard6);
        imageCard7 = findViewById(R.id.imageViewCard7);
        imageCardPrev = findViewById(R.id.imageViewCardPrev);
        imageCardPrev.setVisibility(View.INVISIBLE);

        txtSips       = findViewById(R.id.textViewSips);
        txtPrevious   = findViewById(R.id.textViewPrevious);
        txtPlayersBus = findViewById(R.id.textViewPlayers);
        txtRightWrong = findViewById(R.id.textViewAnswer);

        btnPost   = findViewById(R.id.buttonPost);
        btnLower  = findViewById(R.id.buttonLower);
        btnHigher = findViewById(R.id.buttonHigher);
    }

    private void setStartText() {
        String text = getString(R.string.player_in_the_bus)
                + " player " + String.valueOf(playerLost + 1) + ".";
        txtPrevious.setVisibility(View.INVISIBLE);
        txtPlayersBus.setText(text);
        txtRightWrong.setText("");
        txtSips.setText("");
    }

    private void setDeck() {
        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(cardDeck.getDeck().size());
            PlayingCard newCard = cardDeck.getPlayingCard(index);
            TableCards.add(newCard);
        }
    }

    private void setButtons() {
        btnHigher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked("higher");
            }
        });
        btnPost  .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked("post");
            }
        });
        btnLower .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked("lower");
            }
        });
    }

    private void buttonClicked(String higherLower) {
        currCard  = TableCards.get(currIndex);
        int index = random.nextInt(cardDeck.getDeck().size());
        nextCard  = cardDeck.getPlayingCard(index);

        switch (higherLower) {
            case "higher":
                answer(nextCard.getValue() > currCard.getValue());
                break;
            case "lower":
                answer(nextCard.getValue() < currCard.getValue());
                break;
            case "post":
                answer(nextCard.getValue() == currCard.getValue());
                break;
        }
        TableCards.set(prevIndex, nextCard);
        setCardImage();
    }

    private void answer(boolean correct) {
        String text = getString(R.string.you_have_to_take) + " ";
        prevIndex = currIndex;
        if (correct) {
            if (currIndex >= 6) {
                Intent intent = new Intent(this, Finished.class);
                startActivity(intent);
                finish();
            } else {
                currIndex++;
                txtRightWrong.setText(R.string.that_answer_is_correct);
                text += "0";
            }
        } else {
            txtRightWrong.setText(R.string.that_answer_is_wrong);
            text += String.valueOf(currIndex + 1);
            currIndex = 0;
        }
        text += " " + getString(R.string.sips);
        txtSips.setText(text);

        txtPrevious  .setVisibility(View.VISIBLE);
        imageCardPrev.setVisibility(View.VISIBLE);
        int id = CardImage.get(currCard.getSuit(), currCard.getValue());
        imageCardPrev.setImageResource(id);
    }

    private void setCardImage() {
        resetCards();
        switch (currIndex) {
            case 0:
                imageCard1.setAlpha(255);
                setPrevIndexCard();
                break;
            case 1:
                imageCard2.setAlpha(255);
                int id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard1.setImageResource(id);
                break;
            case 2:
                imageCard3.setAlpha(255);
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard2.setImageResource(id);
                break;
            case 3:
                imageCard4.setAlpha(255);
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard3.setImageResource(id);
                break;
            case 4:
                imageCard5.setAlpha(255);
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard4.setImageResource(id);
                break;
            case 5:
                imageCard6.setAlpha(255);
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard5.setImageResource(id);
                break;
            case 6:
                imageCard7.setAlpha(255);
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard6.setImageResource(id);
                break;
        }
    }

    private void resetCards() {
        imageCard1.setAlpha(128);
        imageCard2.setAlpha(128);
        imageCard3.setAlpha(128);
        imageCard4.setAlpha(128);
        imageCard5.setAlpha(128);
        imageCard6.setAlpha(128);
        imageCard7.setAlpha(128);
    }

    private void setPrevIndexCard() {
        switch (prevIndex) {
            case 0:
                int id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard1.setImageResource(id);
                break;
            case 1:
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard2.setImageResource(id);
                break;
            case 2:
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard3.setImageResource(id);
                break;
            case 3:
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard4.setImageResource(id);
                break;
            case 4:
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard5.setImageResource(id);
                break;
            case 5:
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard6.setImageResource(id);
                break;
            case 6:
                id = CardImage.get(nextCard.getSuit(), nextCard.getValue());
                imageCard7.setImageResource(id);
                break;
        }
    }
}
