package com.example.docomomyapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.docomomyapplication.R;
import com.example.docomomyapplication.utils.SharedUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity{
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_welcome );
        Timer timer = new Timer ( );
        timer.schedule ( new TimerTask ( ){
            @Override
            public void run() {

                if (SharedUtils.getWelcomeBoolean ( getApplicationContext ( ) )) {
                    startActivity ( new Intent ( getApplicationContext ( ), FirstAccountLoginActivity.class ) );
                } else {
                    startActivity( new Intent( WelcomeActivity.this, TutorialActivity.class ) );
                }
                finish ();

            }
        }, 3000 );
    }

}
