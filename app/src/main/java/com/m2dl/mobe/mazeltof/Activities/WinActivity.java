package com.m2dl.mobe.mazeltof.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.m2dl.mobe.mazeltof.MainActivity;
import com.m2dl.mobe.mazeltof.R;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        ImageButton play = (ImageButton) findViewById(R.id.boutonRejouer);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WinActivity.this, LabyrintheActivity.class));
            }
        });

        ImageButton main = (ImageButton) findViewById(R.id.boutonMenu);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WinActivity.this, MainActivity.class));
            }
        });
    }
}
