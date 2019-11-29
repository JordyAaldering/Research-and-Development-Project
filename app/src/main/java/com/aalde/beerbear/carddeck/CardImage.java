package com.aalde.beerbear.carddeck;

import com.aalde.beerbear.R;

public class CardImage {
    public static int get(CardSuit suit, int value) {
        switch (suit) {
            case HEARTS:
                return hearts(value);
            case DIAMONDS:
                return diamonds(value);
            case CLUBS:
                return clubs(value);
            default:
                return spades(value);
        }
    }

    private static int hearts(int value) {
        switch (value) {
            case 0:
                return R.drawable.card_joker_red;
            case 2:
                return R.drawable.hearts_2;
            case 3:
                return R.drawable.hearts_3;
            case 4:
                return R.drawable.hearts_4;
            case 5:
                return R.drawable.hearts_5;
            case 6:
                return R.drawable.hearts_6;
            case 7:
                return R.drawable.hearts_7;
            case 8:
                return R.drawable.hearts_8;
            case 9:
                return R.drawable.hearts_9;
            case 10:
                return R.drawable.hearts_10;
            case 11:
                return R.drawable.hearts_jack;
            case 12:
                return R.drawable.hearts_queen;
            case 13:
                return R.drawable.hearts_king;
            default:
                return R.drawable.hearts_ace;
        }
    }

    private static int diamonds(int value) {
        switch (value) {
            case 0:
                return R.drawable.card_joker_red;
            case 2:
                return R.drawable.diamonds_2;
            case 3:
                return R.drawable.diamonds_3;
            case 4:
                return R.drawable.diamonds_4;
            case 5:
                return R.drawable.diamonds_5;
            case 6:
                return R.drawable.diamonds_6;
            case 7:
                return R.drawable.diamonds_7;
            case 8:
                return R.drawable.diamonds_8;
            case 9:
                return R.drawable.diamonds_9;
            case 10:
                return R.drawable.diamonds_10;
            case 11:
                return R.drawable.diamonds_jack;
            case 12:
                return R.drawable.diamonds_queen;
            case 13:
                return R.drawable.diamonds_king;
            default:
                return R.drawable.diamonds_ace;
        }
    }

    private static int clubs(int value) {
        switch (value) {
            case 0:
                return R.drawable.card_joker_black;
            case 2:
                return R.drawable.clubs_2;
            case 3:
                return R.drawable.clubs_3;
            case 4:
                return R.drawable.clubs_4;
            case 5:
                return R.drawable.clubs_5;
            case 6:
                return R.drawable.clubs_6;
            case 7:
                return R.drawable.clubs_7;
            case 8:
                return R.drawable.clubs_8;
            case 9:
                return R.drawable.clubs_9;
            case 10:
                return R.drawable.clubs_10;
            case 11:
                return R.drawable.clubs_jack;
            case 12:
                return R.drawable.clubs_queen;
            case 13:
                return R.drawable.clubs_king;
            default:
                return R.drawable.clubs_ace;
        }
    }

    private static int spades(int value) {
        switch (value) {
            case 0:
                return R.drawable.card_joker_black;
            case 2:
                return R.drawable.spades_2;
            case 3:
                return R.drawable.spades_3;
            case 4:
                return R.drawable.spades_4;
            case 5:
                return R.drawable.spades_5;
            case 6:
                return R.drawable.spades_6;
            case 7:
                return R.drawable.spades_7;
            case 8:
                return R.drawable.spades_8;
            case 9:
                return R.drawable.spades_9;
            case 10:
                return R.drawable.spades_10;
            case 11:
                return R.drawable.spades_jack;
            case 12:
                return R.drawable.spades_queen;
            case 13:
                return R.drawable.spades_king;
            default:
                return R.drawable.spades_ace;
        }
    }
}
