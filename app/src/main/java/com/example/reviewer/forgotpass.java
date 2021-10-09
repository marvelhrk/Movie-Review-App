package com.example.reviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.sdsmdg.tastytoast.TastyToast;

public class forgotpass extends AppCompatActivity {
    TextInputLayout forgetpassword;
    Button Reset;
    FirebaseAuth FAuth;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        forgetpassword = (TextInputLayout) findViewById(R.id.Emailid);
        Reset = (Button) findViewById(R.id.button2);
        dialog = new Dialog(this);

        FAuth = FirebaseAuth.getInstance();
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.checkinfo);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();
               /* final ProgressDialog mDialog = new ProgressDialog(forgotpass.this);
                mDialog.setCancelable(false);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setMessage("Checking info...");
                mDialog.show();*/

                FAuth.sendPasswordResetEmail(forgetpassword.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            TastyToast.makeText(getApplicationContext(), "Reset Password Link sent Succesfully to your Email Account ", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            Intent intent = new Intent(forgotpass.this, mainmenu.class);
                            startActivity(intent);
                            finishAffinity();

                        } else {
                            dialog.dismiss();
                            TastyToast.makeText(getApplicationContext(), task.getException().getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                          //  ReusableCodeForAll.ShowAlert(ChefForgotPassword.this, "Error", task.getException().getMessage());
                        }
                    }
                });
            }
        });

    }
}