package com.example.helloworld;

import java.util.*;

public class Deck {
    private ArrayList<Card> deck;
    public Deck() {
        deck = initDeck();
    }

    public static ArrayList<Card> initDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        for (int i = 1; i <= 13; i++) {
            for (Suit suit : Suit.values()) {
                deck.add(new Card(i, suit));
            }
        }
        return deck;
    }
    public Card pickCard() {
        Random rand = new Random();
        int randInt = rand.nextInt(deck.size());
        return deck.remove(randInt);
    }
    public int getSize() {
        return deck.size();
    }
}
