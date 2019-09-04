package com.example.docomomyapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.docomomyapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TutorialNoticeActivity extends Activity{
    private ImageView iv01, phone, iv02, iv03;
    private Button btn_star;
    private TextView tv_care;
    private TextView tv_back;

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
        btn_star=(Button)findViewById( R.id.btn_start );
        tv_care=(TextView)findViewById( R.id.care ) ;
        tv_back=(TextView)findViewById( R.id.back ) ;

        iv03.setAlpha( 0.0f );
        iv02.setAlpha( 0.0f );
        iv01.setAlpha( 0.0f );
        phone.setAlpha( 0.0f );

        tv_care.setOnClickListener( new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
                Intent intent_notice=new Intent( getApplicationContext(), WebViewActivity.class );
                intent_notice.putExtra( "url", "https://id.smt.docomo.ne.jp/src/utility/notice.html" );
                startActivity( intent_notice );
            }
        } );

        btn_star.setOnClickListener( new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
           Intent intent=new Intent(TutorialNoticeActivity.this,MainActivity.class );
           startActivity( intent );
        }
        } );

        tv_back.setOnClickListener( new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
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
        animator.setStartDelay( 3850 );
        animator.setDuration( 1500 );


        ObjectAnimator scale=ObjectAnimator.ofFloat(view,"scaleX",0.7f,1.00f);
        scale.setStartDelay( 3850 );
        scale.setDuration( 1500);

        list.add( animator );
        list.add( scale );

    }
    public void fukudasi2(ImageView view, List<Animator> list){

        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",0f,1.0f );
        animator.setStartDelay( 3000 );
        animator.setDuration( 1500 );

        ObjectAnimator scale=ObjectAnimator.ofFloat(view,"scaleX",0.7f,1.00f);
        scale.setStartDelay( 3000 );
        scale.setDuration( 1500);

        list.add( animator );
        list.add( scale );

    }
    public void fukudasi3(ImageView view, List<Animator> list){
//
       ObjectAnimator scaleX=ObjectAnimator.ofFloat(view,"scaleX", 0.557f,1.0f);
       ObjectAnimator scaley=ObjectAnimator.ofFloat(view,"scaleY",0.56f, 1.0f );
        scaleX.setStartDelay( 1750 );
        scaleX.setDuration( 750);
        scaley.setStartDelay( 1750 );
        scaley.setDuration( 750);
        list.add( scaleX);
        list.add( scaley);

        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",0f,1.0f );
        animator.setStartDelay( 1750 );
        animator.setDuration( 750 );


//        list.add( scale );
        list.add( animator);


    }
    public void phone(ImageView view, List<Animator> list){

        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",0f,1.0f );
        animator.setStartDelay( 750 );
        animator.setDuration( 1000 );

        ObjectAnimator scale=ObjectAnimator.ofFloat(view,"translationY",30f,0f);
        scale.setStartDelay( 750 );
        scale.setDuration( 1000);

        list.add( animator );
        list.add( scale );

    }
}
