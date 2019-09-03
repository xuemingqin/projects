package com.itaccess.interphone.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.itaccess.interphone.model.AutoResponseSet;
import com.itaccess.interphone.model.User;

/**
 * Created by linxi on 2019/01/16.
 */

public class PreferencesUtil {

    /** ユーザー情報 */
    private static final String USER_INFO = "user_info";
    /** ログインID */
    private static final String USER_ID = "user_id";
    /** パスワード */
    private static final String PASSWORD = "password";

    /** 認証情報 */
    private static final String AUTHENTICATION= "authentication";
    /** 認証/非認証 */
    private static final String IS_AUTHENTICATED= "is_Authenticated";
    /** ニックネーム */
    private static final String NICKNAME = "nickname";
    /** ログイン/非ログイン */
    private static final String IS_NICKNAME_LOGINED= "is_nickname_logined";
    /** 音声出力先 */
    private static final String VOICE_OUTPUT = "voice_output";
    /** 音声出力先番号 */
    private static final String VOICE_OUTPUT_ID= "voice_output_id";
    /** 着信音 */
    private static final String RING_TONE = "ring_tone";
    /** 着信音タイプ */
    private static final String RING_TONE_NAME= "ring_tone_name";
    /** 自動応答設定 */
    private static final String AUTO_RESPONSE_SET = "auto_response_set";
    /** 自動応答設定_(決定・解除) */
    private static final String AUTO_RESPONSE_ISDECIDED = "auto_response_isdecided";
    /** 自動応答設定_内容*/
    private static final String AUTO_RESPONSE_CONTENT_NUM = "auto_response_content_num";
    /** 音量設定 */
    private static final String CALL_VOLUME = "call_volume";
    /** 音量設定内容 */
    private static final String CALL_VOLUME_SET= "call_volume_set";


    /**
     * ユーザー情報を保存.
     * @param mContext
     * @param mUser
     */
    public static void saveUser(Context mContext, User mUser){
        SharedPreferences sp = mContext.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_ID, mUser.getUserId());
        if (!StringUtil.isNull(mUser.getPassword())) {
            editor.putString(PASSWORD, mUser.getPassword());
        }
        editor.commit();
    }

    /**
     * ユーザー情報を読み取り
     * @param mContext
     * @return  User
     */
    public static User readUser(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        User user = new User();
        user.setUserId(sp.getString(USER_ID,""));
        user.setPassword(sp.getString(PASSWORD, ""));
        return user;
    }

    /**
     * 認証情報を保存.
     * @param mContext
     * @param isAuthenticated
     */
    public static void setAuthentication(Context mContext, Boolean isAuthenticated){
        SharedPreferences sp = mContext.getSharedPreferences(AUTHENTICATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_AUTHENTICATED, isAuthenticated);
        editor.commit();
    }

    /**
     * 認証情報を読み取り
     * @param mContext
     * @return  Boolean
     */
    public static Boolean getAuthentication(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(AUTHENTICATION, Context.MODE_PRIVATE);
        Boolean isAuthenticated = sp.getBoolean(IS_AUTHENTICATED, false);
        return isAuthenticated;
    }

    /**
     * ニックネーム登録情報を保存.
     * @param mContext
     * @param nickname
     */
    public static void setNickname(Context mContext, String nickname) {
        SharedPreferences sp = mContext.getSharedPreferences(NICKNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(IS_NICKNAME_LOGINED, nickname);
        editor.commit();
    }

    /**
     * ニックネーム登録情報を読み取り
     * @param mContext
     * @return  String
     */
    public static String getNickname(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(NICKNAME, Context.MODE_PRIVATE);
        String nickname = sp.getString(IS_NICKNAME_LOGINED, null);
        return nickname;
    }

    /**
     * 音声出力先番号を保存.
     * @param mContext
     * @param isSpeakerphoneOn
     */
    public static void setSpeakerphoneOn(Context mContext, Boolean isSpeakerphoneOn) {
        SharedPreferences sp = mContext.getSharedPreferences(VOICE_OUTPUT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(VOICE_OUTPUT_ID, isSpeakerphoneOn);
        editor.commit();
    }

    /**
     * 音声出力先番号を読み取り
     * @param mContext
     * @return  Boolean
     */
    public static Boolean getSpeakerphoneOn(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(VOICE_OUTPUT, Context.MODE_PRIVATE);
        Boolean isSpeakerphoneOn = sp.getBoolean(VOICE_OUTPUT_ID, false);
        return isSpeakerphoneOn;
    }

    /**
     * 着信音情報を保存.
     * @param mContext
     * @param ringtone
     */
    public static void setRingtone(Context mContext, String ringtone) {
        SharedPreferences sp = mContext.getSharedPreferences(RING_TONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(RING_TONE_NAME, ringtone);
        editor.commit();
    }

    /**
     * 着信音情報を読み取り
     * @param mContext
     * @return  String
     */
    public static String getRingtone(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(RING_TONE, Context.MODE_PRIVATE);
        String ringtone = sp.getString(RING_TONE_NAME, null);
        return ringtone;
    }

    /**
     * 自動応答設定情報を保存.
     * @param mContext
     * @param mAutoResponseSet
     */
    public static void setAutoResponseSeting(Context mContext, AutoResponseSet mAutoResponseSet) {
        SharedPreferences sp = mContext.getSharedPreferences(AUTO_RESPONSE_SET, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(AUTO_RESPONSE_ISDECIDED, mAutoResponseSet.getDecided());
        editor.putString(AUTO_RESPONSE_CONTENT_NUM, mAutoResponseSet.getContentNum());
        editor.commit();
    }

    /**
     * 自動応答設定情報を読み取り
     * @param mContext
     * @return  AutoResponseSet
     */
    public static AutoResponseSet getAutoResponseSeting(Context mContext){
        AutoResponseSet mAutoResponseSet = new AutoResponseSet();
        SharedPreferences sp = mContext.getSharedPreferences(AUTO_RESPONSE_SET, Context.MODE_PRIVATE);

        mAutoResponseSet.setDecided(sp.getBoolean(AUTO_RESPONSE_ISDECIDED, false));
        mAutoResponseSet.setContentNum(sp.getString(AUTO_RESPONSE_CONTENT_NUM, ""));
        return mAutoResponseSet;
    }

    /**
     * 音量設定を保存.
     * @param mContext
     * @param callVolume
     */
    public static void setCallVolume(Context mContext, int callVolume) {
        SharedPreferences sp = mContext.getSharedPreferences(CALL_VOLUME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(CALL_VOLUME_SET, callVolume);
        editor.commit();
    }

    /**
     * 音量設定を読み取り
     * @param mContext
     * @return  String
     */
    public static int getCallVolume(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(CALL_VOLUME, Context.MODE_PRIVATE);
        int callVolume = sp.getInt(CALL_VOLUME_SET, 0);
        return callVolume;
    }


}
