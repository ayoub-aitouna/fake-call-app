package com.android.new_call_app.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import  com.android.new_call_app.Db.DatabaseHelper;
import  com.android.new_call_app.NetworkAdsManager.ShowAds;
import  com.android.new_call_app.R;
import  com.android.new_call_app.Utils.Config;
import  com.android.new_call_app.Utils.TinyDB;
import  com.android.new_call_app.interfaces.Select;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class WallpaperFullAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<String> items;
    Select select;
    RecyclerView recyclerView;
    int ClickCount = 0;
    TinyDB db;
    Context context;
    private int NO_POSITION = -1;
    List<String> FavList;
    ImageView FavBtn;
    DatabaseHelper databaseHelper;

    public WallpaperFullAdapter(Context context, Select select, RecyclerView recyclerView, int pos) {
        this.select = select;
        this.context = context;
        this.recyclerView = recyclerView;
        this.items = new ArrayList<>(Config.appdata.getWallpaper());
        Collections.swap(items, 0, pos);
        databaseHelper = DatabaseHelper.getInstance(context);
        FavList = databaseHelper.getAllWallpapers();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item_view_full, parent, false));
    }

    private void Check(Holder holidayHolder, int i) {
        for (String item : FavList) {
            if (Objects.equals(item, items.get(i))) {
                holidayHolder.FavBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
            } else {
                holidayHolder.FavBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            Holder holder1 = (Holder) holder;
            Picasso.get().load(items.get(position)).into(holder1.img);

            holder1.next.setOnClickListener(v -> {
                if (ClickCount == 3) {
                    ShowAds.ShowInter((Activity) context, () -> {
                        Next(position);
                        ClickCount = 0;
                    });

                } else {
                    ClickCount += 1;
                    Next(position);
                }

            });


            holder1.back.setOnClickListener(v -> {
                if (ClickCount == 3) {
                    ShowAds.ShowInter((Activity) context, () -> {
                        Back(position);
                        ClickCount = 0;
                    });

                } else {
                    ClickCount += 1;
                    Back(position);
                }

            });
            holder1.back.setVisibility(position > 0 ? View.VISIBLE : View.GONE);
            holder1.next.setVisibility(position == (items.size() - 1) ? View.GONE : View.VISIBLE);
            Check(holder1, position);
            holder1.FavBtn.setOnClickListener(v -> {
                if (!databaseHelper.deleteWallpaper(items.get(position))) {
                    databaseHelper.addOrUpdateWallpaper(items.get(position));
                }
                FavList = databaseHelper.getAllWallpapers();
                Check(holder1, position);
            });
        }

    }


    private void Back(int position) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        assert linearLayoutManager != null;
        linearLayoutManager.scrollToPositionWithOffset(position - 1, 0);
    }

    private void Next(int position) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        assert linearLayoutManager != null;
        linearLayoutManager.scrollToPositionWithOffset(position + 1, 0);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img, FavBtn;
        ImageView next, back;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            FavBtn = itemView.findViewById(R.id.fav);
            next = itemView.findViewById(R.id.next);
            back = itemView.findViewById(R.id.back);
            itemView.findViewById(R.id.wallpaper).setOnClickListener(v -> ShowAds.ShowInter((Activity) context, () -> {
                select.selectItem(Config.appdata.getWallpaper().indexOf(items.get(getAdapterPosition())), Select.Options.Apply);
            }));


        }
    }

}
