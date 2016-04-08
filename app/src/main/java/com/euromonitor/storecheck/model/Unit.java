package com.euromonitor.storecheck.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Noora.Akhtar on 08/04/2016.
 */
public class Unit {
    int _id;

    @SerializedName("UnitId")
    private String _unit_id;

    @SerializedName("UnitName")
    private String _unit_name;

    @SerializedName("UnitBase")
    private String _unit_base;

    @SerializedName("UnitMultiplier")
    private String _unit_multiplier;


    public Unit(String _unit_id, String _unit_name, String _unit_base, String _unit_multiplier) {
        this._unit_id = _unit_id;
        this._unit_name = _unit_name;
    }

    public int get_id() {

        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_unit_id(String _unit_id) {
        this._unit_id = _unit_id;
    }

    public String get_unit_id() {
        return _unit_id;
    }

    public void set_unit_name(String _unit_name) {
        this._unit_name = _unit_name;
    }

    public String get_unit_name() {
        return _unit_name;
    }

    public void set_unit_base(String _unit_base) {
        this._unit_base = _unit_base;
    }

    public String get_unit_base() {
        return _unit_base;
    }

    public String get_unit_multiplier() {
        return _unit_multiplier;
    }

    public void set_unit_multiplier(String _unit_multiplier) {
        this._unit_multiplier = _unit_multiplier;
    }

}