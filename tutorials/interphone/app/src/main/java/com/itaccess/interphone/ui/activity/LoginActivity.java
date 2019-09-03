package com.itaccess.interphone.ui.activity;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itaccess.interphone.MainApplication;
import com.itaccess.interphone.R;
import com.itaccess.interphone.model.User;
import com.itaccess.interphone.presenter.LoginPresenter;
import com.itaccess.interphone.presenter.impl.LoginPresenterImpl;
import com.itaccess.interphone.utils.PreferencesUtil;
import com.itaccess.interphone.utils.StringUtil;
import com.itaccess.interphone.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by linxi on 2019/01/15.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    public static final String TAG = "LoginActivity";
    public static final String LOGIN_ACTIVITY = "01";

    @BindView(R.id.user_id)
    EditText mUserId;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.login_forgot_link)
    TextView forgotLink;

    private LoginPresenter mLoginPresenter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.activity_login_title));
        mLoginPresenter = new LoginPresenterImpl(this);
        mPassword.setOnEditorActionListener(mOnEditorActionListener);
        mUserId.addTextChangedListener(getTextWatcher());
        mPassword.addTextChangedListener(getTextWatcher());
    }

    @OnClick({R.id.login, R.id.login_forgot_link})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.login_forgot_link:
                Bundle mBundle = new Bundle();
                mBundle.putString(TAG, LOGIN_ACTIVITY);
                startActivity(InquiryActivity.class, mBundle);
                break;
        }
    }

    //TODO
    private void login() {
        hideKeyBoard();
        if (!checkNetWork()) {
            return;
        }
        String userId = mUserId.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        mLoginPresenter.login(userId, password);
    }

    @Override
    public void onStartRequest() {
        showProgress(getResStr(R.string.activity_login_progress_message));
    }

    @Override
    public void onSuccess() {
        //TODO
        //ユーザー情報を保存する
        User mUser = new User();
        String userId = mUserId.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        mUser.setUserId(userId);
        mUser.setPassword(password);
        PreferencesUtil.saveUser(this, mUser);

        postDelay(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                Bundle mBundle = getIntent().getExtras();
                if (mBundle != null) {
                    if (!StringUtil.isNull(mBundle.getString(MainActivity.TAG))) {
                        startActivity(MainActivity.class);
                    }
                } else {
                    startActivity(TermOfUseActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        }, 2000);
    }

    @Override
    public void onFail() {
        hideProgress();
        showDialog(getResStr(R.string.dialog_error), getResStr(R.string.activity_login_btn_login_error),
                getResStr(R.string.dialog_error_close));
    }

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            String userId = mUserId.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            if (!userId.isEmpty() && !password.isEmpty()) {
                hideKeyBoard();
                return true;
            }
            return false;
        }
    };

    /**
     * アカウント入力、パスワード入力テキストに設定するTextWatcher生成.
     * @return EditTextにセットするWatcher
     */
    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              // nop
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean enableButton = false;
                String userId = mUserId.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (StringUtil.isNull(userId) || StringUtil.isNull(password)) {
                    enableButton = false;
                } else {
                    enableButton = true;
                }

                // ボタンの状態設定.
                if (mLogin != null) {
                    mLogin.setEnabled(enableButton);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // nop
            }
        };
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        if (v != null && v instanceof EditText) {
            Rect outRect = new Rect();
            v.getGlobalVisibleRect(outRect);
            if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                v.clearFocus();
                hideKeyBoard();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void dialogBtnClick(DialogInterface dialogInterface) {
        dialogInterface.dismiss();
    }

}
