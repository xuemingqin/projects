package com.itaccess.interphone.ui.activity;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itaccess.interphone.R;
import com.itaccess.interphone.model.User;
import com.itaccess.interphone.presenter.NicknamePresenter;
import com.itaccess.interphone.presenter.impl.NicknamePresenterImpl;
import com.itaccess.interphone.utils.PreferencesUtil;
import com.itaccess.interphone.utils.StringUtil;
import com.itaccess.interphone.view.NicknameView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by linxi on 2019/01/19.
 */

public class NicknameActivity extends BaseActivity implements NicknameView {

    @BindView(R.id.nickname_edit)
    EditText mLoginEdit;
    @BindView(R.id.nickname_now_text)
    TextView mChangeNowText;
    @BindView(R.id.nickname_new_edit)
    EditText mChangeNewEdit;
    @BindView(R.id.nickname_login)
    Button mBtnLogin;
    @BindView(R.id.nickname_change)
    Button mBtnChange;
    @BindView(R.id.cancel_login)
    Button mBtnLoginCel;
    @BindView(R.id.cancel_change)
    Button mBtnChangeCel;
    @BindView(R.id.nickname_login_layout)
    LinearLayout mLoginLayout;
    @BindView(R.id.nickname_change_layout)
    LinearLayout mChangeLayout;

    private Boolean isLogin = true;
    private NicknamePresenter mNicknamePresenter;
    private String nickname;

    @Override
    public int getLayoutRes(){
        return R.layout.activity_nickname;
    }

    @Override
    public void init() {
        super.init();
        setLayout();
        setToolBarTitle(getResStr(isLogin ? R.string.nick_name_login_title : R.string.nick_name_change_title));
        showTitleLeftIcon(true);
        mNicknamePresenter = new NicknamePresenterImpl(this);
    }

    private void setLayout() {
        nickname = PreferencesUtil.getNickname(this);
        //ニックネーム登録のLayout設定
        if (StringUtil.isNull(nickname)) {
            mLoginLayout.setVisibility(View.VISIBLE);
            mChangeLayout.setVisibility(View.GONE);
            mLoginEdit.addTextChangedListener(getTextWatcher());
            isLogin = true;
        } else {
            //ニックネーム変更のLayout設定
            mLoginLayout.setVisibility(View.GONE);
            mChangeLayout.setVisibility(View.VISIBLE);
            mChangeNowText.setText(nickname);
            mChangeNewEdit.addTextChangedListener(getTextWatcher());
            isLogin = false;
        }
    }

    @OnClick({R.id.txt_left, R.id.cancel_login, R.id.cancel_change, R.id.nickname_login, R.id.nickname_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
            case R.id.cancel_login:
            case R.id.cancel_change:
                startActivity();
                break;
            case R.id.nickname_login:
                startLogin();
                break;
            case R.id.nickname_change:
                startChange();
                break;
        }
    }

    private void startActivity() {
        startActivity(SettingActivity.class);
    }

    private void startLogin() {
        User mUser = PreferencesUtil.readUser(this);
        String userId = mUser.getUserId();
        String nickname = mLoginEdit.getText().toString().trim();
        if (!checkNetWork()) {
            return;
        }
        mNicknamePresenter.login(userId, nickname);
    }

    private void startChange() {
        String newNickname = mChangeNewEdit.getText().toString().trim();
        if (!nickname.equals(newNickname)) {
            User mUser = PreferencesUtil.readUser(this);
            String userId = mUser.getUserId();
            if (!checkNetWork()) {
                return;
            }
            mNicknamePresenter.login(userId, newNickname);
        } else {
            showDialog(getResStr(R.string.dialog_error), getResStr(R.string.nick_name_change_btn_error),
                    getResStr(R.string.dialog_error_close));
        }
    }

    @Override
    public void onStartRequest() {
        if (isLogin) {
            showProgress(getResStr(R.string.nick_name_login_progress_message));
        } else {
            showProgress(getResStr(R.string.nick_name_change_progress_message));
        }

    }

    @Override
    public void onSuccess() {
        //TODO
        if (isLogin) {
            nickname = mLoginEdit.getText().toString().trim();
        } else {
            nickname = mChangeNewEdit.getText().toString().trim();
        }
        PreferencesUtil.setNickname(this, nickname);

        postDelay(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                startActivity();

            }
        }, 2000);
    }

    @Override
    public void onFail() {
        hideProgress();
        showDialog(getResStr(R.string.dialog_error), getResStr(R.string.activity_login_btn_login_error),
                getResStr(R.string.dialog_error_close));
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
                if (isLogin) {
                    boolean enableButton = false;
                    String nickname = mLoginEdit.getText().toString().trim();
                    if (!StringUtil.isNull(nickname)) {
                        enableButton = true;
                    } else {
                        enableButton = false;
                    }
                    if (mBtnLogin != null) {
                        mBtnLogin.setEnabled(enableButton);
                        mBtnLoginCel.setEnabled(enableButton);
                    }
                } else {
                    boolean enableButton = false;
                    String newNickname = mChangeNewEdit.getText().toString().trim();
                    if (!StringUtil.isNull(newNickname)) {
                        enableButton = true;
                    } else {
                        enableButton = false;
                    }
                    if (mBtnLogin != null) {
                        mBtnChange.setEnabled(enableButton);
                        mBtnChangeCel.setEnabled(enableButton);
                    }
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
        if (!isLogin) {
            mChangeNewEdit.setText("");
            //mChangeNewEdit.requestFocus();
        }
    }
}
