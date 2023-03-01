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

    AutoCompleteTextView autotxtView;
    ArrayList<String> list_gender;
    ArrayAdapter<String> adap_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        autotxtView = (AutoCompleteTextView)findViewById(R.id.autotxtView);

        list_gender = new ArrayList<>();
        list_gender.add("Male");
        list_gender.add("Female");
        list_gender.add("Other");

        adap_gender = new ArrayAdapter<>(getApplicationContext(),com.google.android.material.R.layout.support_simple_spinner_dropdown_item,list_gender);
        autotxtView.setAdapter(adap_gender);

        autotxtView.setThreshold(1);
    }
}