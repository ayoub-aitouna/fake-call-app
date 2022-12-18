package com.android.new_call_app.Call;

import static com.android.new_call_app.Utils.Config.characters;
import static com.android.new_call_app.Utils.Utils.StatBarUI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.new_call_app.NetworkAdsManager.ShowAds;
import com.android.new_call_app.R;
import com.android.new_call_app.Utils.Config;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MainCall extends AppCompatActivity {
    public static final int MY_CAMERA_REQUEST_CODE = 100;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_main);
        StatBarUI(this, R.color.call_color);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }
        Navigation_drawer();
        Ads();
        findViewById(R.id.chat).setOnClickListener(v -> {
            Intent view = new Intent(MainCall.this, Messanger.class);
            Config.selectedCharacter = characters.get(0);
            startActivity(view);
        });
        findViewById(R.id.call).setOnClickListener(v -> {
            Intent view = new Intent(MainCall.this, outGoingCall.class);
            Config.selectedCharacter = characters.get(0);
            startActivity(view);
        });
        findViewById(R.id.video_call).setOnClickListener(v -> {
            Intent view = new Intent(MainCall.this, InCommingVideoCall.class);
            Config.selectedCharacter = characters.get(0);
            startActivity(view);
        });
        findViewById(R.id.live).setOnClickListener(v -> {
            Intent view = new Intent(MainCall.this, LiveActivty.class);
            startActivity(view);
        });
        findViewById(R.id.contact).setOnClickListener(v -> {
            Intent view = new Intent(MainCall.this, Contact.class);
            startActivity(view);
        });
        findViewById(R.id.settings).setOnClickListener(v -> {
            Intent view = new Intent(MainCall.this, Settings.class);
            startActivity(view);
        });

    }

    private void Navigation_drawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.getHeaderView(0).findViewById(R.id.close_drawer).setOnClickListener(view -> drawer.closeDrawers());
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.name)).setText("AYOUB");
        this.<TextView>findViewById(R.id.main_name).setText("AYOUB");
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.call:
                    //nothing for now
                    break;
            }
            return false;
        });

    }


    private void Ads() {
//        ShowAds.ShowNative(findViewById(R.id.native_conatiner));
        ShowAds.ShowBanner(findViewById(R.id.banner));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isOpen())
            drawer.closeDrawers();
        else
            super.onBackPressed();
    }
}