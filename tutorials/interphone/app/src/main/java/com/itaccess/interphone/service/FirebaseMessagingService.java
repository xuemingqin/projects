package com.itaccess.interphone.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.firebase.messaging.RemoteMessage;
import com.itaccess.interphone.R;
import com.itaccess.interphone.ui.activity.MainActivity;
import com.itaccess.interphone.ui.activity.ReceivingActivity;
import com.itaccess.interphone.utils.WindowUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

/**
 * Created by linxi on 2019/01/29.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_PEER_ID = "peerId";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_CALL_LOCATION = "call_location";
    public static final String NOTIFICATION_ACTION = "firrebase_message_notification_action";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();

        if(TextUtils.isEmpty(data.get(EXTRA_PEER_ID))) {

            if (WindowUtil.isAppForeground(this.getApplicationContext())) {
                //foreground
                Intent intent = new Intent(NOTIFICATION_ACTION);
                intent.putExtra(EXTRA_URL, data.get(EXTRA_URL));
                intent.putExtra(EXTRA_MESSAGE, data.get(EXTRA_MESSAGE));
                sendBroadcast(intent);
            } else {
                //標準のプッシュ通知が来た
                final Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(EXTRA_URL, data.get(EXTRA_URL));
                intent.putExtra(EXTRA_MESSAGE, data.get(EXTRA_MESSAGE));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //foreground のほか
                final PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                final Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(data.get(EXTRA_URL))
                        .setContentText(data.get(EXTRA_MESSAGE))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(contentIntent);

                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                final int notifyId = getNotifyId();
                notificationManager.notify(notifyId, builder.build());
            }

        } else {
            final Intent intent = new Intent(this, ReceivingActivity.class);
            intent.putExtra(EXTRA_PEER_ID, data.get(EXTRA_PEER_ID));
            intent.putExtra(EXTRA_CALL_LOCATION, "01");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    private int getNotifyId() {
        Calendar calendar = Calendar.getInstance();
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.JAPAN).format(calendar.getTime()));
    }
}
