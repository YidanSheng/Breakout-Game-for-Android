package com.example.yidan.breakoutgame;

/**
 * Created by Yidan on 11/30/2017.
 */

import android.graphics.RectF;

public class Paddle {

    // RectF is an object that holds four coordinates - just what we need
    private RectF rect;

    // How long and high our paddle will be
    private float length = 200;
    private float height;

    // X is the far left of the rectangle which forms our paddle
    private float positionX;

    // Y is the top coordinate
    private float positionY;

    // This will hold the pixels per second speedthat the paddle will move
    private float paddleSpeed;

    // Which ways can the paddle move
    public final int STOPPED = 0;


    public Paddle(int screenX, int screenY){


        // Initialize rectangle
        rect = new RectF();

        // How fast is the paddle in pixels per second
        paddleSpeed = 350;
    }


    public void reset(int screenx, int screeny){
        // 130 pixels wide and 20 pixels high


        // Start paddle in roughly the sceen centre

        rect.left = screenx / 2 -100 ;
        rect.top =  screeny - 200;
        rect.right = screenx / 2 + 100;
        rect.bottom = screeny - 180;
    }


    // This is a getter method to make the rectangle that
    // defines our paddle available in BreakoutView class
    public RectF getRect(){
        return rect;
    }


    // This update method will be called from update in BreakoutView
    // It determines if the paddle needs to move and changes the coordinates
    // contained in rect if necessary
    public void update(long fps){
        rect.left = rect.left;
        rect.right = rect.right;

    }

    public void changePosition(float x, float y){

        positionX = x;
        positionY = y;
        rect.left = positionX - 100;
        rect.right = rect.left + length;

    }

}


