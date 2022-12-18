package com.android.new_call_app.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.new_call_app.R;
import com.android.new_call_app.interfaces.Msg_callback;
import com.android.new_call_app.models.Messege;


import java.util.ArrayList;

public class Message_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Messege> msgs;
    Msg_callback callback;

    public Message_adapter(ArrayList<Messege> msgs) {
        this.msgs = msgs;

    }

    public void onClick(Msg_callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder _holder, int position) {
        Holder holder = (Holder) _holder;
        holder.msg.setText(msgs.get(position).getMSG());
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView msg;

        public Holder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.msg);
            msg.setOnClickListener(v -> {
                callback.call(getBindingAdapterPosition());
            });


        }
    }


}
