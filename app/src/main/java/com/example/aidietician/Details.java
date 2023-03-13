package com.example.aidietician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {

    Button submitBtn;
    Spinner spinnerGender,spinnerFoodpref,spinnerDietplan;
    EditText editTextHeight,editTextWeight;

    String[] gender = {"Gender","Male","Female","Other"};
    String[] foodpref = {"Food Preference","Vegetarian","Strictly Vegetarian","Non-Vegetarian","Vegan"};
    String[] dietplan = {"Diet Plan","Lose Weight","Maintain Weight","Gain Weight"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        submitBtn = findViewById(R.id.btnSubmit);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerFoodpref = findViewById(R.id.spinnerFoodplan);
        spinnerDietplan = findViewById(R.id.spinnerDietPref);
        editTextHeight = findViewById(R.id.edtTxtHeight);
        editTextWeight = findViewById(R.id.edtTxtWeight);

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(Details.this, android.R.layout.simple_spinner_item,gender);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGender.setAdapter(adapter1);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Details.this, android.R.layout.simple_spinner_item, foodpref);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFoodpref.setAdapter(adapter2);
        spinnerFoodpref.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value2 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(Details.this, android.R.layout.simple_spinner_item, dietplan);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDietplan.setAdapter(adapter3);
        spinnerDietplan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value3 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userID= mAuth.getCurrentUser().getUid();
                String weight,height,foodpref,dietplan,gender;
                weight = String.valueOf(editTextWeight.getText());
                height = String.valueOf(editTextHeight.getText());
                foodpref=spinnerFoodpref.getSelectedItem().toString();
                dietplan=spinnerDietplan.getSelectedItem().toString();
                gender=spinnerGender.getSelectedItem().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("weight",weight);
                map.put("Height",height);
                map.put("foodpref",foodpref);
                map.put("dietplan",dietplan);
                map.put("gender",gender);
                DocumentReference df = fstore.collection("users").document(userID);
                df.set(map, SetOptions.merge());
                Intent intent = new Intent(getApplicationContext(),Homepage.class);
                startActivity(intent);
                finish();

            }
        });
    }
}