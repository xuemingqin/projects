package com.itaccess.interphone.presenter;

/**
 * Created by linxi on 2019/01/31.
 */

public interface AutoAnswerPresenter {

    public static final String TAG = "AutoAnswerPresenter";

    void autoAnswer(String userId, String answerId);
}
