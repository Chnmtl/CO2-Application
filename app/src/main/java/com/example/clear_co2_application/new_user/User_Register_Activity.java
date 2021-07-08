package com.example.clear_co2_application.new_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clear_co2_application.HomePageActivity;
import com.example.clear_co2_application.R;
import com.example.clear_co2_application.navigator.HomePage;
import com.example.clear_co2_application.quiz.Questionnaire0_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User_Register_Activity extends AppCompatActivity
{
    //UI
    EditText firstName,lastName,email,company,job,address,city;
    Button continue_BUTTON;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    //User
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        //UI
        firstName = findViewById(R.id.firstNameTextBox);
        lastName = findViewById(R.id.lastNameTextBox);
        email = findViewById(R.id.emailTextBox);
        company = findViewById(R.id.companyTextBox);
        job = findViewById(R.id.jobTextBox);
        address = findViewById(R.id.addressTextBox);
        city = findViewById(R.id.cityTextBox);
        continue_BUTTON = findViewById(R.id.register_continueBTN);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //User
        userID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);

        //Button on click action
        continue_BUTTON.setOnClickListener(v ->
        {
            if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !email.getText().toString().isEmpty())
            {
                String first = firstName.getText().toString();
                String last = lastName.getText().toString();
                String userEmail = email.getText().toString();
                String userCompany = company.getText().toString();
                String userJob = job.getText().toString();
                String userAddress = address.getText().toString();
                String userCity = city.getText().toString();

                Map<String,Object> user = new HashMap<>();
                user.put("firstName",first);
                user.put("lastName",last);
                user.put("emailAddress",userEmail);
                user.put("companyName",userCompany);
                user.put("jobTitle",userJob);
                user.put("address",userAddress);
                user.put("city",userCity);

                Intent intent = new Intent(User_Register_Activity.this, Questionnaire0_Activity.class);
                intent.putExtra("map", (Serializable) user);
                startActivity(intent);

                /*
                documentReference.set(user).addOnCompleteListener(task ->
                {
                    if(task.isSuccessful())
                    {
                        Intent intent = new Intent(getApplicationContext(), Questionnaire0_Activity.class);
                        intent.putExtra("map", (Serializable) user);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(User_Register_Activity.this,"Data not inserted",Toast.LENGTH_SHORT).show();
                    }
                });

                 */
            }
            else
            {
                Toast.makeText(User_Register_Activity.this,"All Fields are Required",Toast.LENGTH_SHORT).show();
            }
        });
    }
}