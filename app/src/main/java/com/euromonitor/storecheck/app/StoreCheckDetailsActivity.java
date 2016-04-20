package com.euromonitor.storecheck.app;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Binder;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.adapter.StoreCheckDetailAdapter;
import com.euromonitor.storecheck.databinding.StorecheckDetailsBinding;
import com.euromonitor.storecheck.databinding.StorecheckdetailItemBinding;
import com.euromonitor.storecheck.listener.ClickListener;
import com.euromonitor.storecheck.listener.RecyclerTouchListener;
import com.euromonitor.storecheck.model.StoreCheckDetail;

public class StoreCheckDetailsActivity extends MainActivity
{
    public StoreCheckDetailAdapter adapter;
    private StoreCheckDetail detail = new StoreCheckDetail();
    private int position;
    StorecheckDetailsBinding binding;
    Context context;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.storecheck_details);
        // setContentView(R.layout.storecheckdetail_item);
        View view=binding.getRoot();
        setUpStoreCheckDetails(view);

    }

    public void setUpStoreCheckDetails(View view)
    {

        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.storecheckDetailsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StoreCheckDetailAdapter(this.getLayoutInflater(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getApplicationContext(), recyclerView, new ClickListener()
        {
            @Override
            public void onClick(View view,int position) {

                StorecheckdetailItemBinding binding = DataBindingUtil.getBinding(view);
                StoreCheckDetail detail = binding.getStoreCheckDetail();
                if (detail != null)
                {

                    StoreCheckAddProductDetailsActivity.priceId = detail.getPriceId();

                    Log.e("PricingId", String.valueOf(StoreCheckAddProductDetailsActivity.priceId));
                }
                StoreCheckAddProductDetailsActivity  activity=new StoreCheckAddProductDetailsActivity();
                intent=new Intent(StoreCheckDetailsActivity.this,StoreCheckAddProductDetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }
}
