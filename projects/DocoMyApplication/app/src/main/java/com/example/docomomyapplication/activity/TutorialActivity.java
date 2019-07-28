package com.example.docomomyapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.docomomyapplication.R;
import com.example.docomomyapplication.adapter.TutorialFragmentPagerAdapter;

public class TutorialActivity extends AppCompatActivity{
    private ViewPager mViewPager;
    private View mViewPagerIndicator1;
    private View mViewPagerIndicator2;
    private View mViewPagerIndicator3;
    private TextView mSkipLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.tutorial );
        FragmentManager manager = getSupportFragmentManager ( );
        TutorialFragmentPagerAdapter pagerAdapter = new TutorialFragmentPagerAdapter ( manager );
        mViewPager = (ViewPager) findViewById ( R.id.pager );
        mViewPager.setAdapter ( pagerAdapter );
        mViewPager.setCurrentItem ( 0 );
        setViewPagerIndicator ( );
    }

    private void setViewPagerIndicator() {
        mViewPagerIndicator1 = findViewById ( R.id.tutorial_indicator_circle_over_1 );
        mViewPagerIndicator2 = findViewById ( R.id.tutorial_indicator_circle_over_2 );
        mViewPagerIndicator3 = findViewById ( R.id.tutorial_indicator_circle_over_3 );
        mSkipLink = (TextView) findViewById ( R.id.tutorial_skip_link );
        mSkipLink.setOnClickListener ( mSkipListener );
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener( ){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled( position, positionOffset, positionOffsetPixels );
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected( position );
                switch (position) {
                    case 0:
                        //DocomoAnalyticsHelper.getInstance().sendFirstTutorial1();
                        break;
                    case 1:
                        //DocomoAnalyticsHelper.getInstance().sendFirstTutorial2();
                        break;
                    case 2:
                        //DocomoAnalyticsHelper.getInstance().sendFirstTutorial3();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int position) {
                super.onPageScrollStateChanged( position );
                switch (position) {
                    case 0:
                        //DocomoAnalyticsHelper.getInstance().sendFirstTutorial1();
                        break;
                    case 1:
                        //DocomoAnalyticsHelper.getInstance().sendFirstTutorial2();
                        break;

                }
            }
        });

    }

    TextView.OnClickListener mSkipListener = new View.OnClickListener ( ){
        @Override
        public void onClick(View v) {
            switch (mViewPager.getCurrentItem()) {
                case 0:
                    //DocomoAnalyticsHelper.getInstance().sendTutorialSkip1();
                    break;
                case 1:
                    //DocomoAnalyticsHelper.getInstance().sendTutorialSkip2();
                    break;
                default:
            }
            setScreenTransition();

        }
    };
    private void setIndicatorNoActive() {
        mViewPagerIndicator1.setAlpha(0.0f);
        mViewPagerIndicator2.setAlpha(0.0f);
        mViewPagerIndicator3.setAlpha(0.0f);
    }
    public void setScreenTransition(){
        Intent intent = new Intent(getApplicationContext(), FirstAccountLoginActivity.class);
        startActivity(intent);
        finish();

    }
    @Override
    public final boolean dispatchKeyEvent(final KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.dispatchKeyEvent(event);
    }
}