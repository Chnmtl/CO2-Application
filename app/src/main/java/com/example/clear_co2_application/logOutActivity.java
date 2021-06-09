package com.example.clear_co2_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logOutActivity extends AppCompatActivity
{
    //UI
    private Button logOut_BUTTON;

    //FireBase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        //UI
        logOut_BUTTON = findViewById(R.id.logOutBTN);

        //FireBase
        mAuth = FirebaseAuth.getInstance();

        //Button Listener
        logOut_BUTTON.setOnClickListener(v ->
        {

            //On click Sign Out and Go to Log in Activity
           mAuth.signOut();
           startActivity(new Intent(logOutActivity.this , PhoneVerification.class));
           finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Check if User is Logged In
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            startActivity(new Intent(logOutActivity.this , PhoneVerification.class));
            finish();
        }
    }
}