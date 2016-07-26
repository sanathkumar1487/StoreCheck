package com.euromonitor.Storechecker.model;

import java.util.ArrayList;

/**
 * Created by Noora.Akhtar on 4/23/2016.
 */
public class PricingDetail {


    public String getPrice() {
        return String.valueOf(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    double price;

    public int getSelectedOutletId() {
        return selectedOutletId;
    }

    public void setSelectedOutletId(int selectedOutletId) {
        this.selectedOutletId = selectedOutletId;
    }

    int selectedOutletId = -1;

    public String getPackSize()
    {
        return String.valueOf(packSize);
    }

    public void setPackSize(double packSize)
    {
        this.packSize = packSize;
    }

    double packSize;

    public String getMultiPack() {
        return String.valueOf(multiPack);
    }

    public void setMultiPack(int multiPack) {
        this.multiPack = multiPack;
    }

    int multiPack = 1;

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    int unitId;
    private ArrayList<CustomField> customFields;


    public int getPricingId() {
        return pricingId;
    }

    int pricingId;

    public void setPricingId(int pricingId) {
        this.pricingId = pricingId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    String brandName;

    public ArrayList<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(ArrayList<CustomField> customFields) {
        this.customFields = customFields;
    }

    public String getSelectedOutletName() {
        return selectedOutletName;
    }

    public void setSelectedOutletName(String selectedOutletName) {
        this.selectedOutletName = selectedOutletName;
    }

    private String selectedOutletName;

    public int getPackTypeCode() {
        return packTypeCode;
    }

    public void setPackTypeCode(int packTypeCode) {
        this.packTypeCode = packTypeCode;
    }

    public int packTypeCode;

    public String getPackTypeName() {
        return packTypeName;
    }

    public void setPackTypeName(String packTypeName) {
        this.packTypeName = packTypeName;
    }

    public String packTypeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public boolean isUpdated;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productName;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    private int productId;

    public long getBrandMarketId() {
        return brandMarketId;
    }

    public void setBrandMarketId(long brandMarketId) {
        this.brandMarketId = brandMarketId;
    }

    private long brandMarketId;

    public String getNbo() {
        return nbo;
    }

    public void setNbo(String nbo) {
        this.nbo = nbo;
    }

    private String nbo;

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    private long brandId;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    private String channelName;

    public void setNewOutletId(int newOutletId) {
        this.newOutletId = newOutletId;
    }

    public int getNewOutletId() {
        return newOutletId;
    }

    private int newOutletId;
}