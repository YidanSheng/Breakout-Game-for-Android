package com.example.yidan.breakoutgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Yidan on 12/1/2017.
 */

//The activity when Game is end
public class GameEnd extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameend);
        AppManager.getAppManager().addActivity(this);

        //Click event for entering main menu
        Button exit = (Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Context context = getApplicationContext();
                intent.setClass(context,BreakoutGame.class);
                startActivity(intent);
            }
        });

        //Bundle the data and pass the data to Score.class
        Bundle bundle=getIntent().getExtras();
        final String strscore=bundle.getString("Score");
        TextView score = (TextView)findViewById(R.id.score);
        score.setText("You got " +strscore+ " !");

        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Context context = getApplicationContext();
                Bundle bundle2 = new Bundle();
                bundle2.putString("ScoreSave", strscore);
                intent.setClass(context, Score.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });


    }
}
