package com.example.clear_co2_application.navigator;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clear_co2_application.Process_Activity;
import com.example.clear_co2_application.Product_Adapter;
import com.example.clear_co2_application.Product_PopUp_Activity;
import com.example.clear_co2_application.ProductProfileActivity;
import com.example.clear_co2_application.R;
import com.example.clear_co2_application.UploadedProduct;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductPage extends Fragment implements Product_Adapter.RecyclerViewDeleteListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductPage() {
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
    public static ProductPage newInstance(String param1, String param2) {
        ProductPage fragment = new ProductPage();
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
    private Product_Adapter productAdapter;
    private ProgressBar progressCircle;

    //Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mRef;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseStorage delStorage = FirebaseStorage.getInstance();
    private ValueEventListener mDBListener;

    //List
    private List<UploadedProduct> list;

    private Product_Adapter.RecyclerViewClickListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_page, container, false);

        setHasOptionsMenu(true);

        //UI
        recyclerView_products_list = view.findViewById(R.id.product_list);
        recyclerView_products_list.setHasFixedSize(true);
        recyclerView_products_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        addProductBTN = view.findViewById(R.id.add_product_btn);
        progressCircle = view.findViewById(R.id.progress_circle);

        //List
        list = new ArrayList<>();

        // Firebase

        mRef =FirebaseDatabase.getInstance("https://clear-co2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users/" + mAuth.getCurrentUser().getUid() + "/Products/");

        mDBListener = mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UploadedProduct uploadedProduct = postSnapshot.getValue(UploadedProduct.class);
                    uploadedProduct.setDeleteKey(postSnapshot.getKey());
                    list.add(uploadedProduct);
                }
                setOnClickListener();
                productAdapter = new Product_Adapter(getActivity(), list, listener);
                recyclerView_products_list.setAdapter(productAdapter);
                productAdapter.setRecyclerViewDeleteListener(ProductPage.this);
                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressCircle.setVisibility(View.INVISIBLE);

            }
        });

        addProductBTN.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), Product_PopUp_Activity.class);
            startActivity(intent);

        });
        return view;
    }

    public void setOnClickListener() {
        listener = (v, position) ->
        {
            // Pass parameters to next activity
            Intent intent = new Intent(getActivity(), Process_Activity.class);
            intent.putExtra("pName", list.get(position).getName());
            intent.putExtra("pUrl", list.get(position).getImageUrl());
            intent.putExtra("pInfo", list.get(position).getmInfo());
            startActivity(intent);

        };
    }

    // Deleting products from DB
    @Override
    public void onDelete(View view, int position) {
        UploadedProduct selectedItem = list.get(position);
        String selectedDeleteKey = selectedItem.getDeleteKey();

        StorageReference delImageRef = delStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        delImageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void dVoid) {
                mRef.child(selectedDeleteKey).removeValue();
                Toast.makeText(ProductPage.this.getActivity(), "Product deleted.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRef.removeEventListener(mDBListener);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.product_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}