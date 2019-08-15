package com.example.docomomyapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.docomomyapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PassCodeActivity extends Activity {
    private ImageView num1, num2,num3,num4,num5,num6,num7,num8,num9, num0,imageView;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_passcode );
        initId();
        animaForAll();

    }

    private void initId() {
        num0=(ImageView) findViewById( R.id. num0);
        num1=(ImageView) findViewById( R.id. num1);
        num2=(ImageView) findViewById( R.id. num2);
        num3=(ImageView) findViewById( R.id. num3);
        num4=(ImageView) findViewById( R.id. num4);
        num5=(ImageView) findViewById( R.id. num5);
        num6=(ImageView) findViewById( R.id. num6);
        num7=(ImageView) findViewById( R.id. num7);
        num8=(ImageView) findViewById( R.id. num8);
        num9=(ImageView) findViewById( R.id. num9);
        imageView=(ImageView) findViewById( R.id.image);

        num0.setAlpha( 0.0f );
        num1.setAlpha( 0.0f );
        num2.setAlpha( 0.0f );
        num3.setAlpha( 0.0f );
        num4.setAlpha( 0.0f );

        num5.setAlpha( 0.0f );
        num6.setAlpha( 0.0f );
        num7.setAlpha( 0.0f );
        num8.setAlpha( 0.0f );
        num9.setAlpha( 0.0f );
        imageView.setAlpha( 0.0f );

    }

    private void animaForAll(){

        AnimatorSet set=new AnimatorSet();

        List<Animator> list=new ArrayList<Animator>();

        animaFor1( num1, list );
        animaFor2And4( num2, list );
        animaFor2And4( num4, list );
        animaFor3And5And7(num3,list);
        animaFor3And5And7(num7,list);
        animaFor3And5And7(num5,list);
        animaFor6And8( num6,list );
        animaFor6And8( num8,list );
        animaFor9And0( num9,list );
        animaFor9And0( num0, list);
        fukudasi(imageView,list);
        set.playTogether( list );
        set.start();
    }

    private void animaFor1(ImageView view, List<Animator> list) {

        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",0f,1.0f );
        animator.setStartDelay( 1500 );
        animator.setDuration( 1500 );

        ObjectAnimator scale=ObjectAnimator.ofFloat(view,"scaleX",0.7f,1.0f);
        scale.setStartDelay( 1500 );
        scale.setDuration( 500 );

        list.add( animator );
        list.add( scale );
    }
    private void animaFor2And4(ImageView view, List<Animator> list){

        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",0f,1.0f );

        animator.setStartDelay( 2000 );
        animator.setDuration( 1500 );

        ObjectAnimator scale=ObjectAnimator.ofFloat(view,"scaleX",0.7f,1.0f);
        scale.setStartDelay( 2000 );
        scale.setDuration( 500 );

        list.add( animator );
        list.add( scale );
    }
    private void animaFor3And5And7(ImageView view, List<Animator> list){

        ObjectAnimator animator=ObjectAnimator.ofFloat( view,"alpha",0f,1.0f );
        animator.setStartDelay( 2600 );
        animator.setDuration( 1500 );

        ObjectAnimator scale=ObjectAnimator.ofFloat( view,"scaleX",0.7f,1.0f);
        scale.setStartDelay( 2600 );
        scale.setDuration( 500 );

        list.add( animator );
        list.add( scale );


    }
    private void animaFor6And8(ImageView view, List<Animator> list){

        ObjectAnimator animator=ObjectAnimator.ofFloat( view,"alpha",0f,1.0f );
        animator.setStartDelay( 3000 );
        animator.setDuration( 1500 );

        ObjectAnimator scale=ObjectAnimator.ofFloat( view,"scaleX",0.7f,1.0f);
        scale.setStartDelay( 3000 );
        scale.setDuration( 500 );

        list.add( animator );
        list.add( scale );


    }

    private void animaFor9And0(ImageView view, List<Animator> list){

        ObjectAnimator animator=ObjectAnimator.ofFloat( view,"alpha",0f,1.0f );
        animator.setStartDelay( 3500 );
        animator.setDuration( 1500 );

        ObjectAnimator scale=ObjectAnimator.ofFloat( view,"scaleX",0.7f,1.0f);
        scale.setStartDelay( 3500 );
        scale.setDuration( 500 );

        list.add( animator );
        list.add( scale );
    }
    private void fukudasi(ImageView view, List<Animator> list){
        ObjectAnimator animator=ObjectAnimator.ofFloat( view,"translationY",-20f,0);
        animator.setStartDelay( 4500 );
        animator.setDuration( 500);
        ObjectAnimator alpha=ObjectAnimator.ofFloat( view,"alpha",0.0f,1.0f );
        alpha.setStartDelay( 3500 );
        alpha.setDuration( 1500 );

        list.add( animator );
        list.add( alpha );
    }
}