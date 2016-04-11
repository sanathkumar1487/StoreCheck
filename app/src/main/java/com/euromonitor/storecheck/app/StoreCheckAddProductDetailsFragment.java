package com.euromonitor.storecheck.app;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.databinding.StorecheckdetailItemBinding;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckAddProductDetailsFragment extends Fragment {

    public static int priceId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storecheck_productdetails, container, false);

        Log.e("PriceID", String.valueOf(StoreCheckAddProductDetailsFragment.priceId));

        return view;
    }
}