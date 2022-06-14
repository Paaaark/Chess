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
    Handler handler;
    boolean isCardChecked[] = new boolean[4];
    boolean isAllCardsChecked = false;
    int whoseTurn = 0;
    int alternateTurn = -1;
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
                    finishBetting = true;
                    int diff = game.getPlayerTwoBetAmount() - game.getPlayerOneBetAmount();
                    game.playerOneBet(diff);
                    updateStanding();
                    alternateTurn = 1;
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
        isAllCardsChecked = flag;
    }

    public void updateBet(Player player, int amount, TextView betAmountText, TextView moneyText) {
        player.addBetAmount(amount);
        betAmountText.setText(Integer.toString(player.getBetAmount()));
        moneyText.setText(Integer.toString(player.getHolding()));
    }

    public void updateStanding() {
        playerOneBettedAmount.setText(game.getPlayerOneBetAmount());
        playerOneMoney.setText(game.getPlayerOneHolding());
        playerTwoBettedAmount.setText(game.getPlayerTwoBetAmount());
        playerTwoMoney.setText(game.getPlayerTwoHolding());
    }

    public void startGame() {
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

    public void showToast(String msg, TextView toast) {
        System.out.println("showToast() triggered");
        toast.setText(msg);
        toast.setVisibility(View.VISIBLE);
        Runnable r = new Runnable() {
            public void run() {
                toast.setVisibility(View.GONE);
            }
        };
        handler.postDelayed(r, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        handler = new Handler();

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

        startGame();

/*
        while (true) {

            System.out.println("Inside the first while loop");
            if (game.numCardLeft() < 9) {
                Toast.makeText(this, "Not Enough Cards Left, New Deck will be shuffled",
                        Toast.LENGTH_SHORT).show();
                game.getNewDeck();
            }
            game.giveOutCards();
            Arrays.fill(isCardChecked, false);
            Toast.makeText(this, "Check your cards", Toast.LENGTH_SHORT).show();

            // Make sure the players checked their cards
            while (true) {
                System.out.println("Inside the second while loop");
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (Exception e) {
                    // #TODO: Catch an exception
                }
                Toast.makeText(this, "Check your cards", Toast.LENGTH_SHORT).show();
                boolean flag = true;
                for (boolean isChecked: isCardChecked) {
                    if (!isChecked) flag = false;
                }
                if (flag) break;
            }
            System.out.println("Out of the second while loop");

            whoseTurn = game.whoseTurnIsIt();
            // Initial bet from the big blind
            if (whoseTurn == 0) {
                updateBet(game.getPlayerTwo(), BIG_BLIND_AMOUNT, playerTwoBettedAmount,
                        playerTwoMoney);
            } else {
                updateBet(game.getPlayerOne(), BIG_BLIND_AMOUNT, playerOneBettedAmount,
                        playerOneMoney);
            }
            alternateTurn = whoseTurn;
            // Play without any shared cards showing
            while (!finishBetting && !finishTurn) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    // #TODO: Catch exception logic
                }
                if (alternateTurn == 0) {
                    // #TODO: Player 1 plays
                } else {
                    // #TODO: Player 2 plays
                }
            }
            alternateTurn = -1;
            if (finishTurn) continue;
            alternateTurn = whoseTurn;
            // Flip 3 cards
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    // #TODO: Exception logic
                }
                // #TODO:
            }


        }*/
    }
}