package com.example.myapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

public class CustomFragment extends Fragment{
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static CustomFragment newInstance() {
       CustomFragment fragment = new CustomFragment();
        return fragment;
    }

}
