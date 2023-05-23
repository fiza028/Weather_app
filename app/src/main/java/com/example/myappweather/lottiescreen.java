package com.example.myappweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class lottiescreen extends AppCompatActivity {

    TextView intro;
    LottieAnimationView lottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottiescreen);
        intro = findViewById(R.id.intro);
        lottie = findViewById(R.id.lottie);

        intro.animate().translationY(-1400).setDuration(2500).setStartDelay(0);
        lottie.animate().translationY(2000).setDuration(2500).setStartDelay(2900);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 4500);

    }

}