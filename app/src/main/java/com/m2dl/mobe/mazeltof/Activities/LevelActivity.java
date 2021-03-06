package com.m2dl.mobe.mazeltof.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.m2dl.mobe.mazeltof.MainActivity;
import com.m2dl.mobe.mazeltof.Models.ImageAdapter;
import com.m2dl.mobe.mazeltof.R;

public class LevelActivity extends AppCompatActivity {

    private GridView gridView;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        this.imageAdapter = new ImageAdapter(this);
        this.gridView = (GridView) findViewById(R.id.gridlevel);
        this.gridView.setAdapter(imageAdapter);

        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position != 0) {
                    Toast.makeText(getApplicationContext(), "Ce niveau n'est pas encore disponible", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LevelActivity.this, MainActivity.class);
                    intent.putExtra("img_position", imageAdapter.getIdResource(position));
                    intent.putExtra("position", (position+1));
                    startActivity(intent);
                }

            }
        });
    }
}
