package com.example.clear_co2_application.quiz;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.clear_co2_application.R;
import java.io.Serializable;
import java.util.Map;

public class Questionnaire0_Activity extends AppCompatActivity {

    //UI
    private Button start_BUTTON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire0);

        //UI
        start_BUTTON = findViewById(R.id.startBTN);

        //Get the map
        Intent before_intent = getIntent();
        Map<String,Object> user = (Map<String, Object>) before_intent.getSerializableExtra("map");

        start_BUTTON.setOnClickListener(v ->
        {
            //Change page
            Intent intent = new Intent(Questionnaire0_Activity.this, Questionnaire1_Activity.class);
            intent.putExtra("map", (Serializable) user);
            startActivity(intent);
            Animatoo.animateSlideLeft(this);
        });
    }
}