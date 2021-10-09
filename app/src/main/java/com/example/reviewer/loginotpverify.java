package com.example.reviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class loginotpverify extends AppCompatActivity {
    String verificationId;
    FirebaseAuth FAuth;
    Button verify , Resend ;
    TextView txt;
    EditText entercode;
    String phoneno;
    Dialog dialog,dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginotpverify);
        phoneno = getIntent().getStringExtra("Phonenum").trim();

        entercode = (EditText) findViewById(R.id.codeotp);
        txt = (TextView) findViewById(R.id.text);
        Resend = (Button)findViewById(R.id.Resendotp2);
        verify = (Button) findViewById(R.id.Verifyotp);
        FAuth = FirebaseAuth.getInstance();
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
                txt.setText("Resend Code Within "+millisUntilFinished/1000+" Seconds");

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
                        txt.setText("Resend Code Within"+millisUntilFinished/1000+"Seconds");

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

    private void Resendotp(String phonenum) {

        sendverificationcode(phonenum);
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
            Toast.makeText(loginotpverify.this , e.getMessage(),Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(String s , PhoneAuthProvider.ForceResendingToken forceResendingToken){
            super.onCodeSent(s,forceResendingToken);

            verificationId = s;

        }
    };

    private void verifyCode(String code) {

        dialog2.setContentView(R.layout.verifiyingotp);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setCancelable(false);
        dialog2.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId , code);
        signInWithPhone(credential);
    }

    private void signInWithPhone(PhoneAuthCredential credential) {

        FAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            dialog2.dismiss();
                            SharedPreferences sharedPreferences = getSharedPreferences(emailogin.PREFS_NAME,0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("hasloggedin",true);
                            editor.commit();
                            TastyToast.makeText(getApplicationContext(), "Congratulation!\nYou Have Successfully Login", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            startActivity(new Intent(loginotpverify.this,Mainwindow.class));
                            finish();

                        }else{
                            dialog2.dismiss();
                            TastyToast.makeText(getApplicationContext(), task.getException().getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                           // ReusableCodeForAll.ShowAlert(Chefsendotp.this,"Error",task.getException().getMessage());
                        }

                    }
                });

    }

}