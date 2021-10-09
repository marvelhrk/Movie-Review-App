package com.example.reviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1400;
    Timer timer;
    ImageView image;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        new Handler().postDelayed(() -> {
           SharedPreferences sharedPreferences = getSharedPreferences(emailogin.PREFS_NAME,0);
           boolean hasloggedin = sharedPreferences.getBoolean("hasloggedin",false);
           if(hasloggedin)
            {
              Intent homeintent = new Intent(MainActivity.this, Mainwindow.class);
            startActivity(homeintent);
            finish();
            }
           else{
               Intent homeintent = new Intent(MainActivity.this, WelcomeScreen.class);
               startActivity(homeintent);
               finish();
           }
            


        },SPLASH_TIME_OUT);
        image = findViewById(R.id.imageView);
    }

}