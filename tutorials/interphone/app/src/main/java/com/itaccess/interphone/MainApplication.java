package com.itaccess.interphone;

import android.app.Application;
import android.content.Intent;

import com.itaccess.interphone.ui.activity.LoginActivity;
import com.itaccess.interphone.ui.activity.MainActivity;
import com.itaccess.interphone.utils.PreferencesUtil;

/**
 * Created by linxi on 2019/01/16.
 */

public class MainApplication extends Application{

    public static final String TAG = "app";

    private Boolean isMainOpened;

    @Override
    public void onCreate()
    {
        super.onCreate();
        setMainOpened(false);//初回設定
        //認証状況を確認
        isAuthenticated();
    }

    public void setMainOpened(Boolean isMainOpened) {
        this.isMainOpened = isMainOpened;
    }

    public Boolean getMainOpened() {
        return isMainOpened;
    }


    private void isAuthenticated() {
        Boolean isAuthenticated = PreferencesUtil.getAuthentication(this);
        Intent intent;
        if (isAuthenticated) {
            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        } else {
            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
    }

}