package com.example.helloworld;

import java.util.*;

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

    public void handCards (Card firstCard, int firstSlotID, Card secondCard, int secondSlotID) {
        this.firstCard = firstCard;
        firstCard.setPlaceID(firstSlotID);
        this.secondCard = secondCard;
        secondCard.setPlaceID(secondSlotID);
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
    public int[] getCombination(ArrayList<Card> sharedCards) {
        int cards[] = new int[sharedCards.size() + 2];
        for (int i = 0; i < sharedCards.size(); i++) {
            cards[i] = sharedCards.get(i).getCardID();
        }
        cards[sharedCards.size()] = firstCard.getCardID();
        cards[sharedCards.size() + 1] = secondCard.getCardID();
        Arrays.sort(cards);
        int result[] = new int[4];
        // Detect type of poker hands
        // Royal flush: 10-J-Q-K-A in the same suit
        if (isRoyalFlush(cards) >= 0) {
            result[0] = Card.ROYAL_FLUSH;
            result[1] = isRoyalFlush(cards);
            return result;
        }
        // Straight flush: straight in the same suit
        if (isStraightFlush(cards) >= 0) {
            // #TODO: straight flush response
            return result;
        }
        // #TODO: detect type of poker hands
        return result;
    }

    /**
     * Returns a non-negative value if the given cards can form a royal flush.
     * The returned value represents the value of the Ace that form the royal flush.
     * Royal flush: 10-J-Q-K-A in the same suit
     * @param cards
     * @return boolean value; true if possible, false otherwise
     */
    private int isRoyalFlush(int cards[]) {
        for (int i = 0; i < 3; i++) {
            int baseSuit = cards[i] % 4;
            int cnt = 0;
            for (int j = 0; j < cards.length; j++) {
                if (cards[j] % 4 == baseSuit && cards[j] % 13 >= Card.TEN) {
                    cnt++;
                }
            }
            if (cnt == 5) return Card.ACE * 4 + baseSuit;
        }
        return -1;
    }

    private int isStraightFlush(int cards[]) {
        // #TODO: Straight flush logic
        return -1;
    }
}
