package com.example.welcomemyapplication.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.welcomemyapplication.fragment.FragmentHome;
import com.example.welcomemyapplication.fragment.FragmentMy;
import com.example.welcomemyapplication.fragment.FragmentSeach;
import com.example.welcomemyapplication.fragment.Fragmenttuan;
import com.example.welcomemyapplication.R;

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

        //初始化fragmentManager
        manager = getSupportFragmentManager ( );
        //设置默认选中
        rb.setChecked ( true );
        rg.setOnCheckedChangeListener ( this );
        //切换不同的fragment
        changeFragment ( new FragmentHome(),false );
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.home:
                changeFragment ( new FragmentHome (),true );
                break;
            case R.id.tuan:
                changeFragment ( new Fragmenttuan(),true );
                break;
            case R.id.search:
                changeFragment ( new FragmentSeach(),true );
                break;
            case R.id.my:
                changeFragment ( new FragmentMy(),true );
                break;
        }
    }


    private void changeFragment(Fragment fragment, boolean b) {
        FragmentTransaction transaction = manager.beginTransaction ( );
        transaction.replace ( R.id.main_content, fragment);
        if(b){
            transaction.addToBackStack ( null );
        }
        transaction.commit ();
    }

    @Override
    public void onBackPressed() {
        exit();  ///退出应用
    }

    private long exitTime = 0; //两次按返回退出
    public void exit() {   //退出应用
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            //System.exit(0);
        }
    }
}





