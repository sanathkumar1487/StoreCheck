package com.euromonitor.storecheck.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.adapter.StoreCheckDetailAdapter;
import com.euromonitor.storecheck.adapter.StoreCheckOutletDetailAdapter;

import com.euromonitor.storecheck.databinding.StorecheckDetailsBinding;
import com.euromonitor.storecheck.databinding.StorecheckdetailItemBinding;
import com.euromonitor.storecheck.databinding.StorecheckoutletDetailsBinding;
import com.euromonitor.storecheck.databinding.StorecheckoutletItemBinding;
import com.euromonitor.storecheck.listener.ClickListener;
import com.euromonitor.storecheck.listener.RecyclerTouchListener;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.StoreCheckDetail;


/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckOutletDetailsFragment extends Fragment {

    public StoreCheckOutletDetailAdapter adapter;
    private StoreCheckDetail detail = new StoreCheckDetail();
    private int position;
    StorecheckoutletDetailsBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = StorecheckoutletDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setUpStoreOutletDetails(view);
        return view;
    }

    public void setUpStoreOutletDetails(View view){
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.storecheckOutletDetailsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new StoreCheckOutletDetailAdapter(getActivity().getLayoutInflater(), getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                StorecheckoutletItemBinding binding = DataBindingUtil.getBinding(view);
                Outlet detail = binding.getStoreCheckOutlet();

                Bundle bundle = new Bundle();
                bundle.putInt("ObjectID",detail.get_outlet_id());

                StoreCheckAddOutletFragment fragment = new StoreCheckAddOutletFragment();
                fragment.setOutlet(detail);
                fragment.setArguments(bundle);

                FragmentTransaction transaction =  getActivity().getFragmentManager().beginTransaction();
                transaction.replace(R.id.containerFrame,fragment);
                transaction.addToBackStack("test");

                transaction.commit();





            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }



}