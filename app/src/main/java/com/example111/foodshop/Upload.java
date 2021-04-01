package com.example111.foodshop;


import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Upload{
    private String mImageUrl;
    private String mName;
    private String mPrice;




    public Upload() {

    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public Upload( String mName,String mImageUrl, String mPrice) {
        this.mImageUrl = mImageUrl;
        this.mName = mName;
        this.mPrice = mPrice;
    }


}