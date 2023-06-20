package com.example.aidietician;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BodyMeasure extends AppCompatActivity {

    private EditText editTextHeight,editTextWeight;
    private Button btnUpdate;
    private String getHeight,getWeight;
    FirebaseFirestore db;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_measure);

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = FirebaseFirestore.getInstance();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BodyMeasure.this,"Updated",Toast.LENGTH_SHORT).show();
                getHeight = editTextHeight.getText().toString();
                getWeight = editTextWeight.getText().toString();
                editTextHeight.setText("");
                editTextWeight.setText("");
                Updatedata(getHeight,getWeight);
            }
        });
    }

    private void Updatedata(String height, String weight){

        Map<String,Object> map = new HashMap<>();
        map.put("height",editTextHeight);
        map.put("weight",editTextWeight);

        String userID = mAuth.getCurrentUser().getUid();
        String userName = mAuth.getCurrentUser().getDisplayName();
    }
}