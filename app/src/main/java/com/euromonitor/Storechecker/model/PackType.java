package com.euromonitor.Storechecker.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Sanath.Kumar on 25-Apr-16.
 */
public class PackType {


    private int _id;

    @SerializedName("PackTypeCode")
    private String _packtypecode;

    @SerializedName("PackTypeName")
    private  String _packtypename;


    @SerializedName("ProductCodes")
    private ArrayList<String> _productcodes;

    public PackType(){

    }

    public PackType(int _id, String _packtypecode, String _packtypename, ArrayList<String> _productcodes) {
        this._id = _id;
        this._packtypecode = _packtypecode;
        this._packtypename = _packtypename;
        this._productcodes = _productcodes;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_packtypecode() {
        return _packtypecode;
    }

    public void set_packtypecode(String _packtypecode) {
        this._packtypecode = _packtypecode;
    }

    public String get_packtypename() {
        return _packtypename;
    }

    public void set_packtypename(String _packtypename) {
        this._packtypename = _packtypename;
    }

    public ArrayList<String> get_productcodes() {
        return _productcodes;
    }

    public void set_productcodes(ArrayList<String> _productcodes) {
        this._productcodes = _productcodes;
    }


}
