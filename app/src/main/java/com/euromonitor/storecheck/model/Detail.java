package com.euromonitor.storecheck.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sanath.Kumar on 3/31/2016.
 */
public class Detail {

    private int _id;

    @SerializedName("channelName")
    private  String _channel_name;

    @SerializedName("multipackSize")
    private  String _multipack_size;

    @SerializedName("outletId")
    private  String _outlet_id;

    @SerializedName("outletName")
    private  String _outlet_name;

    @SerializedName("packSize")
    private  String _pack_size;

    @SerializedName("packType")
    private  String _pack_type;

    @SerializedName("packTypeCode")
    private  String _packtype_code;

    @SerializedName("price")
    private  String _price;

    @SerializedName("pricingId")
    private  String _pricing_id;

    @SerializedName("unitCode")
    private  String _unit_code;

    @SerializedName("unitPriceLocal")
    private  String _unit_price_local;

    @SerializedName("unitPriceUS")
    private  String _unit_price_us;

    @SerializedName("brand")
    private  String _brand;

    @SerializedName("nbo")
    private  String _nbo;

    public Detail(String _channel_name, String _multipack_size, String _outlet_id, String _outlet_name, String _pack_size, String _pack_type, String _packtype_code, String _price, String _pricing_id, String _unit_code, String _unit_price_local, String unitPriceUS, String _brand, String nbo) {
        this.set_channel_name(_channel_name);
        this.set_multipack_size(_multipack_size);
        this.set_outlet_id(_outlet_id);
        this.set_outlet_name(_outlet_name);
        this.set_pack_size(_pack_size);
        this.set_pack_type(_pack_type);
        this.set_packtype_code(_packtype_code);
        this.set_price(_price);
        this.set_pricing_id(_pricing_id);
        this.set_unit_code(_unit_code);
        this.set_unit_price_local(_unit_price_local);
        this.set_unit_price_us(unitPriceUS);
        this.set_brand(_brand);
        this.set_nbo(nbo);
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_channel_name() {
        return _channel_name;
    }

    public void set_channel_name(String _channel_name) {
        this._channel_name = _channel_name;
    }

    public String get_multipack_size() {
        return _multipack_size;
    }

    public void set_multipack_size(String _multipack_size) {
        this._multipack_size = _multipack_size;
    }

    public String get_outlet_id() {
        return _outlet_id;
    }

    public void set_outlet_id(String _outlet_id) {
        this._outlet_id = _outlet_id;
    }

    public String get_outlet_name() {
        return _outlet_name;
    }

    public void set_outlet_name(String _outlet_name) {
        this._outlet_name = _outlet_name;
    }

    public String get_pack_size() {
        return _pack_size;
    }

    public void set_pack_size(String _pack_size) {
        this._pack_size = _pack_size;
    }

    public String get_pack_type() {
        return _pack_type;
    }

    public void set_pack_type(String _pack_type) {
        this._pack_type = _pack_type;
    }

    public String get_packtype_code() {
        return _packtype_code;
    }

    public void set_packtype_code(String _packtype_code) {
        this._packtype_code = _packtype_code;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
    }

    public String get_pricing_id() {
        return _pricing_id;
    }

    public void set_pricing_id(String _pricing_id) {
        this._pricing_id = _pricing_id;
    }

    public String get_unit_code() {
        return _unit_code;
    }

    public void set_unit_code(String _unit_code) {
        this._unit_code = _unit_code;
    }

    public String get_unit_price_local() {
        return _unit_price_local;
    }

    public void set_unit_price_local(String _unit_price_local) {
        this._unit_price_local = _unit_price_local;
    }

    public String get_unit_price_us() {
        return _unit_price_us;
    }

    public void set_unit_price_us(String _unit_price_us) {
        this._unit_price_us = _unit_price_us;
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
}
