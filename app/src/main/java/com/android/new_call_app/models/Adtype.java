package com.android.new_call_app.models;
/**
 * Created By Ayoub aitouna 08/26/2022
 */


public class Adtype {
    String Banner, Inter, Native, OpenAd;

    public Adtype() {
    }

    public Adtype(String banner, String inter, String aNative, String openAd) {
        Banner = banner;
        Inter = inter;
        Native = aNative;
        OpenAd = openAd;
    }

    public String getBanner() {
        return Banner;
    }

    public void setBanner(String banner) {
        Banner = banner;
    }

    public String getInter() {
        return Inter;
    }

    public void setInter(String inter) {
        Inter = inter;
    }

    public String getNative() {
        return Native;
    }

    public void setNative(String aNative) {
        Native = aNative;
    }

    public String getOpenAd() {
        return OpenAd;
    }

    public void setOpenAd(String openAd) {
        OpenAd = openAd;
    }
}
