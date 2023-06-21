package com.example.aidietician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Privacy extends AppCompatActivity {

    private CardView email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        email = findViewById(R.id.cardViewEmail);
        password = findViewById(R.id.cardViewPassword);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Privacy.this,EmailChange.class);
                startActivity(intent1);
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Privacy.this,PasswordChange.class);
                startActivity(intent2);
            }
        });
    }
}