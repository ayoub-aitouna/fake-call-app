package com.android.new_call_app.Call;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.new_call_app.Adapters.AdapterChat;
import com.android.new_call_app.Adapters.Message_adapter;
import com.android.new_call_app.NetworkAdsManager.ShowAds;
import com.android.new_call_app.R;
import com.android.new_call_app.Utils.Config;
import com.android.new_call_app.interfaces.Msg_callback;
import com.android.new_call_app.models.ChatObject;
import com.android.new_call_app.models.Messege;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class Messanger extends AppCompatActivity {


    private ImageView back;
    private RecyclerView recyclerView, msgs_recycler;
    private AdapterChat adapterChat;
    private ChatObject chatMsg;
    private final ArrayList<ChatObject> arrayList = new ArrayList<>();
    private ArrayList<Messege> msgs_list = new ArrayList<>();

    private TextView online;

    private final Handler handler = new Handler();
    private final int TimeReplay = 5 * 1000; // reply in 5 second
    private final int TimeToshowOnline = 2 * 1000; // wait 2 second to rest Online again


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messanger);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        online = findViewById(R.id.online);
        back = findViewById(R.id.back);
        ShowAds.ShowBanner(findViewById(R.id.banner));
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ((CircleImageView) findViewById(R.id.img)).setImageResource(Config.selectedCharacter.getImage());
        ((TextView) findViewById(R.id.name)).setText(Config.selectedCharacter.getName());

        adapterChat = new AdapterChat(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapterChat);
        adapterChat.notifyDataSetChanged();

        msgs_list = get_sujetings();
        //send msg adapter
        msgs_recycler = findViewById(R.id.send_msg);
        msgs_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        msgs_recycler.setHasFixedSize(true);

        adapterChat = new AdapterChat(getApplicationContext(), arrayList);
        Message_adapter message_adapter = new Message_adapter(msgs_list);
        message_adapter.onClick(index -> {
            Toast.makeText(this, "Clicked " + index, Toast.LENGTH_SHORT).show();
            msgs_list.remove(index);
            sendMsg(index);
            notifyRecycler();
            message_adapter.notifyDataSetChanged();
        });
        msgs_recycler.setAdapter(message_adapter);


        back.setOnClickListener(v -> {
            finish();
            animation(back);
        });

        findViewById(R.id.call).setOnClickListener(v -> {
            ShowAds.ShowInter(this, () -> LunchVoiceCall());

        });
        findViewById(R.id.video).setOnClickListener(v -> {
            ShowAds.ShowInter(this, () -> LunchVideoCall());

        });


    }

    private ArrayList<Messege> get_sujetings() {
        ArrayList<Messege> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Messege("Hey !!", "Am Fine"));
        }
        return list;
    }

    private void LunchVideoCall() {
        Intent view = new Intent(this, InCommingVideoCall.class);
        startActivity(view);
    }

    private void LunchVoiceCall() {
        Intent view = new Intent(this, outGoingCall.class);
        startActivity(view);
    }

    @SuppressLint("SetTextI18n")
    private void rec(int i) {
        handler.postDelayed(() -> online.setText("Typing..."), TimeToshowOnline);
        handler.postDelayed(() -> {
            receiveMsg(msgs_list.get(i).getResponse());
            online.setText("Online");
        }, TimeReplay);

    }

    private void sendMsg(int index) {
        arrayList.add(new ChatObject(msgs_list.get(index).getMSG(), true));
        rec(index);

    }

    private void receiveMsg(String string) {
        chatMsg = new ChatObject(string, false);
        arrayList.add(chatMsg);
        notifyRecycler();

    }


    @SuppressLint("NotifyDataSetChanged")
    private void notifyRecycler() {
        adapterChat.notifyDataSetChanged();
        recyclerView.postDelayed(() -> recyclerView.smoothScrollToPosition(arrayList.size() - 1), 100);


    }


    private void animation(final View view) {
        view.setScaleX(0.95f);
        view.setScaleY(0.95f);
        new Handler().postDelayed(() -> {
            view.setScaleX(1f);
            view.setScaleY(1f);
        }, 60);

    }


    @Override
    public void onBackPressed() {
        ShowAds.ShowInter(this, this::finish);
    }

}

