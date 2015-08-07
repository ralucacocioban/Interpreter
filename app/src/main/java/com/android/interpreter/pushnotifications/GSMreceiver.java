package com.android.interpreter.pushnotifications;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by demouser on 8/7/15.
 */
public class GSMreceiver extends BroadcastReceiver {

    private static final String TAG = "GcmBroadcastReceiver";

    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        // Handle received message here.
    }

    @Override
    public final void onReceive(Context context, Intent intent) {
        //GCMIntentService.runIntentInService(context, intent);
        String data = null;
        setResult(Activity.RESULT_OK, data, null);
    }
}
