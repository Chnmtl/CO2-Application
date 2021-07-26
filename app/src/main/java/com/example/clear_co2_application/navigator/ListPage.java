package com.example.clear_co2_application.navigator;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clear_co2_application.ImageAdapter;
import com.example.clear_co2_application.PopUp_Activity;
import com.example.clear_co2_application.ProductProfileActivity;
import com.example.clear_co2_application.R;
import com.example.clear_co2_application.UploadedProduct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPage extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ListPage newInstance(String param1, String param2) {
        ListPage fragment = new ListPage();
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
    private RecyclerView recyclerView_products_list;
    private Button addProductBTN;
    private ImageAdapter imageAdapter;
    private ProgressBar progressCircle;

    //Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mRef = FirebaseDatabase.getInstance("https://clear-co2-default-rtdb.europe-west1.firebasedatabase.app/")
                                                     .getReference("Users/"+mAuth.getCurrentUser().getUid()+"/Products/");
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    //List
    private List<UploadedProduct> list;

    private ImageAdapter.RecyclerViewClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_page, container, false);

        //UI
        recyclerView_products_list = view.findViewById(R.id.products_list);
        recyclerView_products_list.setHasFixedSize(true);
        recyclerView_products_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        addProductBTN = view.findViewById(R.id.new_product_button);
        progressCircle = view.findViewById(R.id.progress_circle);

        //List
        list = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                list.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    UploadedProduct uploadedProduct = postSnapshot.getValue(UploadedProduct.class);
                    list.add(uploadedProduct);
                }
                setOnClickListener();
                imageAdapter = new ImageAdapter(getActivity(),list,listener);
                recyclerView_products_list.setAdapter(imageAdapter);
                progressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                progressCircle.setVisibility(View.INVISIBLE);

            }
        });


        addProductBTN.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), PopUp_Activity.class);
            startActivity(intent);

        });
        return view;
    }

    public void setOnClickListener()
    {
       listener = (v, position) ->
       {
           Intent intent = new Intent(getActivity(), ProductProfileActivity.class);
           intent.putExtra("pName",list.get(position).getName());
           intent.putExtra("pUrl",list.get(position).getImageUrl());
           startActivity(intent);

       };
    }

}