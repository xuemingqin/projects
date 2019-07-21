package com.example.welcomemyapplication;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{
  private RadioGroup rg;
  private RadioButton rb;
  private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        rg=(RadioGroup)findViewById( R.id.main_bottom_tabs );
        rb=(RadioButton)findViewById ( R.id.home );
        manager = getSupportFragmentManager ( );
        rb.setChecked ( true );
        rg.setOnCheckedChangeListener ( this );
        changeFragment ( new FragmentHome (),false );
    }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.home:
                changeFragment ( new FragmentHome (),true );
                break;
            case R.id.tuan:
                changeFragment ( new Fragmenttuan (),true );
                break;
            case R.id.search:
                changeFragment ( new FragmentSeach (),true );
                break;
            case R.id.my:
                changeFragment ( new FragmentMy (),true );
                break;
        }
        }


    private void changeFragment(Fragment fragment, boolean b) {
        FragmentTransaction transaction = manager.beginTransaction ( );
        transaction.replace ( R.id.main_content,fragment );
        if(b){
            transaction.addToBackStack ( null );
        }
        transaction.commit ();
    }
}





