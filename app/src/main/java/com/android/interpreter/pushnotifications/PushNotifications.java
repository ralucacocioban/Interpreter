package com.android.interpreter.pushnotifications;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

import java.util.ArrayList;

/**
 * Created by demouser on 8/6/15.
 */
public class PushNotifications {

    public static final String APIKey = "AIzaSyC7ZlcipiQRrUOkVHI8rr3r57xBudFohjI";

    try {

        Sender sender = new Sender(APIKey);

        ArrayList<String> devicesList = new ArrayList<String>();
        //add you deviceID
        devicesList.add("APA91bELVJbxB_NLnLbTkkkX87SDdkJc6OfCN2slhC9t4cLq-KA32eGgiW4-Gi--ZEsEMKIh0AtYJMs5rQGswfm3cH1qK853WcpV98bkaplAaC5AiycDmifuVFSRl21vgf-Rqj0dCrFF");
        //devicesList.add("APA91bHIdM4XGqrjJLTuwCX5OOrTYG4ACXYEVkZDM1bPs5qFdzJP4Bpql-sZqyKB8BU7fDtdxB84aTygHLyASYg_XNY6lqrcA4wj4sZHJXGVFzz_0UEADMfFCx9NAfRZxunIYso_dkBa");
        //APA91bFA-i2l3iEMnIBs0JK80pTLHOsE7p1s-DysRpKGas1MQOVILyIs9xwY7soysSWGz5Uif68uXR6F5Xn0tCTYesv78uQZxhC310a1cvf8aFohhfMGY6awbOSg3t1GRz2i3U-8kVSF
        // Use this line to send message without payload data
        // Message message = new Message.Builder().build();

        // use this line to send message with payload data
        Message message = new Message.Builder()
                //.collapseKey("message")
                //.timeToLive(241000)
                .delayWhileIdle(true)
                .addData("message", "Your message send")
                .build();


                   /**/
        // Use this code to send to a single device
        // Result result = sender
        // .send(message,
        // "APA91bGiRaramjyohc2lKjAgFGpzBwtEmI8tJC30O89C2b3IjP1CuMeU1h9LMjKhmWuZwcXZjy1eqC4cE0tWBNt61Kx_SuMF6awzIt8WNq_4AfwflaVPHQ0wYHG_UX3snjp_U-5kJkmysdRlN6T8xChB1n3DtIq98w",
        // 1);

        // Use this for multicast messages
        MulticastResult result = sender.send(message, devicesList, 1);
        //sender.send(message, devicesList, 0);

        System.out.println(result.toString());
        if (result.getResults() != null) {
            int canonicalRegId = result.getCanonicalIds();
            if (canonicalRegId != 0) {
            }
        } else {
            int error = result.getFailure();
            System.out.println(error);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
//    final String regId = GSMRegistrar.getRegistrationId(this);
//    if (regId.equals("")) {
//        GSMRegistrar.registrer(this, SENDER_ID);
//    }
}
