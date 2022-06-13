package com.example.helloworld;

public class Player {
    private int holding;
    private int betAmount;
    private Card firstCard;
    private Card secondCard;
    public Player() {
        this.holding = 500;
        this.betAmount = 0;
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
    public int getHolding() {
        return holding;
    }
    public int getBetAmount() {
        return betAmount;
    }
    public void addBetAmount(int amount) {
        betAmount += amount;
        holding -= amount;
    }
    public void addHolding(int amount) {
        holding += amount;
    }
    public void emptyBetAmount() {
        betAmount = 0;
    }
}
