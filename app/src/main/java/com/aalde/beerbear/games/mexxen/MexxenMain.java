package com.aalde.beerbear.games.mexxen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aalde.beerbear.R;
import com.aalde.beerbear.dice.Dice;

import java.util.ArrayList;
import java.util.List;

public class MexxenMain extends AppCompatActivity {
    private final Dice dice = new Dice(this);
    private final List<Player> players = new ArrayList<>();
    private TextView errorScreen, scoreScreen;
    private EditText input;
    private Button addPlayer, goOn, roll;
    private int counter = 0, step = 0, numberOfThrows = 3, maxThrows = 3;
    private boolean isInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mexxen_main);
        setView();
        setButtons();
    }

    private void setView() {
        errorScreen = findViewById(R.id.textView2);
        scoreScreen = findViewById(R.id.textView);
        input = findViewById(R.id.editTextPlayer);
        addPlayer = findViewById(R.id.buttonPlayer);
        goOn = findViewById(R.id.buttonGoOn);
        roll = findViewById(R.id.buttonRoll);
    }

    private void setButtons() {
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer();
            }
        });

        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goOn();
            }
        });

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll();
            }
        });
    }

    private void addPlayer() {
        goOn.setVisibility(View.VISIBLE);
        input.setVisibility(View.VISIBLE);
        addPlayer.setVisibility(View.INVISIBLE);

        roll.setText(R.string.back);
        isInput = true;
    }

    private void goOn() {
        if (input.getText().toString().trim().length() > 0) {
            Player newPlayer = new Player(input.getText().toString());
            players.add(newPlayer);

            String text = getString(R.string.new_player_added) + " " + newPlayer.getName();
            errorScreen.setText(text);

            goOn.setVisibility(View.INVISIBLE);
            addPlayer.setVisibility(View.VISIBLE);
            input.setVisibility(View.INVISIBLE);

            input.setText("");
            roll.setText(R.string.throw_dice);
            isInput = false;
        } else {
            errorScreen.setText(R.string.enter_name);
        }
    }

    private void roll() {
        if (isInput) {
            goOn.setVisibility(View.INVISIBLE);
            addPlayer.setVisibility(View.VISIBLE);
            roll.setText(R.string.throw_dice);
            input.setVisibility(View.INVISIBLE);
            input.setText("");
            isInput = false;
        } else {
            roll.setText(R.string.throw_dice);

            if (players.isEmpty()) {
                errorScreen.setText(R.string.please_add_a_player_first);
            } else if (step == players.size() - 1 && numberOfThrows == 0) {
                step = 0;
                int lowest = 0;
                maxThrows = 3;
                numberOfThrows = maxThrows;
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getCurrentRound() <= players.get(lowest).getCurrentRound()) {
                        lowest = i;
                    }
                }
                counter = lowest;

                String text = getString(R.string.new_round) + " " + players.get(lowest).getName() + " " + getString(R.string.begins);
                scoreScreen.setText(text);
                players.get(lowest).addTimesLost();
                if (players.get(lowest).getCurrentRound() == 1000) {
                    players.get(lowest).setCurrentRound(21);
                }

                text = players.get(lowest).getName() + " " + getString(R.string.has_to_drink_because_his_score_is) + " " + players.get(lowest).getCurrentRound();
                errorScreen.setText(text);
                roll.setText(R.string.next_round);
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).setCurrentRound(1000);
                    players.get(i).setPrev(0);
                }
            } else {
                int score;
                if (numberOfThrows != 0) {
                    numberOfThrows--;
                    String text = numberOfThrows + " " + getString(R.string.throws_left);
                    errorScreen.setText(text);
                    score = players.get(counter).rollDice(dice);
                } else {
                    if (players.get(counter).getCurrentRound() == 1000) {
                        String text = players.get(counter).getName() + " " + getString(R.string.has_21);
                        scoreScreen.setText(text);
                    } else {
                        String text = players.get(counter).getName() + " " + getString(R.string.has) + " " + players.get(counter).getCurrentRound();
                        scoreScreen.setText(text);
                    }
                    numberOfThrows = maxThrows;
                    if (counter == players.size() - 1) {
                        counter = 0;
                    } else {
                        counter++;
                    }
                    step++;
                    score = 1000;
                    String text = getString(R.string.it_is_the_turn_of) + " " + players.get(counter).getName();
                    errorScreen.setText(text);
                }

                if (score == 21) {
                    maxThrows = maxThrows - numberOfThrows;
                    numberOfThrows = 0;
                    players.get(counter).setCurrentRound(1000);
                    String text = getString(R.string.mex_in) + " " + maxThrows;
                    errorScreen.setText(text);
                }

                if (score == 31) {
                    numberOfThrows++;
                    errorScreen.setText(R.string.throw_again);
                }
                if (score == 600) {
                    errorScreen.setText(R.string.make_a_new_rule);
                }
            }
        }
    }
}