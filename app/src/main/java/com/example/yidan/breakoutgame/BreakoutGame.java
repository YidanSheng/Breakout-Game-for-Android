package com.example.yidan.breakoutgame;


import android.app.Activity;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BreakoutGame extends Activity {

    // BreakoutView will be the view of the game
    public BreakoutView breakoutView;
    public int screenX;
    public int screenY;
    public int fps;
    public TextView textView;
    private List<String> list;
    public String diff = "";
    public int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakout_game);

        AppManager.getAppManager().addActivity(this);
        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;

/*
        textView = (TextView)findViewById(R.id.textView4);

        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setPrompt("Please choose difficulty");
        ArrayAdapter<String> adapter;

        list=new ArrayList<String>();
        list.add("easy");
        list.add("middle");
        list.add("hard");
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BreakoutGame.this,"Difficulty："+ list.get(position),Toast.LENGTH_SHORT).show();
                textView.setText(list.get(position));
                 diff = textView.getText().toString();

                if(diff.equals("easy")){
                    breakoutView = new BreakoutView(BreakoutGame.this, screenX, screenY, 2000);
                }
                else if(diff.equals("middle")){
                    breakoutView = new BreakoutView(BreakoutGame.this, screenX, screenY, 1000);
                }
                else {
                    breakoutView = new BreakoutView(BreakoutGame.this, screenX, screenY, 500);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

 */
        // Initialize gameView and set it as the view
        breakoutView = new BreakoutView(BreakoutGame.this, screenX, screenY, 1000);

        //Define click event for Game
        Button newGame = (Button)findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(breakoutView);
            }
        });

        //Define the click event which needs to enter high score activity
        Button scoreList = (Button)findViewById(R.id.scores);
        scoreList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Context context = getApplicationContext();
                intent.setClass(context,ScoresList.class);
                startActivity(intent);
            }
        });

        //Define the click event which needs to exit this activity
        Button exitapp = (Button)findViewById(R.id.exitapp);
        exitapp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                AppManager.getAppManager().AppExit(context);
            }
        });

        //Define the click event which needs to get the help
        Button help = (Button)findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Context context = getApplicationContext();
                intent.setClass(context,Help.class);
                startActivity(intent);
            }
        });

    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
       breakoutView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
      breakoutView.pause();
    }

}
