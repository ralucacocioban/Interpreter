package com.android.interpreter.pushnotifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.interpreter.Config;
import com.android.interpreter.interpreter.ChatActivity;
import com.android.interpreter.interpreter.R;

/**
 * Created by demouser on 8/7/15.
 */
public class GSMreceiver extends BroadcastReceiver {

    private static final String TAG = "GcmBroadcastReceiver";

    @Override
    public final void onReceive(Context context, Intent intent) {
        String msg = intent.getExtras().getString(Config.SENT_MESSAGE);
        String senderName = intent.getExtras().getString(Config.SENDER_USER_NAME);
        String url = intent.getExtras().getString(Config.SENDER_USER_PHOTO);
        String msgId = intent.getExtras().getString(Config.MESSAGE_ID);

        Log.d(TAG, intent.toString());

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        //TODO: use user icon or notification_icon
                        .setSmallIcon(R.drawable.no_user)//notification_icon)
                        .setContentTitle(senderName)
                        .setContentText(msg);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, ChatActivity.class);
        //resultIntent.putExtra("ChatActivity", "ChatActivity");
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);

        NotificationManager nm =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        nm.notify(Integer.parseInt(msgId), mBuilder.build());

/*
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.no_user, "Message", System.currentTimeMillis());
        Intent myIntent = new Intent(context, ChatActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, myIntent, 0);
        notification.setLatestEventInfo(context, "Notif title", "Notif text", pIntent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.vibrate = true;
        //TODO:
        nm.notify(1, notification);
*/
    }
//    @Override
//    public void onMessageReceived(String from, Bundle data) {
//        String message = data.getString("message");
//        Log.d(TAG, "From: " + from);
//        Log.d(TAG, "Message: " + message);
//
//        sendNotification(message);
//    }
}
