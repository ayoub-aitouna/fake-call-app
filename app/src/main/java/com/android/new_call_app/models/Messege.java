package com.android.new_call_app.models;

public class Messege {
    String MSG, Response;

    public Messege() {
    }

    public Messege(String MSG, String response) {
        this.MSG = MSG;
        Response = response;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
