package com.itaccess.interphone.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.List;

/**
 * Created by linxi on 2019/01/19.
 */

public class WindowUtil {

    /** phone 高さ
     * @param mContext
     * @return   int
     */
    public static int getWindowHeight(final Context mContext){
        DisplayMetrics _DisplayMetrics = new android.util.DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(_DisplayMetrics);
        return _DisplayMetrics.heightPixels;
    }

    /**
     *  status bar 高さ
     * @param mContext
     * @return int
     */
    public static int getStatusHeight(final Context mContext) {
        int _Statusheight = 50;
        int _ResourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (_ResourceId > 0) {
            _Statusheight = mContext.getResources().getDimensionPixelSize(_ResourceId);
        }
        return _Statusheight;
    }

    /**
     * ActionBar 高さ
     * @param mContext
     * @return
     */
    public static int getActionBarHeight(final Context mContext) {
        int _ActionBarHeight = 0;
        TypedValue _TypedValue = new TypedValue();
        if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, _TypedValue, true))
        {
            _ActionBarHeight = TypedValue.complexToDimensionPixelSize(_TypedValue.data, mContext.getResources().getDisplayMetrics());
        }
        return _ActionBarHeight;
    }

    /**
     * アプリ状態の判断
     * @param context
     * @return
     */
    public static boolean isAppForeground(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
        if (runningAppProcessInfoList==null){
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
            if (processInfo.processName.equals(context.getPackageName()) &&
                    processInfo.importance==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                return true;
            }
        }
        return false;
    }
}
