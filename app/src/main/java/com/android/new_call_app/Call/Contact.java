package com.android.new_call_app.Call;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.new_call_app.Adapters.UsersAdapter;
import com.android.new_call_app.NetworkAdsManager.ShowAds;
import com.android.new_call_app.R;
import com.android.new_call_app.Utils.Config;
import com.android.new_call_app.models.Characters;

import java.util.ArrayList;

public class Contact extends AppCompatActivity {
    private ArrayList<Characters> characters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ShowAds.ShowBanner(findViewById(R.id.banner));
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        characters = new ArrayList<>(Config.characters);
        int max_items = characters.size();
        for (int i = max_items; i < 6; i++)
            characters.add(new Characters(-1, "", "", ""));
        recyclerView.setAdapter(new UsersAdapter(characters, max_items));
        findViewById(R.id.back).setOnClickListener(v -> {
            finish();
        });
    }
}