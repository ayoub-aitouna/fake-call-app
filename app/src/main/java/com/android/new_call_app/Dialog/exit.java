package com.android.new_call_app.Dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;

import com.android.new_call_app.R;
import com.android.new_call_app.interfaces.InterCallback;


public class exit extends Dialog {

    Context context;
    InterCallback callback;

    public exit(@NonNull Context context, InterCallback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exit_dialog_layout);
        findViewById(R.id.yes).setOnClickListener(v -> {
            this.dismiss();
            callback.call();
        });
        findViewById(R.id.no).setOnClickListener(v -> {
            this.dismiss();
        });

    }

}
