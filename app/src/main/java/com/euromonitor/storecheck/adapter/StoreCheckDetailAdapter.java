package com.euromonitor.storecheck.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.euromonitor.storecheck.BR;
import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.app.StoreCheckAddProductDetailsActivity;
import com.euromonitor.storecheck.databinding.StorecheckdetailItemBinding;
import com.euromonitor.storecheck.model.StoreCheckDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckDetailAdapter extends RecyclerView.Adapter<StoreCheckDetailAdapter.BindingHolder>
    implements View.OnClickListener {

    private List<StoreCheckDetail> storeCheckItems;
    private ArrayList<StoreCheckDetail> allStoreCheckItems;
    private LayoutInflater layoutInflater;
    Context context;
    StorecheckdetailItemBinding binding;

    public StoreCheckDetailAdapter(List<StoreCheckDetail> details) {
        this.storeCheckItems = details;
        allStoreCheckItems = new ArrayList<>();
        allStoreCheckItems.addAll(storeCheckItems);
    }

    public StoreCheckDetailAdapter(LayoutInflater layoutInflater, Context context, List<StoreCheckDetail> storeCheckItems) {
        this.layoutInflater = layoutInflater;
        this.storeCheckItems = storeCheckItems;
        allStoreCheckItems = new ArrayList<>();
        if (storeCheckItems != null) {
            allStoreCheckItems.addAll(storeCheckItems);
        }
        this.context = context;
    }


    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = StorecheckdetailItemBinding.inflate(layoutInflater, parent, false);
        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        StoreCheckDetail current = storeCheckItems.get(position);
        binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setVariable(BR.storeCheckDetail, current);

        holder.menutItems.setTag(current);
        holder.menutItems.setOnClickListener(this);

        holder.pricingDetail.setTag(current);
        holder.pricingDetail.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return storeCheckItems == null ? 0 : storeCheckItems.size();
    }

    public void filterByProduct(String productName) {
        productName = productName.toLowerCase(Locale.getDefault());
        storeCheckItems.clear();
        if (productName.length() == 0) {
            storeCheckItems.addAll(allStoreCheckItems);
        } else {
            for (StoreCheckDetail wp : allStoreCheckItems) {
                if (wp.getBrand().toLowerCase(Locale.getDefault()).contains(productName)) {
                    storeCheckItems.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    StoreCheckDetail selectedPricingItem;

    @Override
    public void onClick(View v) {
        selectedPricingItem = (StoreCheckDetail)v.getTag();
        if (v.getId() == R.id.menuItems) {
            PopupMenu popup = new PopupMenu(context, v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.pricingitem_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PricingMenuItemClickListener());
            popup.show();
        } else if (v.getId() == R.id.pricingDetail) {
            loadPricingItem(false);
        }
    }


    private void loadPricingItem( boolean isCopy) {
        StoreCheckDetail detail = selectedPricingItem;
        if (detail != null) {
            StoreCheckAddProductDetailsActivity.priceId = detail.getPriceId();
            StoreCheckAddProductDetailsActivity.itemId = detail.getItemId();
            StoreCheckAddProductDetailsActivity.brandId = detail.getBrandId();
            StoreCheckAddProductDetailsActivity.brandMarketId = detail.getBrandMarketId();
            StoreCheckAddProductDetailsActivity.brandName = detail.getBrand();
            StoreCheckAddProductDetailsActivity.productCode = detail.getProductCode();
            StoreCheckAddProductDetailsActivity.productName = detail.getProductName();
            StoreCheckAddProductDetailsActivity.isCopy = isCopy;
        }

        Intent intent = new Intent(context, StoreCheckAddProductDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    class PricingMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public PricingMenuItemClickListener () {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.copy:
                    loadPricingItem(true);
                    return true;
                case R.id.edit:
                    loadPricingItem(false);
                    return true;
                default:
            }
            return false;
        }
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        private ImageView menutItems;
        private LinearLayout pricingDetail;


        public BindingHolder(View v){
            super(v);
            binding = DataBindingUtil.bind(v);


            menutItems = (ImageView) itemView.findViewById(R.id.menuItems);
            pricingDetail = (LinearLayout)itemView.findViewById(R.id.pricingDetail);
        }

        public ViewDataBinding getBinding(){
            return DataBindingUtil.getBinding(itemView);
        }
    }
}
