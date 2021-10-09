package com.example.reviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mailverify extends AppCompatActivity {
    Button email, chefregis;
    @Override
    public void onBackPressed() {
        int a = 1;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailverify);

        email = (Button) findViewById(R.id.Verify);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mailverify.this, mainmenu.class);
               startActivity(intent);

                Intent launchIntent = null;
                try{
                    launchIntent = new Intent(Intent.ACTION_MAIN);
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    launchIntent.addCategory(Intent.CATEGORY_APP_EMAIL);
                    startActivity(launchIntent);
                } catch (Exception ignored) {}

                if(launchIntent == null){
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "com.google.android.gm")));
                } else {
                    startActivity(launchIntent);
                }
            }
        });
    }
}