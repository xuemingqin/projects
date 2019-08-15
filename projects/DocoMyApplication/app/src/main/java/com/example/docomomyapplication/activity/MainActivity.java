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

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        toolbar = findViewById( R.id.tool_bar );
        drawerLayout=findViewById( R.id.drawer_layout );
        setSupportActionBar( toolbar );
        toolbar.setTitle( "ホーム" );

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle
                ( this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.syncState();
        drawerLayout.addDrawerListener( toggle );


        bottomNavigationView=(BottomNavigationView) findViewById( R.id.bottomNavigation );
        viewPager=(ViewPager)findViewById( R.id.viewpager );
        // textView=(TextView)findViewById( R.id.textbal ) ;
        setupViewPager(viewPager);
        bottomNavigationView.setOnNavigationItemSelectedListener( listener );
        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener( ){
           @Override
           public void onPageScrolled(int i, float v, int i1) {

           }

           @Override
           public void onPageSelected(int i) {
               switch (i){
                   case 0:
                       bottomNavigationView.setSelectedItemId( R.id.home );
                       String title = bottomNavigationView.getMenu().getItem( i ).getTitle().toString();
                       toolbar.setTitle( title );
                       break;
                   case 1:
                       bottomNavigationView.setSelectedItemId( R.id.custom );
                       toolbar.setTitle( "お客様情報" );
                       break;
                   case 2:
                       bottomNavigationView.setSelectedItemId( R.id.mail_setting );
                       toolbar.setTitle("設定" );
                       break;
                   case 3:
                       bottomNavigationView.setSelectedItemId( R.id.support );
                       toolbar.setTitle( "サポート" );
                       break;
               }

           }

           @Override
           public void onPageScrollStateChanged(int i) {

           }
       } );

    }


    private void setupViewPager(ViewPager viewPager) {
      BottomNavPagerAdapter adapter = new BottomNavPagerAdapter(getSupportFragmentManager());
      adapter.addFragment( new HomeFragment() );
      adapter.addFragment( new CustomFragment() );
      adapter.addFragment( new SetingFragment() );
      adapter.addFragment( SupportFragment.newInstance() );
      viewPager.setAdapter( adapter );
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener=new BottomNavigationView.OnNavigationItemSelectedListener( ){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId( )){
                case R.id.home:
                  viewPager.setCurrentItem( 0 );
                    return true;
                case R.id.custom:
                   viewPager.setCurrentItem( 1 );
                    return true;
                case R.id.mail_setting:
                  viewPager.setCurrentItem( 2 );
                    return true;
                case R.id.support:
                    viewPager.setCurrentItem( 3 );
                    return true;
            }

            return false;
        }
    };

}
