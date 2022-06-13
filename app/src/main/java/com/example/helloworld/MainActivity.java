package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Game game;
    // Buttons
    Button playerOneCardOne;
    Button playerOneCardTwo;
    Button playerTwoCardOne;
    Button playerTwoCardTwo;

    @Override
    public void onClick(View v) {
        if (game == null) {
            Toast.makeText(this, "Game hasn't started!", Toast.LENGTH_SHORT).show();
            return;
        }
        Card card;
        switch (v.getId()) {
            case R.id.playerOneCardOne:
                showCard(playerOneCardOne, game.getPlayerOneCardOne());
                break;
            case R.id.playerOneCardTwo:
                showCard(playerOneCardTwo, game.getPlayerOneCardTwo());
                break;
            case R.id.playerTwoCardOne:
                showCard(playerTwoCardOne, game.getPlayerTwoCardOne());
                break;
            case R.id.playerTwoCardTwo:
                showCard(playerTwoCardTwo, game.getPlayerTwoCardTwo());
                break;
        }
    }

    public void showCard(Button btn, Card card) {
        if (btn.getText().toString().equals("Hidden")) {
            if (card == null) {
                Toast.makeText(this, "Card not given yet!", Toast.LENGTH_SHORT).show();
            } else {
                btn.setText(card.toString());
            }
        } else {
            btn.setText("Hidden");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();

        playerOneCardOne = findViewById(R.id.playerOneCardOne);
        playerOneCardTwo = findViewById(R.id.playerOneCardTwo);
        playerTwoCardOne = findViewById(R.id.playerTwoCardOne);
        playerTwoCardTwo = findViewById(R.id.playerTwoCardTwo);
        playerOneCardOne.setOnClickListener(this);
        playerOneCardTwo.setOnClickListener(this);
        playerTwoCardOne.setOnClickListener(this);
        playerTwoCardTwo.setOnClickListener(this);

        while (true) {
            if (game.numCardLeft() < 9) {
                Toast.makeText(this, "Not Enough Cards Left, New Deck will be shuffled",
                        Toast.LENGTH_SHORT).show();
                game.getNewDeck();
            }
            game.giveOutCards();
            Toast.makeText(this, "Check your cards", Toast.LENGTH_SHORT).show();
            
        }
    }
}