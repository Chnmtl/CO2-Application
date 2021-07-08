package com.example.clear_co2_application.quiz;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.clear_co2_application.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

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
        for (Locale locale : locales)
        {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country))
            {
                countries.add(country);
            }
        }
        Collections.sort(countries);

        //Get the map
        Intent before_intent = getIntent();
        Map<String,Object> user = (Map<String, Object>) before_intent.getSerializableExtra("map");



        //Create Adapter for the list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        countriesList.setAdapter(arrayAdapter);

        countriesList.setOnItemClickListener((parent, view, position, id) ->
        {
            //Save users choice Firebase DataBase
            String country = arrayAdapter.getItem(position);
            user.put("country",country);

            //Change page
            Intent intent = new Intent(Questionnaire1_Activity.this, Questionnaire2_Activity.class);
            intent.putExtra("map", (Serializable) user);
            startActivity(intent);
            Animatoo.animateSlideLeft(this);
        });

    }
}