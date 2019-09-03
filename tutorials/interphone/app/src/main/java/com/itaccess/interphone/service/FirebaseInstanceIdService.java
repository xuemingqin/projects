package com.itaccess.interphone.service;

import android.content.Intent;

/**
 * Created by linxi on 2019/01/29.
 */

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        sendRegistrationToServer();
    }

    private void sendRegistrationToServer() {
        // Add custom implementation, as needed.
        startService(new Intent(this, RegistrationIntentService.class));
    }
}
