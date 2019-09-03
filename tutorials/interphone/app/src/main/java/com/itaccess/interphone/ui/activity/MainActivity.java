package com.itaccess.interphone.ui.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.itaccess.interphone.MainApplication;
import com.itaccess.interphone.R;
import com.itaccess.interphone.adapter.MessageListAdapter;
import com.itaccess.interphone.model.Message;
import com.itaccess.interphone.model.MonthData;
import com.itaccess.interphone.presenter.impl.MainPresenterImpl;
import com.itaccess.interphone.ui.OnReLoadListener;
import com.itaccess.interphone.view.MainView;
import com.itaccess.interphone.widget.CustomToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";

    public static final String FILE_NAME_MONTHDATA = "timelinedata";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.icon_setting)
    ImageView mIconSetting;

    private MessageListAdapter mMessageListAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private MainPresenterImpl mMainPresenter;

    List<Message> mMessages = null;

    private Boolean isNetworkError;

    @Override
    public int getLayoutRes(){
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.main_title));
        //メニューボタンを追加
        mToolbar.setLeftTitleDrawable(R.drawable.icon_menu);
        showTitleLeftIcon(true);
        mNavView.setNavigationItemSelectedListener(this);
        mMainPresenter = new MainPresenterImpl(this);
        initRecyclerView();
        isNetworkError = false;
    }

    private void initRecyclerView() {
        messagesLoad();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageListAdapter = new MessageListAdapter(this, mMessages, mRecyclerView);
        mRecyclerView.setAdapter(mMessageListAdapter);
        mRecyclerView.addOnScrollListener(scrollListener);
        mRecyclerView.scrollToPosition(mMessages.size() - 1);
        // メッセージをタップしたときはお知らせ一覧画面へ遷移する
        mMessageListAdapter.setStartActivityListener(new MessageListAdapter.StartActivityListener() {
            @Override
            public void start() {
                startActivity(NoticeListActivity.class);
            }
        });
    }

    private void startWebView(int id) {
        Bundle mBundle = new Bundle();
        mBundle.putString(WebActivity.WEBVIEW_TITLE, getResStr(id));
        startActivity(WebActivity.class, mBundle);
    }

    @OnClick({R.id.txt_left, R.id.icon_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.icon_setting:
                startActivity(SettingActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_home:
//                MessagesReload();
                break;
            case R.id.menu_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.menu_term_guide:
                startWebView(R.string.webview_term_guide_title);
                break;
            case R.id.menu_term:
                startWebView(R.string.webview_term_title);
                break;
            case R.id.menu_user_info:
                startWebView(R.string.webview_user_info_title);
                break;
            case R.id.menu_app:
//                startWebView(R.string.webview_about_app_title);
                startActivity(ReceivingActivity.class);
                break;
            case R.id.menu_inquiry:
                startActivity(InquiryActivity.class);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void dialogBtnClick(DialogInterface dialogInterface) {
//        if (btn.equals(DIALOG_NEGATIVE)) {
////            Bundle mBundle = new Bundle();
////            mBundle.putString(TAG, "00");
////            startActivity(LoginActivity.class, mBundle);
//        } else {
//            if (!isNetworkError) {
//                finish();
//            }
//        }
        dialogInterface.dismiss();
    }

    private RecyclerView.OnScrollListener scrollListener = new OnReLoadListener() {
        @Override
        protected void onLoading(int countItem, int lastItem) {
            postDelay(new Runnable() {
                @Override
                public void run() {
                    List<Message> reloadData = mMainPresenter.getMessagesRe();
                    if (reloadData.size() == mMessages.size()) {
                        Toast.makeText(MainActivity.this, getResStr(R.string.main_un_update_message),
                                Toast.LENGTH_LONG).show();
                        mRecyclerView.scrollToPosition(mMessages.size() - 1);
                        return;
                    }
                    mMessages.clear();
                    mMessages.addAll(reloadData);
                    mMessageListAdapter.notifyDataSetChanged();
                    mRecyclerView.scrollToPosition(mMessages.size() - 1);
                    messagesSave();
                }
            }, 3000);
        }
    };

    //TODO
    @Override
    public void onMessagesLoaded() {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onStartRequest() {
        showProgress(getResStr(R.string.main_reload_message));
    }

    private void messagesLoad() {

        if (checkForLoad()) {
            if (!checkNetWork()) {
                return;
            }
            mMessages = mMainPresenter.getMessages();
            showProgress(getResStr(R.string.main_reload_message));
            messagesSave();
            postDelay(new Runnable() {
                @Override
                public void run() {
                    hideProgress();
                }
            }, 3000);
        } else {
            messagesRead();
        }
    }

    private boolean checkForLoad() {
        MainApplication mApplication = (MainApplication) getApplication();
        boolean isMainOpened = mApplication.getMainOpened();
        if (isMainOpened) {
            return getIntent().getBooleanExtra(IS_LOAD, false);
        }
        mApplication.setMainOpened(true);
        return true;
    }

    private void messagesSave() {
        MonthData mMonthData = new MonthData();
        mMonthData.SetMonthData(mMessages);
        mMonthData.save(this, FILE_NAME_MONTHDATA);
    }

    private void messagesRead() {
        MonthData mMonthData = new MonthData();
        mMonthData.load(this, FILE_NAME_MONTHDATA);
        mMessages = mMonthData.getMonthData();
    }

}
