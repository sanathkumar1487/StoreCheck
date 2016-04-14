package com.euromonitor.storecheck.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.euromonitor.storecheck.BR;
import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.databinding.StorecheckCustomfieldItemBinding;
import com.euromonitor.storecheck.model.CustomField;
import com.euromonitor.storecheck.model.Option;
import com.euromonitor.storecheck.model.StoreCheckDetail;

import java.util.ArrayList;

/**
 * Created by Noora.Akhtar on 13/04/2016.
 */
public class StoreCheckCustomFieldsAdapter extends RecyclerView.Adapter<StoreCheckCustomFieldsAdapter.BindingHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<CustomField> customFields;

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
    public void onBindViewHolder(BindingHolder holder, int position) {
        final CustomField current = customFields.get(position);
        StorecheckCustomfieldItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setVariable(BR.customField, current);

        OptionsAdapter optionsAdapter = new OptionsAdapter(current.get_options(),layoutInflater);

        holder.optionsSpinner.setAdapter(optionsAdapter);
        holder.optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*try {
                    Option currentOption = current.get_options().get(position);

                    if (current.getFrameGroupID() > 0) {
                        String frameGroupId = String.valueOf(current.getFrameGroupID());

                        for (int index = 0; index < customFields.size(); index++) {
                            if (customFields.get(index).get_group_id().equals(frameGroupId)) {
                                boolean isEnabled = !(currentOption.getMinimumAllowed().equals("0") && currentOption.getMaximumAllowed().equals("0"));

                                customFields.get(index).setIsEnabled(isEnabled);
                                customFields.get(index).setCurrentOptionId(Integer.valueOf(currentOption.getOptionId()));
                                notifyCurrentDataSet();

                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    Log.e("selection", e.getMessage());
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void notifyCurrentDataSet(){
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return customFields.size();
    }

    public class BindingHolder  extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        Spinner optionsSpinner;

        private int position;
        StoreCheckDetail current;

        public BindingHolder(View v){
            super(v);
            binding = DataBindingUtil.bind(v);
            optionsSpinner = (Spinner)v.findViewById(R.id.options);
        }

        public ViewDataBinding getBinding(){
            //return binding;
            return DataBindingUtil.getBinding(itemView);
        }
    }

    public class OptionsAdapter extends BaseAdapter implements SpinnerAdapter{
        private ArrayList<Option> options;
        LayoutInflater layoutInflater;

        public OptionsAdapter(ArrayList<Option> options, LayoutInflater layoutInflater){
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
            return Long.valueOf(options.get(position).getOptionId());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View optionItemView;
            if (convertView != null){
                optionItemView = convertView;
            } else {
                optionItemView = layoutInflater.inflate(R.layout.storecheck_productitem, parent, false);
            }
            TextView productItem = (TextView)optionItemView.findViewById(R.id.productItem);

            productItem.setText(options.get(position).getOptionName());
            return optionItemView;
        }
    }
}
