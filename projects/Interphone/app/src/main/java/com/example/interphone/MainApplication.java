package com.example.interphone;

import android.app.Application;
import android.content.Intent;

public class MainApplication extends Application{
    private Boolean isMainOpened;
    @Override
    public void onCreate() {
        super.onCreate( );
        setMainOpened(false);
        isAuthenticated();


    }



    public void setMainOpened(boolean isMainOpened) {
        this.isMainOpened=isMainOpened;

    }
    public Boolean getIsMainOpened(){
        return isMainOpened;
    }
    private void isAuthenticated() {
        Boolean isAuthenticated=PreferencesUtil.getAuthentication( this );
        Intent intent;
        if (isAuthenticated) {
            intent = new Intent( this, MainActivity.class );
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent );
        }else {
            intent=new Intent( this,LoginActivity.class );
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent );

        }
    }

}
