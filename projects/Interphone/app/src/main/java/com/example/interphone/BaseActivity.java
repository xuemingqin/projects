package com.example.interphone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity{
    public static final String TAG = "BaseActivity";

    private InputMethodManager mInputMethodManager;
    private Handler mHandler = new Handler();
    private AlertDialog.Builder mBuilder;
    public static final String IS_LOAD = "is_load";
    private ProgressDialog mProgressDialog;

    @BindView( R.id.tool_bar_head)
    Toolbar toolbar;
    @BindView( R.id.txt_main_title )
    TextView mTitle;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        supportRequestWindowFeature( Window.FEATURE_NO_TITLE );
        setSupportActionBar( toolbar );
        setContentView( getLayoutRes ());
        ButterKnife.bind( this );
        init();
    }

    protected void init(){
    }

    protected abstract int getLayoutRes();
    protected void post(Runnable runnable) {
        postDelay(runnable, 0);
    }

    protected void postDelay(Runnable runnable, long millis) {
        mHandler.postDelayed(runnable, millis);
    }


    protected void setToolBarTitle(String title) {
        mTitle.setText(title);
    }
    protected void startActivity(Class activity) {
        startActivity(activity, null,  true);
    }

    protected void startActivity(Class activity, Bundle bundle) {
        startActivity(activity, bundle, true);
    }



    protected void startActivity(Class activity, Bundle bundle, boolean finish) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (finish) {
            finish();
        }

    }
    protected void hideKeyBoard() {
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
        }
        if (getCurrentFocus() != null) {
            mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }

    }
    protected void showDialog(String title, String msg, String btn) {
        showDialog(title, msg, null, btn);
    }

    protected void showDialog(String title, String msg, String btn1, String btn2) {
        if (mBuilder == null) {

            mBuilder = new AlertDialog.Builder(this);
            mBuilder.setTitle(title);
            mBuilder.setMessage(msg);
            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton(btn1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Bundle mBundle = new Bundle();
                    mBundle.putBoolean(IS_LOAD, true);
                    startActivity(MainActivity.class, mBundle);
                }
            });
            if (btn2 != null) {
                mBuilder.setNegativeButton(btn2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogBtnClick(dialogInterface);
                    }
                });
            }
        }
        mBuilder.create();
        mBuilder.show();
    }
    protected String getResStr(int id) {
        return getResources().getString(id);
    }
    protected void dialogBtnClick(DialogInterface dialogInterface) {

    }
    protected Boolean checkNetWork() {
        if (!RoamingUtil.isNetworkConnected(this)) {
            showDialog(getResStr(R.string.dialog_error), getResStr(R.string.dialog_error_can_not_connect_network),
                    getResStr(R.string.dialog_error_close));
            return false;
        }
        return true;
    }
    protected void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
//            设置ProgressDialog 是否可以按返回键取消；
            mProgressDialog.setCancelable(true);
        }
//        设置提示信息；
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }
    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
