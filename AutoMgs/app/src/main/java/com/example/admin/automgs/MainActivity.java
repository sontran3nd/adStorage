package com.example.admin.automgs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.automgs.Fragments.Dialogs.SimpleDialogFragment;
import com.example.admin.automgs.Views.SampleX;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSample = (Button) findViewById(R.id.activity_main_btn_show);
        btnSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDialogFragment simpleDialogFragment = new SimpleDialogFragment();
                simpleDialogFragment.show(getSupportFragmentManager(), "");
            }
        });

//        SampleX sampleX = (SampleX) findViewById(R.id.activity_main_samplex);
    }
}
