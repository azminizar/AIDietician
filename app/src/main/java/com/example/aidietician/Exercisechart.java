package com.example.aidietician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class Exercisechart extends AppCompatActivity {

    RecyclerView recycleExercise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercisechart);

        recycleExercise = findViewById(R.id.recycleExercise);
        recycleExercise.setHasFixedSize(true);
        recycleExercise.setLayoutManager(new LinearLayoutManager(this));
        Exercisemodel[] emodel = new Exercisemodel[]{
                new Exercisemodel("Plank","It is an exercise that is often used to target and strengthen the core muscles," +
                        " including the abdominals, back, and hips.","170-200",R.drawable.plank),
                new Exercisemodel("Squats","They are a popular exercise that are often used to target and strengthen the lower body muscles, " +
                        "including the quadriceps, hamstrings, glutes, and calves.","300-350",R.drawable.squats),
                new Exercisemodel("Crunches","They are a popular exercise that are often used to target and strengthen the abdominal muscles. "
                        ,"150-200",R.drawable.crunches),
                new Exercisemodel("Lunges","They are a popular exercise that are often used to target and strengthen the lower body muscles," +
                        " including the quadriceps, hamstrings, glutes, and calves. ","350-400",R.drawable.lunges),
                new Exercisemodel("Push-ups","They are a popular exercise that are often used to target and strengthen the upper body muscles," +
                        " including the chest, shoulders, triceps, and core.","500-600",R.drawable.pushup),
        };
        Adapterexercise adapterexercise = new Adapterexercise(emodel,Exercisechart.this);
        recycleExercise.setAdapter(adapterexercise);
    }
}