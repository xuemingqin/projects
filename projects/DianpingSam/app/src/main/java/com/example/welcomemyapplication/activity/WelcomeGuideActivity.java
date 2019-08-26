package com.example.welcomemyapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.welcomemyapplication.R;
import com.example.welcomemyapplication.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class WelcomeGuideActivity extends Activity{
    private Button btn;
    private ViewPager vp;
    private List<ImageView> list;
    private int[] images = {R.drawable.guide_01, R.drawable.guide_02, R.drawable.guide_03};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_welcome_guide );
        btn = (Button) findViewById ( R.id.btn );
        vp = (ViewPager) findViewById ( R.id.vp );

        initViewPager ( );

    }

    private void initViewPager() {

        list = new ArrayList<> ( );
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView ( this );
            iv.setBackgroundResource ( images[i] );
            list.add ( iv );

        }
        vp.setAdapter ( new MyAdapter ( ) );
        vp.addOnPageChangeListener ( new ViewPager.OnPageChangeListener ( ){
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==2){
                    btn.setVisibility ( View.VISIBLE );
                }else {
                    btn.setVisibility ( View.GONE );
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        } );

    }

    class MyAdapter extends PagerAdapter{


        @Override
        public int getCount() {
            return list != null ? list.size ( ) : 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        //初始化item实例方法
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView ( list.get ( position ) );
            return list.get ( position );
        }

        //item的销毁的方法
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(list.get ( position ) );
        }
    }

    public void click(View view) {
     startActivity ( new Intent ( this, MainActivity.class ) );
    }

}