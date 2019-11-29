package com.aalde.beerbear.dice;

import android.app.Activity;
import android.widget.TextView;

import com.aalde.beerbear.R;
import com.aalde.beerbear.games.mexxen.Player;

import java.util.Random;

public class Dice {
    private final Activity activity;

    public Dice(Activity activity) {
        this.activity = activity;
    }

    public int rollDice(Player x) {
        final int min = 1;
        final int max = 6;
        Random x1 = new Random();

        int random = x1.nextInt((max - min) + 1) + min;
        int random2 = x1.nextInt((max - min) + 1) + min;

        TextView textview = this.activity.findViewById(R.id.textView);
        if ((random == 1 && random2 != 2) || (random2 == 1 && random != 2)) {
            x.setPrev(1);
        }

        if ((random == 2 && random2 != 1) || (random2 == 2 && random != 1)) {
            x.setPrev(2);
        }

        String text = x.getName() + " " + activity.getString(R.string.your_numbers_are) + " " + random + " " + activity.getString(R.string.and) + " " + random2 + " " + activity.getString(R.string.meaning_you_have) + " " ;
        if (random > random2) {
            text += random * 10 + random2;
            textview.setText(text);
            return random * 10 + random2;
        } else if (random2 > random) {
            text += random + random2 * 10;
            textview.setText(text);
            return random2 * 10 + random;
        } else {
            text += random * 100;
            textview.setText(text);
            return random * 100;
        }
    }

    public int rollOne(int previous, Player x) {
        final int min = 1, max = 6;
        Random x1 = new Random();

        int random = x1.nextInt((max - min) + 1) + min;
        TextView textview = this.activity.findViewById(R.id.textView);
        String text = x.getName() + " " + activity.getString(R.string.you_kept_a) + " " + previous + " " + activity.getString(R.string.and_rolled_a) + " " + random + " " + R.string.meaning_you_have + " " ;
        if (random > previous) {
            text += random * 10 + previous;
            textview.setText(text);
            return random * 10 + previous;
        } else if (previous > random) {
            text += random + previous * 10;
            textview.setText(text);
            return previous * 10 + random;
        } else {
            text += random * 100;
            textview.setText(text);
            return random * 100;
        }
    }
}
