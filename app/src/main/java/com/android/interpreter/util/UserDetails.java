package com.android.interpreter.util;

/**
 * Created by ralucamelon on 06/08/2015.
 */
public class UserDetails {

    private String sendingLanguage;
    private String receivingLanguage;
    private String nickname;
    private String GCMtoken;

    public UserDetails() {
    }

    public UserDetails(String nickname, String sendingLanguage, String receivingLanguage, String GCMtoken) {
        this.nickname = nickname;
        this.sendingLanguage = sendingLanguage;
        this.receivingLanguage = receivingLanguage;
        this.GCMtoken = GCMtoken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSendingLanguage() {
        return sendingLanguage;
    }

    public void setSendingLanguage(String sendingLanguage) {
        this.sendingLanguage = sendingLanguage;
    }

    public String getGCMtoken() {
        return GCMtoken;
    }

    public void setGCMtoken(String GCMtoken) {
        this.GCMtoken = GCMtoken;
    }


}
