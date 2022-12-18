package com.android.new_call_app.models;

/**
 * Created By Ayoub aitouna 07/10/2022
 */
public class Characters {
    int Image;
    String Name, PhoneNumber, _Url_Image;


    public Characters() {
    }

    public Characters(int image, String name, String phoneNumber) {
        Image = image;
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public Characters(int image, String name, String phoneNumber, String _Url_Image) {
        Image = image;
        Name = name;
        PhoneNumber = phoneNumber;
        this._Url_Image = _Url_Image;
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
