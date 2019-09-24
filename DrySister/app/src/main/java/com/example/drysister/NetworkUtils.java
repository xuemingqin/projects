package com.example.drysister;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils{
    /** 获取网络信息 */
    private static NetworkInfo getActiveNetworkInfo(Context context){
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        return cm.getActiveNetworkInfo();
    }
    /** 判断网络是否可用 */
    public static boolean isAvailable(Context context){
        NetworkInfo info=getActiveNetworkInfo( context );
        return info!=null && info.isAvailable();
    }
}
