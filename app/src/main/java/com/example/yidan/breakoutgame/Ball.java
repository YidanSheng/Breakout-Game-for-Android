package com.example.yidan.breakoutgame;

/**
 * Created by Yidan on 11/30/2017.
 */

import android.graphics.RectF;

import java.util.Random;

//Ball class
public class Ball {
    RectF rect;
    float xVelocity;
    float yVelocity;
    float ballWidth = 10;
    float ballHeight = 10;


    public Ball(int screenX, int screenY){

        // Start the x-velocity and y-velocity for the ball
        xVelocity = 200;
        yVelocity = -400;

        //Create the ball
        rect = new RectF();

    }

    //Get the current ball
    public RectF getRect(){
        return rect;
    }

    //Define how to update about the ball
    public void update(long fps){
        rect.left = rect.left + (xVelocity / fps);
        rect.top = rect.top + (yVelocity / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;
    }

    //Reverse the velocity for x and y direction
    public void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    public void reverseXVelocity(){
        xVelocity = - xVelocity;
    }

    public void setRandomXVelocity(){
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    //Clear the Obstacles
    public void clearObstacleY(float y){
        rect.bottom = y;
        rect.top = y - ballHeight;
    }

    public void clearObstacleX(float x){
        rect.left = x;
        rect.right = x + ballWidth;
    }

    //When one live is over, reset the ball
    public void reset(int x, int y){
        rect.left = x / 2;
        rect.top = y - 210;
        rect.right = x / 2 + ballWidth;
        rect.bottom = y - 210 - ballHeight;
    }


}
