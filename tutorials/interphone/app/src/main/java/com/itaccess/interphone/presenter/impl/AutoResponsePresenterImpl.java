package com.itaccess.interphone.presenter.impl;

import com.itaccess.interphone.model.AutoResponseSet;
import com.itaccess.interphone.presenter.AutoResponsePresenter;
import com.itaccess.interphone.view.AutoResponseView;

/**
 * Created by linxi on 2019/01/25.
 */

public class AutoResponsePresenterImpl implements AutoResponsePresenter {

    public static final String TAG = "AutoResponsePresenterImpl";

    public AutoResponseView mAutoResponseView;

    public AutoResponsePresenterImpl(AutoResponseView autoResponseView) {
        mAutoResponseView = autoResponseView;
    }

    @Override
    public void onDecision(String userId, AutoResponseSet mAutoResponseSet) {
        mAutoResponseView.onStartRequest();
        if ("00000".equals(userId)) {
            mAutoResponseView.onSuccess(mAutoResponseSet.getDecided());
        } else {
            mAutoResponseView.onFail();
        }
    }
}
