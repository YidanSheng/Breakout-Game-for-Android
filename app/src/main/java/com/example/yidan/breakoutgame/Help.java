package com.example.yidan.breakoutgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Yidan on 12/6/2017.
 */

//Help activity
public class Help extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        AppManager.getAppManager().addActivity(this);

        //Click event for entering main menu
        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Context context = getApplicationContext();
                Intent intent = new Intent();
                intent.setClass(context,BreakoutGame.class);
                startActivity(intent);
            }
        });
    }
}