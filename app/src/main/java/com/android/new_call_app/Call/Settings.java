package com.android.new_call_app.Call;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.android.new_call_app.R;


public class Settings extends AppCompatActivity {
    String Name;
    int Age;
    EditText name, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        name = findViewById(R.id.name);
        number = findViewById(R.id.age);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        name.setText(sharedPref.getString(getString(R.string.saved_name), "NONE"));
        number.setText(String.valueOf(sharedPref.getInt(getString(R.string.saved_age), 0)));

        findViewById(R.id.btn).setOnClickListener(v ->
        {
            Name = name.getText().toString();
            Age = Integer.parseInt(number.getText().toString());

            editor.putInt(getString(R.string.saved_age), Age);
            editor.putString(getString(R.string.saved_name), Name);
            editor.apply();
            Settings.this.finish();
        });
    }
}