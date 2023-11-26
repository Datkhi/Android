package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.NewProductsModel;
import com.example.ecommerceapp.models.PopularProductsModel;
import com.example.ecommerceapp.models.ProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailedImg, addItem, removeItem;
    TextView name, description, rating, price, quantity;
    RatingBar star;
    Button add_to_card, buy_now;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    int totalQuantity = 1;
    int totalPrice = 1;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();

        NewProductsModel newProductsModel = null;
        PopularProductsModel popularProductsModel = null;
        ProductsModel productsModel = null;

        //Firestore
        firestore = FirebaseFirestore.getInstance();
        final Object obj = getIntent().getSerializableExtra("detailed");
        if(obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        } else if (obj instanceof PopularProductsModel) {
            popularProductsModel = (PopularProductsModel) obj;
        } else if (obj instanceof ProductsModel) {
            productsModel = (ProductsModel) obj;
        }

        if(newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            description.setText(newProductsModel.getDescription());
            rating.setText(newProductsModel.getRating());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            star.setRating(Float.valueOf(newProductsModel.getRating()));
            totalPrice = newProductsModel.getPrice() * totalQuantity;
        }

        if(popularProductsModel != null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            description.setText(popularProductsModel.getDescription());
            rating.setText(popularProductsModel.getRating());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            star.setRating(Float.valueOf(popularProductsModel.getRating()));
            totalPrice = popularProductsModel.getPrice() * totalQuantity;
        }

        if(productsModel != null){
            Glide.with(getApplicationContext()).load(productsModel.getImg_url()).into(detailedImg);
            name.setText(productsModel.getName());
            description.setText(productsModel.getDescription());
            rating.setText(productsModel.getRating());
            price.setText(String.valueOf(productsModel.getPrice()));
            star.setRating(Float.valueOf(productsModel.getRating()));
            totalPrice = productsModel.getPrice() * totalQuantity;
        }

        //Add to card
        add_to_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCard();
            }
        });

        //Buy now
        NewProductsModel finalNewProductsModel1 = newProductsModel;
        PopularProductsModel finalPopularProductsModel1 = popularProductsModel;
        ProductsModel finalProductsModel1 = productsModel;
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedActivity.this, AddressActivity.class);

                if(finalNewProductsModel1 != null){
                    intent.putExtra("item", finalNewProductsModel1);
                }
                if(finalPopularProductsModel1 != null){
                    intent.putExtra("item", finalPopularProductsModel1);
                }
                if(finalProductsModel1 != null){
                    intent.putExtra("item", finalProductsModel1);
                }
                startActivity(intent);
            }
        });

        NewProductsModel finalNewProductsModel = newProductsModel;
        PopularProductsModel finalPopularProductsModel = popularProductsModel;
        ProductsModel finalProductsModel = productsModel;
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity < 20){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    if(finalNewProductsModel != null){
                        totalPrice = finalNewProductsModel.getPrice() * totalQuantity;
                    }
                    if(finalPopularProductsModel != null){
                        totalPrice = finalPopularProductsModel.getPrice() * totalQuantity;
                    }
                    if(finalProductsModel != null){
                        totalPrice = finalProductsModel.getPrice() * totalQuantity;
                    }
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });
    }

    private void addToCard() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        firestore.collection("MyCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Added to a cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void initView() {
        detailedImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_desc);
        rating = findViewById(R.id.rating);
        price = findViewById(R.id.detailed_price);
        star = findViewById(R.id.my_rating);

        quantity = findViewById(R.id.quantity);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        add_to_card = findViewById(R.id.add_to_cart);
        buy_now = findViewById(R.id.buy_now);

        toolbar = findViewById(R.id.detailed_toolbar);
    }
}