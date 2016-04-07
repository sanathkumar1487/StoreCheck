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

    public StoreCheckDetailAdapter(List<StoreCheckDetail> details){
        this.storeCheckItems = details;
        allStoreCheckItems = new ArrayList<>();
        allStoreCheckItems.addAll(storeCheckItems);
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storecheckdetail_item, parent, false);
        BindingHolder holder = new BindingHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
            StoreCheckDetail current = storeCheckItems.get(position);
            holder.getBinding().setVariable(BR.storeCheckDetail, current);
            holder.getBinding().executePendingBindings();
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
                if (wp.getProductName().toLowerCase(Locale.getDefault()).contains(productName))
                {
                    storeCheckItems.add(wp);
                }
            }
        }

        notifyDataSetChanged();
    }


    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        /*
        TextView productName;
        TextView productPrice;
        TextView packSize;
        TextView itemsPerPack;*/

        private int position;
        StoreCheckDetail current;

        public BindingHolder(View v){
            super(v);
            binding = DataBindingUtil.bind(v);
        }



        public ViewDataBinding getBinding(){return binding;}


        /*
        public ItemsViewHolder(View itemView) {
            super(itemView);
            productName = (TextView)itemView.findViewById(R.id.productName);
            productPrice = (TextView)itemView.findViewById(R.id.price);

            packSize = (TextView)itemView.findViewById(R.id.packSize);
            itemsPerPack = (TextView)itemView.findViewById(R.id.itemsPerPack);
        }

        public  void setData(StoreCheckDetail current, int position){
            this.productName.setText(current.getProductName());

            this.productPrice.setText(current.getCurrency() + " " + current.getPrice().toString());
            this.packSize.setText(current.getPackSize() + " " + current.getPackUnit());
            itemsPerPack.setText(current.getPackSize() + "X" + current.getItemsPerPack());
        }*/
    }
}
