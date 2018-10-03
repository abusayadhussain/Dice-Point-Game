package com.example.ash.dicepointgame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private static final SecureRandom secureRandomNumbers = new SecureRandom();

    private enum  Status{ NOTSTARTEDYET, PROCEED, WON, LOST}

    private static final int TIGER_CLAWS = 2;
    private static final int TREE = 3;
    private static final  int CEVEN = 7;
    private static final int WE_LEVEN = 11;
    private static final int PANTHER = 12;

    TextView txtCalculations;
    ImageView imgDice;
    Button btnRestartTheGame;

    String oldTxtCalculationsValue = "";
    boolean firstTime = true;
    Status gameStatus = Status.NOTSTARTEDYET;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtCalculations = findViewById(R.id.txtCalculations);
        imgDice = findViewById(R.id.imgDice);
        btnRestartTheGame = findViewById(R.id.btnRestartTheGame);
        final TextView txtGameStatus = findViewById(R.id.txtGameStatus);

        if(firstTime) {
            makeBtnRestartInvisible();
            txtGameStatus.setText("");
            txtCalculations.setText("");
            firstTime = false;
        }

        imgDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameStatus == Status.NOTSTARTEDYET){
                    int diceSum = letsRollTheDice();
                    oldTxtCalculationsValue = txtCalculations.getText().toString();
                    points = 0;
                    switch (diceSum){
                        case CEVEN:
                        case WE_LEVEN:
                            gameStatus = Status.WON;
                            txtGameStatus.setText("You Won!");
                            imgDiceInvisible();
                            makeBtnRestartVisible();
                            break;

                        case TIGER_CLAWS:
                        case TREE:
                        case PANTHER:
                            gameStatus = Status.LOST;
                            txtGameStatus.setText("You Lost");
                            imgDiceInvisible();
                            makeBtnRestartVisible();
                            break;
                       default:
                            gameStatus = Status.PROCEED;
                            points = diceSum;
                            txtCalculations.setText(oldTxtCalculationsValue + "Your point is: "+ points + "\n");
                            txtGameStatus.setText("Continue the game!");
                            oldTxtCalculationsValue = "Your point is: "+points+"\n";
                            break;
                    }

                    return;
                }

                if(gameStatus == Status.PROCEED){
                    int diceSum = letsRollTheDice();
                    if(diceSum == points){
                        gameStatus = Status.WON;
                        txtGameStatus.setText("You won!");
                        imgDiceInvisible();
                        makeBtnRestartVisible();
                    }else if(diceSum == CEVEN){
                        gameStatus = Status.LOST;
                        txtGameStatus.setText("You Lost!");
                        imgDiceInvisible();
                        makeBtnRestartVisible();
                    }
                }
            }
        });

        btnRestartTheGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStatus = Status.NOTSTARTEDYET;
                txtGameStatus.setText("");
                txtCalculations.setText("");
                oldTxtCalculationsValue = "";
                makeImgDiceVisible();
                makeBtnRestartInvisible();
            }
        });
    }

    private int letsRollTheDice() {
        int ranDie1 = 1 + secureRandomNumbers.nextInt(6);
        int ranDie2 = 1 + secureRandomNumbers.nextInt(6);
        int sum = ranDie1 + ranDie2;

        txtCalculations.setText(String.format(oldTxtCalculationsValue + "You rolled %d + %d = %d\n",ranDie1,ranDie2,sum));
        return  sum;
    }


    private void imgDiceInvisible(){
        imgDice.setVisibility(View.INVISIBLE);
    }

    private void makeBtnRestartInvisible(){
        btnRestartTheGame.setVisibility(View.INVISIBLE);
    }

    private void makeImgDiceVisible(){
        imgDice.setVisibility(View.VISIBLE);
    }

    private  void makeBtnRestartVisible(){
        btnRestartTheGame.setVisibility(View.VISIBLE);
    }


}
