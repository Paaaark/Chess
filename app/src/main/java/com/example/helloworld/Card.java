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
}
