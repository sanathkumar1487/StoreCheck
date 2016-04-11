package com.euromonitor.storecheck.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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



/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckDetailsFragment extends Fragment {

    public StoreCheckDetailAdapter adapter;
    private StoreCheckDetail detail = new StoreCheckDetail();
    private int position;
    StorecheckDetailsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StorecheckDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setUpStoreCheckDetails(view);
        return view;
    }

    public void setUpStoreCheckDetails(View view){



        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.storecheckDetailsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new StoreCheckDetailAdapter(getActivity().getLayoutInflater(), getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Fragment fragment = new StoreCheckAddProductDetailsFragment();

                StoreCheckAddProductDetailsFragment.priceId = 1;

                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.replace(R.id.containerFrame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }



}