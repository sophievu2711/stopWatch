package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Button;


import java.util.Timer;
import java.util.TimerTask;

import android.view.View;


public class MainActivity extends AppCompatActivity {

    private Timer timer;
    private TextView timeTextView;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private int frac = 0;
    private int sec = 100 * frac;
    private int min = 60 * sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(startButtonListener);
        stopButton.setOnClickListener(stopButtonListener);
        resetButton.setOnClickListener(resetButtonListener);

        timeTextView = findViewById(R.id.timeTextView);
    }

    private void setText(final TextView text, final int min, final int sec, final int frac){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(String.format("%02d", min) +
                        ":" + String.format("%02d", sec) +
                        "." + String.format("%02d", frac));
            }
        });
    }

    private final Button.OnClickListener startButtonListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timer.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            frac++;
                            if (frac > 99) {
                                sec++;
                                frac = 0;
                                if (sec > 60) {
                                    min++;
                                    sec = 0;
                                }
                            }
                            setText(timeTextView, min, sec, frac);
                        }
                    }, 0, 10);
                }
            };

    private final Button.OnClickListener stopButtonListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timer.cancel();
                }
            };

    private final Button.OnClickListener resetButtonListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frac = min = sec = 0;
                    setText(timeTextView, min, sec, frac);
                }
            };
}