package com.android.new_call_app;

import static  com.android.new_call_app.Utils.Utils.StatBarUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import  com.android.new_call_app.NetworkAdsManager.ShowAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatBarUI(this, R.color.call_color);
        setContentView(R.layout.activity_main);

        ShowAds.ShowNative(findViewById(R.id.native_));
    }
}