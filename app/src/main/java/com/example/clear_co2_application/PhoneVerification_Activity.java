package com.example.clear_co2_application;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;


public class PhoneVerification_Activity extends AppCompatActivity {

    //UI
    private EditText prefix_TEXT_BOX, phoneNumber_TEXT_BOX;
    private Button continue_BUTTON;


    //FireBase
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        //UI
        prefix_TEXT_BOX = findViewById(R.id.prefixTextBox);
        phoneNumber_TEXT_BOX = findViewById(R.id.phoneNumberTextBox);
        continue_BUTTON = findViewById(R.id.continueBTN);


        //FireBase
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Button Listener
        continue_BUTTON.setOnClickListener(v ->
        {
            //Taking users data
            String country_code = prefix_TEXT_BOX.getText().toString();
            String number = phoneNumber_TEXT_BOX.getText().toString();
            String full_phone_number = "+" + country_code + "" + number;

            System.out.println("------------------------");
            System.out.println(full_phone_number);
            System.out.println("------------------------");


            if (!country_code.isEmpty() || !number.isEmpty()) {
                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(full_phone_number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(PhoneVerification_Activity.this)
                        .setCallbacks(mCallBacks)
                        .build();

                PhoneAuthProvider.verifyPhoneNumber(options);

            } else {
                //Inform user with a pop up that the data are not completed full
                Toast.makeText(PhoneVerification_Activity.this, "Please enter correctly the phone number...", Toast.LENGTH_SHORT).show();
            }
        });

        //FireBase Call Back
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(PhoneVerification_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(PhoneVerification_Activity.this, "OTP has been Sent", Toast.LENGTH_SHORT).show();


                /*
                new Handler().postDelayed((Runnable) () -> {
                    Intent OTPIntent = new Intent(PhoneVerification_Activity.this, OTPVerification_Activity.class);
                    OTPIntent.putExtra("auth",s);
                    startActivity(OTPIntent);

                },10000);

                 */


                Intent OTPIntent = new Intent(PhoneVerification_Activity.this, OTPVerification_Activity.class);
                OTPIntent.putExtra("auth", s);
                OTPIntent.putExtra("phoneNum", "+" + prefix_TEXT_BOX.getText().toString() + phoneNumber_TEXT_BOX.getText().toString());
                startActivity(OTPIntent);


            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Check if User is already Logged in
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            sendToMain();
        }
    }

    private void sendToMain() {
        //If user is already in send him to log out screen
        Intent mainIntent = new Intent(PhoneVerification_Activity.this, HomePageActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void signIn(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(task ->
        {
            if (task.isSuccessful()) {
                sendToMain();
            } else {
                Toast.makeText(PhoneVerification_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

            }

        });

    }
}