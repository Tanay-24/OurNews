package com.example.ournews;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    Animation topAnim, bottomAnim;
    ImageView imageView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animations);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animaions);

        imageView = findViewById(R.id.startingimg);
        button = findViewById(R.id.EntryBtn);

        imageView.setAnimation(topAnim);
        button.setAnimation(bottomAnim);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RActivity.class);
            startActivity(intent);
        });
    }
}