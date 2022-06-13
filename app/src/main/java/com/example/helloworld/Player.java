package com.example.helloworld;

public class Player {
    private int holding;
    private Card firstCard;
    private Card secondCard;
    public Player() {
        this.holding = 500;
        firstCard = null;
        secondCard = null;
    }

    public void handCards (Card firstCard, Card secondCard) {
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }
    public Card getFirstCard() {
        return firstCard;
    }
    public Card getSecondCard() {
        return secondCard;
    }
}
