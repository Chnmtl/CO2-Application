package com.example.clear_co2_application;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.clear_co2_application.new_user.User_Register_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class OTPVerification_Activity extends AppCompatActivity {

    //UI
    private EditText otp_TEXT_BOX;
    private TextView resend_TEXT_VIEW;
    private Button verify_BUTTON;
    private String OTP;

    //FireBase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


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
        firebaseFirestore = FirebaseFirestore.getInstance();


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
                Toast.makeText(OTPVerification_Activity.this,"Please enter the OTP Code",Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void signIn(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task ->
        {
            if (task.isSuccessful())
            {
                DocumentReference docRef = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());

                docRef.get().addOnSuccessListener(documentSnapshot ->
                {
                    if (documentSnapshot.exists())
                    {
                        sendToHomePage();
                    }
                    else
                    {
                        sendToRegister();
                    }

                });

            }
            else
            {
                Toast.makeText(OTPVerification_Activity.this,"Verification Failed",Toast.LENGTH_SHORT).show();

            }

        });

    }

    private void sendToRegister()
    {
        startActivity(new Intent(OTPVerification_Activity.this, User_Register_Activity.class));
        finish();
    }

    private void sendToHomePage()
    {
        startActivity(new Intent(OTPVerification_Activity.this, HomePageActivity.class));
        finish();
    }


}