package com.example.interphone;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity{
    public static final String TAG = "MainActivity";
   private Toolbar toolbar;
   private DrawerLayout drawerLayout;
   private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        toolbar=findViewById( R.id.tool_bar );
        drawerLayout=findViewById( R.id.drawer );
        navigationView=findViewById( R.id.navi_view );
        setSupportActionBar( toolbar );
        toolbar.setTitle( "インターホンメーイン" );
//        toolbar.setTitleMarginStart( R.drawable.icon_menu );



        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle( this,drawerLayout,toolbar,R.string.app_name,R.string.app_name );
        toggle.syncState();
        drawerLayout.addDrawerListener( toggle );
//        navigationView.inflateHeaderView( R.layout.head_view );
//        navigationView.inflateMenu( R.menu.main_drawer );
//        View navheaderView=navigationView.getHeaderView( 0 );
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener( ){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_home:
                        break;
                    case R.id.menu_setting:
                        break;
                    case R.id.menu_guide:
                        break;
                    case R.id.menu_inquiry:
                        break;
                    case R.id.menu_trem:
                        break;
                    case R.id.menu_user_info:
                        break;
                    case R.id.menu_app:
                        break;
                }

                return false;
            }
        } );
    }



}
