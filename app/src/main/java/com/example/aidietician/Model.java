package com.example.aidietician;

public class Model {
    private String meal,item1,item2,calorie;
    private int img;

    public Model(String meal, String calorie,String item1, String item2, int img){
        this.meal = meal;
        this.calorie = calorie;
        this.item1 = item1;
        this.item2 = item2;
        this.img = img;
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

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
