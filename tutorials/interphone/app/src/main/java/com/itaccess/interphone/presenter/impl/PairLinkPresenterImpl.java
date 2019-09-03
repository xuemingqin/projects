package com.itaccess.interphone.presenter.impl;

import com.itaccess.interphone.presenter.PairLinkPresenter;
import com.itaccess.interphone.utils.StringUtil;
import com.itaccess.interphone.view.PairLinkView;

/**
 * Created by linxi on 2019/01/16.
 */

public class PairLinkPresenterImpl implements PairLinkPresenter{

    public static final String TAG = "PairLinkPresenterImpl";

    public PairLinkView mPairLinkView;

    public PairLinkPresenterImpl(PairLinkView pairLinkView) {
        mPairLinkView = pairLinkView;
    }
    //TODO
    @Override
    public void authentication(String userId, String token) {
        mPairLinkView.onStartRequest();
        if (!StringUtil.isNull(userId) && !StringUtil.isNull(token)) {
            if (userId.equals("00000")) {
                mPairLinkView.onAuthenticationSuccess();
            } else {
                mPairLinkView.onFail();
            }
        }
    }
}
