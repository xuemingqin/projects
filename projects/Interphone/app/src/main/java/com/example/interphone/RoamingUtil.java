package com.example.interphone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class RoamingUtil{
    public static boolean isNetworkConnected(final Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.isConnected();
        }

        return false;
    }

}
