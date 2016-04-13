package com.euromonitor.storecheck.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        CustomField current = customFields.get(position);
        StorecheckCustomfieldItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setVariable(BR.customField, current);

        OptionsAdapter optionsAdapter = new OptionsAdapter(current.get_options(),layoutInflater);

        holder.optionsSpinner.setAdapter(optionsAdapter);
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
