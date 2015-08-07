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
        return Config.usersFirebasePath + userId;
    }

    public static String getPathToConversationsOf(String userId) {

        String path = Config.convsPath + userId + "/all/";
        //String path = String.format("%s/conversations/%s/all/", Config.mainFireBaseRef, userId);
        return path;
    }

    public static String getPathToMessages(String senderId, String receiverId) {
        String path = Config.convsPath + senderId + "/" + receiverId + "/messages";
        //String path = String.format("%s/conversations/%s/%s/messages", Config.mainFireBaseRef, senderId, receiverId);
        return path;
    }

    public static String getPathToAllUsers() {
        String path = String.format("%s/users/all", Config.mainFireBaseRef);
        return path;
    }

}
