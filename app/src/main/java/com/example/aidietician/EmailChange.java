package com.example.aidietician;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailChange extends AppCompatActivity {
    private EditText editTextCurrent,editTextNew;
    private Button btnEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_change);
        editTextCurrent = findViewById(R.id.editTextCurrent);
        editTextNew = findViewById(R.id.editTextNew);
        btnEmail = findViewById(R.id.btnChangeEmail);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmailChange.this,"Email Updated",Toast.LENGTH_SHORT).show();
            }
        });
    }
}