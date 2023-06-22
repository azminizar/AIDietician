package com.example.aidietician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Fitnesstracker extends AppCompatActivity implements SensorEventListener {

    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 1;

    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private TextView progressText,txtViewNumStep,calorieBurnedSteps,cycleTime,calorieBurnedCycle
            ,swimtime,calorieBurnedSwim;
    private boolean running = false;
    private int stepCount = 0;
    private int activity;

    private FirebaseFirestore fstore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitnesstracker);

        progressText = findViewById(R.id.progress_text);
        txtViewNumStep = findViewById(R.id.txtViewNumStep);
        calorieBurnedSteps = findViewById(R.id.calorieBurnedSteps);
        calorieBurnedCycle = findViewById(R.id.calorieBurnedCycle);
        cycleTime = findViewById(R.id.cycleTime);
        calorieBurnedSwim = findViewById(R.id.calorieBurnedSwim);
        swimtime = findViewById(R.id.swimtime);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted, request it
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                        PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
            } else {
                // Permission is already granted, start listening for step counter sensor events
                registerStepCounterListener();
            }
        } else {
            // Permission is automatically granted for earlier versions
            registerStepCounterListener();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_ACTIVITY_RECOGNITION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, start listening for step counter sensor events
                registerStepCounterListener();
            } else {
                // Permission is denied, handle the denied case
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void registerStepCounterListener() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            running = true;
            Toast.makeText(this, "Sensor Found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show();
            running = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor == stepCounterSensor) {
            stepCount = (int) event.values[0];
            progressText.setText(String.valueOf(stepCount));
            txtViewNumStep.setText(String.valueOf(stepCount));

            int calCount = (int) (stepCount * 0.04);
            calorieBurnedSteps.setText(String.valueOf(calCount));

            activity = (int) (stepCount * 0.01);

            String userID = mAuth.getCurrentUser().getUid();
            DocumentReference df = fstore.collection("home").document(userID);
            DocumentReference df2 = fstore.collection("activity").document(userID);

            Map<String, Object> map2 = new HashMap<>();
            map2.put("activity", String.valueOf(activity));
            map2.put("steps", String.valueOf(stepCount));

            Map<String, Object> map = new HashMap<>();
            map.put("activity", String.valueOf(activity));

            df2.set(map2, SetOptions.merge());
            df.set(map, SetOptions.merge());

            df2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Map<String, Object> map = document.getData();
                            map.put("cal_burned", String.valueOf(calCount));
                            df2.set(map, SetOptions.merge());
                        }
                    }
                }
            });
            //Toast.makeText(this, String.valueOf(stepCount), Toast.LENGTH_SHORT).show();
        }

        cycleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCycleDialog();
            }
        });

        // int timeCycle = Integer.parseInt(String.valueOf(cycleTime));

        swimtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSwimDialog();
            }
        });

//        String userID = mAuth.getCurrentUser().getUid();
//        DocumentReference df3 = fstore.collection("users").document(userID);

//        df3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Map<String, Object> map = document.getData();
//                        int weight = Integer.parseInt(String.valueOf(map.get("weight")));
//                        int cycleCalorie = timeCycle * 8 * weight;
//                        int swimCalorie = (6 * weight * timeSwim) / 60;
//                        calorieBurnedCycle.setText(String.valueOf(cycleCalorie));
//                        calorieBurnedCycle.setText(String.valueOf(swimCalorie));
//                    }
//                }
//            }
//        });
    }

    private void openCycleDialog() {

        String userID = mAuth.getCurrentUser().getUid();

        DocumentReference df2 = fstore.collection("activity").document(userID);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10);

        builder1.setView(numberPicker);

        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedValue = numberPicker.getValue();
                cycleTime.setText(String.valueOf(selectedValue));
                Map<String, Object> map = new HashMap<>();
                map.put("cycle_time", cycleTime.getText().toString());
                df2.set(map, SetOptions.merge());
            }
        });

        AlertDialog dialog1 = builder1.create();
        dialog1.show();

    }

    private void openSwimDialog() {

        String userID = mAuth.getCurrentUser().getUid();

        DocumentReference df2 = fstore.collection("activity").document(userID);

        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

        final NumberPicker numberPicker2 = new NumberPicker(this);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(10);

        builder2.setView(numberPicker2);

        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedValue = numberPicker2.getValue();
                swimtime.setText(String.valueOf(selectedValue));
                Map<String, Object> map = new HashMap<>();
                map.put("swim_time", swimtime.getText().toString());
                df2.set(map, SetOptions.merge());
            }
        });

        AlertDialog dialog2 = builder2.create();
        dialog2.show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing for now
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sensorManager != null && stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (sensorManager != null && stepCounterSensor != null) {
            sensorManager.unregisterListener(this, stepCounterSensor);
        }
    }
}