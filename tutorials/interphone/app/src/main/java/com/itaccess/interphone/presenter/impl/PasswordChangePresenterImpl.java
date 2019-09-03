package com.itaccess.interphone.presenter.impl;

import com.itaccess.interphone.presenter.PasswordChangePresenter;
import com.itaccess.interphone.view.PasswordChangeView;

/**
 * Created by linxi on 2019/02/05.
 */

public class PasswordChangePresenterImpl implements PasswordChangePresenter{


    public static final String TAG = "NicknamePresenterImpl";

    public PasswordChangeView mPasswordChangeView;

    public PasswordChangePresenterImpl(PasswordChangeView mPasswordChangeView) {
        this.mPasswordChangeView = mPasswordChangeView;
    }

    @Override
    public void ChangePassword(String userId, String password) {
        mPasswordChangeView.onStartRequest();
        // TODO
        if (userId.equals("00000")) {
            mPasswordChangeView.onSuccess();
        } else {
            mPasswordChangeView.onFail();
        }

    }
}
