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
import java.util.Collections;
import java.util.Locale;

public class Questionnaire1_Activity extends AppCompatActivity {


    //UI
    private ListView countriesList;
    private Button continue_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire1);

        //UI
        countriesList = findViewById(R.id.countries_LIST);
        continue_Button = findViewById(R.id.country_continue_BUTTON);


        //Create Countries List
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);



        //Creat Adapter for the list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        countriesList.setAdapter(arrayAdapter);


        //Button listener to next Question
        continue_Button.setOnClickListener(v ->
        {
            startActivity(new Intent(Questionnaire1_Activity.this, Questionnaire2_Activity.class));
            Animatoo.animateSlideLeft(this);
        });
    }




}