package com.example.docomomyapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.docomomyapplication.R;
import com.example.docomomyapplication.adapter.TabPagerAdapter;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.activity_home,null );
       viewPager=(ViewPager) view.findViewById( R.id.viewpager2);
       tabLayout=(TabLayout)view.findViewById( R.id.tab1 );
       tabLayout.setupWithViewPager( viewPager );
       setupViewPager(viewPager);
        return view;

    }

    private void setupViewPager(ViewPager viewPager) {
        TabPagerAdapter adapter=new TabPagerAdapter( getFragmentManager() );
        adapter.addFragment( new TopFragment(),"TOP");
        adapter.addFragment( new DataFragment(),"データ量" );
        adapter.addFragment( new PriceFragment(),"料金");
        adapter.addFragment( new DpointFragment(),"dポイント" );
        viewPager.setAdapter( adapter );
    }


}