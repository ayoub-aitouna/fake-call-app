package com.android.new_call_app.Adapters;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.new_call_app.Call.InCommingVideoCall;
import com.android.new_call_app.Call.outGoingCall;
import com.android.new_call_app.NetworkAdsManager.ShowAds;
import com.android.new_call_app.R;
import com.android.new_call_app.Utils.Config;
import com.android.new_call_app.models.Characters;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Characters> users;
    private static final int AD_TYPE = 218;
    private static final int Content_Type = 603;
    private final int max_items;
    private ShowAds showAds;

    public UsersAdapter(ArrayList<Characters> users, int max_items) {
        this.max_items = max_items;
        this.users = users;
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= max_items)
            return AD_TYPE;
        else
            return Content_Type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Content_Type)
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false));
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_app, parent, false);
            return new ad_holder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder _holder, int position) {
        if (_holder instanceof Holder) {
            Holder holder = (Holder) _holder;
            holder.img.setImageResource(users.get(position).getImage());
            holder.name.setText(users.get(position).getName());
        } else {
            ad_holder holder = (ad_holder) _holder;
            holder.img.setImageResource(users.get(position).getImage());
            holder.Name.setText(users.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;

        @SuppressLint({"SetTextI18n", "UseCompatLoadingForColorStateLists"})
        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.img);
            itemView.findViewById(R.id.call).setOnClickListener(v -> {
                LunchVoiceCall(getAdapterPosition());
            });
            itemView.findViewById(R.id.video).setOnClickListener(v -> {
                LunchVideoCall(getAdapterPosition());
            });

        }

        private void LunchVideoCall(int i) {
            Intent view = new Intent(itemView.getContext(), InCommingVideoCall.class);
            Config.selectedCharacter = users.get(i);
            itemView.getContext().startActivity(view);
        }

        private void LunchMessenger(int i) {
            Intent view = new Intent(itemView.getContext(), InCommingVideoCall.class);
            Config.selectedCharacter = users.get(i);
            itemView.getContext().startActivity(view);
        }

        private void LunchVoiceCall(int i) {
            Intent view = new Intent(itemView.getContext(), outGoingCall.class);
            Config.selectedCharacter = users.get(i);
            itemView.getContext().startActivity(view);
        }
    }

    public static class ad_holder extends RecyclerView.ViewHolder {
        Button install;
        TextView Name;
        ImageView img;

        public ad_holder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            install = itemView.findViewById(R.id.install);
            img = itemView.findViewById(R.id.img);
        }
    }


}
