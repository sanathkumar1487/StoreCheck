package com.euromonitor.storecheck.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckDetail extends BaseObservable {
    public int itemId;

    public String productName;
    public Double price;
    public int packSize;
    public String packUnit;
    public String outletName;
    public String nbo;
    public String gbo;
    public String brand;
    public String currency;
    public int multiPackSize;
    public int priceId;
    private int brandId;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }



    public int getItemId(){
        return itemId;
    }

    public void setItemId(int itemId){
        this.itemId = itemId;
    }

    @Bindable
    public String getProductName(){
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    @Bindable
    public String getPrice(){
        return  price.toString();
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public void setPriceId(int priceId){this.priceId = priceId;}

    public int getPriceId(){
        return priceId;
    }

    @Bindable
    public String getPackUnit(){
        return  packUnit;
    }

    public void setPackUnit(String packUnit){
        this.packUnit = packUnit;
    }

    public String getPackSize(){
        return  String.valueOf(packSize) + " " + packUnit;
    }

    public void setPackSize(int packSize){
        this.packSize = packSize;
    }

    public String getOutletName(){
        return outletName;
    }

    public void setOutletName(String outletName){
        this.outletName = outletName;
    }

    public String getNbo(){
        return nbo;
    }

    public void setNbo(String productName){
        this.nbo = nbo;
    }

    public String getGbo(){
        return gbo;
    }

    public void setGbo(String gbo){
        this.gbo = gbo;
    }

    public String getBrand(){
        return brand;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public  void setCurrency(String currency){this.currency = currency;}

    public String getCurrency(){return currency;}

    public String getMultiPackSize(){return String.valueOf(packSize) + "X" + String.valueOf(multiPackSize) ;}

    public void setMultiPackSize(int itemsPerPack){this.multiPackSize = itemsPerPack;}

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    private int productCode;

}
