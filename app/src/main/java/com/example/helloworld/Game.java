package com.example.helloworld;

public class Game {
    private Player playerOne;
    private Player playerTwo;
    private Deck deck;
    int turn;
    public Game() {
        playerOne = new Player();
        playerTwo = new Player();
        deck = new Deck();
        turn = 0;
    }

    public int numCardLeft() {
        return deck.getSize();
    }
    public void getNewDeck() {
        deck.initDeck();
    }
    public void giveOutCards() {
        playerOne.handCards(deck.pickCard(), deck.pickCard());
        playerTwo.handCards(deck.pickCard(), deck.pickCard());
    }
    public Card getPlayerOneCardOne() {
        return playerOne.getFirstCard();
    }
    public Card getPlayerOneCardTwo() {
        return playerOne.getSecondCard();
    }
    public Card getPlayerTwoCardOne() {
        return playerTwo.getFirstCard();
    }
    public Card getPlayerTwoCardTwo() {
        return playerTwo.getSecondCard();
    }
}
