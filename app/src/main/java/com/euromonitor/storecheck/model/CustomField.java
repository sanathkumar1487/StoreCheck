package com.euromonitor.storecheck.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.widget.EditText;
import android.widget.Toast;

import com.euromonitor.storecheck.BR;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by Sanath.Kumar on 3/31/2016.
 */
public class CustomField  extends BaseObservable
{

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
    private ArrayList<Option> _options;

    @SerializedName("Tooltip")
    private String _tooltip;

    private String customFieldTextValue;

    @SerializedName("FrameGroupId")
    private int frameGroupID;

    private boolean isEnabled = true;

    private int currentOptionId;

    private boolean isNumeric;

    private Option selectedOption;

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getFrameGroupID() {
        return frameGroupID;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Bindable
    public boolean getIsEnabled() {
        return isEnabled;
    }

    public int getCurrentOptionId() {
        return currentOptionId;
    }

    public void setCurrentOptionId(int currentOptionId) {
        this.currentOptionId = currentOptionId;
    }

    private String uniqueID;

    public void setIsNumeric(boolean isNumeric)
    {
        this.isNumeric = isNumeric;
    }

    @Bindable
    public boolean getIsNumeric() {
        return isNumeric;
    }

    private boolean isZeroAllowed;

    public void setIsZeroAllowed(boolean isZeroAllowed){
        this.isZeroAllowed = isZeroAllowed;
    }

    public boolean getIsZeroAllowed(){
        return isZeroAllowed;
    }

    public void setSelectedOption(Option selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Option getSelectedOption() {
        return selectedOption;
    }

    public CustomField(String _project_id, String _product_code, String _ctt_code, String _group_id, String _label, String _object_id,
                       ArrayList<Option> _options, String _tooltip, int frameGroupID)
    {
        this._project_id = _project_id;
        this._product_code = _product_code;
        this._ctt_code = _ctt_code;
        this._group_id = _group_id;
        this._label = _label;
        this._object_id = _object_id;
        this._options = _options;
        this._tooltip = _tooltip;
        this.frameGroupID = frameGroupID;

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

        Iterator iterator = _options.iterator();
        while (iterator.hasNext()) {
            Option option = (Option) iterator.next();
            option.setUniqueID(uniqueID);
        }
    }

    public void set_project_id(String _project_id)
    {
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

    public ArrayList<Option> get_options() {
        return _options;
    }

    public void set_options(ArrayList<Option> _options) {
        this._options = _options;
    }

    public String get_tooltip() {
        return _tooltip;
    }

    public void set_tooltip(String _tooltip) {
        this._tooltip = _tooltip;
    }

    public void setCustomFieldTextValue(String customFieldTextValue) {
        this.customFieldTextValue = customFieldTextValue;
    }

    public void setFrameGroupID(int frameGroupID){
        this.frameGroupID = frameGroupID;
    }

    @Bindable
    public String getCustomFieldTextValue() {
        return this.customFieldTextValue;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @SerializedName("TypeId")
    private int typeId;

    public void setSelectedOptionById(int optionId){
        for(Option option: this._options){
            if(option.getOptionId().equals(String.valueOf(optionId))){
                this.setSelectedOption(option);
                break;
            }
        }
    }

}