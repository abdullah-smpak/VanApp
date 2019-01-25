package com.vanapp.abdullah.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.vanapp.abdullah.myapplication.Van.Driver_Reg;
import com.vanapp.abdullah.myapplication.Van.Parent_Registration;

import java.util.concurrent.TimeUnit;

public class PhoneVerify extends AppCompatActivity {
    EditText otp;
    Button btn_verify;
    FirebaseAuth firebaseAuth;
    String verify_code,number,user;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);

        user = getIntent().getExtras().getString("user");
       // verify_code = getIntent().getExtras().getString("verify");
        number = getIntent().getExtras().getString("number");
        firebaseAuth = FirebaseAuth.getInstance();

        btn_verify = findViewById(R.id.btn_verify);
        otp = findViewById(R.id.otp);

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
                Toast.makeText(PhoneVerify.this, "Code Sent...", Toast.LENGTH_SHORT).show();

            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 30, TimeUnit.SECONDS, PhoneVerify.this, mCallback);




        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ver = otp.getText().toString();

                try{
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verify_code, ver);
                    signInWithPhoneAuthCredential(credential);
                    btn_verify.setEnabled(false);
                }
                catch (Exception e)
                {
                    Toast.makeText(PhoneVerify.this, e+"", Toast.LENGTH_SHORT).show();
                }

            }


        });


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            if (user.equals("parent")) {

                                Intent intent = new Intent(PhoneVerify.this, Parent_Registration.class);
                                intent.putExtra("mob_no", number);
                                startActivity(intent);
                                finish();
                            } else if (user.equals("driver")) {

                                Intent intent = new Intent(PhoneVerify.this, Driver_Reg.class);
                                intent.putExtra("mob_no", number);
                                startActivity(intent);
                                finish();
                            } else if (user.equals("driver_for")) {

                                Intent intent = new Intent(PhoneVerify.this, Forget_Password.class);
                                intent.putExtra("mob_no", number);
                                intent.putExtra("user", "d");
                                startActivity(intent);
                                finish();
                            } else if (user.equals("parent_for")) {

                                Intent intent = new Intent(PhoneVerify.this, Forget_Password.class);
                                intent.putExtra("mob_no", number);
                                intent.putExtra("user", "p");
                                startActivity(intent);
                                finish();
                            }

                        } else {

                            Toast.makeText(PhoneVerify.this, "Wrong Verification Code, Try Again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}




