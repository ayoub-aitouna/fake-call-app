package com.android.new_call_app.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import  com.android.new_call_app.R;
import  com.android.new_call_app.models.Comments;

import java.util.ArrayList;

public class AdapterComments extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Comments> comments;


    public AdapterComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.live_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder _holder, int position) {
        Holder holder = (Holder) _holder;
        holder.img.setImageResource(comments.get(position).getImage());
        holder.name.setText(comments.get(position).getName());
        holder.content.setText(comments.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, content;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
            img = itemView.findViewById(R.id.img);
        }


    }


}
