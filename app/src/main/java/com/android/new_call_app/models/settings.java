package com.android.new_call_app.models;

/**
 * Created By Ayoub aitouna 08/26/2022
 */


public class settings {
    String UpdatePackageName, img, title, message;
    String adsType;
    String nativeType;
    boolean suspended;

    public settings() {
    }

    public settings(String updatePackageName, String img, String title, String message, String adsType, String nativeType, boolean suspended) {
        UpdatePackageName = updatePackageName;
        this.img = img;
        this.title = title;
        this.message = message;
        this.adsType = adsType;
        this.nativeType = nativeType;
        this.suspended = suspended;
    }

    public String getUpdatePackageName() {
        return UpdatePackageName;
    }

    public void setUpdatePackageName(String updatePackageName) {
        UpdatePackageName = updatePackageName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdsType() {
        return adsType;
    }

    public void setAdsType(String adsType) {
        this.adsType = adsType;
    }

    public String getNativeType() {
        return nativeType;
    }

    public void setNativeType(String nativeType) {
        this.nativeType = nativeType;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

}
