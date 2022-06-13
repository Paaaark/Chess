package com.example.helloworld;

public class Game {
    private Player playerOne;
    private Player playerTwo;
    private Deck deck;
    private int turn;
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
    public int whoseTurnIsIt() {
        return turn % 2;
    }
    public int getPlayerOneHolding() {
        return playerOne.getHolding();
    }
    public int getPlayerTwoHolding() {
        return playerTwo.getHolding();
    }
    public int getPlayerOneBetAmount() {
        return playerOne.getBetAmount();
    }
    public int getPlayerTwoBetAmount() {
        return playerTwo.getBetAmount();
    }
    public Player getPlayerOne() {
        return playerOne;
    }
    public Player getPlayerTwo() {
        return playerTwo;
    }
    public void playerOneWon() {
        int totalAmount = playerOne.getBetAmount() + playerTwo.getBetAmount();
        playerOne.emptyBetAmount();
        playerTwo.emptyBetAmount();
        playerOne.addHolding(totalAmount);
    }
    public void playerTwoWon() {
        int totalAmount = playerOne.getBetAmount() + playerTwo.getBetAmount();
        playerOne.emptyBetAmount();
        playerTwo.emptyBetAmount();
        playerTwo.addHolding(totalAmount);
    }
    public void playerOneBet(int amount) {
        playerOne.addBetAmount(amount);
    }
    public void playerTwoBet(int amount) {
        playerTwo.addBetAmount(amount);
    }
}
