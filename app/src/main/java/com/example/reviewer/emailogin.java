package com.example.reviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

public class emailogin extends AppCompatActivity {
    public static String PREFS_NAME ="MyPrefsFile";
    Button chefregis,cheflogin,createnew;
    TextInputLayout email, pass;
    Button Signin;
    TextView Forgotpassword;
    SharedPreferences sp;
    FirebaseAuth Fauth;
    String emailid, pwd;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailogin);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef = FirebaseDatabase.getInstance().getReference().child("Customer");


        try {

            email = (TextInputLayout) findViewById(R.id.Lemail);
            pass = (TextInputLayout) findViewById(R.id.Lpassword);
            Signin = (Button) findViewById(R.id.button4);
            Forgotpassword = (Button) findViewById(R.id.forgotpass);
            dialog = new Dialog(this);




            Fauth = FirebaseAuth.getInstance();

            Signin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    emailid = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();

                    emailid = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();

                    if (isValid()) {
                        dialog.setContentView(R.layout.signingin);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCancelable(false);
                        dialog.show();
                       /* final ProgressDialog mDialog = new ProgressDialog(emailogin.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Signing In Please Wait.......");
                        mDialog.show();*/

                        Fauth.signInWithEmailAndPassword(emailid, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    dialog.dismiss();
                                   // mDialog.dismiss();

                                    if (Fauth.getCurrentUser().isEmailVerified()) {
                                        dialog.dismiss();
                                       // mDialog.dismiss();
                                        SharedPreferences sharedPreferences = getSharedPreferences(emailogin.PREFS_NAME,0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("hasloggedin",true);
                                        editor.commit();
                                        TastyToast.makeText(getApplicationContext(), "Congratulation!\nYou Have Successfully Login", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                       // Toast.makeText(emailogin.this, "      Congratulation! \nYou Have Successfully Login", Toast.LENGTH_SHORT).show();
                                        Intent Z = new Intent(emailogin.this, Mainwindow.class);
                                        startActivity(Z);
                                        finish();

                                    } else {
                                        TastyToast.makeText(getApplicationContext(), "Verification Failed.\nYou Have Not Verified Account on Your Email.", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                      //  Toast.makeText(emailogin.this, "      Verification Failed. \nYou Have Not Verified Account on Your Email Address.",Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    dialog.dismiss();
                                   // mDialog.dismiss();
                                    TastyToast.makeText(getApplicationContext(), task.getException().getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                   // Toast.makeText(emailogin.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            });
            createnew = (Button) findViewById(R.id.createnew);
            createnew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(emailogin.this,registration.class);
                    startActivity(intent);
                }
            });
            cheflogin = (Button) findViewById(R.id.btnphone);
            cheflogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(emailogin.this, phonelogin.class);
                    startActivity(intent);
                }
            });
            Forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(emailogin.this, forgotpass.class));
                    finish();
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {

        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isvalid = false, isvalidemail = false, isvalidpassword = false;
        if (TextUtils.isEmpty(emailid)) {
            email.setErrorEnabled(true);
            email.setError("Email is required");
        }
        else {
            if (emailid.matches(emailpattern)) {
                isvalidemail = true;
            } else {
                email.setErrorEnabled(true);
                email.setError("Invalid Email Address");
            }
        }
        if (TextUtils.isEmpty(pwd)) {

            pass.setErrorEnabled(true);
            pass.setError("Password is Required");
        } else {
            isvalidpassword = true;
        }
        isvalid = (isvalidemail && isvalidpassword) ? true : false;
        return isvalid;
    }

}