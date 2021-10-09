package com.example.reviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class search extends AppCompatActivity {
Button home,search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(search.this, Mainwindow.class);
                startActivity(intent);
                finish();

            }
        });
        search = (Button) findViewById(R.id.search1);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }

}