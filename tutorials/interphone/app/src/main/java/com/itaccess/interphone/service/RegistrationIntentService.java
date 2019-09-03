package com.itaccess.interphone.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by linxi on 2019/01/29.
 */

public class RegistrationIntentService extends IntentService {

    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("###", "token = " + token);
        insertServer(token);
    }
    private void insertServer(String token) {
        //TODO
        // サーバーへ登録
    }
}
