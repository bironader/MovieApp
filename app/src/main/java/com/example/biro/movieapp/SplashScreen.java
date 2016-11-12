package com.example.biro.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
         ImageView splashScreen = (ImageView) findViewById(R.id.splashScreen);
        splashScreen.setImageResource(R.drawable.splash);

         Animation an = AnimationUtils.loadAnimation(this,R.anim.animation);
        splashScreen.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

}
