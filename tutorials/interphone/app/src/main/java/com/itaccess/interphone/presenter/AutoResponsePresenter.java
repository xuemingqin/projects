package com.itaccess.interphone.presenter;

import com.itaccess.interphone.model.AutoResponseSet;

/**
 * Created by linxi on 2019/01/25.
 */

public interface AutoResponsePresenter {
    public static final String TAG = "AutoResponsePresenter";

    void onDecision(String userId, AutoResponseSet mAutoResponseSet);
}
