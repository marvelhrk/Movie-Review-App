package com.example.reviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.hbb20.CountryCodePicker;

public class phonelogin extends AppCompatActivity {
    Button chefregis,cheflogin;
    TextInputLayout num;
    Button sendotp,signinemail;
    TextView signup;
    CountryCodePicker cpp;
    FirebaseAuth Fauth;
    String number;
    PhoneAuthCredential credential;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelogin);

     chefregis = (Button)findViewById(R.id.acsignup);
        chefregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(phonelogin.this,registration.class);
                startActivity(intent);
                finish();
            }
        });


        cheflogin = (Button)findViewById(R.id.btnEmail);
        cheflogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(phonelogin.this,emailogin.class);
                startActivity(intent);
                finish();
            }
        });

        num = (TextInputLayout)findViewById(R.id.number);
        sendotp = (Button)findViewById(R.id.otp);
        cpp=(CountryCodePicker)findViewById(R.id.CountryCode);

        Fauth = FirebaseAuth.getInstance();
        dialog = new Dialog(this);


       sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.checkinfo);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();
                number = num.getEditText().getText().toString().trim();
                String Phonenum = cpp.getSelectedCountryCodeWithPlus() + number;

                if (number.isEmpty()){
                    dialog.dismiss();
                    num.setError("Cannot be Empty");
                    num.requestFocus();
                    return;
                }
                else if(number.length()<10 || number.length()>10 ){
                    dialog.dismiss();
                    num.setError("Enter Valid Number");
                    num.requestFocus();
                    return;
                }
                else

                   {
                       dialog.dismiss();
                    Intent b = new Intent(phonelogin.this, loginotpverify.class);
                    b.putExtra("Phonenum", Phonenum);
                    startActivity(b);
                    finish();
                }
            }
        });
    }
}