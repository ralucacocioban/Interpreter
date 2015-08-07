package com.android.interpreter.util;

/**
 * Created by ralucamelon on 06/08/2015.
 */
public class User {

    private String uid;
    private String email;
    private UserDetails details;
    private String receivingLanguage;
    private String sendingLanguage;
    private String nickname;
    private String GCMtoken;



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

    public User(){
    }

    public User(String email, String uid, UserDetails details){
        this.email = email;
        this.uid = uid;
        this.details = details;
    }

    public UserDetails getDetails() {
        return details;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
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
