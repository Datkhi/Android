package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {
    EditText name, email, password;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //getSupportActionBar().hide();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);
        if(isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();

            Intent intent = new Intent(RegistrationActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void signup(View view){
        String username = name.getText().toString();
        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Enter name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(useremail)){
            Toast.makeText(this, "Enter email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userpassword)){
            Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(useremail, userpassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegistrationActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistrationActivity.this, "Dang ky that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void signin(View view){
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }
}