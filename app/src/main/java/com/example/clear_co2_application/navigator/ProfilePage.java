package com.example.clear_co2_application.navigator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clear_co2_application.PhoneVerification_Activity;
import com.example.clear_co2_application.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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


    //FireBase
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

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

        //FireBase
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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

        return view;
    }


}