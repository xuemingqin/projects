package com.example.welcomemyapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.welcomemyapplication.R;
import com.example.welcomemyapplication.helper.SharedUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeStartAct extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_welcome );
        Timer timer = new Timer ( );
        timer.schedule ( new TimerTask ( ){
            @Override
            public void run() {

                if (SharedUtils.getWelcomeBoolean ( getApplicationContext ( ) )) {
                    startActivity ( new Intent ( getApplicationContext ( ), MainActivity.class ) );
                } else
                    startActivity ( new Intent ( WelcomeStartAct.this, WelcomeGuideActivity.class ));
                SharedUtils.putWelcome ( getApplicationContext (),true );
                finish ();
}
        }, 3000 );
    }

}
