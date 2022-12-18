package com.android.new_call_app.NetworkAdsManager.Ads;

/**
 * Created By Ayoub aitouna 08/26/2022
 */

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import  com.android.new_call_app.NetworkAdsManager.Ads.Callback.InterCallback;
import  com.android.new_call_app.Utils.Config;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class Network_UnityAd {
    static final String TAG = "UnityAdsExample";
    Activity activity;

    public Network_UnityAd(Activity activity) {
        this.activity = activity;
        UnityAds.initialize(activity, Config.appdata.getUnityId(), Config.appdata.isTestUnity(), new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                Log.d("UnityAdsExample", "Unity Ads initialization complete ");

            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                Log.e("UnityAdsExample", "Unity Ads initialization failed with error: [" + error + "] " + message);
            }
        });
    }


    public void ShowBanner(ViewGroup layout) {
        BannerView bottomBanner = new BannerView(activity, Config.appdata.getUnity().getBanner(), new UnityBannerSize(320, 50));
        bottomBanner.setListener(new BannerView.Listener() {
            @Override
            public void onBannerLoaded(BannerView bannerAdView) {
                super.onBannerLoaded(bannerAdView);
                Log.d(TAG, "onBannerLoaded: " + bannerAdView.getPlacementId());
            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                super.onBannerFailedToLoad(bannerAdView, errorInfo);
                Log.d(TAG, "onBannerFailedToLoad: " + errorInfo.errorMessage);
            }

            @Override
            public void onBannerClick(BannerView bannerAdView) {
                super.onBannerClick(bannerAdView);
            }

            @Override
            public void onBannerLeftApplication(BannerView bannerAdView) {
                super.onBannerLeftApplication(bannerAdView);
            }
        });
        bottomBanner.load();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        bottomBanner.setLayoutParams(params);
        layout.addView(bottomBanner);
    }

    public void ShowInter(InterCallback callback) {
        UnityAds.load(Config.appdata.getUnity().getInter(), new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                UnityAds.show((Activity) activity, Config.appdata.getUnity().getInter(), new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                        Log.d(TAG, "onUnityAdsShowFailure: " + message);
                        callback.call();
                    }

                    @Override
                    public void onUnityAdsShowStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowClick(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                        Log.d(TAG, "onUnityAdsShowed: " + placementId);
                        callback.call();

                    }
                });

            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                callback.call();
            }
        });

    }


}
