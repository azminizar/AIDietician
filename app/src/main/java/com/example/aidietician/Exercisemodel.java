package com.example.aidietician;

public class Exercisemodel {
    private String exerciseName,exerciseDesc,caloriesBurned;
    private int img;

    public Exercisemodel(String exerciseName,String exerciseDesc,String caloriesBurned,int img){
        this.exerciseName = exerciseName;
        this.exerciseDesc = exerciseDesc;
        this.caloriesBurned = caloriesBurned;
        this.img = img;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseDesc() {
        return exerciseDesc;
    }

    public void setExerciseDesc(String exerciseDesc) {
        this.exerciseDesc = exerciseDesc;
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
