package com.itaccess.interphone.ui.activity;

import android.view.View;
import com.itaccess.interphone.R;
import com.itaccess.interphone.utils.StringUtil;

import butterknife.OnClick;

/**
 * Created by linxi on 2019/02/13.
 */

public class InquiryActivity extends BaseActivity {
    public static final String TAG = "InquiryActivity";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_inquiry;
    }

    @Override
    protected void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.inquiry_title));
        showTitleLeftIcon(true);
    }

    @OnClick(R.id.txt_left)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                String activityName = getIntent().getStringExtra(LoginActivity.TAG);
                if (!StringUtil.isNull(activityName) && LoginActivity.LOGIN_ACTIVITY.equals(activityName)) {
                    startActivity(LoginActivity.class);
                }
                startActivity(MainActivity.class);
                break;
        }
    }
}
