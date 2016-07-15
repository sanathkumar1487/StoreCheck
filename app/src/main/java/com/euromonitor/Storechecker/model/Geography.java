package com.euromonitor.Storechecker.model;

/**
 * Created by Noora.Akhtar on 12/04/2016.
 */
public class Geography {
    private String geographyCode;
    private String geographyName;

    public String getGeographyCode(){
        return geographyCode;
    }

    public String getGeographyName(){
        return geographyName;
    }

    public void setGeographyCode(String geographyCode){
        this.geographyCode = geographyCode;
    }

    public void setGeographyName(String geographyName){
        this.geographyName = geographyName;
    }
}
