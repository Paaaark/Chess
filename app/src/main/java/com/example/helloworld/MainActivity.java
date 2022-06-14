package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLOutput;
import java.util.*;
import java.lang.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Constants
    public static final long WAIT_TIME = 5000;
    public static final int TOAST_TIME = 2000;
    public static final int BIG_BLIND_AMOUNT = 20;

    Game game;
    // Buttons
    Button playerOneCardOne;
    Button playerOneCardTwo;
    Button playerTwoCardOne;
    Button playerTwoCardTwo;
    Button playerOneCheck;
    Button playerOneRaise;
    Button playerOneFold;
    Button playerTwoCheck;
    Button playerTwoRaise;
    Button playerTwoFold;
    // TextViews
    TextView playerOneBettedAmount;
    TextView playerTwoBettedAmount;
    TextView playerOneMoney;
    TextView playerTwoMoney;
    TextView playerOneToast;
    TextView playerTwoToast;
    TextView playerOneTurn;
    TextView playerTwoTurn;
    ArrayList<TextView> sharedCardsText;
    Handler handler;
    boolean isCardChecked[] = new boolean[4];
    boolean isAllCardsChecked = false;
    int whoseTurn = 0;
    int alternateTurn = -1;
    ArrayList<Card> sharedCards;
    boolean finishBetting = false;
    boolean finishTurn = false;

    @Override
    public void onClick(View v) {
        if (game == null) {
            Toast.makeText(this, "Game hasn't started!", Toast.LENGTH_SHORT).show();
            return;
        }
        Card card;
        switch (v.getId()) {
            case R.id.playerOneCardOne:
                showCard(playerOneCardOne, game.getPlayerOneCardOne(), 0);
                break;
            case R.id.playerOneCardTwo:
                showCard(playerOneCardTwo, game.getPlayerOneCardTwo(), 1);
                break;
            case R.id.playerTwoCardOne:
                showCard(playerTwoCardOne, game.getPlayerTwoCardOne(), 2);
                break;
            case R.id.playerTwoCardTwo:
                showCard(playerTwoCardTwo, game.getPlayerTwoCardTwo(), 3);
                break;
            case R.id.playerOneCheck:
                if (!isAllCardsChecked) {
                    showToast("Check your cards to proceed", playerOneToast);
                } else if (alternateTurn != 0) {
                    showToast("Wait for your turn!", playerOneToast);
                } else {
                    int diff = game.getPlayerTwoBetAmount() - game.getPlayerOneBetAmount();
                    game.playerOneBet(diff);
                    updateStanding();
                    alternateTurn = 1;
                    if (diff != 0) {
                        if (sharedCards.size() == 5) {
                            determineWinner();
                        }
                        showSharedCards();
                    }
                }
                break;
            case R.id.playerOneRaise:
                if (!isAllCardsChecked) {
                    showToast("Check your cards to proceed", playerOneToast);
                } else if (alternateTurn != 0) {
                    showToast("Wait for your turn!", playerOneToast);
                } else {
                    game.playerOneBet(BIG_BLIND_AMOUNT);
                    updateStanding();
                    alternateTurn = 1;
                }
                break;
            case R.id.playerOneFold:
                if (!isAllCardsChecked) {
                    showToast("Check your cards to proceed", playerOneToast);
                } else if (alternateTurn != 0) {
                    showToast("Wait for your turn!", playerOneToast);
                } else {
                    finishTurn = true;
                    finishBetting = true;
                    game.playerOneWon();
                    updateStanding();
                    alternateTurn = 1;
                }
                break;
            case R.id.playerTwoCheck:
                if (!isAllCardsChecked) {
                    showToast("Check your cards to proceed", playerTwoToast);
                } else if (alternateTurn != 1) {
                    showToast("Wait for your turn!", playerTwoToast);
                } else {
                    finishBetting = true;
                    int diff = game.getPlayerOneBetAmount() - game.getPlayerTwoBetAmount();
                    game.playerTwoBet(diff);
                    updateStanding();
                    alternateTurn = 0;
                }
                break;
            case R.id.playerTwoRaise:
                if (!isAllCardsChecked) {
                    showToast("Check your cards to proceed", playerTwoToast);
                } else if (alternateTurn != 1) {
                    showToast("Wait for your turn!", playerTwoToast);
                } else {
                    game.playerTwoBet(BIG_BLIND_AMOUNT);
                    updateStanding();
                    alternateTurn = 0;
                }
                break;
            case R.id.playerTwoFold:
                if (!isAllCardsChecked) {
                    showToast("Check your cards to proceed", playerTwoToast);
                } else if (alternateTurn != 1) {
                    showToast("Wait for your turn!", playerTwoToast);
                } else {
                    finishTurn = true;
                    finishBetting = true;
                    game.playerTwoWon();
                    updateStanding();
                    alternateTurn = 0;
                }
                break;
        }
    }

    public void bigBlindBet() {
        if (alternateTurn % 2 == 0) {
            showToast("Big Blind Bet", playerTwoToast);
            addBet(game.getPlayerTwo(), BIG_BLIND_AMOUNT, playerTwoBettedAmount, playerTwoMoney);
        } else {
            showToast("Big Blind Bet", playerOneToast);
            addBet(game.getPlayerOne(), BIG_BLIND_AMOUNT, playerOneBettedAmount, playerOneMoney);
        }
    }

    public void showCard(Button btn, Card card, int index) {
        if (btn.getText().toString().equals("Hidden")) {
            if (card == null) {
                Toast.makeText(this, "Card not given yet!", Toast.LENGTH_SHORT).show();
            } else {
                btn.setText(card.toString());
                isCardChecked[index] = true;
            }
        } else {
            btn.setText("Hidden");
        }
        boolean flag = true;
        for (boolean isChecked: isCardChecked) {
            if (!isChecked) flag = false;
        }
        if (!isAllCardsChecked && flag) {
            alternateTurn = whoseTurn;
            bigBlindBet();
            changeTurn(alternateTurn);
        }
        isAllCardsChecked = flag;
    }

    /**
     * Displays whose turn it is. Odd number = player one, even number = player two
     * @param num
     */
    public void changeTurn(int num) {
        String msg;
        if (num % 2 == 0) msg = "Player one's turn";
        else msg = "Player two's turn";
        playerOneTurn.setText(msg);
        playerTwoTurn.setText(msg);
        playerOneTurn.setVisibility(View.VISIBLE);
        playerTwoTurn.setVisibility(View.VISIBLE);
    }

    public void determineWinner() {
        return;
    }

    /**
     * Open shared cards based on the number of cards open already
     */
    public void showSharedCards() {
        int start; int end;
        if (sharedCards.size() == 0) {
            start = 0; end = 3;
        } else if (sharedCards.size() == 3) {
            start = 3; end = 4;
        } else if (sharedCards.size() == 4) {
            start = 4; end = 5;
        } else {
            start = 0; end = 0;
        }
        for (int i = start; i < end; i++) {
            Card card = game.pickCard();
            sharedCards.add(card);
            sharedCardsText.get(i).setText(card.toString());
        }
    }

    /**
     * Adds amount to the player's bet. Also updates betAmount and holding of the respective player
     * @param player player to add the betting to
     * @param amount amount of the added bet
     * @param betAmountText textView of the respective player
     * @param moneyText textView of the respective player
     */
    public void addBet(Player player, int amount, TextView betAmountText, TextView moneyText) {
        player.addBetAmount(amount);
        betAmountText.setText(Integer.toString(player.getBetAmount()));
        moneyText.setText(Integer.toString(player.getHolding()));
    }

    /**
     * Update betAmount and holding of both players
     */
    public void updateStanding() {
        playerOneBettedAmount.setText(Integer.toString(game.getPlayerOneBetAmount()));
        playerOneMoney.setText(Integer.toString(game.getPlayerOneHolding()));
        playerTwoBettedAmount.setText(Integer.toString(game.getPlayerTwoBetAmount()));
        playerTwoMoney.setText(Integer.toString(game.getPlayerTwoHolding()));
    }

    /**
     * Start a new game. Shuffle the deck. Give out cards. Empty shared cards.
     * Hide "Turn display". Display "Check your cards"
     */
    public void startGame() {
        sharedCards.clear();
        playerOneTurn.setVisibility(View.GONE);
        playerTwoTurn.setVisibility(View.GONE);
        if (game.numCardLeft() < 9) {
            Toast.makeText(this, "Not Enough Cards Left, New Deck will be shuffled",
                    Toast.LENGTH_SHORT).show();
            game.getNewDeck();
        }
        game.giveOutCards();
        Arrays.fill(isCardChecked, false);
        isAllCardsChecked = false;
        Toast.makeText(this, "Check your cards", Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays message on an appropriate textview for TOAST_TIME duration
     * @param msg Message to be displayed
     * @param toast TextView to display on
     */
    public void showToast(String msg, TextView toast) {
        System.out.println("showToast() triggered");
        toast.setText(msg);
        toast.setVisibility(View.VISIBLE);
        Runnable r = new Runnable() {
            public void run() {
                toast.setVisibility(View.GONE);
            }
        };
        handler.postDelayed(r, TOAST_TIME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        handler = new Handler();
        sharedCards = new ArrayList<Card>();
        sharedCardsText = new ArrayList<TextView>();

        // Add click listeners to buttons
        playerOneCardOne = findViewById(R.id.playerOneCardOne);
        playerOneCardOne.setOnClickListener(this);
        playerOneCardTwo = findViewById(R.id.playerOneCardTwo);
        playerOneCardTwo.setOnClickListener(this);
        playerTwoCardOne = findViewById(R.id.playerTwoCardOne);
        playerTwoCardOne.setOnClickListener(this);
        playerTwoCardTwo = findViewById(R.id.playerTwoCardTwo);
        playerTwoCardTwo.setOnClickListener(this);
        playerOneCheck = findViewById(R.id.playerOneCheck);
        playerOneCheck.setOnClickListener(this);
        playerOneRaise = findViewById(R.id.playerOneRaise);
        playerOneRaise.setOnClickListener(this);
        playerOneFold = findViewById(R.id.playerOneFold);
        playerOneFold.setOnClickListener(this);
        playerTwoCheck = findViewById(R.id.playerTwoCheck);
        playerTwoCheck.setOnClickListener(this);
        playerTwoRaise = findViewById(R.id.playerTwoRaise);
        playerTwoRaise.setOnClickListener(this);
        playerTwoFold = findViewById(R.id.playerTwoFold);
        playerTwoFold.setOnClickListener(this);
        // Initialize text views
        playerOneBettedAmount = findViewById(R.id.playerOneBettedAmount);
        playerOneBettedAmount.setText("0");
        playerTwoBettedAmount = findViewById(R.id.playerTwoBettedAmount);
        playerTwoBettedAmount.setText("0");
        playerOneMoney = findViewById(R.id.playerOneMoney);
        playerOneMoney.setText(Integer.toString(game.getPlayerOneHolding()));
        playerTwoMoney = findViewById(R.id.playerTwoMoney);
        playerTwoMoney.setText(Integer.toString(game.getPlayerTwoHolding()));
        playerOneToast = findViewById(R.id.playerOneToast);
        playerTwoToast = findViewById(R.id.playerTwoToast);
        sharedCardsText.add(findViewById(R.id.sharedOne));
        sharedCardsText.add(findViewById(R.id.sharedTwo));
        sharedCardsText.add(findViewById(R.id.sharedThree));
        sharedCardsText.add(findViewById(R.id.sharedFour));
        sharedCardsText.add(findViewById(R.id.sharedFive));
        playerOneTurn = findViewById(R.id.playerOneTurn);
        playerTwoTurn = findViewById(R.id.playerTwoTurn);

        startGame();
    }
}