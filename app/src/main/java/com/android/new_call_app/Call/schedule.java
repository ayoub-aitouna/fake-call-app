package com.android.new_call_app.Call;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.android.new_call_app.R;
import com.android.new_call_app.Utils.Config;

import java.util.Locale;

public class schedule extends AppCompatActivity {
    TextView s_5, s_10, m_1, m_5, m_10;
    private CountDownTimer timerdown;
    private long TimeLeft = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        s_5 = findViewById(R.id.s_5);
        s_10 = findViewById(R.id.s_10);
        m_1 = findViewById(R.id.m_1);
        m_5 = findViewById(R.id.m_5);
        m_10 = findViewById(R.id.m_10);
        s_5.setOnClickListener(v -> {

        });

        //        findViewById(R.id.start_call).setOnClickListener(v -> {
//            try {
//                updateCountDownText(findViewById(R.id.time_clock));
//                startTimer();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        updateCountDownText(findViewById(R.id.time_clock));
//        findViewById(R.id.increase).setOnClickListener(v -> {
//            TimeLeft += 1000;
//            updateCountDownText(findViewById(R.id.time_clock));
//        });
//        findViewById(R.id.reduce).setOnClickListener(v -> {
//            if (TimeLeft > 1000)
//                TimeLeft -= 1000;
//            updateCountDownText(findViewById(R.id.time_clock));
//        });
    }


    //    private void startTimer() throws Exception {
//        timerdown = new CountDownTimer(TimeLeft, 1000) {
//            public void onTick(long j) {
//                if (j != TimeLeft) {
//                    TimeLeft = j;
//                    updateCountDownText(findViewById(R.id.time_clock));
//                }
//            }
//
//            public void onFinish() {
//                makeCall();
//            }
//        }.start();
//    }

    private void updateCountDownText(TextView textView) {
        int i = ((int) (TimeLeft / 1000)) / 60;
        int i2 = ((int) (TimeLeft / 1000)) % 60;
        textView.setText(String.format(Locale.getDefault(), "%02d:%02d", i, i2));
    }

    private void makeCall() {
        Intent intent = new Intent(getApplicationContext(), outGoingCall.class);
        Config.selectedCharacter = Config.characters.get(0);
        startActivity(intent);
    }
}


