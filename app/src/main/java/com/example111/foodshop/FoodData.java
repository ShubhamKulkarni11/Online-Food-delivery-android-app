package com.example111.foodshop;

public class FoodData {

    public String foodName;
    public String foodDesci;
    public String foodPrice;
    public String urlImage;

    public FoodData(String s, String toString, String string, String imageName, String s1) {

    }

    public FoodData(String foodName, String fooddesci, String foodPrice, String urlImage) {
        this.foodName = foodName;
        this.foodDesci = fooddesci;
        this.foodPrice = foodPrice;
        this.urlImage = urlImage;
    }

    public FoodData(String foodName, String foodDesci, String foodPrice) {
        this.foodName = foodName;
        this.foodDesci = foodDesci;
        this.foodPrice = foodPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodDesci() {
        return foodDesci;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
