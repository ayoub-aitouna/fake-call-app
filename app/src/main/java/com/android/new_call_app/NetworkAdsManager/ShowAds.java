package com.android.new_call_app.NetworkAdsManager;
/**
 * Created By Ayoub aitouna 08/26/2022
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.ViewGroup;

import  com.android.new_call_app.NetworkAdsManager.Ads.ApplovinMax;
import  com.android.new_call_app.NetworkAdsManager.Ads.Callback.InterCallback;
import  com.android.new_call_app.NetworkAdsManager.Ads.FacebookAd;
import  com.android.new_call_app.NetworkAdsManager.Ads.Network_Admob;
import  com.android.new_call_app.NetworkAdsManager.Ads.Network_UnityAd;
import  com.android.new_call_app.NetworkAdsManager.Ads.Network_Yandex;
import  com.android.new_call_app.Utils.Config;

@SuppressLint("StaticFieldLeak")
public class ShowAds {
    static Activity activity;
    static Network_Admob admobAds;
    static Network_UnityAd unityAd;
    static ApplovinMax applovinMax;
    static Network_Yandex yandex;
    static FacebookAd facebookAd;

    static boolean SwitchInter = false;
    static boolean SwitchBanner = false;

    static public void Init(Activity mActivity) {
        activity = mActivity;
        yandex = new Network_Yandex(mActivity);
        facebookAd = new FacebookAd(mActivity);
        admobAds = new Network_Admob(activity, Config.appdata.getAdmob());
        unityAd = new Network_UnityAd(activity);
        applovinMax = new ApplovinMax(activity);
        LoadInter();
    }

    static public void LoadInter() {
        admobAds.LoadInter();
    }


    static public void ShowBanner(ViewGroup layout) {
        try {
            switch (Config.appdata.getSettings().getAdsType()) {
                case Config.Admob:
                    admobAds.showBanner(layout);
                    break;
                case Config.Unity_Applovin:
                    SwitchBetweenUnityAndApplovinBanner(layout);
                    break;
                case Config.unityAd:
                    unityAd.ShowBanner(layout);
                    break;
                case Config.applovin:
                    applovinMax.createBannerAd(layout);
                    break;
                case Config.fb:
                    facebookAd.ShowBanner(layout, () -> {
                    });
                    break;
                case Config.Yandex:
                    yandex.Banner(layout, 0);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void SwitchBetweenUnityAndApplovinBanner(ViewGroup layout) {
        if (SwitchBanner)
            unityAd.ShowBanner(layout);
        else
            applovinMax.createBannerAd(layout);
        SwitchBanner = !SwitchBanner;

    }

    public static void ShowInter(Activity activity, InterCallback callback) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setTitle("Loading...");
        dialog.setMessage("Getting Page Ready for you");
        dialog.setCancelable(false);
        dialog.show();
        switch (Config.appdata.getSettings().getAdsType()) {
            case Config.Admob:
                admobAds.showInterstitial(() -> {
                    if (dialog.isShowing()) dialog.dismiss();
                    LoadInter();
                    callback.call();
                });
                break;

            case Config.Unity_Applovin:
                SwitchBetweenUnityAndApplovinInter(() -> {
                    if (dialog.isShowing()) dialog.dismiss();
                    LoadInter();
                    callback.call();
                });
                break;
            case Config.unityAd:
                unityAd.ShowInter(() -> {
                    if (dialog.isShowing()) dialog.dismiss();
                    LoadInter();
                    callback.call();
                });
                break;
            case Config.applovin:
                applovinMax.ShowInter(() -> {
                    if (dialog.isShowing()) dialog.dismiss();
                    LoadInter();
                    callback.call();
                });
                break;
            case Config.Yandex:
                yandex.ShowInterLoader(() -> {
                    if (dialog.isShowing()) dialog.dismiss();
                    LoadInter();
                    callback.call();
                });
                break;
            case Config.fb:
                facebookAd.ShowInterLoader(() -> {
                    if (dialog.isShowing()) dialog.dismiss();
                    LoadInter();
                    callback.call();
                });
                break;
       
            default:
                if (dialog.isShowing()) dialog.dismiss();
                LoadInter();
                callback.call();
                break;
        }
        LoadInter();
    }

    private static void SwitchBetweenUnityAndApplovinInter(InterCallback callback) {
        if (SwitchInter)
            applovinMax.ShowInter(callback);
        else
            unityAd.ShowInter(callback);
        SwitchInter = !SwitchInter;
    }



    static public void ShowNative(ViewGroup layout) {
        switch (Config.appdata.getSettings().getNativeType()) {
            case Config.Admob:
                layout.removeAllViews();
                admobAds.AdmobNative(layout);
                break;
            case Config.fb:
                layout.removeAllViews();
                facebookAd.createNative(layout);
                break;
            case Config.Yandex:
                layout.removeAllViews();
                yandex.LoadNative(layout);
                break;

            case Config.applovin:
                layout.removeAllViews();
                applovinMax.createMrecAd(layout);
                break;
        }
    }
    public static void OpenAd() {
        admobAds.showOpenAd(() -> {

        });
    }
    
}
