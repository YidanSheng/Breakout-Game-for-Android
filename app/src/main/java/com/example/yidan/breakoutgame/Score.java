package com.example.yidan.breakoutgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yidan on 12/2/2017.
 */

public class Score extends Activity {
    public FileIO file =  new FileIO();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save);
        AppManager.getAppManager().addActivity(this);

        //Get the bundled data from other activity
        Bundle bundle=getIntent().getExtras();
        final String strscore2=bundle.getString("ScoreSave");
        TextView score = (TextView)findViewById(R.id.score_show);
        score.setText("You got " +strscore2+" !");


        //Click event for entering main menu
        Button save_sc = (Button)findViewById(R.id.save_sc);
        save_sc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText name = (EditText)findViewById(R.id.editname);
                String username = name.getText().toString();
                String scorea = strscore2;
                Context context = getApplicationContext();

                List<String[]> lists = new ArrayList<String[]>();
                String [] newscore = new String[2];
                newscore[0] = username;
                newscore[1] = scorea;
                lists.add(newscore);

                file.writeFileApp(context,lists);
                Intent intent = new Intent();
                intent.setClass(context,ScoresList.class);
                startActivity(intent);
            }
        });


    }
}
