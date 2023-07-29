package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerceapp.R;

public class PaymentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView subTotal, discount, shipping, totalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        double amount = 0.0;
        amount = getIntent().getDoubleExtra("amount", 0.0);
        subTotal.setText(amount+"$");
        totalAmount.setText(amount+"$");
    }

    private void initView() {
        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.discount);
        shipping = findViewById(R.id.shipping);
        totalAmount = findViewById(R.id.total_amt);
        toolbar = findViewById(R.id.payment_toolbar);
    }
}