package com.euromonitor.storecheck.model;
import android.renderscript.Sampler;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class ProductDetail {
    double price;
    double packSize;
    String brandName;
    private int brandId;

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPrice() {
        return String.valueOf(price);
    }

    public void setPackSize(double packSize) {
        this.packSize = packSize;
    }

    public String getPackSize() {
        return String.valueOf(packSize);
    }

    public void setBrandName(String brandName){
        this.brandName = brandName;
    }

    public String getBrandName(){
        return brandName;
    }

    public void setBrandId(int brandId){
        this.brandId = brandId;
    }

    public int getBrandId(){
        return brandId;
    }
}
