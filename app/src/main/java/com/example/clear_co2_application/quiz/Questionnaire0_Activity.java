package com.example.clear_co2_application.quiz;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.clear_co2_application.OTPVerification_Activity;
import com.example.clear_co2_application.R;


public class Questionnaire0_Activity extends AppCompatActivity {

    private Button start_BUTTON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire0);

        start_BUTTON = findViewById(R.id.startBTN);

        start_BUTTON.setOnClickListener(v ->
        {
            startActivity(new Intent(Questionnaire0_Activity.this, Questionnaire1_Activity.class));
            Animatoo.animateSlideLeft(this);

        });
    }
}