package com.example.ecommerceapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.AddAddressActivity;
import com.example.ecommerceapp.activities.AddProductActivity;
import com.example.ecommerceapp.activities.ShowAllActivity;
import com.example.ecommerceapp.adapters.CategoryAdapter;
import com.example.ecommerceapp.adapters.NewProductsAdapter;
import com.example.ecommerceapp.adapters.PopularProdutcsAdapter;
import com.example.ecommerceapp.models.CategoryModel;
import com.example.ecommerceapp.models.NewProductsModel;
import com.example.ecommerceapp.models.PopularProductsModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class AdminHomeFragment extends Fragment {
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView catRecyclerView, newProductsRecyclerView, popularRecyclerView;

    //Category recyclerView
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //New Products recyclerView
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    //Popular Products recyclerView
    PopularProdutcsAdapter popularProdutcsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    //Show all
    TextView catShowAll, popularShowAll, newproductShowALl;

    //Fire store
    FirebaseFirestore db;

    Button add_product_btn;
    public AdminHomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate layout
        View root =  inflater.inflate(R.layout.activity_admin_main, container, false);
        db = FirebaseFirestore.getInstance();


        add_product_btn = root.findViewById(R.id.add_product_btn);

        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}