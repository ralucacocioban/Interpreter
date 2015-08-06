package com.android.interpreter.util;

import java.util.ArrayList;

/**
 * The class representing a conversation.
 */
public class Conversation {

    private Users to;
    private Users from;
    private String receivingLanguage;
    private String sendingLanguage;
    private ArrayList<Message> messages;



    public Conversation(){

    }

    public Users getTo() {
        return this.to;
    }

    public void setTo(Users to) {
        this.to = to;
    }

    public Users getFrom() {
        return this.from;
    }

    public void setFrom(Users from) {
        this.from = from;
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

    public ArrayList<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
