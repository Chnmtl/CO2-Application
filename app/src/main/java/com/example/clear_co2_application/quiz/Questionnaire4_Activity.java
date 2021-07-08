package com.example.clear_co2_application.quiz;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.clear_co2_application.R;
import java.io.Serializable;
import java.util.Map;

public class Questionnaire4_Activity extends AppCompatActivity
{
    //UI
    private Button continue_Button;
    private RadioGroup radio_group;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire4);

        //UI
        continue_Button = findViewById(R.id.experience_continue_BUTTON);
        radio_group = findViewById(R.id.why_group);

        //Get the map
        Intent before_intent = getIntent();
        Map<String,Object> user = (Map<String, Object>) before_intent.getSerializableExtra("map");

        //Button listener to next Question
        continue_Button.setOnClickListener(v ->
        {
            //Save users choice Firebase DataBase
            String experience  =((RadioButton)findViewById(radio_group.getCheckedRadioButtonId())).getText().toString();
            user.put("experience",experience);

            //Change page
            Intent intent = new Intent(Questionnaire4_Activity.this, Questionnaire5_Activity.class);
            intent.putExtra("map", (Serializable) user);
            startActivity(intent);
            Animatoo.animateSlideLeft(this);
        });
    }
}