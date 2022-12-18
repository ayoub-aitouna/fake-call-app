package com.android.new_call_app.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import  com.android.new_call_app.Db.DatabaseHelper;
import  com.android.new_call_app.NetworkAdsManager.ShowAds;
import  com.android.new_call_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class WallpaperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_AD = 57;
    private static final int Content_Type = 786;
    int Rate = 5;
    ArrayList<String> items;

    DatabaseHelper databaseHelper;
    List<String> FavList;
    Activity context;
    RecyclerView recyclerView;

    public WallpaperAdapter(Activity context, RecyclerView recyclerView) {
        this.context = context;
        databaseHelper = DatabaseHelper.getInstance(context);
        this.recyclerView = recyclerView;
        FavList = databaseHelper.getAllWallpapers();
    }

    public void Update(ArrayList<String> items) {
        this.items = items;
    }

    public boolean CalculateNativeRate(int i) {
        return i % Rate == 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (CalculateNativeRate(position)) {
            return TYPE_AD;
        } else return Content_Type;
    }

    private void Check(Holder holidayHolder, int i) {

        if (Map(i)) {
            holidayHolder.FavBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            holidayHolder.FavBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        recyclerView.post(() -> notifyItemChanged(i));
    }

    private boolean Map(int i) {
        for (String item : FavList) {
            if (Objects.equals(item, items.get(i))) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_AD) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_container, parent, false);
            final ViewGroup.LayoutParams lp = v.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
                sglp.setFullSpan(true);
            }
            return new NativeHolder(v);
        } else
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item_view, parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            Holder holder1 = (Holder) holder;
            if (!items.isEmpty())
                Picasso.get().load(items.get(position)).into(holder1.img);
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

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView FavBtn;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            FavBtn = itemView.findViewById(R.id.FavBtn);

//            itemView.setOnClickListener(v -> ShowAds.ShowInter(context,() -> {
//                Intent view = new Intent(itemView.getContext(), WallpaperView.class);
//                view.putExtra("pos", Config.appdata.getWallpaper().indexOf(items.get(getAdapterPosition())));
//                itemView.getContext().startActivity(view);
//            }));
        }
    }

    public class NativeHolder extends RecyclerView.ViewHolder {
        FrameLayout nativeContainer;

        public NativeHolder(@NonNull View itemView) {
            super(itemView);
            nativeContainer = itemView.findViewById(R.id.native_ad);
            ShowAds.ShowNative(nativeContainer);
        }
    }
}
