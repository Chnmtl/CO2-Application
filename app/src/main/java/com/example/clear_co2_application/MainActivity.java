package com.example.clear_co2_application;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button logInButton = findViewById(R.id.button0);
        final Button registrateButton = findViewById(R.id.registrateButton);

        logInButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,LogInActivity.class);
            startActivity(intent);
        });

        registrateButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,CreatAccountActivity.class);
            startActivity(intent);
        });

    }


}