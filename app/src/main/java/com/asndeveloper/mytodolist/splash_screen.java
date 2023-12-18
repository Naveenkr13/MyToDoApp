package com.asndeveloper.mytodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class splash_screen extends AppCompatActivity {
  LottieAnimationView loti;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    loti=(LottieAnimationView) findViewById(R.id.splashsc);
    loti.setAnimation(R.raw.todoan);
    loti.playAnimation();
   new Handler().postDelayed(new Runnable() {
       @Override
       public void run() {
        Intent inext=new Intent(splash_screen.this,MainActivity.class);
        startActivity(inext);
           finish();
       }
   },3000);






    }
}