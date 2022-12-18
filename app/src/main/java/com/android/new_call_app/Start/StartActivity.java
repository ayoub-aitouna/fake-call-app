package com.android.new_call_app.Start;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.new_call_app.Call.MainCall;
import com.android.new_call_app.NetworkAdsManager.ShowAds;
import com.android.new_call_app.R;

import java.util.Objects;

public class StartActivity extends AppCompatActivity {
    String Name;
    int Age;
    EditText name, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        name = findViewById(R.id.name);
        number = findViewById(R.id.age);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (Objects.equals(sharedPref.getString(getString(R.string.saved_name), "NaN"), "NaN")) {
            startActivity(new Intent(StartActivity.this, MainCall.class));
            StartActivity.this.finish();
            return;
        }
        findViewById(R.id.btn).setOnClickListener(v ->
        {
            Name = name.getText().toString();
            Age = Integer.parseInt(number.getText().toString());

            editor.putInt(getString(R.string.saved_age), Age);
            editor.putString(getString(R.string.saved_name), Name);
            editor.apply();
            startActivity(new Intent(StartActivity.this, MainCall.class));
            StartActivity.this.finish();
        });
    }
}