package com.example.docomomyapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.docomomyapplication.fragment.TutorialFragment;

public class TutorialFragmentPagerAdapter extends FragmentPagerAdapter{
    public static final int PAGE_COUNT = 3;


    public TutorialFragmentPagerAdapter(FragmentManager fm) {
        super ( fm );
    }

    @Override
    public Fragment getItem(int i) {
     Fragment fragment= TutorialFragment.newInstance( i );
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    public Fragment findFragmentByPosition(ViewPager viewPager,
                                           int position) {
        return (Fragment) instantiateItem(viewPager, position);
    }

}
