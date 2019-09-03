package com.itaccess.interphone.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itaccess.interphone.R;
import com.itaccess.interphone.model.User;
import com.itaccess.interphone.presenter.PairLinkPresenter;
import com.itaccess.interphone.presenter.impl.PairLinkPresenterImpl;
import com.itaccess.interphone.utils.PreferencesUtil;
import com.itaccess.interphone.utils.StringUtil;
import com.itaccess.interphone.view.PairLinkView;

import butterknife.OnClick;

/**
 * Created by linxi on 2017/12/14.
 */

public class PairLinkActivity extends BaseActivity implements PairLinkView {

    private PairLinkPresenter mPairLinkPresenter;

    @Override
    public int getLayoutRes(){
        return R.layout.acticity_pair_link;
    }

    @Override
    public void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.pair_link_title));
        showTitleLeftIcon(true);
        setIntentIntegrator();
        mPairLinkPresenter = new PairLinkPresenterImpl(this);
    }

    private void setIntentIntegrator() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(PairLinkActivity.this);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt(getResStr(R.string.pair_link_capture_caution));
        Boolean isFromAbout = getIntent().getBooleanExtra(AboutActivity.TAG, false);
        if (isFromAbout) {
            intentIntegrator.addExtra(AboutActivity.TAG, isFromAbout);
        }
        intentIntegrator.setCaptureActivity(CaptureActivity.class);
        intentIntegrator.initiateScan();
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                String userId = "";
                User mUser = PreferencesUtil.readUser(this);
                if (mUser != null && !StringUtil.isNull(mUser.getUserId())) {
                    userId = mUser.getUserId();
                }
                if (!checkNetWork()) {
                    return;
                }
                mPairLinkPresenter.authentication(userId, result.getContents());
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStartRequest() {
        showProgress(getResStr(R.string.pair_link_authentication_message));
    }

    @Override
    public void onAuthenticationSuccess() {
        //TODO
        //認証できた情報を保存する
        PreferencesUtil.setAuthentication(this, true);

        postDelay(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                Bundle mBundle = new Bundle();
                mBundle.putBoolean(IS_LOAD, true);
                startActivity(MainActivity.class, mBundle);
            }
        }, 1000);

    }

    @Override
    public void onFail() {
        postDelay(new Runnable() {
            @Override
            public void run() {
                hideProgress();
            }
        }, 2000);
        showDialog(getResStr(R.string.dialog_error), getResStr(R.string.pair_link_authentication_error_message),
                getResStr(R.string.dialog_error_close));
    }

    @Override
    public void dialogBtnClick(DialogInterface dialogInterface) {
        dialogInterface.dismiss();
        setIntentIntegrator();
    }

}
