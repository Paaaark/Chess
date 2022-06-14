package com.example.helloworld;
import java.util.*;

public class Card {
    public int number;
    public Suit suit;
    public Card(int number, Suit suit) {
        this.number = number;
        this.suit = suit;
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

    public int getCardID() {
        int id = 0;
        switch (suit) {
            case HEART:
                id = 1; break;
            case CLUB:
                id = 2; break;
            case SPADE:
                id = 3; break;
            default:
                id = 0;
        }
        id += 4 * number;
        return id;
    }
}
