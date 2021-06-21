package com.example.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class splash extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        imageView = findViewById(R.id.imageview);
        Animation myanim = AnimationUtils.loadAnimation(this ,R.anim.myanim);
        imageView.startAnimation(myanim);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent = new Intent(splash.this , MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}