package com.example.aidietician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {

    Button submitBtn;
    Spinner spinnerGender, spinnerFoodpref, spinnerActiveLevel;
    EditText editTextHeight, editTextWeight;
    TextView dietPlan;

    String[] gender = {"Gender", "Male", "Female"};
    String[] foodpref = {"Food Preference", "Vegetarian", "Strictly Vegetarian", "Non-Vegetarian", "Vegan"};
    String[] activeLevel = {"Activity Level", "Sedentary", "Lightly Active", "Moderatively Active", "Very Active", "Extra Active"};
    String water;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        submitBtn = findViewById(R.id.btnSubmit);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerFoodpref = findViewById(R.id.spinnerFoodplan);
        spinnerActiveLevel = findViewById(R.id.spinnerDietPref);
        editTextHeight = findViewById(R.id.edtTxtHeight);
        editTextWeight = findViewById(R.id.edtTxtWeight);

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(Details.this, android.R.layout.simple_spinner_item, gender);
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

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(Details.this, android.R.layout.simple_spinner_item, activeLevel);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerActiveLevel.setAdapter(adapter3);
        spinnerActiveLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value3 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Wearable.getDataClient(context).addListener(onDataChangedListener);

//        DataClient.OnDataChangedListener onDataChangedListener = new DataClient.OnDataChangedListener() {
//            @Override
//            public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
//                for (DataEvent event : dataEventBuffer) {
//                    if (event.getType() == DataEvent.TYPE_CHANGED && event.getDataItem().getUri().getPath().equals("/data-path")) {
//                        DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
//
//                         water = dataMapItem.getDataMap().getString("water");
//                    }
//                }
//            }
//        };


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userID = mAuth.getCurrentUser().getUid();
                String weight, height, foodpref, activelevel, gender;
                weight = String.valueOf(editTextWeight.getText());
                height = String.valueOf(editTextHeight.getText());
                foodpref = spinnerFoodpref.getSelectedItem().toString();
                activelevel = spinnerActiveLevel.getSelectedItem().toString();
                gender = spinnerGender.getSelectedItem().toString();

                int w = Integer.parseInt(weight);
                int h = Integer.parseInt(height);
                double bmi = 10000 * w / (h * h);
                String dietplan = getBMICategory(bmi);
                DocumentReference df = fstore.collection("users").document(userID);
                DocumentReference df2 = fstore.collection("home").document(userID);



                double activevalue = activeLevelCalculation(activelevel);


                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String dob;
                        dob=documentSnapshot.get("dob").toString();
                        int age=calculateAge(dob);
                        int cal = calculateCalories(gender, w, h, age, activevalue);
                        Map<String,Object> map = new HashMap<>();
                        map.put("weight", weight);
                        map.put("Height", height);
                        map.put("foodpref", foodpref);
                        map.put("activitylevel", activelevel);
                        map.put("gender", gender);
                        map.put("cal_target", String.valueOf(cal));
                        map.put("age", String.valueOf(age));
                        map.put("diet_plan", dietplan);

                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("weight", weight);
                        map2.put("bmi", String.valueOf(bmi));
                        map2.put("cal_target", String.valueOf(cal));
//                map2.put("water",water);


                        df.set(map, SetOptions.merge());
                        df2.set(map2, SetOptions.merge());
                    }
                });

                Intent intent = new Intent(getApplicationContext(), Homepage.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public static int calculateAge(String dateOfBirth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = Calendar.getInstance().getTime();
        int age = 0;

        try {
            Date birthDate = dateFormat.parse(dateOfBirth);
            Calendar currentCal = Calendar.getInstance();
            Calendar birthCal = Calendar.getInstance();
            currentCal.setTime(currentDate);
            birthCal.setTime(birthDate);



            int yearDiff = currentCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
            int monthDiff = currentCal.get(Calendar.MONTH) - birthCal.get(Calendar.MONTH);
            int dayDiff = currentCal.get(Calendar.DAY_OF_MONTH) - birthCal.get(Calendar.DAY_OF_MONTH);

            // Decrease age if birth date is yet to occur this year
            if (monthDiff < 0 || (monthDiff == 0 && dayDiff < 0)) {
                age = yearDiff - 1;
            } else {
                age = yearDiff;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return age;
    }

                public double calculateBMR(String gender, int weight, int height, int age) {
        double bmr;

        if (gender.equalsIgnoreCase("male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        return bmr;
    }

    public int calculateCalories(String gender, int weight, int height, int age, double activityLevel) {
        double bmr = calculateBMR(gender, weight, height, age);
        int calories = (int) (bmr * activityLevel);

        return calories;
    }

    public double activeLevelCalculation(String activelevel) {
        double actlvl = 0;
        switch (activelevel) {
            case "Sedentary":
                actlvl = 1.2;
                break;
            case "Lightly Active":
                actlvl = 1.375;
                break;
            case "Moderately Active":
                actlvl = 1.55;
                break;
            case "Very Active":
                actlvl = 1.725;
                break;
            case "Extreme Active":
                actlvl = 1.9;
                break;
            default:
                break;
        }
        return actlvl;
    }

    public String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Gain Weight";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "Maintain Weight";
        } else {
            return "Lose Weight";
        }
    }
}