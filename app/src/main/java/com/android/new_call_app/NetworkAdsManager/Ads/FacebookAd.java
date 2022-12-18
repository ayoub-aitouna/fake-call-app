package com.android.new_call_app.NetworkAdsManager.Ads;
/**
Created By Ayoub aitouna 07/20/2022
*/

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import  com.android.new_call_app.NetworkAdsManager.Ads.Callback.InterCallback;
import  com.android.new_call_app.R;
import  com.android.new_call_app.Utils.Config;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

public class FacebookAd {
    private static final String TAG = "Facebook Ads";
    private AdView adView = null;
    private InterstitialAd interstitialAd;
    private Activity activity;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adViewNative;
    private ViewGroup adChoicesContainer;
    InterCallback callback;

    public FacebookAd(Activity p_activity) {
        activity = p_activity;
        AudienceNetworkAds.initialize(activity);
        AdSettings.addTestDevice("abe0977f-0344-4c49-8575-10ca1ad1abe1");
        LoadInter(0);
    }

    public void ShowInterLoader(InterCallback callback) {
        interstitialAd = new InterstitialAd(activity, Config.appdata.getFacebook().getInter());
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {

                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                                callback.call();
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {

                                callback.call();
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                interstitialAd.show();
                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        })
                        .build());
    }

    public void ShowBanner(ViewGroup adLayout, InterCallback callback) {
        adView = new AdView(activity, Config.appdata.getFacebook().getBanner(), AdSize.BANNER_HEIGHT_50);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d(TAG, "onError: " + adError.getErrorMessage());
                callback.call();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                callback.call();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
        adLayout.addView(adView);

    }

    public void createNative(ViewGroup layout) {
        adChoicesContainer = layout;
        NativeAd nativeAd = new NativeAd(activity, Config.appdata.getFacebook().getNative());
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d(TAG, "nativeAd onError: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }


    private void inflateAd(NativeAd nativeAd) {
        nativeAd.unregisterView();
        // Add the Ad view into the ad container.
        // nativeAdLayout = findViewById(R.id.native_ad_container);
        nativeAdLayout = new NativeAdLayout(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adViewNative = (LinearLayout) inflater.inflate(R.layout.fb_native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adViewNative);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(nativeAdLayout);

        // Add the AdOptionsView
        LinearLayout adChoices = activity.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd, nativeAdLayout);
        if (adChoices != null) {
            adChoices.removeAllViews();
            adChoices.addView(adOptionsView, 0);
        }


        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adViewNative.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adViewNative.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adViewNative.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adViewNative.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adViewNative.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adViewNative.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adViewNative.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adViewNative,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);

    }


    public void LoadInter(int i) {
        interstitialAd = new InterstitialAd(activity, Config.appdata.getFacebook().getInter());
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {

                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                                callback.call();
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {
                                Log.d(TAG, "inter onError: " + adError.getErrorMessage());
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                Log.d(TAG, "inter onAdLoaded: " + ad.getPlacementId());
                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        })
                        .build());
    }


    public void ShowInter(InterCallback callback) {
        this.callback = callback;
        if (interstitialAd.isAdLoaded())
            interstitialAd.show();
        else
            callback.call();
    }
}
