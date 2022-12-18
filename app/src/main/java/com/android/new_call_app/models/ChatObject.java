package com.android.new_call_app.models;
/**
Created By Ayoub aitouna 07/10/2022
*/
public class ChatObject {

    private String msg ;


    private boolean isSend;


    public ChatObject(String msg, boolean isSend ) {
        this.msg = msg;
        this.isSend = isSend;

    }

    public String getMsg() {
        return msg;
    }

    public boolean isSend() {
        return isSend;
    }






}
