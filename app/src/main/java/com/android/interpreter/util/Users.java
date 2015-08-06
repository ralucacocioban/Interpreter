package com.android.interpreter.util;

/**
 * The class representing the User object
 */
public class Users {

    private String nickname;
    private String email;
    private String password;
    private String sendingLanguage;
    private String receivingLanguage;


    public Users() {
    }

    public Users(String email, String pass, String sendingLanguage, String receivingLanguage, String nickname) {
        this.nickname = nickname;
        this.sendingLanguage = sendingLanguage;
        this.receivingLanguage = receivingLanguage;
        this.password = pass;
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
