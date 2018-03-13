package com.example.yidan.breakoutgame;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Yidan on 12/1/2017.
 */

// Here is our implementation of BreakoutView, implement runnable.

class BreakoutView extends SurfaceView implements Runnable {

    // Define a thread
    Thread gameThread = null;
    //Define a SurfaceHolder for SurfaceView
    SurfaceHolder ourHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    volatile boolean playing;

    // Game is paused at the start
    boolean paused = true;

    // A Canvas and a Paint object
    Canvas canvas;
    Paint paint;

    // This variable tracks the game frame rate
    long fps;

    // This is used to help calculate the fps
    private long timeThisFrame;

    // The size of the screen in pixels
    int screenX;
    int screenY;

    // The players paddle
    Paddle paddle;

    // A ball
    Ball ball;

    // Up to 200 bricks
    Brick[] bricks = new Brick[200];
    int numBricks = 0;

    // The score
    int score = 0;

    // Lives
    int lives = 3;
    int speed;
    Context c = getContext();

    int count;


    // Constructor method runs for Runnable
    public BreakoutView(Context context, int x, int y, int setspeed) {
        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        speed = setspeed;
        paint = new Paint();

        screenX = x;
        screenY = y;
        paddle = new Paddle(screenX, screenY);

        // Create a ball
        ball = new Ball(screenX, screenY);

        // Load the sounds


        createBricksAndRestart();

    }

    public void createBricksAndRestart(){

        // Put the ball back to the start
        ball.reset(screenX, screenY);
        paddle.reset(screenX,screenY);

        int brickWidth = screenX / 6;
        int brickHeight = screenY / 30;

        // Build a wall of bricks
        numBricks = 0;
        for(int column = 0; column < 6; column ++ ){
            for(int row = 0; row < 8; row ++ ){
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks ++;
            }
        }
    }

    @Override
    public void run() {
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

           // Draw the frame
            draw();

            // Update the frame
            if(!paused){
                update();
            }
            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = speed / timeThisFrame;
            }

        }

    }

    // Everything that needs to be updated goes in here
    // Movement, collision detection etc.
    public void update() {

        // Move the paddle if required
        paddle.update(fps);

        ball.update(fps);

        // Check for ball colliding with a brick
        for(int i = 0; i < numBricks; i++){

            if (bricks[i].getVisibility()){


                if(RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                    bricks[i].setInvisible();
                    ball.reverseYVelocity();
                    score = score + 10;

                }
            }
        }

        // Check for ball colliding with paddle
        if(RectF.intersects(paddle.getRect(),ball.getRect())) {
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(paddle.getRect().top - 2);
        }

        // Bounce the ball back when it hits the bottom of screen
        if(ball.getRect().bottom > screenY){
            ball.reverseYVelocity();
            ball.clearObstacleY(screenY - 2);

            // Lose a life
            lives --;

            ball.reset(screenX,screenY);
            paddle.reset(screenX,screenY);

            if(lives == 0){
                paused = true;
                Intent intent= new Intent(c, GameEnd.class);
                Bundle bundle = new Bundle();

                bundle.putString("Score", String.valueOf(score));
                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        }

        // Bounce the ball back when it hits the top of screen
        if(ball.getRect().top < 0){
            ball.reverseYVelocity();
            ball.clearObstacleY(12);

        }

        // If the ball hits left wall bounce
        if(ball.getRect().left < 0){
            ball.reverseXVelocity();
            ball.clearObstacleX(2);
        }

        // If the ball hits right wall bounce
        if(ball.getRect().right > screenX - 10){
            ball.reverseXVelocity();
            ball.clearObstacleX(screenX - 22);

        }


        // Pause if cleared screen
        if(score == numBricks * 10){
            paused = true;
            createBricksAndRestart();
        }

    }

    // Draw the newly updated scene
    public void draw() {

        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255,  211, 249, 249));

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  102,	0	,255));

            // Draw the paddle
            canvas.drawRect(paddle.getRect(), paint);

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  	0,	102,	255));
            // Draw the ball
            canvas.drawRect(ball.getRect(), paint);


            // Change the brush color for drawing
            paint.setColor(Color.argb(255,  255,	165,	0));

            // Draw the bricks if visible
            for(int i = 0; i < numBricks; i++){
                if(bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].getRect(), paint);
                }
            }

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  0, 0, 0));

            // Draw the score
            paint.setTextSize(40);
            canvas.drawText("Score: " + score  , 10,50, paint);
            canvas.drawText("Lives: " + lives,920,50, paint);



            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }



    }

    // If SimpleGameEngine Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    // If SimpleGameEngine Activity is started theb
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean flag = false;

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                paused = false;
                paddle.changePosition(motionEvent.getX(),motionEvent.getY());

                break;

            case MotionEvent.ACTION_MOVE:
                paddle.changePosition(motionEvent.getX(),motionEvent.getY());

                break;

            // Player has removed finger from screen
            default:
                break;
        }
        return true;
    }

}
    // This is the end of our BreakoutView inner class
