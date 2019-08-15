package com.example.docomomyapplication.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter{
    private final List<Fragment> fragmentList=new ArrayList<Fragment>();
    private  final List<String>titlelist=new ArrayList<String>(  );
    public TabPagerAdapter(FragmentManager fm) {
        super( fm );
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get( i ) ;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public  void  addFragment(Fragment fragment,String title){
        fragmentList.add( fragment );
        titlelist.add( title );
     }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titlelist.get( position );
    }
}
