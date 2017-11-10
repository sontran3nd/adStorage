package com.example.admin.detectvs;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
    }

    private Button btnLog;
    private Button btnBack;

    private void setupViews() {
        btnLog = (Button) findViewById(R.id.btn_log);
        btnBack = (Button) findViewById(R.id.btn_play);
        btnLog.setOnClickListener(logAction);
        btnBack.setOnClickListener(backClicked);
    }

    private View.OnClickListener backClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            runMS(R.raw.sound);
        }
    };

    private View.OnClickListener logAction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            runMS(R.raw.ms);
        }
    };

    private MediaPlayer mPlayer = null;

    private void runMS(int id) {
        if (mPlayer == null) {
            mPlayer = MediaPlayer.create(getApplicationContext(), id);
            mPlayer.start();
        }

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mPlayer = null;
            }
        });
    }
}
