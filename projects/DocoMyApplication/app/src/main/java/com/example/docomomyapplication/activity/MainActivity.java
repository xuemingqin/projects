package com.example.docomomyapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docomomyapplication.R;
import com.example.docomomyapplication.adapter.BottomNavPagerAdapter;
import com.example.docomomyapplication.fragment.CustomFragment;
import com.example.docomomyapplication.fragment.HomeFragment;
import com.example.docomomyapplication.fragment.SetingFragment;
import com.example.docomomyapplication.fragment.SupportFragment;
import com.youth.banner.listener.OnBannerListener;

import java.util.Set;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        toolbar = findViewById( R.id.tool_bar );
        drawerLayout=findViewById( R.id.drawer_layout );
//        抽屉式菜单
        setSupportActionBar( toolbar );
        toolbar.setTitle( "ホーム" );

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle
                ( this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.syncState();
        drawerLayout.addDrawerListener( toggle );


        bottomNavigationView=(BottomNavigationView) findViewById( R.id.bottomNavigation );
        bottomNavigationView.setOnNavigationItemSelectedListener( listener );
//        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener( ){
//           @Override
//           public void onPageScrolled(int i, float v, int i1) {
//
//           }
//
//           @Override
//           public void onPageSelected(int i) {
//               switch (i){
//                   case 0:
//                       bottomNavigationView.setSelectedItemId( R.id.home );
//                       String title = bottomNavigationView.getMenu().getItem( i ).getTitle().toString();
//                       toolbar.setTitle( title );
//                       break;
//                   case 1:
//                       bottomNavigationView.setSelectedItemId( R.id.custom );
//                       toolbar.setTitle( "お客様情報" );
//                       break;
//                   case 2:
//                       bottomNavigationView.setSelectedItemId( R.id.mail_setting );
//                       toolbar.setTitle("設定" );
//                       break;
//                   case 3:
//                       bottomNavigationView.setSelectedItemId( R.id.support );
//                       toolbar.setTitle( "サポート" );
//                       break;
//               }
//
//           }
//
//           @Override
//           public void onPageScrollStateChanged(int i) {
//
//           }
//       } );
        addFragment(HomeFragment.newInstance());//最初はadd

    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener=new BottomNavigationView.OnNavigationItemSelectedListener( ){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId( )){
                case R.id.home:
                    replaceFragment(HomeFragment.newInstance());
                    return true;
                case R.id.custom:
                    replaceFragment(CustomFragment.newInstance());
                    return true;
                case R.id.mail_setting:
                    replaceFragment( SetingFragment.newInstance());
                    return true;
                case R.id.support:
                    replaceFragment(SupportFragment.newInstance());
                    return true;
            }

            return false;
        }
    };

    private void addFragment(Fragment newFragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, newFragment).commit();
    }

    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, newFragment).commit();//R.id.fragment_containerのを代える
    }

}
