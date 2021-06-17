package com.example.clear_co2_application.quiz;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.clear_co2_application.R;

import java.util.ArrayList;

public class Questionnaire5_Activity extends AppCompatActivity
{
    //UI
    private Button continue_Button;
    private ListView needs_List;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire5);

        //UI
        continue_Button = findViewById(R.id.last_continue_BUTTON);
        needs_List = findViewById(R.id.needs_LIST);

        //ArrayList with lists items
        ArrayList<String> needs_Array = new ArrayList<>();
        needs_Array.add("Optimize my CSR strategies for carbon reduction and offsetting");
        needs_Array.add("Optimize my supply chain and slave energy costs");
        needs_Array.add("Report my carbon footprint for compliance to regulations(EU ETS,..)");

        //Adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,needs_Array);
        needs_List.setAdapter(adapter);


        //Button listener to next Question
        continue_Button.setOnClickListener(v ->
        {
            Animatoo.animateSlideLeft(this);
        });


    }
}