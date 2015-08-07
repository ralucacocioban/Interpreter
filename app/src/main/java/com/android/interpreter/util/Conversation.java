package com.android.interpreter.util;


public class Conversation {
    private String receivingLanguage;
    private String sendingLanguage;
    private String senderId;
    private String receiverId;


    public Conversation(String receivingLanguage, String sendingLanguage, String senderId, String receiverId) {
        this.receivingLanguage = receivingLanguage;
        this.sendingLanguage = sendingLanguage;
        this.senderId = senderId;
        

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
