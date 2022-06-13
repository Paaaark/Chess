package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
import java.lang.Thread;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Constants
    public static final long WAIT_TIME = 5000;
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
    // Texts
    TextView playerOneBettedAmount;
    TextView playerTwoBettedAmount;
    TextView playerOneMoney;
    TextView playerTwoMoney;
    boolean isCardChecked[] = new boolean[4];
    int whoseTurn = 0;
    int alternateTurn = 0;
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
                // #TODO: playerOneCheck
                break;
            case R.id.playerOneRaise:
                // #TODO: playerOneRaise
                break;
            case R.id.playerOneFold:
                if (alternateTurn != 0) {
                    Toast.makeText(this, "Not your turn", Toast.LENGTH_SHORT).show();
                } else {
                    finishTurn = true;
                    finishBetting = true;
                    game.playerOneWon();
                    updateStanding();
                    alternateTurn = 1;
                }
                break;
            case R.id.playerTwoCheck:
                // #TODO: playerTwoCheck
                break;
            case R.id.playerTwoRaise:
                // #TODO: playerTwoRaise
                break;
            case R.id.playerTwoFold:
                if (alternateTurn != 0) {
                    Toast.makeText(this, "Not your turn", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();

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

        while (true) {
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
                if (alternateTurn == 0) {
                    // #TODO: Player 1 plays
                } else {
                    // #TODO: Player 2 plays
                }
            }
        }
    }
}