package com.itaccess.interphone.ui.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;;

import com.itaccess.interphone.R;
import com.itaccess.interphone.adapter.CallSettingAdapter;
import com.itaccess.interphone.utils.AudioOutputUtil;
import com.itaccess.interphone.utils.PreferencesUtil;
import com.itaccess.interphone.utils.StringUtil;
import com.itaccess.interphone.widget.ItemDecorationLine;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by linxi on 2019/01/23.
 */

public class CallSetttingActivity extends BaseActivity {

    @BindView(R.id.seekBar_call_volume)
    SeekBar mCallVolume;
    @BindView(R.id.call_setting_recycler)
    RecyclerView mRecyclerView;

    public AudioManager mAudioManager;
    private int maxVolume, currentVolume;
    private LinearLayoutManager mLinearLayoutManager;
    private CallSettingAdapter mCallSettingAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_call_setting;
    }

    @Override
    protected void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.call_setting_title));
        showTitleLeftIcon(true);
       // mMicroVolume.setOnSeekBarChangeListener(getSeekBarChangeListener());

        setVoiceCall();
        setVoiceOutputType();
        mCallVolume.setOnSeekBarChangeListener(getSeekBarChangeListener());
    }

    private void setVoiceCall() {
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        mCallVolume.setMax(maxVolume);
        currentVolume = PreferencesUtil.getCallVolume(this);
        if ( currentVolume != 0) {
            mCallVolume.setProgress(currentVolume);
        } else {
            currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
            mCallVolume.setProgress(currentVolume);
        }
    }

    private void setVoiceOutputType() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        List<String> listData = StringUtil.asList(getResources().getStringArray(R.array.call_setting_voice_output_type));
        mCallSettingAdapter = new CallSettingAdapter(this, listData, mRecyclerListener);
        mRecyclerView.setAdapter(mCallSettingAdapter);
        mRecyclerView.addItemDecoration(new ItemDecorationLine(this));
    }

    @OnClick({R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                startActivity(SettingActivity.class);
                break;
        }
    }

    private SeekBar.OnSeekBarChangeListener getSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!getDoNotDisturb()) {
                    mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, progress, 0);
                    currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
                    mCallVolume.setProgress(currentVolume);

                    PreferencesUtil.setCallVolume(CallSetttingActivity.this, currentVolume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nop
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // nop
            }
        };
    }

    private Boolean getDoNotDisturb(){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
            return true;
        } else {
            return false;
        }
    }
    private CallSettingAdapter.OnRecyclerListener mRecyclerListener = new CallSettingAdapter.OnRecyclerListener()
    {
        @Override
        public void onRecyclerClicked (int position){
            switch (position) {
                case 0:
                    //TODO
                    PreferencesUtil.setSpeakerphoneOn(CallSetttingActivity.this, true);
                    //Toast.makeText(CallSetttingActivity.this, getResStr(R.string.call_setting_voice_output_speaker), Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    PreferencesUtil.setSpeakerphoneOn(CallSetttingActivity.this, false);
                    //Toast.makeText(CallSetttingActivity.this, getResStr(R.string.call_setting_voice_output_headph), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}
