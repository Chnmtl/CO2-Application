package com.example.clear_co2_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerification extends AppCompatActivity {

    //UI
    private EditText otp_TEXT_BOX;
    private TextView resend_TEXT_VIEW;
    private Button verify_BUTTON;
    private String OTP;

    //FireBase
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);

        //UI
        resend_TEXT_VIEW = findViewById(R.id.resendTEXT);
        otp_TEXT_BOX = findViewById(R.id.otpTextBox);
        verify_BUTTON = findViewById(R.id.verifyBTN);

        //Data from Intent
        OTP = getIntent().getStringExtra("auth");

        //FireBase
        firebaseAuth = FirebaseAuth.getInstance();

        //Button Listener
        verify_BUTTON.setOnClickListener(v ->
        {
            String verification_code = otp_TEXT_BOX.getText().toString();

            if (!verification_code.isEmpty())
            {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP,verification_code);
                signIn(credential);

            }
            else
            {
                Toast.makeText(OTPVerification.this,"Please enter the OTP Code",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currenUser = firebaseAuth.getCurrentUser();

        if (currenUser != null)
        {
            sendToMain();
        }
    }

    private void signIn(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task ->
        {
            if (task.isSuccessful())
            {
                sendToMain();
            }
            else
            {
                Toast.makeText(OTPVerification.this,"Verification Failed",Toast.LENGTH_SHORT).show();

            }

        });

    }

    private void sendToMain()
    {
        startActivity(new Intent(OTPVerification.this,logOutActivity.class));
        finish();
    }


}