package com.android.interpreter.util;


public class Conversation {
    private String receivingLanguage;
    private String sendingLanguage;


    public Conversation(String receivingLanguage, String sendingLanguage) {
        this.receivingLanguage = receivingLanguage;
        this.sendingLanguage = sendingLanguage;
    }

    public Conversation() {
    }

    public String getReceivingLanguage() {
        return this.receivingLanguage;
    }

    public void setReceivingLanguage(String receivingLanguage) {
        this.receivingLanguage = receivingLanguage;
    }

    public String getSendingLanguage() {
        return this.sendingLanguage;
    }

    public void setSendingLanguage(String sendingLanguage) {
        this.sendingLanguage = sendingLanguage;
    }

}
