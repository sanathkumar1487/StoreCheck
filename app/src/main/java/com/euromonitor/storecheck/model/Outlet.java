package com.euromonitor.storecheck.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sanath.Kumar on 3/30/2016.
 */
public class Outlet {

    private int _id;

    @SerializedName("OutletId")
    private int _outlet_id;

    @SerializedName("ProjectId")
    private int _project_id;

    @SerializedName("GeographyCode")
    private String _geo_code;

    public String get_chccode() {
        return _chccode;
    }

    public void set_chccode(String _chccode) {
        this._chccode = _chccode;
    }

    @SerializedName("CHCCode")
    private  String _chccode;

    @SerializedName("Geography")
    private String _geo_name;

    @SerializedName("Industry")
    private String _industry;

    @SerializedName("OutletCity")
    private String _outlet_city;

    @SerializedName("Year")
    private String _year;

    @SerializedName("OutletDate")
    private String _outlet_date;



    public Outlet() {

    }

    public Outlet(int _outlet_id, int _project_id, String _geo_code, String _geo_name, String _industry, String _outlet_city, String _year, String _outlet_date,String _chccode) {
        this._outlet_id = _outlet_id;
        this._project_id = _project_id;
        this._geo_code = _geo_code;
        this._geo_name = _geo_name;
        this._industry = _industry;
        this._outlet_city = _outlet_city;
        this._year = _year;
        this._outlet_date = _outlet_date;
        this._chccode = _chccode;
    }

    public int get_id() {
        return _id;
    }

    public int get_outlet_id() {
        return _outlet_id;
    }

    public void set_outlet_id(int _outlet_id) {
        this._outlet_id = _outlet_id;
    }

    public int get_project_id() {
        return _project_id;
    }

    public void set_project_id(int _project_id) {
        this._project_id = _project_id;
    }

    public String get_geo_code() {
        return _geo_code;
    }

    public void set_geo_code(String _geo_code) {
        this._geo_code = _geo_code;
    }

    public String get_geo_name() {
        return _geo_name;
    }

    public void set_geo_name(String _geo_name) {
        this._geo_name = _geo_name;
    }

    public String get_industry() {
        return _industry;
    }

    public void set_industry(String _industry) {
        this._industry = _industry;
    }

    public String get_outlet_city() {
        return _outlet_city;
    }

    public void set_outlet_city(String _outlet_city) {
        this._outlet_city = _outlet_city;
    }

    public String get_year() {
        return _year;
    }

    public void set_year(String _year) {
        this._year = _year;
    }

    public String get_outlet_date() {
        return _outlet_date;
    }

    public void set_outlet_date(String _outlet_date) {
        this._outlet_date = _outlet_date;
    }


}
