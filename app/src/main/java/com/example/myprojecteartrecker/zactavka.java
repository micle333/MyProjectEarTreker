package com.example.myprojecteartrecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class zactavka extends Activity {
    ImageView hearing;
    LottieAnimationView lottieAnimationView, konets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zactavka);

        lottieAnimationView = findViewById(R.id.lottie);
        konets = findViewById(R.id.lottie_konets);
        hearing = findViewById(R.id.hearing);

        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        konets.animate().setStartDelay(4000);
        hearing.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(zactavka.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_in,R.anim.slide_up_out);

            }
        }, 5000);
    }


}