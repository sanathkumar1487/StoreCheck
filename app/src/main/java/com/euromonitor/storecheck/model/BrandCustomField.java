package com.euromonitor.storecheck.model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shashwat.Bajpai on 10/05/2016.
 */
public class BrandCustomField extends BaseObservable
{
    @SerializedName("BrandId")
    private int brandId;
    @SerializedName("PricingId")
    private int pricingId;
    @SerializedName("CustomFieldId")
    private String customFieldId;
    @SerializedName("OptionId")
    private int optionId;
    @SerializedName("OptionValue")
    private String optionValue;

    public int getBrandId()
    {
        return brandId;
    }

    public void setBrandId(int brandId)
    {
        this.brandId = brandId;
    }

    public int getPricingId() {
        return pricingId;
    }

    public void setPricingId(int pricingId) {
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
    public BrandCustomField(int brandId,int pricingId, String customFieldId,int optionId,String optionValue)
    {
        this.brandId=brandId;
        this.pricingId=pricingId;
        this.customFieldId=customFieldId;
        this.optionId=optionId;
        this.optionValue=optionValue;
    }
}
