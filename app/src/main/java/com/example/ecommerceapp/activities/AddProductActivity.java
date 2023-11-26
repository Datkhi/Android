package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.NewProductsModel;
import com.example.ecommerceapp.models.ProductsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddProductActivity extends AppCompatActivity {
    EditText description, img_url, name, price, rating, type;
    Button btnAddProduct;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        initView();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();



        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p_description = description.getText().toString();
                String p_img_url = img_url.getText().toString();
                String p_name = name.getText().toString();
                String p_price_raw = price.getText().toString();
                Integer p_price = Integer.parseInt(p_price_raw);
                String p_rating = rating.getText().toString();
                String p_type = type.getText().toString();
                if (!p_description.isEmpty() && !p_img_url.isEmpty() && !p_name.isEmpty() && !p_price_raw.isEmpty() && !p_rating.isEmpty() && !p_type.isEmpty()) {

                    ProductsModel newProduct = new ProductsModel(p_img_url, p_description,  p_name, p_rating, p_price, p_type);

                    NewProductsModel newProduct2 = new NewProductsModel(p_img_url, p_description,  p_name, p_rating, p_price, p_type);
                    firestore.collection("NewProducts")
                            .add(newProduct2)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(AddProductActivity.this, "Added a new product", Toast.LENGTH_SHORT).show();
//
                            }).addOnFailureListener(e -> {
                                Toast.makeText(AddProductActivity.this, "Failed to add new product", Toast.LENGTH_SHORT).show();
                            });

                    firestore.collection("Products")
                            .add(newProduct)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(AddProductActivity.this, "Added a new product", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddProductActivity.this, AdminMainActivity.class);
                                startActivity(intent);
                            }).addOnFailureListener(e -> {
                                Toast.makeText(AddProductActivity.this, "Failed to add new product", Toast.LENGTH_SHORT).show();
                            });

                }
                else {
                    Toast.makeText(AddProductActivity.this, "Please Fill All Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initView() {
        description = findViewById(R.id.p_description);
        img_url = findViewById(R.id.p_img_url);
        name = findViewById(R.id.p_name);
        price = findViewById(R.id.p_price);
        rating = findViewById(R.id.p_rating);
        type = findViewById(R.id.p_type);
        btnAddProduct = findViewById(R.id.p_add_product_btn);
    }
}