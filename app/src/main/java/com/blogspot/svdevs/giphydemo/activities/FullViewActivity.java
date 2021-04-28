package com.blogspot.svdevs.giphydemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.blogspot.svdevs.giphydemo.R;
import com.bumptech.glide.Glide;

public class FullViewActivity extends AppCompatActivity {

    private ImageView fullImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        fullImageView = findViewById(R.id.fullImg);

        Intent receiver = getIntent();
        String sourceUrl = receiver.getStringExtra("imageUrl");

        Glide.with(this).load(sourceUrl).into(fullImageView);
    }
}