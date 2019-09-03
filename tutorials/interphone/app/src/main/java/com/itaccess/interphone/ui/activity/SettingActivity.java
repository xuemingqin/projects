package com.itaccess.interphone.ui.activity;

import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.itaccess.interphone.R;
import com.itaccess.interphone.adapter.SettingAdapter;
import com.itaccess.interphone.ui.fragment.RingtoneDialogFragment;
import com.itaccess.interphone.utils.PreferencesUtil;
import com.itaccess.interphone.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by linxi on 2019/01/19.
 */

public class SettingActivity extends BaseActivity implements SettingAdapter.OnItemClickListener, RingtoneDialogFragment.OnRingtoneDialogListener{

    public static final String TAG = "SettingActivity";
    public static final String POSITION = "position";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private LinearLayoutManager linearLayoutManager;
    private Ringtone mRingtone;
    private RingtoneManager mRingtoneManager;
    private SettingAdapter mSettingAdapter;
    private List<String> mDataList;

    @Override
    public int getLayoutRes(){
        return R.layout.activity_setting;
    }

    @Override
    public void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.setting_title));
        showTitleLeftIcon(true);

        setRecyclerView();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        mRingtone = RingtoneManager.getRingtone(this, uri);
    }

    private void setRecyclerView () {
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mSettingAdapter = new SettingAdapter(this, getListData());
        mRecyclerView.setAdapter(mSettingAdapter);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private List<String> getListData() {
        String nickname = PreferencesUtil.getNickname(this);
        String titles[];
        if (StringUtil.isNull(nickname)) {
            //ニックネーム登録を表示する
            titles = getResources().getStringArray(R.array.setting_recyclerview_title_array);
        } else {
            //ニックネーム変更を表示する
            titles = getResources().getStringArray(R.array.setting_recyclerview_title_array_sec);
        }
        List<String> mList = StringUtil.asList(titles);
        return mList;
    }

    @OnClick(R.id.txt_left)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                startActivity(MainActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 1:
                startActivity(CallSetttingActivity.class);
                break;
            case 2:
                setRingtone();
                break;
            case 3:
                startActivity(NicknameActivity.class);
                break;
            case 4:
                startActivity(AutoResponseActivity.class);
                break;
            case 6:
                startActivity(PasswordChangeActivity.class);
                break;
        }
    }

    @Override
    public void onRingtoneClicked(int position) {

        mRingtone = mRingtoneManager.getRingtone(position);
        mRingtone.play();
    }

    @Override
    public void dialogDismiss(boolean isDismiss) {
        if (isDismiss) {
            mRingtone.stop();
            mSettingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void dialogOkClicked(int position) {
        String ringtone = mDataList.get(position);
        //Preferencesに着信音の変更を保存
        PreferencesUtil.setRingtone(this, ringtone);
    }

    private void setRingtone() {
        mRingtoneManager = new RingtoneManager(getApplicationContext());
        Cursor mCursor = mRingtoneManager.getCursor();
        mDataList = new ArrayList<>();
        while (mCursor.moveToNext()) {
            mDataList.add(mCursor.getString(RingtoneManager.TITLE_COLUMN_INDEX));
        }
        String[] value = StringUtil.toArray(mDataList);
        RingtoneDialogFragment mFragment = RingtoneDialogFragment.newInstance(value);
        mFragment.show(getFragmentManager(), RingtoneDialogFragment.TAG);
        mFragment.setOnRingtoneDialogListener(this);
    }
}
