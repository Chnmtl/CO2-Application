package com.example.clear_co2_application;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button registrateButton = findViewById(R.id.creatAccountButton);


        registrateButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,CreatAccountActivity.class);
            startActivity(intent);
        });

    }
}