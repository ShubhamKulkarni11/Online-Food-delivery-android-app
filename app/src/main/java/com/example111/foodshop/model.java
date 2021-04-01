package com.example111.foodshop;

public class model {
    String foodName;
    String foodDesci;
    String foodPrice;
    String urlImage;
    public model(){

    }

    public model(String foodName, String foodDesci, String foodPrice, String urlImage) {
        this.foodName = foodName;
        this.foodDesci = foodDesci;
        this.foodPrice = foodPrice;
        this.urlImage = urlImage;
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