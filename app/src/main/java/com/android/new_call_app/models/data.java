package com.android.new_call_app.models;
/**
 * Created By Ayoub aitouna 08/26/2022
 */

import java.util.ArrayList;


public class data {
    Adtype admob, applovin, Unity, facebook, yandex;
    String unityId;
    boolean TestUnity;
    settings settings;
    Boolean unityTestMode;
    String unityID;
    ArrayList<String> wallpaper;

    public data() {
    }

    public data(Adtype admob, Adtype applovin, Adtype unity, Adtype facebook, Adtype yandex, String unityId, boolean testUnity,  com.android.new_call_app.models.settings settings, Boolean unityTestMode, String unityID, ArrayList<String> wallpaper) {
        this.admob = admob;
        this.applovin = applovin;
        Unity = unity;
        this.facebook = facebook;
        this.yandex = yandex;
        this.unityId = unityId;
        TestUnity = testUnity;
        this.settings = settings;
        this.unityTestMode = unityTestMode;
        this.unityID = unityID;
        this.wallpaper = wallpaper;
    }

    public ArrayList<String> getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(ArrayList<String> wallpaper) {
        this.wallpaper = wallpaper;
    }

    public Adtype getAdmob() {
        return admob;
    }

    public void setAdmob(Adtype admob) {
        this.admob = admob;
    }

    public Adtype getApplovin() {
        return applovin;
    }

    public void setApplovin(Adtype applovin) {
        this.applovin = applovin;
    }

    public Adtype getUnity() {
        return Unity;
    }

    public void setUnity(Adtype unity) {
        Unity = unity;
    }

    public Adtype getFacebook() {
        return facebook;
    }

    public void setFacebook(Adtype facebook) {
        this.facebook = facebook;
    }

    public Adtype getYandex() {
        return yandex;
    }

    public void setYandex(Adtype yandex) {
        this.yandex = yandex;
    }

    public String getUnityId() {
        return unityId;
    }

    public void setUnityId(String unityId) {
        this.unityId = unityId;
    }

    public boolean isTestUnity() {
        return TestUnity;
    }

    public void setTestUnity(boolean testUnity) {
        TestUnity = testUnity;
    }

    public settings getSettings() {
        return settings;
    }

    public void setSettings(settings settings) {
        this.settings = settings;
    }

    public Boolean getUnityTestMode() {
        return unityTestMode;
    }

    public void setUnityTestMode(Boolean unityTestMode) {
        this.unityTestMode = unityTestMode;
    }

    public String getUnityID() {
        return unityID;
    }

    public void setUnityID(String unityID) {
        this.unityID = unityID;
    }

}
