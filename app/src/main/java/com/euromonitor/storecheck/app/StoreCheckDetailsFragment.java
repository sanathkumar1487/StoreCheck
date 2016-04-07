package com.euromonitor.storecheck.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.adapter.StoreCheckDetailAdapter;
import com.euromonitor.storecheck.model.StoreCheckDetail;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckDetailsFragment extends Fragment {
    public StoreCheckDetailAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.storecheck_details, container, false);
        setUpStoreCheckDetails(view);
        return view;
    }

    public void setUpStoreCheckDetails(View view){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.storecheckDetailsView);
        Context context = this.getActivity();

        adapter = new StoreCheckDetailAdapter(context, StoreCheckDetail.getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }



}