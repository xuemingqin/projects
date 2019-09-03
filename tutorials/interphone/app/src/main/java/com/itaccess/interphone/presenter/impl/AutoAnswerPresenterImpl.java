package com.itaccess.interphone.presenter.impl;

import com.itaccess.interphone.presenter.AutoAnswerPresenter;
import com.itaccess.interphone.view.AutoAnswerView;

/**
 * Created by linxi on 2019/01/31.
 */

public class AutoAnswerPresenterImpl implements AutoAnswerPresenter{

    public static final String TAG = "AutoAnswerPresenterImpl";
    private AutoAnswerView mAutoAnswerView;

    public AutoAnswerPresenterImpl(AutoAnswerView autoAnswerView) {
        mAutoAnswerView = autoAnswerView;
    }

    @Override
    public void autoAnswer(String userId, String answerId) {
        mAutoAnswerView.onStartRequest();
        if (userId.equals("00000")) {
            mAutoAnswerView.onSuccess();
        } else {
            mAutoAnswerView.onFail();
        }

    }
}
