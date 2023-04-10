package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hw1.Logic.GameManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public final int DELAY = 1000;
    private final int ROWS = 5;
    private final int COLS = 3;

    private final int LIFE = 3;

    private AppCompatImageView main_IMG_background;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] main_IMG_dogs;
    private ShapeableImageView[][] main_IMG_ball;
    private ExtendedFloatingActionButton game_BTN_left;
    private ExtendedFloatingActionButton game_BTN_right;

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        gameManager = new GameManager(main_IMG_hearts.length,ROWS,COLS);
        viewDog();
        setButtons();
        start();
    }

    private void start() {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                handler.postDelayed(this, 2100);
                gameManager.randomBall();
                refreshUI();
            }
        }, DELAY);

        runOnUiThread(new Runnable() {
            public void run() {
                handler.postDelayed(this,1700);
                gameManager.updateBoard();
            }
        });
    }

    private void refreshUI() {
        if(gameManager.isCrashed()){
            gameManager.crash();
            if(gameManager.isLose()){
                toast("GAME OVER!");
                gameManager.setLife(LIFE);
                for(int i = 0;i < main_IMG_hearts.length;i++){
                    main_IMG_hearts[i].setVisibility(View.VISIBLE);
                }

            }
            else{
                toast("Lost Life!");
                vibrate();
                for(int i = gameManager.getLife();i < main_IMG_hearts.length;i++){
                    main_IMG_hearts[i].setVisibility(View.INVISIBLE);
                }
            }
        }
        gameManager.randomBall();
        viewBoard();
    }

    private void viewBoard() {
        int[][] board = gameManager.getBallBoard();
        int height=0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == 1) {
                    main_IMG_ball[i][j].setVisibility(View.VISIBLE);
                }
                else  {
                    main_IMG_ball[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void setButtons() {
        game_BTN_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clicked(0);
            }
        });
        game_BTN_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clicked(1);
            }
        });
    }
    private void clicked(int move) {
        int dogInd = gameManager.getDogIndex();
        if(move == 0){
            dogInd --;
        }
        else if(move == 1){
            dogInd ++;
        }
        if(dogInd >= 0 && dogInd <COLS){
            gameManager.moveDog(dogInd);
            viewDog();
        }
        gameManager.updateBoard();
        refreshUI();
    }

    private void viewDog() {
        int dogInd = gameManager.getDogIndex();
        for (int i=0;i<COLS;i++){
            if(i==dogInd){
                main_IMG_dogs[i].setVisibility(View.VISIBLE);
            }
            else {
                main_IMG_dogs[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void toast(String st) {
        Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
    }



    private void findViews() {
        main_IMG_background=findViewById(R.id.main_IMG_background);
            main_IMG_hearts = new ShapeableImageView[]{
                    findViewById(R.id.main_IMG_heart1),
                    findViewById(R.id.main_IMG_heart2),
                    findViewById(R.id.main_IMG_heart3)};
            main_IMG_dogs= new ShapeableImageView[]{
                    findViewById(R.id.main_IMG_dog1),
                    findViewById(R.id.main_IMG_dog2),
                    findViewById(R.id.main_IMG_dog3)};
            game_BTN_left = findViewById(R.id.game_BTN_left);
            game_BTN_right = findViewById(R.id.game_BTN_right);
            main_IMG_ball = new ShapeableImageView[ROWS][];
            main_IMG_ball[0] = new ShapeableImageView [] {findViewById(R.id.main_IMG_ball1), findViewById(R.id.main_IMG_ball2), findViewById(R.id.main_IMG_ball3)};
            main_IMG_ball[1] = new ShapeableImageView [] {findViewById(R.id.main_IMG_ball4), findViewById(R.id.main_IMG_ball5), findViewById(R.id.main_IMG_ball6)};
            main_IMG_ball[2] = new ShapeableImageView [] {findViewById(R.id.main_IMG_ball7), findViewById(R.id.main_IMG_ball8), findViewById(R.id.main_IMG_ball9)};
            main_IMG_ball[3] = new ShapeableImageView [] {findViewById(R.id.main_IMG_ball10), findViewById(R.id.main_IMG_ball11), findViewById(R.id.main_IMG_ball12)};
            main_IMG_ball[4] = new ShapeableImageView [] {findViewById(R.id.main_IMG_ball13), findViewById(R.id.main_IMG_ball14), findViewById(R.id.main_IMG_ball15)};
            for(int i=0;i<ROWS;i++){
                for(int j=0;j<COLS;j++){
                    main_IMG_ball[i][j].setVisibility(View.INVISIBLE);
                }
            }
}


    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }
}