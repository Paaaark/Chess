package com.example.helloworld;

import java.util.ArrayList;

public class Game {

    /* Contsants */
    public static final int TOTAL_RESULT_SIZE = 13;
    public static final int PLAYER_ONE_WON = 1;
    public static final int PLAYER_TWO_WON = 2;
    public static final int TIE = 3;

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
        playerOne.handCards(deck.pickCard(), Card.PLAYER_ONE_CARD_ONE, deck.pickCard(),
                Card.PLAYER_ONE_CARD_TWO);
        playerTwo.handCards(deck.pickCard(), Card.PLAYER_TWO_CARD_ONE, deck.pickCard(),
                Card.PLAYER_TWO_CARD_TWO);
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
    public Card pickCard() {
        return deck.pickCard();
    }

    /**
     * Returns an array containing information about winning and player's cards
     * 0: set to PLAYER_ONE_WON, PLAYER_TWO_WON, or TIE depending on the result;
     * 1: set to player one's combination type, i.e) Card.FLUSH or Card.TWO_PAIRS
     * 2-6: set to value of cards that form player one's combination
     * 7: set to player two's combination type; 8-12: set to value of cards of player two
     * @param sharedCards
     * @return
     */
    public int[] getWinner(ArrayList<Card> sharedCards) {
        int playerOneResult[] = playerOne.getCombination(sharedCards);
        int playerTwoResult[] = playerTwo.getCombination(sharedCards);
        int totalResult[] = new int[TOTAL_RESULT_SIZE];
        for (int i = 1; i < 7; i++) {
            totalResult[i] = playerOneResult[i - 1];
        }
        for (int i = 7; i < 13; i++) {
            totalResult[i] = playerTwoResult[i - 7];
        }
        if (playerOneResult[0] != playerTwoResult[0]) {
            totalResult[0] = playerOneResult[0] > playerTwoResult[0] ? PLAYER_ONE_WON : PLAYER_TWO_WON;
        } else {
            for (int i = 1; i < 6; i++) {
                if (playerOneResult[i] != playerTwoResult[i]) {
                    totalResult[0] = playerOneResult[i] > playerTwoResult[i] ? PLAYER_ONE_WON : PLAYER_TWO_WON;
                    break;
                }
            }
            if (totalResult[0] != PLAYER_ONE_WON && totalResult[0] != PLAYER_TWO_WON) {
                totalResult[0] = TIE;
            }
        }
        return totalResult;
    }
}
