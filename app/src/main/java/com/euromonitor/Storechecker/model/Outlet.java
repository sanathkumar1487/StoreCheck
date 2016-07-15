package com.euromonitor.Storechecker.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.euromonitor.Storechecker.utility.DatabaseHelper;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sanath.Kumar on 3/30/2016.
 */
public class Outlet extends BaseObservable implements Serializable {



    private int _id;

    @SerializedName("OutletId")
    private String _outlet_id;


    @SerializedName("OutletName")
    private String _outlet_Name;


    @SerializedName("ProjectId")
    private int _project_id;

    @SerializedName("GeographyCode")
    private String _geo_code;


    @SerializedName("CHCCode")
    private  String _chccode;

    @SerializedName("Geography")
    private String _geo_name;

    @SerializedName("Industry")
    private String _industry;

    @SerializedName("OutletCity")
    private String _outlet_city;

    @SerializedName("Updated")
    private int _updated;

    @Bindable
    public String getOutlet_city(){
        return _outlet_city;
    }

    @SerializedName("Year")
    private String _year;

    @SerializedName("OutletDate")
    private String _outlet_date;



    @SerializedName("ChannelName")
    private  String _channel_name;



    public Outlet() {

    }


    public Outlet(String _outlet_date, String _outlet_id, String _outlet_Name, int _project_id, String _geo_code, String _chccode, String _geo_name, String _industry, String _outlet_city, String _year, String _channel_name,int _updated )
    {

        this._outlet_date = _outlet_date;
        this._outlet_id = _outlet_id;
        this._outlet_Name = _outlet_Name;
        this._project_id = _project_id;
        this._geo_code = _geo_code;
        this._chccode = _chccode;
        this._geo_name = _geo_name;
        this._industry = _industry;
        this._outlet_city = _outlet_city;
        this._year = _year;
        this._channel_name = _channel_name;
        this._updated =  _updated;
    }

    @Bindable
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Bindable
    public String get_outlet_id() {
        return _outlet_id;
    }

    public void set_outlet_id(String _outlet_id) {
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

    @Bindable
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

    @Bindable
    public String get_outlet_date() {
        return _outlet_date;
    }

    public void set_outlet_date(String _outlet_date) {
        this._outlet_date = _outlet_date;
    }

    @Bindable
    public String get_chccode() {
        return _chccode;
    }

    public void set_chccode(String _chccode) {
        this._chccode = _chccode;
    }

//    public  static ArrayList<Outlet> getData(Context context){
//        DatabaseHelper dbHelper = new DatabaseHelper(context);
//        return dbHelper.getOutlets(false);
//    }

    public  int get_updated()
    {
        return _updated;
    }
    public void set_updated(int _updated )
    {
        this._updated=_updated;
    }
    @Bindable
    public String get_outlet_Name() {
        return _outlet_Name;
    }

    public void set_outlet_Name(String _outlet_Name) {
        this._outlet_Name = _outlet_Name;
    }

    @Bindable
    public String get_channel_name() {
        return _channel_name;
    }

    public void set_channel_name(String _channel_name) {
        this._channel_name = _channel_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

}
