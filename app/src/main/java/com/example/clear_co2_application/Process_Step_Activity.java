package com.example.clear_co2_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Process_Step_Activity extends AppCompatActivity implements Process_Step_Adapter.OnStepClickListener {


    //UI
    private RecyclerView recyclerView_step_list;
    private TextView name;
    private TextView name2;
    private ImageView image;
    private TextView info;
    private FloatingActionButton pop_up_add_BTN;
    private Process_Step_Adapter processStepAdapter;
    //private Toolbar mToolbar;


    //Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();


    //List
    private List<UploadedProduct> stepsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_step);

      /*  mToolbar = findViewById(R.id.top_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

       */

        //UI
        recyclerView_step_list = findViewById(R.id.process_steps_list);
        recyclerView_step_list.setHasFixedSize(true);
        recyclerView_step_list.setLayoutManager(new LinearLayoutManager(Process_Step_Activity.this));

        name = findViewById(R.id.selected_process_name);
        image = findViewById(R.id.step_image_upload);
        info = findViewById(R.id.process_info);
        pop_up_add_BTN = findViewById(R.id.add_step_popUp);

        //Extras
        Bundle extras = getIntent().getExtras();

        name.setText(extras.getString("proName"));
        info.setText(extras.getString("proInfo"));
        Picasso.get().load(extras.getString("proUrl")).fit()
                .centerCrop()
                .into(image);

        //Firebase
        mDatabaseRef = FirebaseDatabase.getInstance("https://clear-co2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Products/" + extras.getString("pName") + "/Processes/" + extras.getString("proName") + "/Steps/");

        //List
        stepsList = new ArrayList<>();

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                stepsList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UploadedProduct uploadedProduct = postSnapshot.getValue(UploadedProduct.class);
                    uploadedProduct.setStepDeleteKey(postSnapshot.getKey());

                    stepsList.add(uploadedProduct);
                }
                processStepAdapter = new Process_Step_Adapter(Process_Step_Activity.this, stepsList);
                recyclerView_step_list.setAdapter(processStepAdapter);

                processStepAdapter.setOnStepClickListener(Process_Step_Activity.this);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        pop_up_add_BTN.setOnClickListener(v ->
        {
            Intent intent = new Intent(Process_Step_Activity.this, Process_Step_PopUp_Activity.class);
            intent.putExtra("proName", extras.getString("proName"));
            intent.putExtra("pName", extras.getString("pName"));
            startActivity(intent);
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDeleteClick(View view, int position) {
        UploadedProduct selectedItem = stepsList.get(position);
        String selectedKey = selectedItem.getStepDeleteKey();

        StorageReference stepRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        stepRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(Process_Step_Activity.this, "Step deleted.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.step_menu, menu);
        return true;
    }

 */
}
