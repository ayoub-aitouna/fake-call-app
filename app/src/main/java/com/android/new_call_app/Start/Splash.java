package com.android.new_call_app.Start;

import static com.android.new_call_app.Utils.Config.Init;
import static com.android.new_call_app.Utils.Global.connectionType;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;

import com.android.new_call_app.Dialog.Retry;
import com.android.new_call_app.Dialog.UpdateDialog;
import com.android.new_call_app.NetworkAdsManager.ShowAds;
import com.android.new_call_app.R;
import com.android.new_call_app.Utils.Config;
import com.android.new_call_app.Utils.Global;
import com.android.new_call_app.models.data;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class Splash extends AppCompatActivity {
    private static String TAG;
    JsonObjectRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LoadData();
        findViewById(R.id.title).animate().alpha(1f).scaleX(1.2f).scaleY(1.2f).setDuration(2 * 1000);
    }


    private void LoadData() {
        if (connectionType == Config.ConnectionType.Online) {
            request = new JsonObjectRequest(Request.Method.GET, Global.URL, null,
                    response -> {
                        Config.appdata = new Gson().fromJson(String.valueOf(response), data.class);
                        ShowAds.Init(Splash.this);
                        Init();
                        new Handler().postDelayed(this::OpenActivity, 3 * 1000);
                    }, error -> {
                Log.d(TAG, "LoadData: " + error.getMessage());
                Show_Error_Dialog();
            });
            final RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
            requestQueue.getCache().clear();
        } else {
            Config.appdata = new Gson().fromJson(String.valueOf(loadJSONFromAsset()), data.class);
            ShowAds.Init(Splash.this);
            Init();
            new Handler().postDelayed(this::OpenActivity, 3 * 1000);
        }
    }

    private void OpenActivity() {
        if (Config.appdata.getSettings().isSuspended())
            ShowDialog();
        else
            startActivity(new Intent(Splash.this, StartActivity.class));
        finish();
    }

    private void Show_Error_Dialog() {
        Retry retry = new Retry(this, this::LoadData);
        retry.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        retry.getWindow().setGravity(Gravity.CENTER);
        retry.setCancelable(false);
        retry.show();
    }

    private void ShowDialog() {
        UpdateDialog updateDialog = new UpdateDialog(this);
        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updateDialog.getWindow().setGravity(Gravity.CENTER);
        updateDialog.setCancelable(false);
        updateDialog.show();
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open(" com.android.new_call_app.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}