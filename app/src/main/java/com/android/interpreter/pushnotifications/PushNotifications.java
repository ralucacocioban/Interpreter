package com.android.interpreter.pushnotifications;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.interpreter.Config;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by demouser on 8/6/15.
 */
public class PushNotifications {

    public void sendMessage(String deviceId) {
        //TODO: comment it
        //deviceId = "APA91bGiRaramjyohc2lKjAgFGpzBwtEmI8tJC30O89C2b3IjP1CuMeU1h9LMjKhmWuZwcXZjy1eqC4cE0tWBNt61Kx_SuMF6awzIt8WNq_4AfwflaVPHQ0wYHG_UX3snjp_U-5kJkmysdRlN6T8xChB1n3DtIq98w";
        try {
            Sender sender = new Sender(Config.SERVER_API_KEY);

            //ArrayList<String> devicesList = new ArrayList<String>();
            //add you deviceID
            //devicesList.add("APA91bELVJbxB_NLnLbTkkkX87SDdkJc6OfCN2slhC9t4cLq-KA32eGgiW4-Gi--ZEsEMKIh0AtYJMs5rQGswfm3cH1qK853WcpV98bkaplAaC5AiycDmifuVFSRl21vgf-Rqj0dCrFF");
            //devicesList.add("APA91bHIdM4XGqrjJLTuwCX5OOrTYG4ACXYEVkZDM1bPs5qFdzJP4Bpql-sZqyKB8BU7fDtdxB84aTygHLyASYg_XNY6lqrcA4wj4sZHJXGVFzz_0UEADMfFCx9NAfRZxunIYso_dkBa");
            //APA91bFA-i2l3iEMnIBs0JK80pTLHOsE7p1s-DysRpKGas1MQOVILyIs9xwY7soysSWGz5Uif68uXR6F5Xn0tCTYesv78uQZxhC310a1cvf8aFohhfMGY6awbOSg3t1GRz2i3U-8kVSF
            // Use this line to send message without payload data
             //Message message = new Message.Builder().build();

            // use this line to send message with payload data
            Message message = new Message.Builder()
                    //.collapseKey("message")
                    //.timeToLive(241000)
                    .delayWhileIdle(true)
                    .addData(Config.SENT_MESSAGE, "Your message send")
                    .addData(Config.SENDER_USER_NAME, "Sender name")
                    .build();

            System.out.println("####message: " + message);
                   /**/
            // Use this code to send to a single device
            Result result = sender.send(message, deviceId, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDeviceId(Context context) {
        try {
            InstanceID instanceID = InstanceID.getInstance(context);

            System.out.println("####instanceID: " + instanceID);
            String token = instanceID.getToken(Config.CLIENT_API_KEY, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            return token;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
//    public void onTokenRefresh(Context context) {
//        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
//        Intent intent = new Intent(this, RegistrationIntentService.class);
//        startService(intent);
//    }
//    final String regId = GSMRegistrar.getRegistrationId(this);
//    if (regId.equals("")) {
//        GSMRegistrar.registrer(this, SENDER_ID);
//    }
}
