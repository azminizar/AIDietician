package com.example.aidietician;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    Spinner spinnerGender,spinnerFoodpref,spinnerDietplan;
    String[] gender = {"Gender","Male","Female","Other"};
    String[] foodpref = {"Food preference","Vegetarian","Strictly Vegeterian","Non-vegetarian","Vegan"};
    String[] dietplan = {"Diet plan","Lose weight","Maintain weight","Gain weight"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerFoodpref = findViewById(R.id.spinnerFoodplan);
        spinnerDietplan = findViewById(R.id.spinnerDietPref);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Details.this, android.R.layout.simple_spinner_item,gender);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGender.setAdapter(adapter1);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Details.this, android.R.layout.simple_spinner_item,foodpref);
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

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Details.this, android.R.layout.simple_spinner_item,dietplan);
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
    }
}