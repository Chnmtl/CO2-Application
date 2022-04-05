package com.example.clear_co2_application;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Process_Activity extends AppCompatActivity implements Process_Adapter.RecyclerViewProcessListener, Process_Adapter.ProcessViewDeleteListener {

    private RecyclerView recyclerView_process_list;
    private FloatingActionButton add_processBTN;
    private Process_Adapter processAdapter;
    private ProgressBar progressCircle;

    private TextView name;
    private ImageView image;
    private TextView info;
    // private Toolbar mToolbar;

    //Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseStorage delStorage = FirebaseStorage.getInstance();
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseRef;


    //List
    private List<UploadedProduct> processlist;

    private Process_Adapter.RecyclerViewProcessListener listener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_process);

        // Toolbar
       /* mToolbar = findViewById(R.id.top_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        */

        //UI
        recyclerView_process_list = findViewById(R.id.process_list);
        recyclerView_process_list.setHasFixedSize(true);
        recyclerView_process_list.setLayoutManager(new LinearLayoutManager(Process_Activity.this));

        name = findViewById(R.id.selected_process_name);
        image = findViewById(R.id.process_image_upload);
        info = findViewById(R.id.product_info);

        add_processBTN = findViewById(R.id.add_step_pop_up);
        progressCircle = findViewById(R.id.progress_circle);

        //List
        processlist = new ArrayList<>();

        //Extras
        Bundle extras = getIntent().getExtras();

        name.setText(extras.getString("pName"));
        info.setText(extras.getString("pInfo"));
        Picasso.get().load(extras.getString("pUrl")).fit()
                .centerCrop()
                .into(image);

        // Firebase

        mDatabaseRef = FirebaseDatabase.getInstance("https://clear-co2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Products/" + extras.getString("pName") + "/Processes/");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                processlist.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UploadedProduct uploadedProduct = postSnapshot.getValue(UploadedProduct.class);
                    uploadedProduct.setProdDeleteKey(postSnapshot.getKey());
                    processlist.add(uploadedProduct);
                }
                setOnClickListener();
                processAdapter = new Process_Adapter(Process_Activity.this, processlist, listener);
                recyclerView_process_list.setAdapter(processAdapter);
                processAdapter.setProcessViewDeleteListener(Process_Activity.this);

                //processAdapter.setOnStepClickListener(Process_Activity.this);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        add_processBTN.setOnClickListener(v ->
        {
            Intent intent = new Intent(Process_Activity.this, Process_PopUp_Activity.class);
            intent.putExtra("pName", extras.getString("pName"));
            startActivity(intent);
        });
    }

    public void setOnClickListener() {
        listener = (v, position) ->
        {
            // Pass parameters to next activity
            Intent intent = new Intent(Process_Activity.this, Process_Step_Activity.class);
            intent.putExtra("proName", processlist.get(position).getName());
            intent.putExtra("proUrl", processlist.get(position).getImageUrl());
            intent.putExtra("proInfo", processlist.get(position).getmInfo());

            Bundle extras = getIntent().getExtras();
            intent.putExtra("pName", extras.getString("pName"));
            startActivity(intent);
        };
    }

    @Override
    public void onDeleteClick(View view, int position) {
        UploadedProduct selectedItem = processlist.get(position);
        String selectedProdKey = selectedItem.getProdDeleteKey();

        StorageReference pRef = delStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        pRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void unused) {
                mDatabaseRef.child(selectedProdKey).removeValue();
                Toast.makeText(Process_Activity.this, "Process deleted.", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.product_menu, menu);
        return true;
    }

     */

    @Override
    public void onClick(View v, int position) {

    }

    // Sorting list items. But something is not working properly
   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.aToZ:
                Collections.sort(processlist, UploadedProduct.AZComparator);
                Toast.makeText(Process_Activity.this, "Az", Toast.LENGTH_SHORT).show();
                processAdapter.notifyDataSetChanged();

            case R.id.zToA:
                Collections.sort(processlist, Collections.reverseOrder(UploadedProduct.AZComparator));
                Toast.makeText(Process_Activity.this, "Za", Toast.LENGTH_SHORT).show();
                processAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    */
}
