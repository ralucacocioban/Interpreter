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

    public static String getPathToConversations(String userId) {

        String path = String.format("%s/%s/conversations", Config.usersFirebasePath, userId);
        return path;
    }

    public static String getPathToConv(String senderId, String receiverId) {
        String path = String.format("%s/%s/conversations/%s", Config.usersFirebasePath, senderId, receiverId);
        return path;
    }

    public static String getPathToMessages(String senderId, String receiverId) {
        String path = String.format("%s/%s/conversations/%s/messages", Config.usersFirebasePath, senderId, receiverId);
        return path;
    }
}
