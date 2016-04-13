package com.euromonitor.storecheck.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by Sanath.Kumar on 3/31/2016.
 */
public class CustomField  extends BaseObservable {

    final String DropDown = "1";
    final String TextBox = "2";
    final String CustomDropDown = "3";
    final String CustomCompanyID = "4";

    private int _id;

    @SerializedName("ProjectId")
    private String _project_id;

    @SerializedName("ProductCode")
    private String _product_code;

    @SerializedName("CTTCode")
    private String _ctt_code;

    @SerializedName("GroupId")
    private String _group_id;

    @SerializedName("Label")
    private String _label;

    @SerializedName("ObjectId")
    private String _object_id;

    @SerializedName("Options")
    private Option[] _options;

    @SerializedName("Tooltip")
    private String _tooltip;

    private String customFieldTextValue;

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    private String uniqueID;

    public CustomField(String _project_id, String _product_code, String _ctt_code, String _group_id, String _label, String _object_id, Option[] _options, String _tooltip) {
        this._project_id = _project_id;
        this._product_code = _product_code;
        this._ctt_code = _ctt_code;
        this._group_id = _group_id;
        this._label = _label;
        this._object_id = _object_id;
        this._options = _options;
        this._tooltip = _tooltip;

    }

    public CustomField() {


    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_project_id() {
        return _project_id;
    }

    public void generateUniqueID() {
        uniqueID = UUID.randomUUID().toString();
        for (int i = 0; i < _options.length; i++) {
            _options[i].setUniqueID(uniqueID);
        }
    }

    public void set_project_id(String _project_id) {
        this._project_id = _project_id;
    }

    public String get_product_code() {
        return _product_code;
    }

    public void set_product_code(String _product_code) {
        this._product_code = _product_code;
    }

    public String get_ctt_code() {
        return _ctt_code;
    }

    public void set_ctt_code(String _ctt_code) {
        this._ctt_code = _ctt_code;
    }

    public String get_group_id() {
        return _group_id;
    }

    public void set_group_id(String _group_id) {
        this._group_id = _group_id;
    }

    public String get_label() {
        return _label;
    }

    @Bindable
    public String getLabel() {
        return _label;
    }

    @Bindable
    public boolean getIsTextVisible() {
        return this._object_id.equals(TextBox);
    }

    @Bindable
    public boolean getIsOptionVisible() {
        return this._object_id.equals(DropDown);
    }

    public void set_label(String _label) {
        this._label = _label;
    }

    public String get_object_id() {
        return _object_id;
    }

    public void set_object_id(String _object_id) {
        this._object_id = _object_id;
    }

    public Option[] get_options() {
        return _options;
    }

    public void set_options(Option[] _options) {
        this._options = _options;
    }

    public String get_tooltip() {
        return _tooltip;
    }

    public void set_tooltip(String _tooltip) {
        this._tooltip = _tooltip;
    }

    public void setCustomFieldTextValue(String customFieldTextValue){
        this.customFieldTextValue = customFieldTextValue;
    }

    @Bindable
    public String getCustomFieldTextValue(){
        return this.customFieldTextValue;
    }

}
