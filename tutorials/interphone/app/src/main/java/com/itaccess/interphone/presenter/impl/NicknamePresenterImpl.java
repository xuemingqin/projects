package com.itaccess.interphone.presenter.impl;

import com.itaccess.interphone.presenter.LoginPresenter;
import com.itaccess.interphone.presenter.NicknamePresenter;
import com.itaccess.interphone.view.LoginView;
import com.itaccess.interphone.view.NicknameView;

/**
 * Created by linxi on 2019/01/15.
 */

public class NicknamePresenterImpl implements NicknamePresenter {

    public static final String TAG = "NicknamePresenterImpl";

    public NicknameView mNicknameView;

    public NicknamePresenterImpl(NicknameView nicknameView) {
        mNicknameView = nicknameView;
    }

    @Override
    public void login(String userId, String nickname) {
        mNicknameView.onStartRequest();
        // TODO
        if (userId.equals("00000")) {
            mNicknameView.onSuccess();
        } else {
            mNicknameView.onFail();
        }

    }
}
