package com.example.interphone;

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

import butterknife.BindView;
import butterknife.OnClick;

public class MyLoginActivity extends BaseActivity implements LoginView{
    public static final String TAG = "LoginActivity";
    public static final String LOGIN_ACTIVITY = "01";

    @BindView(R.id.edit_id)
    EditText mUserId;
    @BindView(R.id.edit_password)
    EditText mPassword;
    @BindView(R.id.btn_login)
    Button mLogin;
    @BindView(R.id.forget)
    TextView forgotLink;

    private LoginPresenter mloginPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;

    }

    @Override
    protected void init() {
        super.init( );
        setToolBarTitle( getResStr( R.string.activity_login_title ) );
        mloginPresenter = new LoginPresenterImpl( this );
        mPassword.setOnEditorActionListener( monEditorActionListener );
        mUserId.addTextChangedListener( textWatcher );
        mPassword.addTextChangedListener( textWatcher );
    }

    @OnClick({R.id.btn_login, R.id.forget})
    public void onClick(View view) {
        switch (view.getId( )) {
            case R.id.btn_login:
                login( );
                break;
            case R.id.forget:
                Bundle mBundle = new Bundle( );
                mBundle.putString( TAG, LOGIN_ACTIVITY );
                startActivity( TermsOfUseActivity.class, mBundle );
                break;
        }

    }

    private void login() {
        hideKeyBoard( );
        if (!checkNetWork( )) {
            return;
        }
        String userId = mUserId.getText( ).toString( ).trim( );
        String password = mPassword.getText( ).toString( ).trim( );
        mloginPresenter.login( userId, password );
    }


    @Override
    public void onSuccess() {
        User mUser = new User( );
        String userId = mUserId.getText( ).toString( ).trim( );
        String password = mPassword.getText( ).toString( ).trim( );
        mUser.setUserId( userId );
        mUser.setPassword( password );
        PreferencesUtil.saveUser( this, mUser );

        postDelay( new Runnable( ){
            @Override
            public void run() {
                hideProgress( );
                Bundle mBundle = getIntent( ).getExtras( );
                if (mBundle != null) {
                    if (!StringUtil.isNull( mBundle.getString( MainActivity.TAG ) )) {
                        startActivity( TermsOfUseActivity.class );
                    }
                } else {
                    startActivity( MainActivity.class );
                    overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
                }

            }
        }, 2000 );

    }

    @Override
    public void onStartRequest() {
        showProgress( getResStr( R.string.activity_login_progress_message ) );

    }

    @Override
    public void onFail() {
        hideProgress( );
        showDialog( getResStr( R.string.dialog_error ), getResStr( R.string.activity_login_btn_login_error ),
                getResStr( R.string.dialog_error_close ) );


    }

    private TextView.OnEditorActionListener monEditorActionListener = new TextView.OnEditorActionListener( ){
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            String useId = mUserId.getText( ).toString( ).trim( );
            String password = mPassword.getText( ).toString( ).trim( );
            if (!useId.isEmpty( ) && !password.isEmpty( )) {
                hideKeyBoard( );
                return true;
            }
            return false;
        }
    };
    TextWatcher textWatcher = new TextWatcher( ){
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean enableButton = false;
            String useId = mUserId.getText( ).toString( ).trim( );
            String password = mPassword.getText( ).toString( ).trim( );
            if (StringUtil.isNull( useId ) || StringUtil.isNull( password )) {
                enableButton = false;
            } else {
                enableButton = true;
            }
            if (mLogin != null) {
                mLogin.setEnabled( enableButton );
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();
        if (v != null && v instanceof EditText){
            Rect outRect = new Rect();
            v.getGlobalVisibleRect( outRect );
            if (! outRect.contains( (int)  ev.getRawX(),(int) ev.getRawY())){
                v.clearFocus();
                hideKeyBoard();
            }
        }
        return super.dispatchTouchEvent( ev );
    }

    @Override
    protected void dialogBtnClick(DialogInterface dialogInterface) {
        dialogInterface.dismiss();
    }
}
