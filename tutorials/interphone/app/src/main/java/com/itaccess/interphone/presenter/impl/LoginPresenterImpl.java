package com.itaccess.interphone.presenter.impl;

import com.itaccess.interphone.presenter.LoginPresenter;
import com.itaccess.interphone.view.LoginView;

/**
 * Created by linxi on 2019/01/15.
 */

public class LoginPresenterImpl implements LoginPresenter {

    public static final String TAG = "LoginPresenterImpl";

    public LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void login(String userId, String pwd) {
        mLoginView.onStartRequest();
        // TODO
        if (userId.equals("00000")) {
            mLoginView.onSuccess();
        } else {
            mLoginView.onFail();
        }

    }
}
