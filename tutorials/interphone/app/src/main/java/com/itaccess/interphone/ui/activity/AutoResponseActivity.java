package com.itaccess.interphone.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itaccess.interphone.R;
import com.itaccess.interphone.model.AutoResponseSet;
import com.itaccess.interphone.presenter.AutoResponsePresenter;
import com.itaccess.interphone.presenter.impl.AutoResponsePresenterImpl;
import com.itaccess.interphone.utils.PreferencesUtil;
import com.itaccess.interphone.utils.StringUtil;
import com.itaccess.interphone.view.AutoResponseView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by linxi on 2019/01/24.
 */

public class AutoResponseActivity extends BaseActivity implements AutoResponseView{

    public static final String TAG = "AutoResponseActivity";
    public static final String CONTENT_TXT_1 = "01";
    public static final String CONTENT_TXT_2 = "02";
    public static final String CONTENT_TXT_3 = "03";

    @BindView(R.id.auto_content_1)
    TextView mContentTxt1;
    @BindView(R.id.auto_content_2)
    TextView mContentTxt2;
    @BindView(R.id.auto_content_3)
    TextView mContentTxt3;
    @BindView(R.id.auto_decision)
    Button mAutoDecision;
    @BindView(R.id.auto_release)
    Button mAutoRelease;

    private String selectedContent;
    private AutoResponsePresenter mAutoResponsePresenter;

    @Override
    public int getLayoutRes(){
        return R.layout.activity_auto_response;
    }

    @Override
    public void init() {
        super.init();
        if (checkIsSetted()) {
          setLayout();
        }
        setToolBarTitle(getResStr(R.string.auto_response_title));
        showTitleLeftIcon(true);
        mAutoResponsePresenter = new AutoResponsePresenterImpl(this);
    }

    @OnClick({R.id.txt_left, R.id.auto_content_1, R.id.auto_content_2, R.id.auto_content_3,
     R.id.auto_decision, R.id.auto_release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                startActivity(SettingActivity.class);
                break;
            case R.id.auto_content_1:
                clearContentPressed();
                mContentTxt1.setBackgroundResource(R.drawable.auto_response_text_pressed);
                mAutoDecision.setEnabled(true);
                selectedContent = CONTENT_TXT_1;
                break;
            case R.id.auto_content_2:
                clearContentPressed();
                mContentTxt2.setBackgroundResource(R.drawable.auto_response_text_pressed);
                mAutoDecision.setEnabled(true);
                selectedContent = CONTENT_TXT_2;
                break;
            case R.id.auto_content_3:
                clearContentPressed();
                mContentTxt3.setBackgroundResource(R.drawable.auto_response_text_pressed);
                mAutoDecision.setEnabled(true);
                selectedContent = CONTENT_TXT_3;
                break;
            case R.id.auto_decision:
                AutoResponseSet mAutoResponseSet = new AutoResponseSet();
                mAutoResponseSet.setDecided(true);
                mAutoResponseSet.setContentNum(selectedContent);
                if (!checkNetWork()) {
                    return;
                }
                mAutoResponsePresenter.onDecision("00000", mAutoResponseSet);
                break;
            case R.id.auto_release:
                //TODO
                AutoResponseSet mAutoResponseSetRe = new AutoResponseSet();
                mAutoResponseSetRe.setDecided(false);
                mAutoResponseSetRe.setContentNum(selectedContent);
                if (!checkNetWork()) {
                    return;
                }
                mAutoResponsePresenter.onDecision("00000", mAutoResponseSetRe);
                setLayoutAfterReleased();
                break;
        }
    }

    @Override
    public void onStartRequest(){
        showProgress(getResStr(R.string.auto_respon_progress_message));
    }

    @Override
    public void onSuccess(boolean isDecided){
        AutoResponseSet mAutoResponseSet = new AutoResponseSet();
        mAutoResponseSet.setDecided(isDecided);
        if (isDecided) {
            mAutoResponseSet.setContentNum(selectedContent);
        }
        PreferencesUtil.setAutoResponseSeting(this, mAutoResponseSet);

        postDelay(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                startActivity(SettingActivity.class);
            }
        }, 2000);
        postDelay(new Runnable() {
            @Override
            public void run() {
                AutoResponseSet mAutoResponseSet = new AutoResponseSet();
                mAutoResponseSet.setDecided(false);
                PreferencesUtil.setAutoResponseSeting(AutoResponseActivity.this, mAutoResponseSet);
                setLayoutAfterReleased();
            }
        }, 10000);
    }

    @Override
    public void onFail(){
        //TODO network error
    }

    private Boolean checkIsSetted() {
        AutoResponseSet selectedContent = PreferencesUtil.getAutoResponseSeting(this);
        if (selectedContent.getDecided()) {
            this.selectedContent = selectedContent.getContentNum();
            return true;
        }
        return false;
    }

    private void setLayout() {
        clearContentPressed();
        setLayoutAfterDecisioned();
        if (CONTENT_TXT_1.equals(selectedContent)) {
            mContentTxt1.setBackgroundResource(R.drawable.auto_response_text_pressed);
        } else if(CONTENT_TXT_2.equals(selectedContent)) {
            mContentTxt2.setBackgroundResource(R.drawable.auto_response_text_pressed);
        } else {
            mContentTxt3.setBackgroundResource(R.drawable.auto_response_text_pressed);
        }
    }

    private void clearContentPressed() {
        mContentTxt1.setBackgroundResource(R.drawable.auto_response_text_normal);
        mContentTxt2.setBackgroundResource(R.drawable.auto_response_text_normal);
        mContentTxt3.setBackgroundResource(R.drawable.auto_response_text_normal);
    }

    private void setLayoutAfterDecisioned() {
        mAutoRelease.setEnabled(true);
        mAutoDecision.setEnabled(false);
        mContentTxt1.setEnabled(false);
        mContentTxt2.setEnabled(false);
        mContentTxt3.setEnabled(false);
    }

    private void setLayoutAfterReleased() {
        mAutoRelease.setEnabled(false);
        mContentTxt1.setEnabled(true);
        mContentTxt2.setEnabled(true);
        mContentTxt3.setEnabled(true);
        clearContentPressed();
    }
}
