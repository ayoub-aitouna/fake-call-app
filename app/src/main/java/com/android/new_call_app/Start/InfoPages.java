package com.android.new_call_app.Start;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import  com.android.new_call_app.Adapters.ViewPager2Adapter;
import  com.android.new_call_app.MainActivity;
import  com.android.new_call_app.NetworkAdsManager.ShowAds;
import  com.android.new_call_app.R;
import  com.android.new_call_app.models.Info;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class InfoPages extends AppCompatActivity {
    Button btn;
    ImageButton prv;
    private ViewPager2 viewPager;
    private ViewPager2Adapter adapter;
    private DotsIndicator dotsIndicator;
    public ArrayList<Info> items;
    static final String TAG = "Main_Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pages);
        btn = findViewById(R.id.btn);
        prv = findViewById(R.id.prv);
        items = new ArrayList<>(List.of(new Info(this.getString(R.string.title_1), this.getString(R.string.text_1), R.drawable.icon),
                new Info(this.getString(R.string.title_2), this.getString(R.string.text_2), R.drawable.icon),
                new Info(this.getString(R.string.title_3), this.getString(R.string.text_3), R.drawable.icon)));
        dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        viewPager = findViewById(R.id.pager);
        adapter = new ViewPager2Adapter(this, items);

        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        dotsIndicator.attachTo(viewPager);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                Log.d(TAG, "onPageScrolled: " + (position + positionOffset) / (items.size() - 1));
                Log.d(TAG, "position: " + position);
                Log.d(TAG, "positionOffset: " + positionOffset + "\n");
                if (position == 0) {
                    prv.setVisibility(View.GONE);
                } else {
                    prv.setVisibility(View.VISIBLE);
                }
                if (position == items.size() - 1) {
                    btn.setText("get Started");
                } else {
                    btn.setText("Next");
                }
            }
        });
        changeBtn();

        btn.setOnClickListener(v -> {
            changeBtn();
            if (getAdapterPosition() == items.size() - 1) {
                ShowAds.ShowInter(this, () -> {
                    startActivity(new Intent(InfoPages.this, MainActivity.class));
                    finish();
                });

            } else {
                Next(getAdapterPosition());
            }
        });
        prv.setOnClickListener(v -> {
            changeBtn();
            if (getAdapterPosition() > 0) {
                Previose(getAdapterPosition());
            }
        });
        ShowAds.ShowBanner(findViewById(R.id.banner));
    }

    private void changeBtn() {
        Log.d(TAG, "changeBtn: position >>" + getAdapterPosition() + " Size >> " + items.size());

    }

    private int getAdapterPosition() {
        return viewPager.getCurrentItem();
    }

    private void Next(int position) {
        viewPager.setCurrentItem(position + 1, true);
    }

    private void Previose(int position) {
        viewPager.setCurrentItem(position - 1, true);
    }


}