package com.itaccess.interphone.ui.activity;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itaccess.interphone.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by linxi on 2019/01/15.
 */

public class TermOfUseActivity extends BaseActivity implements View.OnTouchListener{

    public static final String TAG = "TermOfUseActivity";

    private int height;

    @BindView(R.id.check_box)
    ImageButton mCheckBox;
   @BindView(R.id.conform_text)
    TextView mConformText;
   @BindView(R.id.agree)
    Button mAgree;
   @BindView(R.id.scroll_layout)
    ScrollView mScrollLayout;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_term;
    }

    @Override
    protected void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.term_title));
        showTitleLeftIcon(true);
        mScrollLayout.setOnTouchListener(this);
    }

    @OnClick({R.id.agree, R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.agree:
                agree();
                break;
            case R.id.txt_left:
                startActivity(LoginActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE :
                height++;
                break;
            case MotionEvent.ACTION_UP :
                if (height > 0) {
                    height = 0;
                    View view = ((ScrollView) v).getChildAt(0);
                    if (view.getMeasuredHeight() <= v.getScrollY() + v.getHeight()) {
                        conform();
                    }
                }
                break;
        }
        return false;
    }

    private void agree() {
        startActivity(PairLinkActivity.class);
    }

    private void conform() {
        Drawable checked = getResources().getDrawable(R.drawable.checked);
        if (mCheckBox != null) {
            mCheckBox.setImageDrawable(checked);
        }
        if (mConformText != null) {
            mConformText.setEnabled(true);
        }
        if (mAgree != null) {
            mAgree.setEnabled(true);
        }
    }

}
