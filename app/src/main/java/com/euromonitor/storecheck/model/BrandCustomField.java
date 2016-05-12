package com.euromonitor.storecheck.model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shashwat.Bajpai on 10/05/2016.
 */
public class BrandCustomField extends BaseObservable
{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("Id")
    private int id;

    private int brandId;

    @SerializedName("BrandMarketId")
    private String brandMarketId;

    private String pricingId;

    private String customFieldId;

    @SerializedName("OptionId")
    private int optionId;

    private String optionValue;

    @SerializedName("OptionText")
    private String optionText;

    @SerializedName("GroupId")
    private int groupId;

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    private int updated;

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    @SerializedName("Label")
    private String Label;

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getBrandId()
    {
        return brandId;
    }

    public String getBrandMarketId() {
        return brandMarketId;
    }

    public void setBrandMarketId(String brandMarketId) {
        this.brandMarketId = brandMarketId;
    }

    public void setBrandId(int brandId)
    {
        this.brandId = brandId;
    }

    public String getPricingId() {
        return pricingId;
    }

    public void setPricingId(String pricingId) {
        this.pricingId = pricingId;
    }

    public String getCustomFieldId()
    {
        return customFieldId;
    }

    public void setCustomFieldId(String customFieldId) {
        this.customFieldId = customFieldId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId)
    {
        this.optionId = optionId;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionValue() {
        return optionValue;
    }
    public BrandCustomField(int brandId,String pricingId, String customFieldId,int optionId,String optionValue)
    {
        this.brandId=brandId;
        this.pricingId=pricingId;
        this.customFieldId=customFieldId;
        this.optionId=optionId;
        this.optionValue=optionValue;
    }

    public  BrandCustomField(){

    }
}
