package com.example.interphone;

import android.content.Context;
import android.content.SharedPreferences;

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
}
