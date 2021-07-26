package com.example.clear_co2_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class ProductProfileActivity extends AppCompatActivity {
    //UI
    private TextView name;
    private ImageView image;
    private FloatingActionButton pop_up_add_BTN;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productprofile);

        //UI
        name = findViewById(R.id.selected_product_name);
        image = findViewById(R.id.single_image_upload);
        pop_up_add_BTN = findViewById(R.id.add_step_popUp);

        Bundle extras = getIntent().getExtras();

        name.setText(extras.getString("pName"));
        Picasso.get().load(extras.getString("pUrl")).fit()
                .centerCrop()
                .into(image);


        pop_up_add_BTN.setOnClickListener(v ->
    {
        Intent intent = new Intent(ProductProfileActivity.this, ProcessPopUp_Activity.class);
        startActivity(intent);

    });



    }
}
