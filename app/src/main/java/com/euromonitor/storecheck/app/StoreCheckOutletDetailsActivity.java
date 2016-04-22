package com.euromonitor.storecheck.app;

import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.adapter.StoreCheckOutletDetailAdapter;
import com.euromonitor.storecheck.databinding.StorecheckoutletDetailsBinding;
import com.euromonitor.storecheck.databinding.StorecheckoutletItemBinding;
import com.euromonitor.storecheck.listener.ClickListener;
import com.euromonitor.storecheck.listener.RecyclerTouchListener;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.StoreCheckDetail;

/**
 * Created by Sanath.Kumar on 4/20/2016.
 */
public class StoreCheckOutletDetailsActivity extends MainActivity {

    public StoreCheckOutletDetailAdapter adapter;
    private StoreCheckDetail detail = new StoreCheckDetail();
    private int position;
    StorecheckoutletDetailsBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.i("onCreate","called from StoreCheckOutletDetailsActivity................................................");
        binding = DataBindingUtil.setContentView(this, R.layout.storecheckoutlet_details);

        View view=binding.getRoot();
        setUpStoreOutletDetails(view);
    }


    public void setUpStoreOutletDetails(View view){
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.storecheckOutletDetailsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new StoreCheckOutletDetailAdapter(this.getLayoutInflater(),this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                StorecheckoutletItemBinding binding = DataBindingUtil.getBinding(view);
                Outlet detail = binding.getStoreCheckOutlet();

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }
}