package com.itaccess.interphone.presenter;

/**
 * Created by linxi on 2019/02/05.
 */

public interface PasswordChangePresenter {

    public static final String TAG = "PasswordChangePresenter";

    void ChangePassword(String userId, String pwd);
}
