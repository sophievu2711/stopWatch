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

    private int frac = 0;
    private int sec = 100 * frac;
    private int min = 60 * sec;
    private int savedFrac = 0;
    private int savedSec = 100 * savedFrac;
    private int savedMin = 60 * savedSec;
    private boolean freeStorage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button startButton;
        Button stopButton;
        Button resetButton;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);
        timeTextView = findViewById(R.id.timeTextView);

        startButton.setOnClickListener(startButtonListener);
        stopButton.setOnClickListener(stopButtonListener);
        resetButton.setOnClickListener(resetButtonListener);
    }

    private void saveCurrentTime(){
        savedFrac = frac;
        savedMin = min;
        savedSec = sec;
    }

    private void clearTimeHistory(){
        savedFrac = 0;
        savedMin = 0;
        savedSec = 0;
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

    private void calculate() {
        frac++;
        if (frac > 99) {
            sec++;
            frac = 0;
            if (sec > 60) {
                min++;
                sec = 0;
            }
        }
    }

    private final Button.OnClickListener startButtonListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(freeStorage){
                        timer.scheduleAtFixedRate(new TimerTask() {
                            public void run() {
                                calculate();
                                setText(timeTextView, min, sec, frac);
                            }
                        }, 0, 10);
                    }else{
                        timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            public void run() {
                                calculate();
                                setText(timeTextView, min, sec, frac);
                            }
                        }, 0, 10);
                        clearTimeHistory();
                    }
                }
            };

    private final Button.OnClickListener stopButtonListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveCurrentTime();
                    timer.cancel();
                    freeStorage = false;
                }
            };

    private final Button.OnClickListener resetButtonListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frac = min = sec = 0;
                    timer = new Timer();
                    setText(timeTextView, min, sec, frac);
                }
            };
}