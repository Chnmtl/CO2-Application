package com.example.clear_co2_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Process_Step_PopUp_Activity extends AppCompatActivity
{



    //UI
    private Button add_step_BTN;
    private FloatingActionButton add_photo_BTN;
    private EditText step_name;
    private ImageView step_image;

    //Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://clear-co2-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference root;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();



    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_step_pop_up);

        //UI
        add_step_BTN = findViewById(R.id.add_process_step_pop_up_btn);
        step_image = findViewById(R.id.process_step_img);
        step_name = findViewById(R.id.process_step_name);
        add_photo_BTN = findViewById(R.id.add_process_step_picture);

        //Pop Up Settings
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9), (int)(height*.9));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        //Extras
        Bundle extras = getIntent().getExtras();
        String pName = extras.getString("pName");

        //Firebase
        root = db.getReference().child("Users").child(mAuth.getCurrentUser().getUid()+"/Products/"+ pName + "/Processes/" + extras.getString("proName") + "/Steps/");

        //On click listener
        add_photo_BTN.setOnClickListener(v ->
        {
            ImagePicker.with(Process_Step_PopUp_Activity.this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });

        add_step_BTN.setOnClickListener(v ->
        {
            String stepName = step_name.getText().toString();
            if (stepName.isEmpty() || imageUri == null)
            {
                Toast.makeText(Process_Step_PopUp_Activity.this,"You have to add photo and name for the product",Toast.LENGTH_SHORT).show();
            }
            else
            {
                uploadDataToFirebase(imageUri, pName, extras.getString("proName"), stepName);
                //Finish Pop up
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        imageUri = data.getData();

        Picasso.get().load(imageUri).into(step_image);

    }

    //Uploading image to Firebase Storage
    private void uploadDataToFirebase(Uri imageUri, String productName, String processName, String stepName) {
        StorageReference fileRef = storageRef.child("users/" + mAuth.getCurrentUser().getUid() + "/Products/" + productName + "/Processes/" + processName
                + "/Steps/" + stepName);

        fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {

            Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();

            firebaseUri.addOnSuccessListener(uri -> {
                UploadedProduct upload = new UploadedProduct(stepName, uri.toString(), 0, "no value", "no info");
                root.child(stepName).setValue(upload);
            });

            Toast.makeText(Process_Step_PopUp_Activity.this, "Step Uploaded.", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(e ->
        {
            Toast.makeText(Process_Step_PopUp_Activity.this, "Failed", Toast.LENGTH_SHORT).show();

        });
    }
}