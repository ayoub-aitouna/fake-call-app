package com.android.new_call_app.NetworkAdsManager.Ads;
/**
 * Created By Ayoub aitouna 08/26/2022
 */

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import  com.android.new_call_app.NetworkAdsManager.Ads.Callback.InterCallback;
import  com.android.new_call_app.NetworkAdsManager.Ads.Callback.RewardCall;
import  com.android.new_call_app.Utils.Config;
import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdEventListener;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.common.MobileAds;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.nativeads.NativeAd;
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener;
import com.yandex.mobile.ads.nativeads.NativeAdLoader;
import com.yandex.mobile.ads.nativeads.NativeAdMedia;
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration;
import com.yandex.mobile.ads.nativeads.NativeAdView;
import com.yandex.mobile.ads.nativeads.template.NativeBannerView;
import com.yandex.mobile.ads.rewarded.RewardedAd;

public class Network_Yandex {


    private String TAG = "Network_Yandex";
    private Activity activity;
    private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;
    private NativeAdView mNativeAdView;
    private boolean rewarded = false;


    public Network_Yandex(Activity activity) {
        MobileAds.initialize(activity, () -> Log.d(TAG, "SDK initialized"));
        MobileAds.enableDebugErrorIndicator(true);
        this.activity = activity;
    }

    RewardCall call;

    public void ShowReward(RewardCall call) {
        this.call = call;
        if (mRewardedAd.isLoaded()) {
            mRewardedAd.show();
        } else {
            call.error();
        }
    }

    public void ShowInterLoader(InterCallback callback) {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(Config.appdata.getYandex().getInter());
        final AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "Inter onAdLoaded: ");
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(AdRequestError adRequestError) {
                Log.d(TAG, "Inter onAdFailedToLoad: " + adRequestError.getDescription());
                callback.call();
            }

            @Override
            public void onAdShown() {
            }

            @Override
            public void onAdDismissed() {
                callback.call();
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onLeftApplication() {
            }

            @Override
            public void onReturnedToApplication() {
            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {
            }
        });


    }

    public void Banner(ViewGroup banner, int i) {
        BannerAdView mBannerAdView = new BannerAdView(activity);
        mBannerAdView.setAdUnitId(Config.appdata.getYandex().getBanner());
        mBannerAdView.setAdSize(AdSize.BANNER_320x50);
        final AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAdView.setBannerAdEventListener(new BannerAdEventListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(AdRequestError adRequestError) {
                Log.d(TAG, "onAdFailedToLoad: " + adRequestError.getDescription());
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {
            }

            @Override
            public void onReturnedToApplication() {
            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });
        // Loading the ad.
        banner.addView(mBannerAdView);
        mBannerAdView.loadAd(adRequest);
    }

    InterCallback callback;

    public void LoadInter(int i) {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(Config.appdata.getYandex().getInter());
        final AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "Inter onAdLoaded: ");

            }

            @Override
            public void onAdFailedToLoad(AdRequestError adRequestError) {
                Log.d(TAG, "Inter onAdFailedToLoad: " + adRequestError.getDescription());
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {
                callback.call();
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });

    }

    public void ShowInter(InterCallback callback) {
        this.callback = callback;
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            callback.call();
        }


    }


    private NativeAdLoader mNativeAdLoader;

    public void LoadNative(ViewGroup layout) {
        mNativeAdLoader = new NativeAdLoader(activity);
        mNativeAdLoader.setNativeAdLoadListener(new NativeAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull final NativeAd nativeAd) {
                bind_NativeAd(nativeAd, layout);
            }

            @Override
            public void onAdFailedToLoad(@NonNull final AdRequestError adRequestError) {
                Log.d("SAMPLE_TAG", adRequestError.getDescription());
            }
        });

        final NativeAdRequestConfiguration nativeAdRequestConfiguration =
                new NativeAdRequestConfiguration.Builder(Config.appdata.getYandex().getNative())
                        .setShouldLoadImagesAutomatically(true)
                        .build();
        mNativeAdLoader.loadAd(nativeAdRequestConfiguration);


    }

    private void bind_NativeAd(@NonNull final NativeAd nativeAd, ViewGroup layout) {
        NativeBannerView mNativeBannerView = new NativeBannerView(activity);
        mNativeBannerView.setAd(nativeAd);
        mNativeBannerView.setVisibility(View.VISIBLE);
        layout.addView(mNativeBannerView);
    }


    private void configureMediaView(@NonNull final NativeAd nativeAd) {
        final NativeAdMedia nativeAdMedia = nativeAd.getAdAssets().getMedia();
        if (nativeAdMedia != null) {
            //you can use the aspect ratio if you need it to determine the size of media view.
            final float aspectRatio = nativeAdMedia.getAspectRatio();
        }
    }


    public void destroy() {
        if (mRewardedAd != null) {
            mRewardedAd.setRewardedAdEventListener(null);
            mRewardedAd.destroy();
        }
    }
}
