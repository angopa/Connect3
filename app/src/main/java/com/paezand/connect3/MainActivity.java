package com.paezand.connect3;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //0 == yellow, 1 == red
    int activePlayer = 0;

    boolean isGameActive = true;

    //2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        final int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2
                && isGameActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);

                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);

                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).setDuration(300);

            for (int [] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2) {

                    isGameActive = false;
                    LinearLayout newGame = (LinearLayout) findViewById(R.id.layout_container_new_game);
                    TextView winner = (TextView) findViewById(R.id.winner);
                    newGame.setVisibility(View.VISIBLE);
                    winner.setText("The winner is:" +  gameState[winningPosition[0]]);
                } else {
                    boolean isGameOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2) isGameOver = false;
                    }

                    if (isGameOver) {
                        LinearLayout newGame = (LinearLayout) findViewById(R.id.layout_container_new_game);
                        TextView winner = (TextView) findViewById(R.id.winner);
                        newGame.setVisibility(View.VISIBLE);
                        winner.setText("It's a draw!");
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        LinearLayout newGame = (LinearLayout) findViewById(R.id.layout_container_new_game);
        newGame.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        GridLayout boardGame = (GridLayout) findViewById(R.id.board);

        for (int i = 0; i < boardGame.getChildCount(); i++) {
            ((ImageView) boardGame.getChildAt(i)).setImageResource(0);
        }

        isGameActive = true;

     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
