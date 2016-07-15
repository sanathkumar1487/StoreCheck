package com.euromonitor.Storechecker.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sanath.Kumar on 4/5/2016.
 */
public class Product {

    private  int _id;

    @SerializedName("ProductId")
    private  String _product_id;

    @SerializedName("ProductName")
    private  String _product_name;

    public  Product(){

    }

    public Product(String _product_id, String _product_name) {
        this._product_id = _product_id;
        this._product_name = _product_name;
    }

    public int get_id() {

        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_product_id() {
        return _product_id;
    }

    public void set_product_id(String _product_id) {
        this._product_id = _product_id;
    }

    public String get_product_name() {
        return _product_name;
    }

    public void set_product_name(String _product_name) {
        this._product_name = _product_name;
    }

    public int getResearched()
    {
        return researched;
    }

    public void setResearched(int researched)
    {
        this.researched = researched;
    }

    @SerializedName("Researched")
    public int researched;
}
