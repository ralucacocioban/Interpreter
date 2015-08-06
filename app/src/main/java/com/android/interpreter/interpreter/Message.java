package com.android.interpreter.interpreter;

import java.util.Date;

/**
 * Created by ralucamelon on 05/08/2015.
 */
public class Message {

    private String msgText;
    private Date date;

    public Message(){};

    public Message(String msgTxt, Date date){
        this.msgText = msgTxt;
        this.date = date;
    }


    public String getMessage(){
        return this.msgText;
    }

    public Date getDate(){
        return this.date;
    }

    public void setMessage(String msg){
        this.msgText = msg;
    }

    public void setDate(Date date){
        this.date = date;
    }
}
