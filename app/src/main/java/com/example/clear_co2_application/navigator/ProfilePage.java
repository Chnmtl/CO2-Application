package com.example.clear_co2_application.navigator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.clear_co2_application.PhoneVerification_Activity;
import com.example.clear_co2_application.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilePage.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilePage newInstance(String param1, String param2) {
        ProfilePage fragment = new ProfilePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    //UI
    private Button logOut;
    private TextView pName,pMail,pCompany,pPhoneNumber,pJobTitle,pAddress,pCity;
    private CircleImageView profileImage;
    private ImageButton uploadPhoto;


    //FireBase
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private StorageReference storageRef;
    private StorageReference profileRef;

    //ProfileImage
    private Uri imageUri;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_page, container, false);

        //UI
        pName = view.findViewById(R.id.name);
        pMail = view.findViewById(R.id.mail);
        pCompany = view.findViewById(R.id.company);
        pPhoneNumber = view.findViewById(R.id.phone_number);
        pJobTitle = view.findViewById(R.id.job_title);
        pAddress = view.findViewById(R.id.address);
        pCity = view.findViewById(R.id.city);
        logOut = view.findViewById(R.id.logOut_BUTTON);
        profileImage = view.findViewById(R.id.profile_image);
        uploadPhoto = view.findViewById(R.id.profile_change);

        //FireBase
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        profileRef = storageRef.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profileImage));



        DocumentReference documentReference = fStore.collection("users").document(mAuth.getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(documentSnapshot ->
        {
            String fullName = documentSnapshot.getString("firstName") +" "+documentSnapshot.getString("lastName");
            pName.setText(fullName);
            pMail.setText(documentSnapshot.getString("emailAddress"));
            pCompany.setText(documentSnapshot.getString("companyName"));
            pPhoneNumber.setText(mAuth.getCurrentUser().getPhoneNumber());
            pJobTitle.setText(documentSnapshot.getString("jobTitle"));
            pAddress.setText(documentSnapshot.getString("address"));
            pCity.setText(documentSnapshot.getString("city"));;




        });

        //Button Listener
        logOut.setOnClickListener(v ->
        {

            //On click Sign Out and Go to Log in Activity
            mAuth.signOut();
            startActivity(new Intent(getActivity() , PhoneVerification_Activity.class));


        });

        uploadPhoto.setOnClickListener(v ->
        {
            //Open Gallery
            Intent open_gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(open_gallery,1000);



        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                imageUri = data.getData();
                //profileImage.setImageURI(imageUri);
                
                uploadImageToFirebase(imageUri);
            }
        }


    }

    //Uploading image to Firebase Storage
    private void uploadImageToFirebase(Uri imageUri)
    {
        StorageReference fileRef = storageRef.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");

        fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
        {
            Toast.makeText(getActivity(), "Image Uploaded.", Toast.LENGTH_SHORT).show();
            fileRef.getDownloadUrl().addOnSuccessListener(uri ->
            {
                Picasso.get().load(uri).into(profileImage);

            });

        }).addOnFailureListener(e ->
        {
            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

        });
    }



}