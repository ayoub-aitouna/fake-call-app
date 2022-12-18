package com.android.new_call_app.Call;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import  com.android.new_call_app.Adapters.AdapterComments;
import  com.android.new_call_app.NetworkAdsManager.ShowAds;
import  com.android.new_call_app.R;
import  com.android.new_call_app.Utils.Config;
import  com.android.new_call_app.Utils.Utils;
import  com.android.new_call_app.models.Characters;
import  com.android.new_call_app.models.Comments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LiveActivty extends AppCompatActivity implements SurfaceHolder.Callback {

    ShowAds showAds;
    Camera maincamera;
    SurfaceView miniview;
    Chronometer mChronometer;
    TextView viewers;
    Handler handler, commentHandler;
    Runnable runnable, commentsRunnable;
    int ViewersCount = 20, cameraCount;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_activty);
        initcam();
        mChronometer = findViewById(R.id.mChronometer);
        viewers = findViewById(R.id.viewers);
        viewers.setText("" + ViewersCount);
        handler = new Handler();
        commentHandler = new Handler();
        runnable = () -> {
            ViewersCount = Utils.RandomInt(0, ViewersCount);
            ViewersCount += 100;
            viewers.setText("" + ViewersCount);
            handler.postDelayed(runnable, 500);

        };
        handler.postDelayed(runnable, 500);
        VideoStart();
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        setComments();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setComments() {
        RecyclerView recyclerView = findViewById(R.id.comments);
        ArrayList<Comments> comments = new ArrayList<>();
        AdapterComments adapterComments = new AdapterComments(comments);
        commentsRunnable = () -> {
            Characters character = Config.characters.get(Utils.RandomInt(0, Config.characters.size() - 1));
            String msg = Config.comments.get(Utils.RandomInt(0, Config.comments.size() - 1));
            comments.add(new Comments(character.getImage(), character.getName(), msg));
            adapterComments.notifyDataSetChanged();
            recyclerView.postDelayed(() -> recyclerView.smoothScrollToPosition(comments.size() - 1), 100);
            commentHandler.postDelayed(commentsRunnable, 1500);

        };
        commentHandler.postDelayed(commentsRunnable, 1500);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterComments);


    }

    public void VideoStart() {

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