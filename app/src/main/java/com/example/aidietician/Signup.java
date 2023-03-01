package com.example.aidietician;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Signup");
    }

    public void onBtnSignupClick(View v){
        Button btnSignup = findViewById(R.id.btnSignUp);
        Toast.makeText(this, "New user registered", Toast.LENGTH_SHORT).show();
    }

    public void onAcntClick(View v2){
        startActivity(new Intent(this,MainActivity.class));
    }
}