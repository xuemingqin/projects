package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        toolbar = findViewById( R.id.tool_bar );
        drawerLayout = findViewById( R.id.drawer_layout );
//        抽屉式菜单
        setSupportActionBar( toolbar );
        toolbar.setTitle( "ホーム" );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                ( this, drawerLayout, toolbar, R.string.app_name, R.string.app_name );
        toggle.syncState( );
        drawerLayout.addDrawerListener( toggle );


        bottomNavigationView = (BottomNavigationView) findViewById( R.id.bottomNavigation );
        bottomNavigationView.setOnNavigationItemSelectedListener( listener );
        addFragment( HomeFragment.newInstance( ) );//最初はadd
       bottomNavigationView.setItemIconTintList( null );

    }

//    private void resetToDefaultIcon() {
//        MenuItem home = bottomNavigationView.getMenu( ).findItem( R.id.home );
//        home.setIcon( R.drawable.icon_bottom_home_off );
//        MenuItem custom = bottomNavigationView.getMenu( ).findItem( R.id.custom );
//        custom.setIcon( R.drawable.icon_bottom_customer1_off );
//        MenuItem setting = bottomNavigationView.getMenu( ).findItem( R.id.mail_setting );
//        setting.setIcon( R.drawable.icon_bottom_mail_setting1_off );
//        MenuItem support = bottomNavigationView.getMenu( ).findItem( R.id.support );
//        support.setIcon( R.drawable.icon_bottom_support1_off );
//
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener( ){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId( )) {
                case R.id.home:
                    replaceFragment( HomeFragment.newInstance( ) );
                    return true;
                case R.id.custom:
                    replaceFragment( CustomFragment.newInstance( ) );
                    return true;
                case R.id.mail_setting:
                    replaceFragment( SetingFragment.newInstance( ) );
                    return true;
                case R.id.support:
                    replaceFragment( SupportFragment.newInstance( ) );
                    return true;
            }

            return false;
        }
    };

    private void addFragment(Fragment newFragment) {
        FragmentTransaction ft = getSupportFragmentManager( ).beginTransaction( );
        ft.add( R.id.fragment_container, newFragment ).commit( );
    }

    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction ft = getSupportFragmentManager( ).beginTransaction( );
        ft.replace( R.id.fragment_container, newFragment ).commit( );//R.id.fragment_containerのを代える
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener( ){
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//            resetToDefaultIcon( );
//            switch (menuItem.getItemId( )) {
//                case R.id.home:
//                    menuItem.setIcon( R.drawable.icon_frequentd_card_used );
//                    return true;
//                case R.id.support:
//                    menuItem.setIcon( R.drawable.icon_frequent_online_procedures );
//                    return true;
//                case R.id.mail_setting:
//                    menuItem.setIcon( R.drawable.icon_menu_device_sub );
//                    return true;
//                case R.id.custom:
//                    menuItem.setIcon( R.drawable.icon_menu_device_main );
//                    return true;
//            }
//            return false;
//        }
//   };
}
