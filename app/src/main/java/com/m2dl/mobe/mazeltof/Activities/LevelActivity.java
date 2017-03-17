package com.m2dl.mobe.mazeltof.Activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.m2dl.mobe.mazeltof.Models.ImageAdapter;
import com.m2dl.mobe.mazeltof.R;

public class LevelActivity extends AppCompatActivity {

    private GridView gridView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        this.gridView = (GridView) findViewById(R.id.gridlevel);
        this.gridView.setAdapter(new ImageAdapter(this));

        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(LevelActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
