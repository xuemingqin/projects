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

import com.example.docomomyapplication.R;
import com.example.docomomyapplication.adapter.NetTabPagerAdapter;

public class SupportFragment extends Fragment{
    private ViewPager viewPager;
    private TabLayout tabLayout;


    public static SupportFragment newInstance() {
        SupportFragment fragment = new SupportFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.activity_support,null );
        viewPager=(ViewPager) view.findViewById( R.id.viewpager3);
        tabLayout=(TabLayout)view.findViewById( R.id.tab2 );
        return view;

    }

    @Override
    public void onResume() {
        super.onResume( );
        setupViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
//        for (Fragment fragment : getFragmentManager().getFragments()) {
//            getFragmentManager().beginTransaction().remove(fragment).commit();
//        }
        NetTabPagerAdapter adapter = new NetTabPagerAdapter( getActivity().getSupportFragmentManager() );
        adapter.addFragment( new OkhttpFragment(),"Okhttp");
        adapter.addFragment( new HttpURLConnectionFragment(),"HttpURLConnection" );
        adapter.addFragment( new VolleyFragment(),"Volley");
        adapter.addFragment( new RetrofitFragment(),"Retrofit" );
        viewPager.setAdapter( adapter );
        tabLayout.setupWithViewPager( viewPager );
    }

    @Override
    public void onDestroy() {
        super.onDestroy( );
    }

    @Override
    public void onStop() {
        super.onStop( );
    }

    @Override
    public void onDetach () {
        super.onDetach( );
    }
}