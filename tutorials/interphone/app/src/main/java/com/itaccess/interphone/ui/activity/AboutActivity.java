package com.itaccess.interphone.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.itaccess.interphone.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by linxi on 2019/02/13.
 */

public class AboutActivity extends BaseActivity{
    public static final String TAG = "AboutActivity";

    @BindView(R.id.about_version_content)
    TextView mVersionName;

    @Override
       public int getLayoutRes() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.about_title));
        showTitleLeftIcon(true);
        initVersionName();
    }

    @OnClick({R.id.about_button_pair_link, R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_button_pair_link:
                Bundle mBundle = new Bundle();
                mBundle.putBoolean(TAG, true);
                startActivity(PairLinkActivity.class, mBundle);
                break;
            case R.id.txt_left:
                startActivity(MainActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }

    private void initVersionName() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
            mVersionName.setText(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //TODO
    //アプリ有効期限表示、最新バージョンを確認
}
