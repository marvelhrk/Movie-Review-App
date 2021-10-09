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
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;

public class registration extends AppCompatActivity {
    TextInputLayout Fname,Lname,Email,Pass,cpass,mobileno;
    Button register, Emaill,email, Phone,cheflogin;
    CountryCodePicker Cpp;
    FirebaseAuth Fauth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname,lname,emailid,password,confpassword,mobile;
    String role="Customer";
    Dialog dialog,dialog2;
    ProgressBar progressBar;
    int a=0,b;
    @Override
    public void onBackPressed() {
        if (a==0) {
            super.onBackPressed();
        } else {
            b=a;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        cheflogin = (Button) findViewById(R.id.phone);
        cheflogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registration.this, phoneverify.class);
                startActivity(intent);
            }
        });

        email = (Button) findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registration.this,emailogin.class);
                startActivity(intent);
            }
        });
        Fname = (TextInputLayout)findViewById(R.id.Firstname);
        Lname = (TextInputLayout)findViewById(R.id.Lastname);
        Email = (TextInputLayout)findViewById(R.id.Emailid);
        Pass = (TextInputLayout)findViewById(R.id.Pwd);
        cpass = (TextInputLayout)findViewById(R.id.Cpass);
        mobileno = (TextInputLayout)findViewById(R.id.Mobileno);


        dialog = new Dialog (this);
        dialog2 = new Dialog (this);

     /*   progressBar = new ProgressBar(this);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setVisibility(View.INVISIBLE);*/


        register = (Button)findViewById(R.id.registersu);
        Emaill = (Button)findViewById(R.id.email);

        Cpp = (CountryCodePicker)findViewById(R.id.CountryCode);


        Fauth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = Fname.getEditText().getText().toString().trim();
                lname = Lname.getEditText().getText().toString().trim();
                emailid = Email.getEditText().getText().toString().trim();
                mobile = mobileno.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();
                confpassword = cpass.getEditText().getText().toString().trim();


                if (isValid()){
                    dialog2.setContentView(R.layout.progress);
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog2.setCancelable(false);
                    dialog2.show();

                /*    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminateDrawable(doubleBounce);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/


                 /*   final ProgressDialog mDialog = new ProgressDialog(registration.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in progress......");
                    mDialog.show();*/

                    Fauth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                                final HashMap<String , String> hashMap = new HashMap<>();
                                hashMap.put("Role",role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        HashMap<String , String> hashMap1 = new HashMap<>();
                                        hashMap1.put("Mobile No",mobile);
                                        hashMap1.put("First Name",fname);
                                        hashMap1.put("Last Name",lname);
                                        hashMap1.put("EmailId",emailid);
                                        hashMap1.put("Password",password);
                                        hashMap1.put("Confirm Password",confpassword);


                                        firebaseDatabase.getInstance().getReference("Customer")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                //mDialog.dismiss();
                                               // progressBar.setVisibility(View.INVISIBLE);

                                                Fauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful())
                                                        {
                                                            dialog2.dismiss();
                                                            opendialog();
                                                            /*AlertDialog.Builder builder = new AlertDialog.Builder(registration.this);
                                                            builder.setMessage( "    Your Registration is almost Complete.\n    Next Step, Verify Your Phone. ");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    dialog.dismiss();
                                                                    String phonenumber = Cpp.getSelectedCountryCodeWithPlus() + mobile;
                                                                    Intent b = new Intent(registration.this,phoneverify.class);
                                                                    b.putExtra("phonenumber",phonenumber);
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
                                                        }
                                                    }
                                                });

                                            }
                                        });

                                    }
                                });
                            }
                            else {
                                dialog2.dismiss();
                                // mDialog.dismiss();
                                TastyToast.makeText(getApplicationContext(), task.getException().getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                // Toast.makeText(emailogin.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
//
            }
        });

    }

    private void opendialog() {
        dialog.setContentView(R.layout.registercomp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
       Button btnok = dialog.findViewById(R.id.lesgo);
       btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String phonenumber = Cpp.getSelectedCountryCodeWithPlus() + mobile;
                Intent b = new Intent(registration.this,phoneverify.class);
                b.putExtra("phonenumber",phonenumber);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                a=0;
                startActivity(b);


            }
        });

    }


    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        Email.setErrorEnabled(false);
        Email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        cpass.setErrorEnabled(false);
        cpass.setError("");


        boolean isValid=false,isValidlname=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobilenum=false;
        if(TextUtils.isEmpty(fname)){
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        }else{
            isValidname = true;
        }
        if(TextUtils.isEmpty(lname)){
            Lname.setErrorEnabled(true);
            Lname.setError("Enter Last Name");
        }else{
            isValidlname = true;
        }
        if(TextUtils.isEmpty(emailid)){
            Email.setErrorEnabled(true);
            Email.setError("Email Is Required");
        }else{
            if(emailid.matches(emailpattern)){
                isValidemail = true;
            }else{
                Email.setErrorEnabled(true);
                Email.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(password)){
            Pass.setErrorEnabled(true);
            Pass.setError("Enter Password");
        }else{
            if(password.length()<8){
                Pass.setErrorEnabled(true);
                Pass.setError("Password is Weak.\nA Strong Password must contain Alphabets, Numbers and Special Characters.");
            }else{
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(confpassword)){
            cpass.setErrorEnabled(true);
            cpass.setError("Enter Password Again");
        }else{
            if(!password.equals(confpassword)){
                cpass.setErrorEnabled(true);
                cpass.setError("Password Dosen't Match");
            }else{
                isValidconfpassword = true;
            }
        }
        if(TextUtils.isEmpty(mobile)){
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile Number Is Required");
        }else{
            if(mobile.length()<10){
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid Mobile Number");
            }
            else if(mobile.length()>10){
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid Mobile Number");
            }
            else{
                isValidmobilenum = true;
            }
        }


        isValid = (  isValidconfpassword && isValidpassword  && isValidemail && isValidmobilenum && isValidname  && isValidlname) ? true : false;
        return isValid;


    }
}