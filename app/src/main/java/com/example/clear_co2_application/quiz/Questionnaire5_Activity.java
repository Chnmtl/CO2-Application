package com.example.clear_co2_application.quiz;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.clear_co2_application.HomePageActivity;
import com.example.clear_co2_application.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Map;

public class Questionnaire5_Activity extends AppCompatActivity
{
    //UI
    private Button continue_Button;
    private RadioGroup radio_group;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire5);

        //UI
        continue_Button = findViewById(R.id.last_continue_BUTTON);
        radio_group = findViewById(R.id.why_group);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //User
        userID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);

        //Get the map
        Intent before_intent = getIntent();
        Map<String,Object> user = (Map<String, Object>) before_intent.getSerializableExtra("map");

        //Button listener to next Question
        continue_Button.setOnClickListener(v ->
        {
            //Save users choice Firebase DataBase
            String why  =((RadioButton)findViewById(radio_group.getCheckedRadioButtonId())).getText().toString();
            user.put("why",why);

            documentReference.set(user).addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    //Change page
                    startActivity(new Intent(Questionnaire5_Activity.this, HomePageActivity.class));
                    Animatoo.animateSlideLeft(this);
                }
            });
        });
    }
}