package com.android.new_call_app.NetworkAdsManager.Ads;
/**
 * Created By Ayoub aitouna 08/26/2022
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import  com.android.new_call_app.NetworkAdsManager.Ads.Callback.InterCallback;
import  com.android.new_call_app.Utils.Config;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkUtils;

import java.util.Arrays;

public class ApplovinMax {

    private MaxAdView adView;

    Activity activity;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;
    private String TAG = "Applovin_";


    public ApplovinMax(Activity activity) {
        this.activity = activity;
        AppLovinSdk.getInstance(activity).setMediationProvider("max");
        AppLovinSdk.getInstance(activity).getSettings().setTestDeviceAdvertisingIds(Arrays.asList("abe0977f-0344-4c49-8575-10ca1ad1abe1"));
        AppLovinSdk.initializeSdk(activity, configuration -> createInterstitialAd());
    }


    @SuppressLint("ResourceAsColor")
    public void createBannerAd(ViewGroup rootView) {
        adView = new MaxAdView(Config.appdata.getApplovin().getBanner(), activity);
        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        adView.setLayoutParams(new FrameLayout.LayoutParams(width, AppLovinSdkUtils.dpToPx(activity, 60)));
        rootView.addView(adView);
        adView.loadAd();
    }

    private MaxInterstitialAd interstitialAd;

    void createInterstitialAd() {

    }

    public void ShowInter(InterCallback callback) {
        interstitialAd = new MaxInterstitialAd(Config.appdata.getApplovin().getInter(), activity);
        interstitialAd.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(final MaxAd maxAd) {
                if (interstitialAd != null && interstitialAd.isReady()) {
                    interstitialAd.showAd();
                    interstitialAd.setListener(new MaxAdViewAdListener() {
                        @Override
                        public void onAdExpanded(MaxAd ad) {

                        }

                        @Override
                        public void onAdCollapsed(MaxAd ad) {

                        }

                        @Override
                        public void onAdLoaded(MaxAd ad) {

                        }

                        @Override
                        public void onAdDisplayed(MaxAd ad) {

                        }

                        @Override
                        public void onAdHidden(MaxAd ad) {
                            callback.call();
                        }

                        @Override
                        public void onAdClicked(MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(String adUnitId, MaxError error) {
                            callback.call();
                        }

                        @Override
                        public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                            callback.call();
                        }
                    });

                } else {
                    callback.call();
                }
            }

            @Override
            public void onAdLoadFailed(final String adUnitId, final MaxError error) {
                callback.call();
            }

            @Override
            public void onAdDisplayFailed(final MaxAd maxAd, final MaxError error) {
                interstitialAd.loadAd();
            }

            @Override
            public void onAdDisplayed(final MaxAd maxAd) {
            }

            @Override
            public void onAdClicked(final MaxAd maxAd) {
            }

            @Override
            public void onAdHidden(final MaxAd maxAd) {
                // Interstitial ad is hidden. Pre-load the next ad
                interstitialAd.loadAd();
            }

        });
        // Load the first ad
        interstitialAd.loadAd();
    }

    public void createNativeAd(FrameLayout nativeAdContainer) {
        nativeAdLoader = new MaxNativeAdLoader(Config.appdata.getApplovin().getNative(), activity);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            public void onNativeAdLoaded(View view, MaxAd maxAd) {
                if (nativeAd != null) {
                    nativeAdLoader.destroy(nativeAd);
                }
                // Save ad for cleanup.
                nativeAd = maxAd;
                // Add ad view to view.
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView(view);
            }

            @Override
            public void onNativeAdLoadFailed(String s, MaxError maxError) {
                super.onNativeAdLoadFailed(s, maxError);
                Log.d(TAG, "onNativeAdLoadFailed: " + s + ": " + maxError.getMessage());
            }

            @Override
            public void onNativeAdClicked(MaxAd maxAd) {
                super.onNativeAdClicked(maxAd);
            }
        });

        nativeAdLoader.loadAd();
    }

    public void createMrecAd(ViewGroup rootView) {
        adView = new MaxAdView(Config.appdata.getApplovin().getNative(), MaxAdFormat.MREC, activity);
        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });
        int widthPx = AppLovinSdkUtils.dpToPx(activity, 300);
        int heightPx = AppLovinSdkUtils.dpToPx(activity, 250);
        adView.setLayoutParams(new FrameLayout.LayoutParams(widthPx, heightPx));
        rootView.addView(adView);
        adView.loadAd();
    }

}
