package com.aalde.beerbear.games.mexxen;

import com.aalde.beerbear.dice.Dice;

public class Player {
    private final String name;
    private int prev, currentRound, timesLost;

    Player(String nameInput) {
        this.name = nameInput;
        this.currentRound = 1000;
    }

    public void setPrev(int x) {
        this.prev = x;
    }

    public String getName() {
        return name;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public void addTimesLost() {
        this.timesLost = this.timesLost + 1;
    }

    public int rollDice(Dice Dice) {
        int currentRoll;
        if (prev == 0) {
            currentRoll = Dice.rollDice(this);
        } else {
            currentRoll = Dice.rollOne(prev, this);
        }

        currentRound = currentRoll;
        return currentRoll;
    }
}
