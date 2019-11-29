package com.aalde.beerbear.games.poker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.aalde.beerbear.R;
import com.aalde.beerbear.carddeck.CardDeck;
import com.aalde.beerbear.carddeck.CardImage;
import com.aalde.beerbear.carddeck.PlayingCard;
import com.aalde.beerbear.carddeck.UserDeck;
import com.aalde.beerbear.games.buses.handlers.PlayerHandler;

import java.util.ArrayList;
import java.util.Random;

public class PokerRound extends AppCompatActivity {
    private final ArrayList<Integer> winnersArray = new ArrayList<>();
    private final Random random = new Random();

    private ImageView playerCard1, playerCard2, tableCard1, tableCard2, tableCard3, tableCard4, tableCard5;
    private EditText  shotsText;
    private Button    btnStart, btnContinue;

    private UserDeck table;
    private CardDeck deck;
    private PlayerHandler playerHandler;

    private int[] playerValue, playerBets;
    private int   players, turn, round = 0;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_poker_round);
        getBundle();
        setView();
        setButtons();
        startGame();
    }

    private void getBundle() {
        Bundle extra  = getIntent().getExtras();
        assert extra != null;
        players = (int) extra.get("PLAYERS");
    }

    private void setView() {
        tableCard1  = findViewById(R.id.tableCard1);
        tableCard2  = findViewById(R.id.tableCard2);
        tableCard3  = findViewById(R.id.tableCard3);
        tableCard4  = findViewById(R.id.tableCard4);
        tableCard5  = findViewById(R.id.tableCard5);

        playerCard1 = findViewById(R.id.playerCard1);
        playerCard2 = findViewById(R.id.playerCard2);

        btnStart    = findViewById(R.id.buttonStart);
        btnContinue = findViewById(R.id.continueBtn);
    }

    private void setButtons() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart();
            }
        });

        shotsText = findViewById(R.id.editTextShots);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnContinue();
            }
        });
    }

    private void assignTable() {
        int id = CardImage.get(table.get(0).getSuit(), table.get(0).getValue());
        tableCard1.setImageResource(id);
        id = CardImage.get(table.get(1).getSuit(), table.get(1).getValue());
        tableCard2.setImageResource(id);
        id = CardImage.get(table.get(2).getSuit(), table.get(2).getValue());
        tableCard3.setImageResource(id);
        id = CardImage.get(table.get(3).getSuit(), table.get(3).getValue());
        tableCard4.setImageResource(id);
        id = CardImage.get(table.get(4).getSuit(), table.get(4).getValue());
        tableCard5.setImageResource(id);
    }

    private void btnStart() {
        btnContinue.setVisibility(View.VISIBLE);
        shotsText  .setVisibility(View.VISIBLE);
        btnStart   .setVisibility(View.INVISIBLE);

        turn = 0;
        showHand(turn);
    }

    private void btnContinue() {
        playerCard1.setVisibility(View.INVISIBLE);
        playerCard2.setVisibility(View.INVISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(PokerRound.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.bet_made);
        builder.setMessage(R.string.pass_the_phone_to_the_next_player);
        builder.setPositiveButton("Next Player Click Here", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showHand(turn);
            }
        });
        builder.show();
        continueRound();
    }

    private void continueRound() {
        if (round == 4) {
            endGame();
        }
        else if (round == 3 && turn < players) {
            makeBets(turn);
            turn++;
        }
        else if (round == 3 && turn == players) {
            round++;
            turn = 0;
            btnContinue.setText(R.string.finish_game);
        }
        else if (round == 2 && turn < players) {
            makeBets(turn);
            turn++;
        }
        else if (round == 2 && turn == players) {
            round++;
            turn = 0;
            tableCard5.setVisibility(View.VISIBLE);
        }
        else if (round == 1 && turn < players) {
            makeBets(turn);
            turn++;
        }
        else if (round == 1 && turn == players) {
            round++;
            turn = 0;
            tableCard4.setVisibility(View.VISIBLE);
        }
        else if (round == 0 && turn < players) {
            makeBets(turn);
            turn++;
        }
        else if (round == 0 && turn == players) {
            round++;
            turn = 0;
            tableCard1.setVisibility(View.VISIBLE);
            tableCard2.setVisibility(View.VISIBLE);
            tableCard3.setVisibility(View.VISIBLE);
        }
    }

    private void startGame() {
        table         = new UserDeck();
        playerHandler = new PlayerHandler(players);
        playerBets    = new int[players];
        playerValue   = new int[players];
        deck          = new CardDeck("normalHigh");

        for (int i = 0; i < players; i++) {
            newHand(i);
        }
        flopTurnRiver();
        assignTable();
    }

    private void flopTurnRiver() {
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(deck.getDeck().size());
            table.add(deck.getPlayingCard(index));
            deck.pop(index);
        }
    }

    private void newHand(int player) {
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(deck.getDeck().size());
            playerHandler.get(player).add(deck.getPlayingCard(index));
            deck.pop(index);
        }
    }

    private void makeBets(int player) {
        int shots = 0;
        if (shotsText.getText().toString().isEmpty()) {
            playerBets[player] = playerBets[player] + shots;
        } else {
            shots = Integer.parseInt(shotsText.getText().toString());
            playerBets[player] = playerBets[player] + shots;
        }
    }

    private void endGame() {
        checkWinner();
        showWinners();
    }

    private void checkWinner() {
        PlayingCard[] playingCards = new PlayingCard[7];
        int highestValue;

        for (int i = 0; i < players; i++) {
            for (int k = 0; k < 2; k++) {
                playingCards[k] = playerHandler.get(i).get(k);
            }
            for (int k = 2; k < 7; k++) {
                playingCards[k] = table.get(k - 2);
            }
            int highestCard = highestCardIndex(playingCards);

            if (royalFlush(playingCards)) {
                playerValue[i] = 1000 + value(playingCards[highestCard]);
            }
            else if (straightFlush(playingCards)) {
                playerValue[i] = 500 + value(playingCards[highestCard]);
            }
            else if (numValMatch(playingCards) == 4) {
                playerValue[i] = 250 + value(playingCards[highestCard]);
            }
            else if (pairSeeker(playingCards, 3)) {
                playerValue[i] = 200 + value(playingCards[highestCard]);
            }
            else if (flush(playingCards)) {
                playerValue[i] = 150 + value(playingCards[highestCard]);
            }
            else if (straight(playingCards)) {
                playerValue[i] = 100 + value(playingCards[highestCard]);
            }
            else if (numValMatch(playingCards) == 3) {
                playerValue[i] = 60 + value(playingCards[highestCard]);
            }
            else if (pairSeeker(playingCards, 2)) {
                playerValue[i] = 40 + value(playingCards[highestCard]);
            }
            else if (numValMatch(playingCards) == 2) {
                playerValue[i] = 20 + value(playingCards[highestCard]);
            } else {
                playerValue[i] = value(playingCards[highestCard]);
            }
        }

        highestValue = 0;
        for (int i = 0; i < players; i++) {
            if (playerValue[i] >= highestValue) {
                highestValue = playerValue[i];
            }
        }

        for (int i = 0; i < players; i++) {
            if (playerValue[i] == highestValue) {
                winnersArray.add(i);
            }
        }
    }

    private void showWinners() {
        StringBuilder playerWin  = new StringBuilder();
        StringBuilder playerBets = new StringBuilder();

        for (int i = 0; i < winnersArray.size(); i++) {
            playerWin.append(" ").append(i);
            playerBets = playerBets.append(" ").append(String.valueOf(this.playerBets[i]));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(PokerRound.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.end_of_game);
        builder.setMessage(getString(R.string.winning_players) + playerWin + getString(R.string.with_bets_of) + playerBets);
        builder.setPositiveButton("End game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    private void showHand(int player) {
        int id = CardImage.get(playerHandler.get(player).get(0).getSuit(), playerHandler.get(player).get(0).getValue());
        playerCard1.setImageResource(id);
        id = CardImage.get(playerHandler.get(player).get(1).getSuit(), playerHandler.get(player).get(1).getValue());
        playerCard2.setImageResource(id);

        playerCard1.setVisibility(View.VISIBLE);
        playerCard2.setVisibility(View.VISIBLE);
    }

    private boolean straightFlush(PlayingCard[] playingCards) {
        int matching = 0;
        for (int i = 0; i < 7 && matching != 5; i++) {
            matching = 0;
            for (int k = 0; k < 7; k++) {
                if (sameSuit(playingCards[i], playingCards[k]) && value(playingCards[i]) != value(playingCards[k])) {
                    if (value(playingCards[i]) > value(playingCards[k])) {
                        if ((value(playingCards[i]) - value(playingCards[k])) < 5) {
                            matching++;
                        }
                    }
                    if (value(playingCards[i]) < value(playingCards[k])) {
                        if ((value(playingCards[k]) - value(playingCards[i])) < 5) {
                            matching++;
                        }
                    }
                }
            }
        }
        return matching == 5;
    }

    private boolean pairSeeker(PlayingCard[] playingCards, int type) {
        int matchingCards = 1, index = -1, index1 = -1, index2 = -1;
        for (int i = 0; i < 7 && matchingCards != type; i++) {
            matchingCards = 1;
            for (int k = 0; k < 7; k++) {
                if (value(playingCards[i]) == value(playingCards[k]) && playingCards[i].getSuit() != playingCards[k].getSuit()) {
                    matchingCards++;
                    if (matchingCards != 3) {
                        index = i;
                        index1 = k;
                    } else {
                        index2 = k;
                    }
                }
            }
        }

        if (matchingCards == type) {
            for (int i = 0; i < 7 && matchingCards != 2; i++) {
                matchingCards = 1;
                if (i != index) {
                    for (int k = 0; k < 7; k++) {
                        if (k != index1 && k != index2) {
                            if (value(playingCards[i]) == value(playingCards[k]) && playingCards[i].getSuit() != playingCards[k].getSuit()) {
                                matchingCards++;
                            }
                        }
                    }
                }
            }
            return matchingCards == 2;
        } else {
            return false;
        }
    }

    private boolean flush(PlayingCard[] playingCards) {
        int matching = 0;
        for (int i = 0; i < 7 && matching != 5; i++) {
            matching = 0;
            for (int k = 0; k < 7; k++) {
                if (playingCards[i].getSuit().equals(playingCards[k].getSuit()) && value(playingCards[i]) != value((playingCards[k]))) {
                    matching++;
                }
            }
        }
        return matching == 5;
    }

    private boolean straight(PlayingCard[] playingCards) {
        int matching = 0;
        for (int i = 0; i < 7 && matching != 5; i++) {
            matching = 0;
            for (int k = 0; k < 7; k++) {
                if (value(playingCards[i]) > value(playingCards[k]) && !(playingCards[i].equals(playingCards[k]))) {
                    if ((value(playingCards[i]) - value(playingCards[k])) < 5) {
                        matching++;
                    }
                }
                if (value(playingCards[i]) < value(playingCards[k]) && !(playingCards[i].equals(playingCards[k]))) {
                    if ((value(playingCards[k]) - value(playingCards[i])) < 5) {
                        matching++;
                    }
                }
            }
        }
        return matching == 5;
    }

    private boolean royalFlush(PlayingCard[] playingCards) {
        int matching = 0;
        for (int i = 0; i < 7 && matching != 5; i++) {
            matching = 0;
            if (royalCard(playingCards[i])) {
                for (int k = 0; k < 7; k++) {
                    if (sameSuit(playingCards[i], playingCards[k]) && royalCard(playingCards[k]) && value(playingCards[i]) != value(playingCards[k])) {
                        matching++;
                    }
                }
            }
        }
        return matching == 5;
    }

    private int highestCardIndex(PlayingCard[] playingCards) {
        int highest = 0;
        int index = -1;
        for (int i = 0; i < 7; i++) {
            if (value(playingCards[i]) > highest) {
                highest = value(playingCards[i]);
            }
        }
        for (int i = 0; i < 7; i++) {
            if (value(playingCards[i]) == highest) {
                index = i;
            }
        }
        return index;
    }

    private int numValMatch(PlayingCard[] playingCards) {
        int highestMatch = 0;
        for (int i = 0; i < 7; i++) {
            int matchingCards = 1;
            for (int k = 1; k < 7; k++) {
                if (value(playingCards[i]) == value(playingCards[k]) && playingCards[i].getSuit() != playingCards[k].getSuit()) {
                    matchingCards++;
                    if (matchingCards > highestMatch) {
                        highestMatch = matchingCards;
                    }
                }
            }
        }
        return highestMatch;
    }

    private boolean sameSuit(PlayingCard card1, PlayingCard card2) {
        return card1.getSuit().equals(card2.getSuit());
    }

    private boolean royalCard(PlayingCard card) {
        return value(card) > 9;
    }

    private int value(PlayingCard card) {
        return card.getValue();
    }
}