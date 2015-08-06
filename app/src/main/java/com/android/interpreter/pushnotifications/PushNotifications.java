package com.android.interpreter.pushnotifications;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.util.ArrayList;

/**
 * Created by demouser on 8/6/15.
 */
public class PushNotifications {
    public static final String SERVER_API_KEY = "AIzaSyCXQPEmG2qw5C5iPCDWi3KieBzM7WtyIQY";
    public static final String CLIENT_API_KEY = "545042974867";

    public void sendMessage(String deviceId) {
        deviceId = "APA91bGiRaramjyohc2lKjAgFGpzBwtEmI8tJC30O89C2b3IjP1CuMeU1h9LMjKhmWuZwcXZjy1eqC4cE0tWBNt61Kx_SuMF6awzIt8WNq_4AfwflaVPHQ0wYHG_UX3snjp_U-5kJkmysdRlN6T8xChB1n3DtIq98w";
        try {
            Sender sender = new Sender(SERVER_API_KEY);

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
                    .addData("message", "Your message send")
                    .build();


                   /**/
            // Use this code to send to a single device
            Result result = sender.send(message, deviceId, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAndSaveDeviceId(Context context) {
        try {
            InstanceID instanceID = InstanceID.getInstance(context);
            String token = instanceID.getToken(CLIENT_API_KEY, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

        } catch (Exception e) {
            e.printStackTrace();
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
