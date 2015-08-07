package com.android.interpreter.pushnotifications;

import android.app.Activity;
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

        Log.d(TAG, intent.toString());

//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.notification_icon)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!");
//// Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(GSMreceiver.this, ChatActivity.class);
//
//// The stack builder object will contain an artificial back stack for the
//// started Activity.
//// This ensures that navigating backward from the Activity leads out of
//// your application to the Home screen.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//// Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(ChatActivity.class);
//// Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//// mId allows you to update the notification later on.
//        mNotificationManager.notify(mId, mBuilder.build());

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
