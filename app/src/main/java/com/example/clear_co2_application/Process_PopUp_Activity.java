package com.example.clear_co2_application;

import androidx.annotation.Nullable;
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

public class Process_PopUp_Activity extends AppCompatActivity {

    //UI
    private Button add_process_BTN;
    private FloatingActionButton add_process_image_BTN;
    private EditText process_name;
    private EditText process_info;
    private ImageView process_image;

    //Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://clear-co2-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference root;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_pop_up);

        //UI
        add_process_BTN = findViewById(R.id.add_process_pop_up_btn);
        process_image = findViewById(R.id.process_img);
        process_name = findViewById(R.id.process_name);
        process_info = findViewById(R.id.info_process);
        add_process_image_BTN = findViewById(R.id.add_process_picture);

        //Pop Up Settings
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .9));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        //Extras
        Bundle extras = getIntent().getExtras();

        //Firebase
        root = db.getReference().child("Users").child(mAuth.getCurrentUser().getUid() + "/Products/" + extras.getString("pName") + "/Processes/");

        //On click listener
        add_process_image_BTN.setOnClickListener(v ->
        {
            ImagePicker.with(Process_PopUp_Activity.this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });

        add_process_BTN.setOnClickListener(v ->
        {
            String processName = process_name.getText().toString();
            String processInfo = process_info.getText().toString();
            if (processName.isEmpty() || processInfo.isEmpty() || imageUri == null) {
                Toast.makeText(Process_PopUp_Activity.this, "You have to add photo and name for the product", Toast.LENGTH_SHORT).show();
            } else {
                uploadDataToFirebase(imageUri, extras.getString("pName"), processName, processInfo);
                //Finish Pop up
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageUri = data.getData();

        Picasso.get().load(imageUri).into(process_image);
    }

    //Uploading image to Firebase Storage
    private void uploadDataToFirebase(Uri imageUri, String productName, String processName, String processInfo) {
        StorageReference fileRef = storageRef.child("users/" + mAuth.getCurrentUser().getUid() + "/Products/" + productName + "/Processes/" + "/" +
                processName + "/" + processName);

        fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {

            Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();

            firebaseUri.addOnSuccessListener(uri -> {
                UploadedProduct upload = new UploadedProduct(processName, uri.toString(), 0, "no value", processInfo);
                root.child(processName).setValue(upload);

            });

            Toast.makeText(Process_PopUp_Activity.this, "Process Uploaded.", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(e ->
        {
            Toast.makeText(Process_PopUp_Activity.this, "Failed", Toast.LENGTH_SHORT).show();

        });
    }
}