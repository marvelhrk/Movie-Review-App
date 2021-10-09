package com.example.reviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.concurrent.TimeUnit;

public class phoneverify extends AppCompatActivity {
    String verificationId;
    FirebaseAuth FAuth;
    Button verify , Resend ;
    TextView txt;
    EditText entercode;
    String phoneno;
    Dialog dialog,dialog2;
    @Override
    public void onBackPressed() {
        int a=1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverify);
        phoneno = getIntent().getStringExtra("phonenumber").trim();

        entercode = (EditText) findViewById(R.id.code);
        txt = (TextView) findViewById(R.id.text);
        Resend = (Button)findViewById(R.id.Resendotp);
        verify = (Button) findViewById(R.id.Verify);
        FAuth = FirebaseAuth.getInstance();
        dialog = new Dialog(this);
        dialog2 = new Dialog(this);
        Resend.setVisibility(View.INVISIBLE);
        txt.setVisibility(View.INVISIBLE);

        sendverificationcode(phoneno);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = entercode.getText().toString().trim();
                Resend.setVisibility(View.INVISIBLE);

                if (code.isEmpty() && code.length()<6){
                    entercode.setError("Enter code");
                    entercode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

                txt.setVisibility(View.VISIBLE);
                txt.setText("Resend Code After "+millisUntilFinished/1000+" Seconds");

            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish() {
                Resend.setVisibility(View.VISIBLE);
                txt.setVisibility(View.INVISIBLE);

            }
        }.start();

        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Resend.setVisibility(View.INVISIBLE);
                Resendotp(phoneno);

                new CountDownTimer(60000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                        txt.setVisibility(View.VISIBLE);
                        txt.setText("Resend Code After "+millisUntilFinished/1000+" seconds");

                    }

                    /**
                     * Callback fired when the time is up.
                     */
                    @Override
                    public void onFinish() {
                        Resend.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.INVISIBLE);

                    }
                }.start();
            }
        });

    }
    private void sendverificationcode(String number) {

        PhoneAuthProvider.verifyPhoneNumber(
                PhoneAuthOptions
                        .newBuilder(FirebaseAuth.getInstance())
                        .setActivity(this)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(mcallBack)
                        .build()
        );
    }

    private void Resendotp(String phonenum) {

        sendverificationcode(phonenum);
    }



    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                entercode.setText(code);  // Auto Verification
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(phoneverify.this , e.getMessage(),Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(String s , PhoneAuthProvider.ForceResendingToken forceResendingToken){
            super.onCodeSent(s,forceResendingToken);

            verificationId = s;

        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId , code);
        linkCredential(credential);
        dialog2.setContentView(R.layout.verifiyingotp);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setCancelable(false);
        dialog2.show();
       /* final ProgressDialog mDialog = new ProgressDialog(phoneverify.this);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage("Verifying Code......");
        mDialog.show();*/
    }

    private void linkCredential(PhoneAuthCredential credential) {

        FAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(phoneverify.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {dialog2.dismiss();
                            opendialog();

                            /*AlertDialog.Builder builder = new AlertDialog.Builder(phoneverify.this);
                            builder.setMessage("\n    Your Number is Verified. ");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    Intent b = new Intent(phoneverify.this,mailverify.class);
                                    startActivity(b);
                                }
                            });
                            AlertDialog Alert = builder.create();
                            Alert.show();*/
                        }
                        else{
                            dialog2.dismiss();
                            // mDialog.dismiss();
                            TastyToast.makeText(getApplicationContext(), task.getException().getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            Intent b = new Intent(phoneverify.this,mainmenu.class);
                        }
                    }
                });

    }
    private void opendialog() {
        dialog.setContentView(R.layout.otpverified);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        Button btnok = dialog.findViewById(R.id.lesgotp);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent b = new Intent(phoneverify.this,mailverify.class);
                startActivity(b);

            }
        });

    }

}