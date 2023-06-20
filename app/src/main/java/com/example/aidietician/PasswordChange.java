package com.example.aidietician;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordChange extends AppCompatActivity {
    private EditText current,newPass,retype;
    private Button btnPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        current = findViewById(R.id.editTextCurrentPass);
        newPass = findViewById(R.id.editTextNewPass);
        retype = findViewById(R.id.editTextRetype);
        btnPass = findViewById(R.id.btnChangePassword);

        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PasswordChange.this,"Password Updated",Toast.LENGTH_SHORT).show();
            }
        });
    }
}