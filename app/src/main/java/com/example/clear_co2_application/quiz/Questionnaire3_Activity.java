package com.example.clear_co2_application.quiz;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.clear_co2_application.R;

import java.util.ArrayList;

public class Questionnaire3_Activity extends AppCompatActivity
{

    //UI
    private Button continue_Button;
    private Spinner years_Spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire3);

        //UI
        continue_Button = findViewById(R.id.year_continue_BUTTON);
        years_Spinner = findViewById(R.id.years_SPINNER);

        //Creat List with last 100 years
        ArrayList<Integer> yearsList = new ArrayList<>();
        for(int i=1921; i<2022; i ++)
            yearsList.add(i);

        //Adapter for spinner
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,yearsList);
        years_Spinner.setAdapter(adapter);

        //Button listener to next Question
        continue_Button.setOnClickListener(v ->
        {
            startActivity(new Intent(Questionnaire3_Activity.this, Questionnaire4_Activity.class));
            Animatoo.animateSlideLeft(this);
        });
    }
}