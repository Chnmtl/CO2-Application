package com.example.clear_co2_application.quiz;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.clear_co2_application.R;

import java.util.ArrayList;

public class Questionnaire4_Activity extends AppCompatActivity
{
    //UI
    private Button continue_Button;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire4);

        //UI
        continue_Button = findViewById(R.id.experience_continue_BUTTON);

        //Button listener to next Question
        continue_Button.setOnClickListener(v ->
        {
            startActivity(new Intent(Questionnaire4_Activity.this, Questionnaire5_Activity.class));
            Animatoo.animateSlideLeft(this);
        });
    }
}