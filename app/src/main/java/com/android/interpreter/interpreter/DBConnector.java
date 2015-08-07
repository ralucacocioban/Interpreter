package com.android.interpreter.interpreter;

import com.android.interpreter.Config;

/**
 * Created by ralucamelon on 06/08/2015.
 */
public class DBConnector {

    public static String getPathToUsers(){
        return Config.usersFirebasePath;
    }

    public static String getPathToUser(String userId) {
        String path = String.format("%s/%s", Config.usersFirebasePath, userId);
        return path;
    }

    public static String getPathToConversationsOf(String userId) {
        String path = String.format("%s/conversations/%s/", Config.mainFireBaseRef, userId);
        return path;
    }

    public static String getPathToMessages(String senderId, String receiverId) {
        String path = String.format("%s/conversations/%s/%s/messages", Config.mainFireBaseRef, senderId, receiverId);
        return path;
    }

}
