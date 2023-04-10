package com.example.hw1.Logic;

import java.util.Random;

public class GameManager {

    private int life;
    private int rows;
    private int cols;
    private int ballBoard[][];

    private int dogArr[];
    private int dogIndex;

    public GameManager(int life, int row, int col) {
        this.life = life;
        this.ballBoard = new int [row][col];
        this.dogArr = new int [col];
        this.rows = row;
        this.cols = col;
        initBoard();
        moveDog((col-1)/2);
    }

    private void initBoard() {
        for(int i=0;i<rows;i++){
            for (int j=0;j<cols;j++){
                this.ballBoard[i][j] = 0;
            }
        }
    }
    public void moveDog(int dog) {
        this.dogIndex = dog;
        for(int i = 0; i<cols;i++){
            this.dogArr[i] = 0;
        }
        this.dogArr[dog] = 1;
    }

    public int getLife(){
        return life;
    }

    public int getDogIndex(){
       return dogIndex;
    }

    public int [][] getBallBoard(){
        return ballBoard;
    }

    public void updateBoard(){
        for(int i = rows-1;i>=0;i--){
            for (int j=cols-1;j>=0;j--){
                if(i==rows-1){
                    ballBoard[i][j]=0;
                }
                else{
                    if(ballBoard[i][j]==1){
                        ballBoard[i+1][j]=1;
                        ballBoard[i][j]= 0;
                    }
                }
            }
        }
    }

    public void randomBall(){
        for(int i =0;i<cols;i++){
            if(ballBoard[0][i] == 1){
                return;
            }
        }
        int randomCol = new Random().nextInt(cols);
        ballBoard[0][randomCol] = 1;
    }

    public boolean isCrashed(){
        return (this.ballBoard[rows-1][dogIndex] == dogArr[dogIndex]);
    }

    public void crash(){
        this.life--;
    }

    public boolean isLose() {
        return life == 0;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
