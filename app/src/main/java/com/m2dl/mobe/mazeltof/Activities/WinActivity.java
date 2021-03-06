package com.m2dl.mobe.mazeltof.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.m2dl.mobe.mazeltof.MainActivity;
import com.m2dl.mobe.mazeltof.R;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            TextView labelTemps = (TextView) findViewById(R.id.tempsMenu);
            labelTemps.setText("Temps: " + bundle.getString("time"));
        }


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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
