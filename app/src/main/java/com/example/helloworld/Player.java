package com.example.helloworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

public class Player {

    public static final int RESULT_SIZE = 6;

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
        // 0: combination type, 1-5: cards in combination
        int result[] = new int[RESULT_SIZE];
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
        // Full house: A triple and a pair
        result = isFullHouse(cards);
        if (result[0] == Card.FULL_HOUSE) {
            return result;
        }
        // Flush: Five cards in the same suit
        result = isFlush(cards);
        if (result[0] == Card.FLUSH) {
            return result;
        }
        // Straight: Five cards in consecutive ranks
        result = isStraight(cards);
        if (result[0] == Card.STRAIGHT) {
            return result;
        }
        // Triple: Three cards of the same rank
        result = isTriple(cards);
        if (result[0] == Card.TRIPLE) {
            return result;
        }
        // #TODO: detect type of poker hands
        return result;
    }

    /**
     * Returns an array. Content of the array (array.length = RESULT_SIEZ) is shown below.
     * 0: set to Card.ROYAL_FLUSH if the given cards form a royal flush, -1 otherwise;
     * 1-5: id of the cards that form royal flush, order in ascending order;
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
                    result[1 + cnt] = cards[j];
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
     * 1-5: id of the cards that form royal flush, order not in ascending order;
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
                    result[5 - j] = prev - 4 * j; // Fill with cards that form straight
                }
                result[0] = Card.STRAIGHT_FLUSH;
                return result;
            }
        }
        result[0] = -1;
        return result;
    }

    /**
     * Returns an array. Contents of the array (array.length = RESULT_SIZE) is shown below.
     * 0: set to Card.FOUR_OF_A_KIND if detected, -1 otherwise;
     * 1-5: value of the cards in the combination, quadruple comes first then a high card.
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
                for (int j = 0; j < 4; j++) {
                    result[j + 1] = baseRank * 4 + j;
                }
                result[5] = highest;
                return result;
            }
        }
        result[0] = -1;
        return result;
    }

    /**
     * Returns an array. Contents of the array (array.length = RESULT_SIZE) is shown below
     * 0: set to Card.FULL_HOUSE if detected, -1 otherwise;
     * 1-5: value of the cards forming full house, triple come first than a pair.
     * @param cards Must be in ascending order
     * @return
     */
    private int[] isFullHouse(int cards[]) {
        int result[] = new int[RESULT_SIZE];
        int ranks[] = new int[Card.NUM_RANKS];
        for (int i = 0; i < cards.length; i++) {
            ranks[cards[i] / 13]++;
        }
        int doubleRank = -1;
        int tripleRank = -1;
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i] == 2) doubleRank = i;
            if (ranks[i] == 3) tripleRank = i;
        }
        if (doubleRank != -1 && tripleRank != -1) {
            result[0] = Card.FULL_HOUSE;
            int triplePtr = 1; int doublePtr = 4;
            for (int i = 0; i < ranks.length; i++) {
                if (cards[i] / 13 == doubleRank) { result[doublePtr] = cards[i]; doublePtr++; }
                if (cards[i] / 13 == tripleRank) { result[triplePtr] = cards[i]; triplePtr++; }
            }
            return result;
        }
        result[0] = -1;
        return result;
    }

    /**
     * Returns an array. Contents of the array (array.length = RESULT_SIZE) is shown below.
     * 0: set to Card.FLUSH if detected, -1 otherwise;
     * 1-5: value of cards that form a flush, in ascending order
     * @param cards Must be in ascending order
     * @return
     */
    private int[] isFlush(int cards[]) {
        int result[] = new int[RESULT_SIZE];
        for (int i = 0; i < 3; i++) {
            int baseSuit = cards[i] % 4; int cnt = 0;
            for (int j = i; j < cards.length; j++) {
                if (cards[j] % 4 == baseSuit) { cnt++; result[1+cnt] = cards[j]; }
            }
            if (cnt == 5) {
                result[0] = Card.FLUSH;
                return result;
            }
        }
        result[0] = -1;
        return result;
    }

    /**
     * Returns an array. Contents of the array (array.length = RESULT_SIZE) is shown below.
     * 0: set to Card.STRAIGHT if detected, -1 otherwise;
     * 1-5: content of the cards in straight in ascending order
     * @param cards Must be in ascending order
     * @return
     */
    private int[] isStraight(int cards[]) {
        int result[] = new int[RESULT_SIZE];
        for (int i = 0; i < 3; i++) {
            int prevRank = Card.getRank(cards[i]);
            int cnt = 1; result[1] = cards[i];
            for (int j = 1; j < cards.length; j++) {
                if (prevRank == Card.getRank(cards[j])) {
                    result[cnt] = cards[j];
                } else {
                    if (cnt + 1 >= result.length) { cnt = 0; break; }
                    result[cnt + 1] = cards[j]; cnt++;
                    prevRank = Card.getRank(cards[j]);
                }
            }
            if (cnt == 5) {
                result[0] = Card.STRAIGHT;
                return result;
            }
        }
        result[0] = -1;
        return result;
    }

    /**
     * Returns an array. Contents of the array (array.length = RESULT_SIZE) is shown below.
     * 0: set to Card.TRIPLE if detected, -1 otherwise;
     * 1-5: value of the cards in the combination, triple in ascending order comes first then two
     * high cards in descending order
     * @param cards Must be in ascending order
     * @return
     */
    private int[] isTriple(int cards[]) {
        int result[] = new int[RESULT_SIZE];
        for (int i = cards.length - 1; i >= 2; i--) {
            if ((Card.getRank(cards[i]) == Card.getRank(cards[i - 1])) &&
                    (Card.getRank(cards[i]) == Card.getRank(cards[i - 2]))) {
                result[0] = Card.TRIPLE;
                result[1] = cards[i-2];
                result[2] = cards[i-1];
                result[3] = cards[i];
                int cnt = 0;
                for (int j = cards.length; j >= 0; j--) {
                    if (cnt == 2) break;
                    if (j > i || j < i - 2) {
                        result[4+cnt] = j;
                    }
                }
                return result;
            }
        }
        result[0] = -1;
        return result;
    }

    private int[] isTwoPairs(int cards[]) {
        int result[] = new int[RESULT_SIZE];

    }
}
