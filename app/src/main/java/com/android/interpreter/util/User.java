package com.android.interpreter.util;

/**
 * Created by ralucamelon on 06/08/2015.
 */
public class User {

    private String uid;
    private String email;

    public User(){
    }

    public User(String email, String uid){
        this.email = email;
        this.uid = uid;
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
