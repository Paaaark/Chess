package com.example.helloworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

public class Player {

    public static final int RESULT_SIZE = 9;

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
        // 0: combination type, 1-3: tie breakers, 4-8: cards in the combination
        int result[] = new int[9];
        // Royal flush: 10-J-Q-K-A in the same suit
        result = isRoyalFlush(cards);
        if (result[0] == Card.ROYAL_FLUSH) {
            return result;
        }
        // Straight flush: straight in the same suit
        result = isStraightFlush(cards);
        if (result[0] == Card.STRAIGHT_FLUSH) {
            return result;
        }
        // Four of a kind: four cards with the same rank
        result = isFourOfAKind(cards);
        if (result[0] == Card.FOUR_OF_A_KIND) {
            return result;
        }
        // #TODO: detect type of poker hands
        return result;
    }

    /**
     * Returns an array. Content of the array (array.length = RESULT_SIEZ) is shown below.
     * 0: set to Card.ROYAL_FLUSH if the given cards form a royal flush, -1 otherwise;
     * 1: tie breaker (value of the Ace in the flush); 2-3: padding, unknown value;
     * 4-8: id of the cards that form royal flush, order as appears in the passed argument;
     * Royal flush: 10-J-Q-K-A in the same suit
     * @param cards Must be sorted in ascending order.
     * @return
     */
    private int[] isRoyalFlush(int cards[]) {
        int result[] = new int[RESULT_SIZE];
        for (int i = 0; i < 3; i++) {
            int baseSuit = cards[i] % 4;
            int cnt = 0;
            for (int j = 0; j < cards.length; j++) {
                if (cards[j] % 4 == baseSuit && cards[j] / 13 >= Card.TEN) {
                    result[4 + cnt] = cards[j];
                    cnt++;
                }
            }
            if (cnt == 5) {
                result[0] = Card.ROYAL_FLUSH;
                return result;
            }
        }
        result[0] = -1;
        return result;
    }

    /**
     * Returns an array. Contents of the array (array.length = RESULT_SIZE) is shown below.
     * 0: set to Card.STRAIGHT_FLUSH if the given cards form a straight flush, -1 otherwise;
     * 1: tie breaker (value of the highest card in the flush); 2-3: padding, unknown value;
     * 4-8: id of the cards that form royal flush, order not in any specific order;
     * Straight flush: A straight in the same suit
     * @param cards Must be sorted in ascending order
     * @return
     */
    private int[] isStraightFlush(int cards[]) {
        int result[] = new int[RESULT_SIZE];
        for (int i = 0; i < 3; i++) {
            int baseSuit = cards[i] % 4;    
            int cnt = 1; int prev = cards[i] / 13;
            for (int j = i + 1; j < cards.length; j++) {
                if (cards[j] % 4 == baseSuit && cards[j] / 13 == prev + 1) {
                    cnt++; prev = cards[j] / 13;
                }
            }
            if (cnt == 5) {
                for (int j = 0; j < 5; j++) {
                    result[8 - j] = prev - 4 * j; // Fill with cards that form straight
                }
                result[0] = Card.STRAIGHT_FLUSH;
                result[1] = prev;
                return result;
            }
        }
        result[0] = -1;
        return result;
    }

    /**
     * Returns an array. Contents of the array (array.length = RESULT_SIZE) is shown below.
     * 0: set to Card.FOUR_OF_A_KIND if detected, -1 otherwise;
     * 1: tie breaker (value of the highest card in the quadruple: Spade of that rank);
     * 2: tie breaker (value of the card not in the quadruple); 3: padding, unknown value;
     * 4-8: value of the cards in the combination, quadruple comes first then a high card.
     * @param cards Must be in ascending order
     * @return
     */
    private int[] isFourOfAKind(int cards[]) {
        int result[] = new int[RESULT_SIZE];
        for (int i = 0; i < 4; i++) {
            int baseRank = cards[i] / 13;
            int cnt = 0; int highest = -1;
            for (int j = 0; j < cards.length; j++) {
                if (cards[j] / 13 == baseRank) {
                    cnt++;
                } else {
                    highest = Math.max(highest, cards[j]);
                }
            }
            if (cnt == 4) {
                result[0] = Card.FOUR_OF_A_KIND;
                result[1] = baseRank * 4 + Card.SPADE;
                result[2] = highest;
                for (int j = 0; j < 4; j++) {
                    result[j + 4] = baseRank * 4 + j;
                }
                result[8] = highest;
                return result;
            }
        }
        result[0] = -1;
        return result;
    }

    private int[] isFullHouse(int cards[]) {
        int result[] = new int[RESULT_SIZE];
        // #TODO: Finish full house detection logic
        return result;
    }
}
