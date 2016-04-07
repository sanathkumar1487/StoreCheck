package com.euromonitor.storecheck.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sanath.Kumar on 3/31/2016.
 */
public class Market {


    private int _id;

    @SerializedName("BrandMarketId")
    private String _brand_market_id;

    @SerializedName("ProjectId")
    private String _project_id;

    @SerializedName("GeographyCode")
    private String _geography_code;

    @SerializedName("Geography")
    private String geography;

    @SerializedName("ProductCode")
    private String _product_code;

    @SerializedName("Product")
    private String _product;

    @SerializedName("Brand")
    private String _brand;

    @SerializedName("NBO")
    private String _nbo;

    @SerializedName("CompanyName")
    private String _company_name;

    public Market(String _brand_market_id, String _project_id, String _geography_code, String geography, String _product_code, String _product, String _brand, String _nbo, String _company_name) {
        this._brand_market_id = _brand_market_id;
        this._project_id = _project_id;
        this._geography_code = _geography_code;
        this.geography = geography;
        this._product_code = _product_code;
        this._product = _product;
        this._brand = _brand;
        this._nbo = _nbo;
        this._company_name = _company_name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_brand_market_id() {
        return _brand_market_id;
    }

    public void set_brand_market_id(String _brand_market_id) {
        this._brand_market_id = _brand_market_id;
    }

    public String get_project_id() {
        return _project_id;
    }

    public void set_project_id(String _project_id) {
        this._project_id = _project_id;
    }

    public String get_geography_code() {
        return _geography_code;
    }

    public void set_geography_code(String _geography_code) {
        this._geography_code = _geography_code;
    }

    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    public String get_product_code() {
        return _product_code;
    }

    public void set_product_code(String _product_code) {
        this._product_code = _product_code;
    }

    public String get_product() {
        return _product;
    }

    public void set_product(String _product) {
        this._product = _product;
    }

    public String get_brand() {
        return _brand;
    }

    public void set_brand(String _brand) {
        this._brand = _brand;
    }

    public String get_nbo() {
        return _nbo;
    }

    public void set_nbo(String _nbo) {
        this._nbo = _nbo;
    }

    public String get_company_name() {
        return _company_name;
    }

    public void set_company_name(String _company_name) {
        this._company_name = _company_name;
    }
}
