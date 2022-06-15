package com.example.helloworld;

import java.util.*;

public class Deck {
    private ArrayList<Card> deck;
    public Deck() {
        deck = initDeck();
    }

    public static ArrayList<Card> initDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        for (int i = 0; i <= 12; i++) {
            for (int j = 0; j < 4; j++) {
                deck.add(new Card(i, j));
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
