package com.example.aidietician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Fitnesstracker extends AppCompatActivity implements SensorEventListener {

    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 1;

    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private TextView progressText;
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
            activity = (int) ((int) stepCount * 0.01);
            String userID = mAuth.getCurrentUser().getUid();
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> map2 = new HashMap<>();
            map.put("activity", String.valueOf(activity));
            map2.put("steps", String.valueOf(stepCount));
            DocumentReference df = fstore.collection("home").document(userID);
            DocumentReference df2 = fstore.collection("activity").document(userID);
            df.set(map, SetOptions.merge());
            df2.set(map2, SetOptions.merge());
            //Toast.makeText(this, String.valueOf(stepCount), Toast.LENGTH_SHORT).show();
        }
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
