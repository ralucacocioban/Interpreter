package com.android.interpreter.util;

/**
 * Created by ralucamelon on 06/08/2015.
 */
public class User {

    private String uid;
    private String email;
    private String receivingLanguage;
    private String sendingLanguage;
    private String nickname;
    private String GCMtoken;

    public User(){
    }

    public User(String email, String uid, String receivingLanguage, String sendingLanguage, String nickname, String GCMtoken){
        this.email = email;
        this.uid = uid;
        this.sendingLanguage= sendingLanguage;
        this.receivingLanguage = receivingLanguage;
        this.nickname = nickname;
        this.GCMtoken = GCMtoken;
    }


    public String getSendingLanguage() {
        return sendingLanguage;
    }

    public void setSendingLanguage(String sendingLanguage) {
        this.sendingLanguage = sendingLanguage;
    }

    public String getReceivingLanguage() {
        return receivingLanguage;
    }

    public void setReceivingLanguage(String receivingLanguage) {
        this.receivingLanguage = receivingLanguage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGCMtoken() {
        return GCMtoken;
    }

    public void setGCMtoken(String GCMtoken) {
        this.GCMtoken = GCMtoken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
