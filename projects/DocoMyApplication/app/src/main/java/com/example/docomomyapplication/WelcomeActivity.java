package com.example.docomomyapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity{
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_welcome );
        Timer timer=new Timer (  );
        timer.schedule ( new TimerTask ( ){
            @Override
            public void run() {
        startActivity ( new Intent ( WelcomeActivity.this,MainActivity.class ) );
            }
        } ,3000);

    }
}
