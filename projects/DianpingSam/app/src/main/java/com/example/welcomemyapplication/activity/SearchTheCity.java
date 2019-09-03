package com.example.welcomemyapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.welcomemyapplication.R;

public class SearchTheCity extends Activity{

    private ImageView mSouquancheng_back;
    private TextView mSouquancheng_moresearch, mSouquancheng_text1,
            mSouquancheng_text2, mSouquancheng_text3, mSouquancheng_text4,
            mSouquancheng_text5, mSouquancheng_text6, mSouquancheng_text7,
            mSouquancheng_text8, mSouquancheng_text9, mSouquancheng_text10,
            mSouquancheng_text11, mSouquancheng_text12, mSouquancheng_text13,
            mSouquancheng_text14, mSouquancheng_text15, mSouquancheng_text16,
            mSouquancheng_text17, mSouquancheng_text18, mSouquancheng_text19,
            mSouquancheng_text20, mSouquancheng_text21, mSouquancheng_text22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_sou_quan_cheng );
    }
}
