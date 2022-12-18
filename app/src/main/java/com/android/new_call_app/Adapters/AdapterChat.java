package com.android.new_call_app.Adapters;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import  com.android.new_call_app.R;
import  com.android.new_call_app.models.ChatObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.HolderChat> {

    private ArrayList<ChatObject> dataList;
    private Context context;


    public AdapterChat(Context context, ArrayList<ChatObject> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderChat(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderChat holder, int position) {
        String currentTime = DateFormat.getTimeInstance(3).format(new Date());

        try {
            if (dataList.get(position).isSend()) {
                holder.sendRelative.setVisibility(View.GONE);
                holder.recieveRelative.setVisibility(View.VISIBLE);
                SetColorChat(holder.body_sender, R.color.send_color, "sender");
                holder.recivermessage.setText(dataList.get(position).getMsg());
            } else {
                holder.sendRelative.setVisibility(View.VISIBLE);
                holder.recieveRelative.setVisibility(View.GONE);
                SetColorChat(holder.body_revicer, R.color.receive_color, "receive");
                holder.sendMessage.setText(dataList.get(position).getMsg());
            }
            holder.timeView.setText(currentTime);
            holder.recieveTime.setText(currentTime);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetColorChat(LinearLayout relative, int color, String type) {
        float topLeftRadius = 25;
        float BottomLeft_RightRadius = 25;
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(context.getResources().getColor(color));

        if (type.equals("receive")) {
            gradientDrawable.setCornerRadii(new float[]{
                    topLeftRadius, topLeftRadius, // Top Left
                    BottomLeft_RightRadius, BottomLeft_RightRadius, // Top Right
                    BottomLeft_RightRadius, BottomLeft_RightRadius, // Bottom Right
                    BottomLeft_RightRadius, BottomLeft_RightRadius});//Bottom left
        } else if (type.equals("sender")) {
            gradientDrawable.setCornerRadii(new float[]{
                    BottomLeft_RightRadius, BottomLeft_RightRadius, // Top Left
                    topLeftRadius, topLeftRadius, // Top Right
                    BottomLeft_RightRadius, BottomLeft_RightRadius, // Bottom Right
                    BottomLeft_RightRadius, BottomLeft_RightRadius});//Bottom left
        }
        relative.setBackground(gradientDrawable);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class HolderChat extends RecyclerView.ViewHolder {
        RelativeLayout sendRelative;
        RelativeLayout recieveRelative;
        public RelativeLayout sendLayout_image;
        TextView sendMessage, recivermessage,
                timeView, recieveTime;
        LinearLayout body_revicer, body_sender;
        CircleImageView profile1, profile2;
        ImageView image_receive;

        public HolderChat(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.itemes_chat, viewGroup, false));
            sendRelative = itemView.findViewById(R.id.sendLayout);
            recieveRelative = itemView.findViewById(R.id.recieveLayout);

            sendMessage = itemView.findViewById(R.id.sendMessage);
            recivermessage = itemView.findViewById(R.id.recieveMessage);

            timeView = itemView.findViewById(R.id.sendTime);
            recieveTime = itemView.findViewById(R.id.recieveTime);

            body_revicer = itemView.findViewById(R.id.body_recive);
            body_sender = itemView.findViewById(R.id.body_send);

            profile1 = itemView.findViewById(R.id.profile1);
            profile2 = itemView.findViewById(R.id.profile_img);

            image_receive = itemView.findViewById(R.id.image_receive);
            sendLayout_image = itemView.findViewById(R.id.sendLayout_image);


        }
    }
}
