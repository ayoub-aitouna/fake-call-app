package com.android.new_call_app.models;

public class Info {
    String title;
    String text;
    int img;

    public Info(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Info(String title, String text, int img) {
        this.title = title;
        this.text = text;
        this.img = img;
    }

    public Info() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
