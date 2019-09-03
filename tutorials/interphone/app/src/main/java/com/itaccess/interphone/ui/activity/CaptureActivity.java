package com.itaccess.interphone.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;

import com.itaccess.interphone.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import butterknife.OnClick;

/**
 * Created by linxi on 2019/01/16.
 */

public class CaptureActivity extends BaseActivity{
    /**
     * 管理者
     */
    private CaptureManager mCaptureManager;

    private DecoratedBarcodeView mBarcodeView;

    @Override
    public int getLayoutRes(){
        return R.layout.acticity_pair_link_capture;
    }

    @Override
    public void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.pair_link_title));
        showTitleLeftIcon(true);
        mBarcodeView = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        mCaptureManager = new CaptureManager(this, mBarcodeView);
        if (getSavedInstanceState() != null) {
            mCaptureManager.initializeFromIntent(getIntent(),  getSavedInstanceState());
        } else {
            mCaptureManager.initializeFromIntent(getIntent(),  null);
        }
        mCaptureManager.decode();
    }

    //TODO
    @OnClick(R.id.txt_left)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                Boolean isFromAbout = getIntent().getBooleanExtra(AboutActivity.TAG, false);
                startActivity(isFromAbout ? AboutActivity.class : TermOfUseActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaptureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCaptureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCaptureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCaptureManager.onSaveInstanceState(outState);
    }

    /**
     * 権限処理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mCaptureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * キー処理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
