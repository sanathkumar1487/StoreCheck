package com.euromonitor.Storechecker.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckDetail extends BaseObservable {
    public int itemId;

    public String productName;
    public Double price;
    public Double packSize;
    public String packUnit;
    public String outletName;
    public String nbo;
    public String gbo;
    public String brand;
    public String currency;
    public Double multiPackSize;
    public int priceId;
    private int brandId;

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    private int updated;

    public int getBrandMarketId() {
        return brandMarketId;
    }

    public void setBrandMarketId(int brandMarketId) {
        this.brandMarketId = brandMarketId;
    }

    private int brandMarketId;


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

    public void setPackSize(Double packSize){
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

    public void setMultiPackSize(Double itemsPerPack){this.multiPackSize = itemsPerPack;}

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    private int productCode;

}
