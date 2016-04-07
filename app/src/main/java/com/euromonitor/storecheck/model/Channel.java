package com.euromonitor.storecheck.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sanath.Kumar on 4/6/2016.
 */
public class Channel {

    private  int _id;

    @SerializedName("ChcCode")
    private  String _chc_code;

    @SerializedName("ChcName")
    private  String _chc_name;


    public Channel(String _chc_code, String _chc_name) {
        this._chc_code = _chc_code;
        this._chc_name = _chc_name;
    }


    public int get_id() {
        return _id;
    }


    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_chc_code() {
        return _chc_code;
    }

    public void set_chc_code(String _chc_code) {
        this._chc_code = _chc_code;
    }

    public String get_chc_name() {
        return _chc_name;
    }

    public void set_chc_name(String _chc_name) {
        this._chc_name = _chc_name;
    }
}
