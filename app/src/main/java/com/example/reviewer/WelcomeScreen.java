package com.example.reviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {

    Button getstarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        getstarted = (Button) findViewById(R.id.getstarted);
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeScreen.this, mainmenu.class);
                startActivity(intent);
            }
        });
    }
}