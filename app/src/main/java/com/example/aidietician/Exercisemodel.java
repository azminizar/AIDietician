package com.example.aidietician;

public class Exercisemodel {
    private String exerciseName,caloriesBurned;
    private int img;

    public Exercisemodel(String exerciseName,String caloriesBurned,int img){
        this.exerciseName = exerciseName;
        this.caloriesBurned = caloriesBurned;
        this.img = img;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(String caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
