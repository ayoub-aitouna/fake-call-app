package com.android.new_call_app.Call;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import  com.android.new_call_app.NetworkAdsManager.ShowAds;
import  com.android.new_call_app.R;
import  com.android.new_call_app.Utils.Config;

import java.io.IOException;
import java.util.List;


public class VideoCall extends AppCompatActivity implements SurfaceHolder.Callback {
    ShowAds showAds;
    VideoView mVideoView;
    Camera maincamera;
    SurfaceView miniview;
    int cameraCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        mVideoView = findViewById(R.id.video_play);

        initcam();
        VideoStart();
        findViewById(R.id.end).setOnClickListener(v -> showAds.ShowInter(this, () -> {

        }));

        findViewById(R.id.msg).setOnClickListener(v -> {
            Intent view = new Intent(VideoCall.this, Messanger.class);
            view.putExtra(Config.PUT_ID, getIntent().getIntExtra(Config.PUT_ID, 0));
            startActivity(view);
            finish();
        });
    }

    public void VideoStart() {

        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videoone));
        mVideoView.setOnPreparedListener(mp -> {
            // TODO Auto-generated method stub
            mp.setLooping(true);

        });

        mVideoView.start();
    }

    public void initcam() {
        miniview = findViewById(R.id.surfaceCam);
        miniview.getHolder().addCallback(this);
        miniview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        VideoCam();

    }

    public void VideoCam() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int FacingCam = 0; FacingCam < cameraCount; FacingCam++) {
            Camera.getCameraInfo(FacingCam, cameraInfo);
            if (cameraInfo.facing == 1) {
                try {
                    maincamera = Camera.open(FacingCam);
                } catch (RuntimeException e) {
                    //do nothing
                }
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            Camera.Parameters params = maincamera.getParameters();
            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
            Camera.Size selected = sizes.get(0);
            params.setPreviewSize(selected.width, selected.height);
            params.getSupportedPreviewSizes();
            maincamera.setParameters(params);
            maincamera.setDisplayOrientation(90);
            maincamera.setPreviewDisplay(holder);
            maincamera.startPreview();
        } catch (Exception e0) {
            //error to Initialize Camera
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (maincamera != null) {

            Camera.Parameters params = maincamera.getParameters();
            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
            Camera.Size previewSize = sizes.get(0);

            params.setPreviewSize(previewSize.width, previewSize.height);
            maincamera.setParameters(params);
            maincamera.setDisplayOrientation(90);

            try {
                maincamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("motya", "surfaceCreated");
            }

            // Important: Call startPreview() to start updating the preview
            // surface. Preview must be started before you can take a picture.
            maincamera.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (maincamera != null) {
            // Call stopPreview() to stop updating the preview surface.
            maincamera.stopPreview();
        }
        Log.e("motya", "surfaceDestroyed");
    }


    @Override
    public void onPause() {
        if (maincamera != null) {
            maincamera.stopPreview();
            maincamera.release();
            maincamera = null;
        }
        super.onPause();

    }
}