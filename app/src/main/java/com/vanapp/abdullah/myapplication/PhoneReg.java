package com.vanapp.abdullah.myapplication;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.CountryCodePicker;
import com.vanapp.abdullah.myapplication.Van.Driver_Reg;
import com.vanapp.abdullah.myapplication.Van.Parent_Registration;

import java.util.concurrent.TimeUnit;

public class PhoneReg extends AppCompatActivity {

    Button tim;
    EditText otp;
    Button btn_send, btn_verify;
    String number, verify_code;
    FirebaseAuth firebaseAuth;
    String mob_regex = "[0-9]{10}", user;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    CountryCodePicker ccp;
    AppCompatEditText edtPhoneNumber;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_reg);



        user = getIntent().getExtras().getString("user");


        ccp = findViewById(R.id.ccp);
        edtPhoneNumber = findViewById(R.id.phone_number_edt);
       // ccp.registerPhoneNumberTextView(edtPhoneNumber);

        otp = findViewById(R.id.otp);
        btn_send = findViewById(R.id.btn_send);
        btn_verify = findViewById(R.id.btn_verify);
        firebaseAuth = FirebaseAuth.getInstance();

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
               // Toast.makeText(PhoneReg.this, e+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verify_code = s;
                Toast.makeText(PhoneReg.this, "Code Sent...", Toast.LENGTH_SHORT).show();

            }
        };



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number = "+" + ccp.getFullNumber() + edtPhoneNumber.getText().toString();
                //Toast.makeText(PhoneReg.this, number, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(PhoneReg.this,PhoneVerify.class);
                intent.putExtra("user",user);
               // intent.putExtra("verify", verify_code);
                intent.putExtra("number", number);
                startActivity(intent);
//                countDownTimer.start();

            }
        });





        edtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (edtPhoneNumber.getText().toString().matches(mob_regex)) {
                    number = edtPhoneNumber.getText().toString();

                } else {
                    edtPhoneNumber.setError("Invalid Number");
                }
            }
        });


    }




}

