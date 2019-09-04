package com.example.interphone;

import android.widget.TextView;

public interface LoginPresenter {
    public static final String TAG = "LoginPresenter";

    void login (String userId, String pwd);
}
