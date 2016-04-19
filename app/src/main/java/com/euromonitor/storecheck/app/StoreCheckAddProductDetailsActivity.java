package com.euromonitor.storecheck.app;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.databinding.StorecheckProductdetailsBinding;
import com.euromonitor.storecheck.model.ProductDetail;
/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class StoreCheckAddProductDetailsActivity extends MainActivity
{
    public static int priceId;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StorecheckProductdetailsBinding binding= DataBindingUtil.setContentView(this, R.layout.storecheck_productdetails);
        setContentView(R.layout.storecheck_productdetails);
        ProductDetail prod=new ProductDetail();

        prod.setPrice(9.0);
        prod.setPack_size(13.0);
        binding.setProductDetails(prod);
    }
}
