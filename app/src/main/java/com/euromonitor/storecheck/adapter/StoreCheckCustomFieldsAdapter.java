package com.euromonitor.storecheck.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.euromonitor.storecheck.BR;
import com.euromonitor.storecheck.databinding.StorecheckCustomfieldItemBinding;
import com.euromonitor.storecheck.model.CustomField;
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
    }

    @Override
    public int getItemCount() {
        return customFields.size();
    }

    public class BindingHolder  extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        private int position;
        StoreCheckDetail current;

        public BindingHolder(View v){
            super(v);
            binding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding(){
            //return binding;
            return DataBindingUtil.getBinding(itemView);
        }
    }
}
