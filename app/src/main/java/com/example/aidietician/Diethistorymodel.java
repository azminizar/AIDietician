package com.example.aidietician;

import android.widget.TextView;

import java.sql.Time;
import java.util.Date;

public class Diethistorymodel {

    String date,time,meal,calorie;

    public Diethistorymodel(String date, String time, String meal, String calorie) {
        this.date = date;
        this.time = time;
        this.meal = meal;
        this.calorie = calorie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }
}

