package com.example111.foodshop;

public class cartItems {
    String foodName;
    String foodDesci;
    String foodPrice;
    String urlImage;
    String quantity;

    public cartItems() {
    }



    String total;

    public cartItems(String foodName, String foodDesci, String foodPrice, String urlImage, String quantity,String total) {
        this.foodName = foodName;
        this.foodDesci = foodDesci;
        this.foodPrice = foodPrice;
        this.urlImage = urlImage;
        this.quantity = quantity;
        this.total=total;
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

    public String getQuantity() {
        return quantity;
    }
    public String getTotal() {
        return total;
    }
}
