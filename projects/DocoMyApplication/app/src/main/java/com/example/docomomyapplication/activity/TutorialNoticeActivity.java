package com.example.docomomyapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.docomomyapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TutorialNoticeActivity extends Activity{
    private ImageView iv01, phone, iv02, iv03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.tutorial_noti_activity );
        initId( );
        animaForAll();
    }

    private void initId() {
        iv01 = (ImageView) findViewById( R.id.iv01 );
        iv02 = (ImageView) findViewById( R.id.iv02 );
        phone = (ImageView) findViewById( R.id.phone );
        iv03 = (ImageView) findViewById( R.id.iv03 );

        iv03.setAlpha( 0.0f );
        iv02.setAlpha( 0.0f );
        iv01.setAlpha( 0.0f );
        phone.setAlpha( 0.0f );

    }

    private void animaForAll() {

        AnimatorSet set = new AnimatorSet( );

        List<Animator> list = new ArrayList<Animator>( );
        fukudasi2( iv02,list);
        fukudasi1(iv01,list);
        fukudasi3( iv03,list );
        phone(phone,list);
        set.playTogether( list );
        set.start();

    }
    public void fukudasi1(ImageView view, List<Animator> list){

        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",0f,1.0f );
        animator.setStartDelay( 7700 );
        animator.setDuration( 3000 );

        ObjectAnimator scale=ObjectAnimator.ofFloat(view,"scaleX",0.7f,1.00f);
        scale.setStartDelay( 7700 );
        scale.setDuration( 3000);

        list.add( animator );
        list.add( scale );

    }
    public void fukudasi2(ImageView view, List<Animator> list){

        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",0f,1.0f );
        animator.setStartDelay( 6000 );
        animator.setDuration( 3000 );

        ObjectAnimator scale=ObjectAnimator.ofFloat(view,"scaleX",0.7f,1.00f);
        scale.setStartDelay( 6000 );
        scale.setDuration( 3000);

        list.add( animator );
        list.add( scale );

    }
    public void fukudasi3(ImageView view, List<Animator> list){
//
       ObjectAnimator scaleX=ObjectAnimator.ofFloat(view,"scaleX", 0.557f,1.0f);
       ObjectAnimator scaley=ObjectAnimator.ofFloat(view,"scaleY",0.56f, 1.0f );
        scaleX.setStartDelay( 3500 );
        scaleX.setDuration( 1500);
        scaley.setStartDelay( 3500 );
        scaley.setDuration( 1500);
        list.add( scaleX);
        list.add( scaley);

        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",0f,1.0f );
        animator.setStartDelay( 3500 );
        animator.setDuration( 1500 );


//        list.add( scale );
        list.add( animator);


    }
    public void phone(ImageView view, List<Animator> list){

        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",0f,1.0f );
        animator.setStartDelay( 1500 );
        animator.setDuration( 2000 );

        ObjectAnimator scale=ObjectAnimator.ofFloat(view,"translationY",30f,0f);
        scale.setStartDelay( 1500 );
        scale.setDuration( 2000);

        list.add( animator );
        list.add( scale );

    }
}
