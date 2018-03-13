package com.example.yidan.breakoutgame;

/**
 * Created by Yidan on 11/30/2017.
 */

import android.graphics.RectF;

//Brick class for creating the bricks
public class Brick {

    private RectF rect;

    private boolean isVisible;

    public Brick(int row, int column, int width, int height){

        isVisible = true;

        int padding = 3;

        rect = new RectF(column * width + padding,
                row * height + padding +58,
                column * width + width - padding,
                row * height + height - padding+58);
    }

    public RectF getRect(){
        return this.rect;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }
}
