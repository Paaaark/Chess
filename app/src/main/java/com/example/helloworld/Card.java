package com.example.helloworld;
import android.net.wifi.aware.PublishConfig;

import java.util.*;

public class Card {

    // Public constants
    public static final int ROYAL_FLUSH = 10;
    public static final int STRAIGHT_FLUSH = 9;
    public static final int FOUR_OF_A_KIND = 8;
    public static final int FULL_HOUSE = 7;
    public static final int FLUSH = 6;
    public static final int STRAIGHT = 5;
    public static final int TRIPLE = 4;
    public static final int TWO_PAIRS = 3;
    public static final int ONE_PAIR = 2;
    public static final int HIGH_CARD = 1;
    public static final int ACE = 12;
    public static final int TWO = 0;
    public static final int THREE = 1;
    public static final int FOUR = 2;
    public static final int FIVE = 3;
    public static final int SIX = 4;
    public static final int SEVEN = 5;
    public static final int EIGHT = 6;
    public static final int NINE = 7;
    public static final int TEN = 8;
    public static final int JACK = 9;
    public static final int QUEEN = 10;
    public static final int KING = 11;
    public static final int CLUB = 0;
    public static final int DIAMOND = 1;
    public static final int HEART = 2;
    public static final int SPADE = 3;
    public static final int NO_ID = 1000;
    public static final int PLAYER_ONE_CARD_ONE = 1001;
    public static final int PLAYER_ONE_CARD_TWO = 1002;
    public static final int PLAYER_TWO_CARD_ONE = 1003;
    public static final int PLAYER_TWO_CARD_TWO = 1004;
    public static final int SHARED_CARDS_ID = 1005;
    public static final int SHARED_CARD_ONE = 1005;
    public static final int SHARED_CARD_TWO = 1006;
    public static final int SHARED_CARD_THREE = 1007;
    public static final int SHARED_CARD_FOUR = 1008;
    public static final int SHARED_CARD_FIVE = 1009;
    public static final int NUM_RANKS = 13;

    private int number;
    private int suit;
    private int placeID;
    public Card(int number, int suit) {
        this.number = number;
        this.suit = suit;
        this.placeID = NO_ID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public String toString() {
        String result = "";
        switch (suit) {
            case DIAMOND:
                result += "DIAMOND ";
                break;
            case HEART:
                result += "HEART ";
                break;
            case CLUB:
                result += "CLUB ";
                break;
            case SPADE:
                result += "SPADE ";
                break;
            default:
                result += "UNKNOWN ";
        }
        result += Integer.toString(number);
        return result;
    }

    /**
     * Returns a unique value of the card. 0-indexed
     * @return
     */
    public int getCardID() {
        int id = suit + 4 * number;
        return id;
    }

    public static String toString(int cardVal) {
        String result = "";
        switch (getSuit(cardVal)) {
            case CLUB:
                result += "Club "; break;
            case DIAMOND:
                result += "Diamond "; break;
            case HEART:
                result += "Heart "; break;
            case SPADE:
                result += "Spade "; break;
        }
        switch (getRank(cardVal)) {
            case ACE:
                result += "A"; break;
            case TWO:
                result += "2"; break;
            case THREE:
                result += "3"; break;
            case FOUR:
                result += "4"; break;
            case FIVE:
                result += "5"; break;
            case SIX:
                result += "6"; break;
            case SEVEN:
                result += "7"; break;
            case EIGHT:
                result += "8"; break;
            case NINE:
                result += "9"; break;
            case TEN:
                result += "10"; break;
            case JACK:
                result += "J"; break;
            case QUEEN:
                result += "Q"; break;
            case KING:
                result += "K"; break;
        }
        return result;
    }

    public static int getRank(int cardVal) {
        return cardVal / 4;
    }

    public static int getSuit(int cardVal) {
        return cardVal % 4;
    }

    public static String combinationToString(int combinationType) {
        switch (combinationType) {
            case ROYAL_FLUSH:
                return "Royal Flush";
            case STRAIGHT_FLUSH:
                return "Straight Flush";
            case FOUR_OF_A_KIND:
                return "Four of a Kind";
            case FULL_HOUSE:
                return "Full House";
            case FLUSH:
                return "Flush";
            case STRAIGHT:
                return "Straight";
            case TRIPLE:
                return "Triple";
            case TWO_PAIRS:
                return "Two Pairs";
            case ONE_PAIR:
                return "One Pair";
            case HIGH_CARD:
                return "High Card";
            default:
                return "Error (Error code " + combinationType + ")";
        }
    }
}
