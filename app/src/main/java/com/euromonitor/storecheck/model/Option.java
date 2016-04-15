package com.euromonitor.storecheck.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sanath.Kumar on 3/31/2016.
 */
public class Option {

    private boolean _is_dropdown;
    private String OptionId;
    private String OptionName;
    private String MinimumAllowed;
    private String MaximumAllowed;
    private String customFieldID;
    private String isNumeric;
    private String isZeroAllowed;

    public String getUniqueID() {
        return customFieldID;
    }

    public void setUniqueID(String uniqueID) {
        this.customFieldID = uniqueID;
    }



    private  Option[]  DropdownOption;

    public Option(boolean _is_dropdown, String optionId, String optionName, String minimumAllowed, String maximumAllowed, String isNumeric, String isZeroAllowed) {
        this._is_dropdown = _is_dropdown;
        OptionId = optionId;
        OptionName = optionName;
        MinimumAllowed = minimumAllowed;
        MaximumAllowed = maximumAllowed;
        this.isZeroAllowed = isZeroAllowed;
        this.isNumeric = isNumeric;
    }

    public  Option()
    {

    }

    public boolean is_is_dropdown() {
        return _is_dropdown;
    }

    public void set_is_dropdown(boolean _is_dropdown) {
        this._is_dropdown = _is_dropdown;
    }

    public String getOptionId() {
        return OptionId;
    }

    public void setOptionId(String optionId) {
        OptionId = optionId;
    }

    public String getOptionName() {
        return OptionName;
    }

    public void setOptionName(String optionName) {
        OptionName = optionName;
    }

    public String getMinimumAllowed() {
        return MinimumAllowed;
    }

    public void setMinimumAllowed(String minimumAllowed) {
        MinimumAllowed = minimumAllowed;
    }

    public String getMaximumAllowed() {
        return MaximumAllowed;
    }

    public void setMaximumAllowed(String maximumAllowed) {
        MaximumAllowed = maximumAllowed;
    }

    public String getIsNumeric(){
        return isNumeric;
    }

    public String getIsZeroAllowed(){
        return isZeroAllowed;
    }

    public void setIsNumeric(String isNumeric){
        this.isNumeric = isNumeric;
    }

    public void setIsZeroAllowed(String isZeroAllowed){
        this.isZeroAllowed = isZeroAllowed;
    }
}
