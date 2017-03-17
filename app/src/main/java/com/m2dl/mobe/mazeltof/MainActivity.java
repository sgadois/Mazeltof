package com.m2dl.mobe.mazeltof;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.m2dl.mobe.mazeltof.Activities.LabyrintheActivity;
import com.m2dl.mobe.mazeltof.Activities.LevelActivity;

public class MainActivity extends AppCompatActivity {
    private Context context;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        mediaPlayer = MediaPlayer.create(this, R.raw.expert);
        mediaPlayer.start();
        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            //Set de l'image
            ImageView imageView = (ImageView) findViewById(R.id.imglevel);
            imageView.setImageResource(bundle.getInt("img_position"));
            //Set du label du niveau
            TextView labelniveau = (TextView) findViewById(R.id.imglabel);
            labelniveau.setText("Niveau " + bundle.getInt("position"));
        }

        ImageButton play = (ImageButton) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                startActivity(new Intent(context, LabyrintheActivity.class));
            }
        });

        ImageButton level = (ImageButton) findViewById(R.id.level);
        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                startActivity(new Intent(context, LevelActivity.class));
            }
        });
    }
}
