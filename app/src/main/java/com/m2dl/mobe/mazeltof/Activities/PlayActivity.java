package com.m2dl.mobe.mazeltof.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.m2dl.mobe.mazeltof.R;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        TextView hello = (TextView) findViewById(R.id.textView);
        hello.setText("play");
    }
}
