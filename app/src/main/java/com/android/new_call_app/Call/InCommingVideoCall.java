package com.android.new_call_app.Call;


import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import  com.android.new_call_app.NetworkAdsManager.ShowAds;
import  com.android.new_call_app.R;
import  com.android.new_call_app.Utils.Config;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InCommingVideoCall extends AppCompatActivity implements SurfaceHolder.Callback {
    Camera camrea;
    SurfaceView videomini;
    private int camnumberscount;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_comming_video_call);
        ShowAds.ShowBanner(findViewById(R.id.banner));
        ((CircleImageView) findViewById(R.id.img)).setImageResource(Config.selectedCharacter.getImage());
        ((TextView) findViewById(R.id.name)).setText(Config.selectedCharacter.getName());
        initcamera();
        PlayRingtone();

        findViewById(R.id.end).setOnClickListener(v -> ShowAds.ShowInter(this, () -> {
            finish();
        }));
        findViewById(R.id.start).setOnClickListener(v -> {
            Intent view = new Intent(InCommingVideoCall.this, VideoCall.class);
            startActivity(view);
            finish();
        });
        findViewById(R.id.msg).setOnClickListener(v -> {
            Intent view = new Intent(InCommingVideoCall.this, Messanger.class);
            startActivity(view);
            finish();
        });

    }

    public void initcamera() {
        videomini = findViewById(R.id.surfaceVideo);
        videomini.getHolder().addCallback(this);
        videomini.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        facecam();

    }

    public void facecam() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        camnumberscount = Camera.getNumberOfCameras();
        for (int FacingCam = 0; FacingCam < camnumberscount; FacingCam++) {
            Camera.getCameraInfo(FacingCam, cameraInfo);
            if (cameraInfo.facing == 1) {
                try {
                    camrea = Camera.open(FacingCam);
                } catch (RuntimeException e) {

                }
            }
        }

    }

    private void PlayRingtone() {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.start();
        player.setOnCompletionListener(mp -> player.start());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (camrea != null) {

            Camera.Parameters params = camrea.getParameters();
            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
            Camera.Size previewSize = sizes.get(0);

            params.setPreviewSize(previewSize.width, previewSize.height);
            camrea.setParameters(params);
            camrea.setDisplayOrientation(90);

            try {
                camrea.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("motya", "surfaceCreated");
            }


            camrea.startPreview();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        try {
            Camera.Parameters params = camrea.getParameters();
            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
            Camera.Size selected = sizes.get(0);
            params.setPreviewSize(selected.width, selected.height);
            params.getSupportedPreviewSizes();
            camrea.setParameters(params);
            camrea.setDisplayOrientation(90);
            camrea.setPreviewDisplay(holder);
            camrea.startPreview();
        } catch (Exception e0) {

        }

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camrea != null) {
            camrea.stopPreview();
        }

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