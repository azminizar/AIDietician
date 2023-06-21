package com.example.aidietician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;

public class Exercisechart extends AppCompatActivity {

    RecyclerView recycleExercise;
    ArrayList<Exercisemodel> exercise = new ArrayList<>();

    int[] exerciseImages = {R.drawable.plank,R.drawable.squats,R.drawable.crunches,
            R.drawable.lunges,R.drawable.pushup};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercisechart);
        setUpExerciseModel();
    }

    private void setUpExerciseModel() {
        recycleExercise = findViewById(R.id.recycleExercise);
        recycleExercise.setHasFixedSize(true);
        recycleExercise.setLayoutManager(new LinearLayoutManager(this));
        String [] execiseName = getResources().getStringArray(R.array.Exercise_Name);
        String[] caloriesBurned = getResources().getStringArray(R.array.Calories_Burned);
        for (int i = 0; i<execiseName.length; i++){
            exercise.add(new Exercisemodel(execiseName[i],
                    caloriesBurned[i],
                    exerciseImages[i]));
        }

        Adapterexercise adapterexercise = new Adapterexercise(this,exercise);
        recycleExercise.setAdapter(adapterexercise);
    }
}