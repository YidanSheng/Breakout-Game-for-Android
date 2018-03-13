package com.example.yidan.breakoutgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yidan on 12/2/2017.
 */

public class ScoresList extends Activity{
    public FileIO file =  new FileIO();
    public ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorelist);
        AppManager.getAppManager().addActivity(this);
        TextView test = (TextView)findViewById(R.id.textView7);
        Context context = getApplicationContext();

        Button menu = (Button)findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Context context = getApplicationContext();
                Intent intent = new Intent();
                intent.setClass(context,BreakoutGame.class);
                startActivity(intent);
            }
        });

        List<String[]> lists = new ArrayList<String[]>();
        String [] rec = new String[2];
        String Name = "";
        String Score = "";

        //Create ArrayList<HashMap<String, String>> to put data into listview.
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        lists = file.readFile(context);

        listView = (ListView)findViewById(R.id.listview);
        for(int i = 0; i < lists.size();i++) {
            rec = lists.get(i);
            Name = rec[0];
            Score = rec[1];
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("personName", Name);
            map.put("personScore", Score);
            mylist.add(map);
        }

        SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.list_item,
                new String[] {"personName","personScore"},
                new int[] {R.id.personName, R.id.personScore});

        //Add data into Adapter and show them
        listView.setAdapter(mSchedule);

    }

}
