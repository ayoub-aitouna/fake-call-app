package com.android.new_call_app.models;

public class Comments {
    int Image;
    String Name, content;

    public Comments() {
    }

    public Comments(int image, String name, String content) {
        Image = image;
        Name = name;
        this.content = content;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
