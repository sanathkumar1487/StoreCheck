package com.euromonitor.storecheck.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Noora.Akhtar on 4/21/2016.
 */
public class MetaData {
    @SerializedName("GeographyCode")
    private String geographyCode;

    @SerializedName("GeographyName")
    private String geographyName;

    @SerializedName("IndustryName")
    private String industryName;

    @SerializedName("Year")
    private int year;

    public void setGeographyCode(String geographyCode) {
        this.geographyCode = geographyCode;
    }

    public void setGeographyName(String geographyName) {
        this.geographyName = geographyName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGeographyCode() {
        return geographyCode;
    }

    public String getGeographyName() {
        return geographyName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public int getYear() {
        return year;
    }
}
