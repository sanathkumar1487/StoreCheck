package com.euromonitor.storecheck.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Noora.Akhtar on 5/6/2016.
 */
public class Validation {
    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    @SerializedName("IndustryCode")
    private String industryCode;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @SerializedName("ColumnName")
    private String columnName;

    public double getMinimumAllowed() {
        return minimumAllowed;
    }

    public void setMinimumAllowed(double minimumAllowed) {
        this.minimumAllowed = minimumAllowed;
    }

    @SerializedName("MinimumAllowed")
    private double minimumAllowed;

    @SerializedName("MaximumAllowed")
    private double maximumAllowed;

    public double getMaximumAllowed() {
        return maximumAllowed;
    }

    public void setMaximumAllowed(double maximumAllowed) {
        this.maximumAllowed = maximumAllowed;
    }

    public int getDecimalPlace() {
        return decimalPlace;
    }

    public void setDecimalPlace(int decimalPlace) {
        this.decimalPlace = decimalPlace;
    }

    @SerializedName("DecimalPlaces")
    private int decimalPlace;


    public int getIsNumeric() {
        return isNumeric;
    }

    public void setIsNumeric(int isNumeric) {
        this.isNumeric = isNumeric;
    }

    @SerializedName("IsNumeric")
    private int isNumeric;
}
