package com.euromonitor.storecheck.model;

import java.util.ArrayList;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckDetail {
    private int itemId;
    private String productName;
    private Double price;
    private int packSize;
    private String packUnit;
    private String outletName;
    private String nbo;
    private String gbo;
    private String brand;
    private String currency;
    private int itemsPerPack;

    public int getItemId(){
        return itemId;
    }

    public void setItemId(int itemId){
        this.itemId = itemId;
    }

    public String getProductName(){
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public Double getPrice(){
        return  price;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public String getPackUnit(){
        return  packUnit;
    }

    public void setPackUnit(String packUnit){
        this.packUnit = packUnit;
    }

    public int getPackSize(){
        return  packSize;
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

    public int getItemsPerPack(){return itemsPerPack;}

    public void setItemsPerPack(int itemsPerPack){this.itemsPerPack = itemsPerPack;}

    public  static ArrayList<StoreCheckDetail> getData(){
        ArrayList<StoreCheckDetail> storeCheckItems = new ArrayList<>();

        for (int index = 0;index<100;index++){
            StoreCheckDetail tempItem = new StoreCheckDetail();
            tempItem.setItemId(index);
            tempItem.setProductName("Coke " + index);
            tempItem.setPackSize(10);
            tempItem.setPackUnit("ml");
            tempItem.setPrice(100.0 *index);
            tempItem.setCurrency("INR");
            tempItem.setItemsPerPack(10);
            storeCheckItems.add(tempItem);
        }
        return  storeCheckItems;
    }

}
