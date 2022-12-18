package com.android.new_call_app.Dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import  com.android.new_call_app.R;
import  com.android.new_call_app.Utils.Config;
import com.squareup.picasso.Picasso;


public class UpdateDialog extends Dialog {

    Context context;

    public UpdateDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.update_dialog_layout);
        ((TextView) findViewById(R.id.title)).setText(Config.appdata.getSettings().getTitle());
        ((TextView) findViewById(R.id.message)).setText(Config.appdata.getSettings().getMessage());

        Picasso.get().load(Config.appdata.getSettings().getImg()).into((ImageView) findViewById(R.id.img));

        findViewById(R.id.downloadApp).setOnClickListener(v -> {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + Config.appdata.getSettings().getUpdatePackageName())));
            } catch (android.content.ActivityNotFoundException e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + Config.appdata.getSettings().getUpdatePackageName())));
            }

        });

    }

}
