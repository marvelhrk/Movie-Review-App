package com.example.reviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainmenu extends AppCompatActivity {

    private Button SignwithEmail,Signup,SignwithPhone;


    @Override
    public void onBackPressed()
    {
        finishAffinity();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);


        SignwithEmail = (Button) findViewById(R.id.SignwithEmail);
        SignwithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainmenu.this,emailogin.class);
                startActivity(intent);
            }
        });
        Signup = (Button)findViewById(R.id.Signup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainmenu.this,registration.class);
                startActivity(intent);
            }
        });
        SignwithPhone = (Button) findViewById(R.id.SignwithPhone);
        SignwithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainmenu.this,phonelogin.class);
                startActivity(intent);
            }
        });
    }
}