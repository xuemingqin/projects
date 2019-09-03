package com.itaccess.interphone.ui.activity;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.itaccess.interphone.R;
import com.itaccess.interphone.model.User;
import com.itaccess.interphone.presenter.PasswordChangePresenter;
import com.itaccess.interphone.presenter.impl.PasswordChangePresenterImpl;
import com.itaccess.interphone.utils.PreferencesUtil;
import com.itaccess.interphone.utils.StringUtil;
import com.itaccess.interphone.view.PasswordChangeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by linxi on 2019/01/22.
 */

public class PasswordChangeActivity extends BaseActivity implements PasswordChangeView{

    public static final String TAG = "PasswordChangeActivity";

    @BindView(R.id.now_password_edit)
    EditText mNowPassword;
    @BindView(R.id.new_password_edit)
    EditText mNewPassword;
    @BindView(R.id.conform_password_edit)
    EditText mConformPassword;
    @BindView(R.id.password_change)
    Button mPasswordChange;

    private PasswordChangePresenter mPasswordChangePresenter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_password_change;
    }

    @Override
    protected void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.activity_password_change_title));
        showTitleLeftIcon(true);

        mNowPassword.addTextChangedListener(getTextWatcher());
        mNewPassword.addTextChangedListener(getTextWatcher());
        mConformPassword.addTextChangedListener(getTextWatcher());
        mPasswordChangePresenter = new PasswordChangePresenterImpl(this);
    }

    @OnClick({R.id.txt_left, R.id.password_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                startActivity(SettingActivity.class);
                break;
            case R.id.password_change:
                checkPassword();
                break;
        }
    }

    private void checkPassword() {
        String nowPassword = mNowPassword.getText().toString().trim();
        String newPassword = mNewPassword.getText().toString().trim();
        String conformPassword = mConformPassword.getText().toString().trim();

        if (newPassword.length() < 4 && conformPassword.length() < 4) {
            showDialog(getResStr(R.string.dialog_error), getResStr(R.string.activity_password_change_error_length),
                    getResStr(R.string.dialog_error_close));
            return;
        }

        if (!newPassword.equals(conformPassword)) {
            showDialog(getResStr(R.string.dialog_error), getResStr(R.string.activity_password_change_error_equal),
                    getResStr(R.string.dialog_error_close));
            return;
        }
        User mUser = PreferencesUtil.readUser(this);
        if (!StringUtil.isNull(mUser.getPassword())) {
            if (!nowPassword.equals(mUser.getPassword())) {
                showDialog(getResStr(R.string.dialog_error), getResStr(R.string.activity_password_change_error_now),
                        getResStr(R.string.dialog_error_close));
                return;
            }
        }
        //TODO
        if (!checkNetWork()) {
            return;
        }
        mPasswordChangePresenter.ChangePassword("00000", newPassword);

        int position2 = getIntent().getIntExtra(SettingActivity.POSITION, 0);
        Bundle mBundle2 = new Bundle();
        mBundle2.putInt(SettingActivity.POSITION, position2);
        startActivity(SettingActivity.class, mBundle2);

    }

    /**
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
                String nowPassword = mNowPassword.getText().toString().trim();
                String newPassword = mNewPassword.getText().toString().trim();
                String conformPassword = mConformPassword.getText().toString().trim();
                if (StringUtil.isNull(nowPassword) || StringUtil.isNull(newPassword) || StringUtil.isNull(conformPassword)) {
                    enableButton = false;
                } else {
                    enableButton = true;
                }

                // ボタンの状態設定.
                if (mPasswordChange != null) {
                    mPasswordChange.setEnabled(enableButton);
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
//            mNowPassword.setText("");
//            mNewPassword.setText("");
//            mConformPassword.setText("");
    }

    @Override
    public void onStartRequest(){

    }

    public void onSuccess() {

    }

    public void onFail() {

    }
}
