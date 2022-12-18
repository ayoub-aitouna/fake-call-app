package com.android.new_call_app.Call;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import  com.android.new_call_app.NetworkAdsManager.ShowAds;
import  com.android.new_call_app.R;
import  com.android.new_call_app.Utils.Config;

public class InVoiceCall extends AppCompatActivity {
    Chronometer clock;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_voice_call);
        clock = findViewById(R.id.clock);
        clock.start();
//        setsoundavatar(Config.selectedCharacter.getSound());
        ((ImageView) findViewById(R.id.img)).setImageResource(Config.selectedCharacter.getImage());
        ((TextView) findViewById(R.id.name)).setText(Config.selectedCharacter.getName());
        findViewById(R.id.end).setOnClickListener(v -> {
            ShowAds.ShowInter(this, this::finish);
        });

    }

    @Override
    protected void onPause() {
        try {
            player.release();
            player.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();

    }


    public void setsoundavatar(@RawRes final int mRaw) {

        player = MediaPlayer.create(this, mRaw);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        player.setOnCompletionListener(mp -> {
            player.reset();
        });


        player.start();
    }
}