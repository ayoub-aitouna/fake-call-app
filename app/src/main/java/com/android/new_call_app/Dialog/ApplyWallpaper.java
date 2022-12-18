package com.android.new_call_app.Dialog;
/**
 * Created By Ayoub aitouna
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import  com.android.new_call_app.NetworkAdsManager.ShowAds;
import  com.android.new_call_app.R;
import  com.android.new_call_app.interfaces.OnBitmapLoaded;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ApplyWallpaper extends Dialog {
    private static final String TAG = "ApplyWallpaper";
    public Dialog dialog;
    Context context;
    WallpaperManager myWallpaperManager;
    String src;
    private List<Disposable> compositeDisposable;

    public ApplyWallpaper(@NonNull Context context) {
        super(context);
        dialog = this;
        this.context = context;
    }

    public ApplyWallpaper(@NonNull Context context, String src, int themeResId) {
        super(context, themeResId);
        dialog = this;
        this.src = src;
        this.context = context;
        compositeDisposable = new ArrayList<Disposable>();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.apply_wallpaper_pop_up);


        findViewById(R.id.home).setOnClickListener(v -> {
            SetWallpaperToHome();
        });
        findViewById(R.id.lock).setOnClickListener(v -> {
            SetWallpaperToLock();
        });
        findViewById(R.id.homeLock).setOnClickListener(v -> {
            SetWallpaperToBoth();
        });
        findViewById(R.id.download).setOnClickListener(v -> {
            saveImage();
        });


    }

    void ToggleLoading(int State) {
        LinearLayout Container = findViewById(R.id.options_container);
        LinearLayout done = findViewById(R.id.done);
        ProgressBar loading = findViewById(R.id.loading);
        switch (State) {
            case 0:
                Container.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                break;
            case 1:
                loading.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                this.setCancelable(true);
                this.setCanceledOnTouchOutside(true);

                break;

        }

        if (Container.getVisibility() == View.VISIBLE) {

        } else {

        }
    }

    private void SetWallpaperToHome() {
        GetBitmapFromUrl(new OnBitmapLoaded() {
            @Override
            public void Loaded(Bitmap bitmap) {
                try {
                    myWallpaperManager = WallpaperManager.getInstance(context.getApplicationContext());
                    myWallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_SYSTEM);

                } catch (Exception e) {
                    Log.e(TAG, "onResourceReady: " + e.getMessage());
                }
            }
        });


    }

    private void SetWallpaperToLock() {
        GetBitmapFromUrl(bitmap -> {
            try {
                myWallpaperManager = WallpaperManager.getInstance(context.getApplicationContext());
                myWallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_LOCK);
            } catch (Exception e) {
                Log.e(TAG, "onResourceReady: " + e.getMessage());
            }
        });


    }

    private void SetWallpaperToBoth() {
        GetBitmapFromUrl(bitmap -> {
            try {
                myWallpaperManager = WallpaperManager.getInstance(context.getApplicationContext());
                myWallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_LOCK | WallpaperManager.FLAG_SYSTEM);
            } catch (Exception e) {
                Log.e(TAG, "onResourceReady: " + e.getMessage());
            }
        });


    }

    public void Destroy() {
        for (Disposable element : compositeDisposable) {
            element.dispose();
        }
    }

    public void GetBitmapFromUrl(OnBitmapLoaded bitmapLoaded) {
        ToggleLoading(0);
        Observable.fromCallable(() -> {
                    // do stuff
                    try {
                        URL url = new URL(this.src);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        bitmapLoaded.Loaded(bitmap);
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // so that this can be properly disposed in onDestroy()
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        ShowAds.ShowInter((Activity) context, () -> {
                            ToggleLoading(1);
                        });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

    }

    public void saveImage() {
        String Time = new SimpleDateFormat("yyyyMMdd_HHmmss"
                , Locale.getDefault()).format(System.currentTimeMillis());
        GetBitmapFromUrl(bitmap -> {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    bitmap, context.getPackageName() + "-wallpaper-" + Time, null);
        });


    }

}