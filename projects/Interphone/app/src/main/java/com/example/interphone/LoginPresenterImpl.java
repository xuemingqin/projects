package com.example.interphone;

import android.view.KeyEvent;
import android.widget.TextView;

public class LoginPresenterImpl implements LoginPresenter{
    public static final String TAG = "LoginPresenterImpl";

    public LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView){
        mLoginView = loginView;
    }
    @Override
    public void login(String userId, String pwd) {
        mLoginView.onStartRequest();
        if (userId.equals( "00000" )) {
            mLoginView.onSuccess();
        }else {
            mLoginView.onFail();
        }
    }

}
