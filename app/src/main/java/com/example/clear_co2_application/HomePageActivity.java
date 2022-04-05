package com.example.clear_co2_application;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageActivity extends AppCompatActivity
{
    //UI
    private BottomNavigationView navigationView;
    private NavController controller;
  //  private Toolbar mToolbar;

    //FireBase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //FireBase
        mAuth = FirebaseAuth.getInstance();

        //UI
        navigationView = findViewById(R.id.menu);
        controller = Navigation.findNavController(this,R.id.fragment);

        //Connection NavBar->Fragment
        NavigationUI.setupWithNavController(navigationView, controller);

        // Toolbar
       /* mToolbar = findViewById(R.id.top_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        */
    }
/*
    // Override toolbar to add different options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

 */

    @Override
    protected void onStart()
    {
        super.onStart();

        //Check if User is Logged In
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            startActivity(new Intent(HomePageActivity.this , PhoneVerification_Activity.class));
            finish();
        }
    }
}