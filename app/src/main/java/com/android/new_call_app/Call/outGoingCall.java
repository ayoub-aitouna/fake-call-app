package com.android.new_call_app.Call;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import  com.android.new_call_app.NetworkAdsManager.ShowAds;
import  com.android.new_call_app.R;
import  com.android.new_call_app.Utils.Config;

import de.hdodenhof.circleimageview.CircleImageView;

public class outGoingCall extends AppCompatActivity {
    MediaPlayer player;
    ShowAds showAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_going_call);
        ShowAds.ShowBanner(findViewById(R.id.banner));
        ((CircleImageView) findViewById(R.id.img)).setImageResource(Config.selectedCharacter.getImage());
        ((TextView) findViewById(R.id.name)).setText(Config.selectedCharacter.getName());
        PlayRingtone();
        findViewById(R.id.start).setOnClickListener(v -> {
            Intent view = new Intent(outGoingCall.this, InVoiceCall.class);
            startActivity(view);
            finish();

        });
    }

    private void PlayRingtone() {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.start();
        player.setOnCompletionListener(mp -> player.start());
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            player.release();
            player.stop();
        } catch (Exception e) {

        }


    }
}