package com.euromonitor.storecheck.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.euromonitor.storecheck.BR;
import com.euromonitor.storecheck.databinding.StorecheckdetailItemBinding;
import com.euromonitor.storecheck.databinding.StorecheckoutletDetailsBinding;
import com.euromonitor.storecheck.databinding.StorecheckoutletItemBinding;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.StoreCheckDetail;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckOutletDetailAdapter extends RecyclerView.Adapter<StoreCheckOutletDetailAdapter.BindingHolder>{

    private List<Outlet> outletItems;
    private ArrayList<Outlet> allOutletItems;
    private LayoutInflater layoutInflater;

    public StoreCheckOutletDetailAdapter(List<Outlet> details){
        this.outletItems = details;
        allOutletItems = new ArrayList<>();
        allOutletItems.addAll(outletItems);
    }

    public StoreCheckOutletDetailAdapter(LayoutInflater layoutInflater, Context context, List<Outlet> details)
    {

        this.layoutInflater = layoutInflater;
        this.outletItems = details;
        allOutletItems = new ArrayList<>();
        if(outletItems!=null) {
            allOutletItems.addAll(outletItems);
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        StorecheckoutletItemBinding binding = StorecheckoutletItemBinding.inflate(layoutInflater, parent,false);

        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        Outlet current = outletItems.get(position);
        StorecheckoutletItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setVariable(BR.storeCheckOutlet, current);

    }

    @Override
    public int getItemCount() {
        Log.e("outlet count", String.valueOf(outletItems.size()));
        return outletItems.size();

    }

    public void filterByOutLet(String outletName) {
        outletName = outletName.toLowerCase(Locale.getDefault());
        outletItems.clear();
        if (outletName.length() == 0) {
            outletItems.addAll(allOutletItems);
        }
        else
        {
            for (Outlet wp : allOutletItems)
            {
                if (wp.get_outlet_Name().toLowerCase(Locale.getDefault()).contains(outletName))
                {
                    outletItems.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        private int position;
        Outlet current;

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
