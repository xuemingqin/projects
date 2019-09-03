package com.itaccess.interphone.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.itaccess.interphone.R;
import com.itaccess.interphone.service.FirebaseMessagingService;
import com.itaccess.interphone.utils.RoamingUtil;
import com.itaccess.interphone.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by linxi on 2019/01/15.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "BaseActivity";

    public static final String IS_LOAD = "is_load";

    private Handler mHandler = new Handler();

    private AlertDialog.Builder mBuilder;

    private ProgressDialog mProgressDialog;

    private InputMethodManager mInputMethodManager;

    private Bundle mSavedInstanceState;

    protected NotificationReceiver mNotificationReceiver;

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.txt_main_title)
    TextView mTitle;
    @BindView(R.id.txt_left)
    TextView mLeftText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// 在Activity中去掉标题栏只需要在onCreate()中在setContentView前使用requestWindowFeature()。
//在AppCompatActivity中去掉标题栏只需要在onCreate()中在setContentView前使用supportRequestWindowFeature()。
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//自定义标题栏
        setSupportActionBar(mToolbar);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        mSavedInstanceState = savedInstanceState;
        initNotification();
        init();
    }

    protected void init() {}

    protected void initNotification() {
        mNotificationReceiver = new NotificationReceiver();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(FirebaseMessagingService.NOTIFICATION_ACTION);
        registerReceiver(mNotificationReceiver, mFilter);
    }

    protected void setToolBarTitle(String title) {
        mTitle.setText(title);
    }

    protected void showTitleLeftIcon(Boolean bool) {
        mLeftText.setVisibility(bool ? View.VISIBLE : View.INVISIBLE);
    }

    protected Bundle getSavedInstanceState() {
        return mSavedInstanceState;
    }

    public abstract int getLayoutRes();

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

    protected void post(Runnable runnable) {
        postDelay(runnable, 0);
    }

    protected void postDelay(Runnable runnable, long millis) {
        mHandler.postDelayed(runnable, millis);
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

    protected void dialogBtnClick(DialogInterface dialogInterface) {}

    protected void hideKeyBoard() {
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    protected Boolean checkNetWork() {
        if (!RoamingUtil.isNetworkConnected(this)) {
            showDialog(getResStr(R.string.dialog_error), getResStr(R.string.dialog_error_can_not_connect_network),
                    getResStr(R.string.dialog_error_close));
            return false;
        }
        return true;
    }

    protected String getResStr(int id) {
        return getResources().getString(id);
    }


    protected void notification(String message, String url) {
        notificationRing();
        showDialog(getResStr(R.string.notice_webview_title), message + url,
                getResStr(R.string.dialog_error_close), getResStr(R.string.dialog_error_confirm));
    }

    private void notificationRing() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone mRingtone = RingtoneManager.getRingtone(this, uri);
        mRingtone.play();
    }

    public class
    NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String url = intent.getStringExtra(FirebaseMessagingService.EXTRA_URL);
            String message = intent.getStringExtra(FirebaseMessagingService.EXTRA_MESSAGE);
            notification(message, url);
        }

    }
//广播取消
    @Override
    protected void onDestroy() {
        if (mNotificationReceiver != null) {
            unregisterReceiver(mNotificationReceiver);
        }
        super.onDestroy();
    }

}