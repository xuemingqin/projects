package com.itaccess.interphone.utils;

import android.media.AudioManager;
import android.os.Build;

/**
 * Created by linxi on 2019/01/23.
 */

public class AudioOutputUtil {

    /**
     * Speakerへ切替
     * @param mAudioManager
     */
    public static void changeToSpeaker(AudioManager mAudioManager){
        mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        mAudioManager.setBluetoothScoOn(false);
        mAudioManager.setSpeakerphoneOn(true);
    }

    /**
     * ヘッドフォンへ切替
     * @param mAudioManager
     */
    public static void changeToHeadset(AudioManager mAudioManager){
        mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        mAudioManager.setBluetoothScoOn(false);
        mAudioManager.setSpeakerphoneOn(false);
    }

}
