package com.android.interpreter.util;

/**
 * Created by ralucamelon on 06/08/2015.
 */
public class UserDetails {

    private String sendingLanguage;
    private String receivingLanguage;
    private String nickname;

    public UserDetails(){}

    public UserDetails(String nickname, String sendingLanguage, String receivingLanguage){
        this.nickname = nickname;
        this.sendingLanguage = sendingLanguage;
        this.receivingLanguage = receivingLanguage;
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








}
