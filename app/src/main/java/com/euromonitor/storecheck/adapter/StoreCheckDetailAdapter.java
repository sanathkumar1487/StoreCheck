package com.euromonitor.storecheck.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.euromonitor.storecheck.BR;
import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.databinding.StorecheckdetailItemBinding;
import com.euromonitor.storecheck.model.StoreCheckDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckDetailAdapter extends RecyclerView.Adapter<StoreCheckDetailAdapter.BindingHolder>{

    private List<StoreCheckDetail> storeCheckItems;
    private ArrayList<StoreCheckDetail> allStoreCheckItems;
    private LayoutInflater layoutInflater;

    public StoreCheckDetailAdapter(List<StoreCheckDetail> details){
        this.storeCheckItems = details;
        allStoreCheckItems = new ArrayList<>();
        allStoreCheckItems.addAll(storeCheckItems);
    }

    public StoreCheckDetailAdapter(LayoutInflater layoutInflater, Context context){
        this.layoutInflater = layoutInflater;
        this.storeCheckItems = StoreCheckDetail.getData(context);
        allStoreCheckItems = new ArrayList<>();
        allStoreCheckItems.addAll(storeCheckItems);
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        StorecheckdetailItemBinding binding = StorecheckdetailItemBinding.inflate(layoutInflater, parent,false);

        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        StoreCheckDetail current = storeCheckItems.get(position);
        StorecheckdetailItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setVariable(BR.storeCheckDetail, current);

    }

    @Override
    public int getItemCount() {
        return storeCheckItems.size();
    }

    public void filterByProduct(String productName) {
        productName = productName.toLowerCase(Locale.getDefault());
        storeCheckItems.clear();
        if (productName.length() == 0) {
            storeCheckItems.addAll(allStoreCheckItems);
        }
        else
        {
            for (StoreCheckDetail wp : allStoreCheckItems)
            {
                if (wp.getBrand().toLowerCase(Locale.getDefault()).contains(productName))
                {
                    storeCheckItems.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    public static class BindingHolder extends RecyclerView.ViewHolder {

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
