package com.example.aidietician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import java.util.Date;

public class Dietchart extends AppCompatActivity {
    DayScrollDatePicker dayDatePicker;
    String selectDate;
    RecyclerView recycleDiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietchart);

        dayDatePicker = findViewById(R.id.dayDatePicker);
        dayDatePicker.setStartDate(15,4,2023);
        dayDatePicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                selectDate = date.toString();
            }
        });

        recycleDiet = findViewById(R.id.recycleDiet);
        recycleDiet.setHasFixedSize(true);
        recycleDiet.setLayoutManager(new LinearLayoutManager(this));
        Dietmodel[] model = new Dietmodel[]{
                new Dietmodel("Breakfast","200","Chapati","Potato Curry",R.drawable.breakfast),
                new Dietmodel("Lunch","250","Rice","Salad",R.drawable.lunch),
                new Dietmodel("Dinner","150","Chicken","Salad",R.drawable.dinner),
        };
        Adapterdiet adapterdiet = new Adapterdiet(model,Dietchart.this);
        recycleDiet.setAdapter(adapterdiet);
    }
}