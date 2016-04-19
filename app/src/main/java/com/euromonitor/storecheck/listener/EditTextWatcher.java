package com.euromonitor.storecheck.listener;

import android.text.Editable;
import android.text.TextWatcher;

import com.euromonitor.storecheck.model.CustomField;

/**
 * Created by Noora.Akhtar on 4/19/2016.
 */
public class EditTextWatcher implements TextWatcher {

    private CustomField customField;
    private int position;

    public EditTextWatcher(CustomField customField){
        this.customField = customField;
    }

    public void updatePosition(int position){
        this.position = position;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newValue = "";
        if(s!=null){
            newValue = s.toString();
        }
        customField.setCustomFieldTextValue(newValue);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
