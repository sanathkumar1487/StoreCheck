package com.euromonitor.Storechecker.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.euromonitor.Storechecker.BR;
import com.euromonitor.Storechecker.R;
import com.euromonitor.Storechecker.databinding.StorecheckCustomfieldItemBinding;
import com.euromonitor.Storechecker.listener.EditTextWatcher;
import com.euromonitor.Storechecker.model.CustomField;
import com.euromonitor.Storechecker.model.Option;
import com.euromonitor.Storechecker.model.StoreCheckDetail;

import java.util.ArrayList;

/**
 * Created by Noora.Akhtar on 13/04/2016.
 */

public class StoreCheckCustomFieldsAdapter extends RecyclerView.Adapter<StoreCheckCustomFieldsAdapter.BindingHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<CustomField> customFields;
    CustomField current;

    public StoreCheckCustomFieldsAdapter(LayoutInflater layoutInflater, Context context, ArrayList<CustomField> customFields) {
        this.layoutInflater = layoutInflater;
        this.customFields = customFields;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StorecheckCustomfieldItemBinding binding = StorecheckCustomfieldItemBinding.inflate(layoutInflater, parent, false);

        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final BindingHolder holder, int position) {
        current = customFields.get(position);
        StorecheckCustomfieldItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setVariable(BR.customField, current);
        OptionsAdapter optionsAdapter = new OptionsAdapter(current.get_options(), layoutInflater);
        holder.optionsSpinner.setAdapter(optionsAdapter);
        if (current.get_object_id().equals("1") || current.get_object_id().equals("3")) {
            if (current.getSelectedOption() == null && current.get_options().size() > 0) {
                current.setSelectedOption(current.get_options().get(0));
            }
            holder.optionsSpinner.setSelection(optionsAdapter.getPositionById(current.getSelectedOption().getOptionId()));


            holder.optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CustomField currentCustomField = null;
                    Option selectedOption = (Option) parent.getItemAtPosition(position);
                    for (CustomField cf : customFields) {
                        if (cf.getUniqueID().equals(selectedOption.getUniqueID())) {
                            currentCustomField = cf;
                            break;
                        }
                    }

                    if (currentCustomField != null) {
                        String frameGroupId = String.valueOf(currentCustomField.getFrameGroupID());
                        for (int index = 0; index < customFields.size(); index++) {
                            if (customFields.get(index).get_group_id().equals((frameGroupId))) {
                                customFields.get(index).setIsEnabled(!(selectedOption.getMinimumAllowed().equals("0")
                                        && selectedOption.getMaximumAllowed().equals("0")));

                                if (!customFields.get(index).getIsEnabled()) {
                                    customFields.get(index).setCustomFieldTextValue("");
                                }

                               // customFields.get(index).setSelectedOption(selectedOption);
                                String isNumeric = selectedOption.getIsNumeric();

                                if (isNumeric.equals("1")) {
                                    customFields.get(index).setIsNumeric(true);
                                    holder.optionName.setSelectAllOnFocus(true);
                                } else if (isNumeric.equals("0")) {
                                    customFields.get(index).setIsNumeric(false);
                                    holder.optionName.setSelectAllOnFocus(false);
                                }

                                notifyItemChangedAtPosition(index);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else if (current.get_object_id().equals("2")) {
            holder.optionName.addTextChangedListener(new EditTextWatcher(current));
        }
    }

    private void notifyItemChangedAtPosition(int position) {
        this.notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return customFields.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        Spinner optionsSpinner;
        public EditText optionName;

        private int position;
        StoreCheckDetail current;

        public BindingHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
            optionsSpinner = (Spinner) v.findViewById(R.id.options);
            optionName = (EditText) v.findViewById(R.id.optionName);
            optionName.setTextColor(Color.parseColor("#424242"));
        }

        public ViewDataBinding getBinding() {
            //return binding;
            return DataBindingUtil.getBinding(itemView);
        }
    }

    public class OptionsAdapter extends BaseAdapter implements SpinnerAdapter {
        private ArrayList<Option> options;
        LayoutInflater layoutInflater;

        public OptionsAdapter(ArrayList<Option> options, LayoutInflater layoutInflater) {
            this.options = options;
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return options.size();
        }

        @Override
        public Object getItem(int position) {
            return options.get(position);
        }

        @Override
        public long getItemId(int position) {
            return Long.valueOf(options.get(position).getId());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View optionItemView;
            if (convertView != null) {
                optionItemView = convertView;
            } else {
                optionItemView = layoutInflater.inflate(R.layout.storecheck_productitem, parent, false);
            }
            TextView productItem = (TextView) optionItemView.findViewById(R.id.productItem);
            productItem.setText(options.get(position).getOptionName());
            return optionItemView;
        }

        public int getPositionById(String optionId) {
            int position = 0;
            for (int index = 0; index < options.size(); index++) {
                if (options.get(index).getOptionId().equals(optionId)) {
                    position = index;
                    break;
                }
            }
            return position;
        }
    }
}