package com.itaccess.interphone.widget;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.itaccess.interphone.R;

import butterknife.BindView;

/**
 * Created by linxi on 2019/01/15.
 */

public class CustomToolbar extends Toolbar{
    /**
     * 左側Title
     */
    @BindView(R.id.txt_left)
    TextView mTxtLeftTitle;
    /**
     * 中央Title
     */
    @BindView(R.id.txt_main_title)
    TextView mTxtMiddleTitle;

    public CustomToolbar(Context context) {
        this(context,null);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /*
     * 中央タイトルの内容設定
     */
    public void setMainTitle(String text) {
        this.setTitle(" ");
        mTxtMiddleTitle.setVisibility(View.VISIBLE);
        mTxtMiddleTitle.setText(text);
    }

    /*
    * 中央タイトルの色設定
    */
    public void setMainTitleColor(int color) {
        mTxtMiddleTitle.setTextColor(color);
    }

    /*
     * 左側タイトルの内容設定
     */
    public void setLeftTitleText(String text) {
        mTxtLeftTitle.setVisibility(View.VISIBLE);
        mTxtLeftTitle.setText(text);
    }

    /*
     * 左側タイトルの色設定
     */
    public void setLeftTitleColor(int color) {
        mTxtLeftTitle.setTextColor(color);
    }

    /*
     * 左側タイトルのアイコン設定
     */
    public void setLeftTitleDrawable(int res) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        TextView mLeftTitle = (TextView) findViewById(R.id.txt_left);
        mLeftTitle.setCompoundDrawables(dwLeft, null, null, null);
    }

}

