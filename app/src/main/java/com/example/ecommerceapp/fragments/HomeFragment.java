package com.example.ecommerceapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.ShowAllActivity;
import com.example.ecommerceapp.adapters.CategoryAdapter;
import com.example.ecommerceapp.adapters.NewProductsAdapter;
import com.example.ecommerceapp.adapters.PopularProdutcsAdapter;
import com.example.ecommerceapp.models.CategoryModel;
import com.example.ecommerceapp.models.NewProductsModel;
import com.example.ecommerceapp.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
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
    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate layout
        View root =  inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        catRecyclerView = root.findViewById(R.id.rec_category);
        newProductsRecyclerView = root.findViewById(R.id.new_product_rec);
        popularRecyclerView = root.findViewById(R.id.popular_rec);
        catShowAll = root.findViewById(R.id.category_see_all);
//        popularShowAll = root.findViewById(R.id.popular_see_all);
//        newproductShowALl = root.findViewById(R.id.newProducts_see_all);

        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        //Image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1, "Street wear", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2, "Denim wear", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, "Sport brands", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner4, "Watches for au", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);

        //Progress Dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Welcome to My ECommerce App");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIcon(R.drawable.baseline_shopping_cart_24_black);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //Category
        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //New Products
        newProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        newProductsRecyclerView.setAdapter(newProductsAdapter);
        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Popular Products
        popularRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        popularProductsModelList = new ArrayList<>();
        popularProdutcsAdapter = new PopularProdutcsAdapter(getContext(), popularProductsModelList);
        popularRecyclerView.setAdapter(popularProdutcsAdapter);
        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularProdutcsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
//        popularShowAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), ShowAllActivity.class);
//                startActivity(intent);
//            }
//        });
//        newproductShowALl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), ShowAllActivity.class);
//                startActivity(intent);
//            }
//        });

        return root;
    }
}