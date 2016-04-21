package com.euromonitor.storecheck.app;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
 * Created by Sanath.Kumar on 4/20/2016.
 */
public class OutletDetailsActivity extends MainActivity {
    public StoreCheckOutletDetailAdapter adapter;
    private StoreCheckDetail detail = new StoreCheckDetail();
    private int position;
    StorecheckoutletDetailsBinding binding;
    Context context;
    Intent intent;
    private SearchView.OnQueryTextListener queryTextListener;

    public OutletDetailsActivity()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.storecheckoutlet_details);

        View view = binding.getRoot();
        setUpStoreCheckDetails(view);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.storecheck_menu, menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView)menu.findItem(R.id.searchItem).getActionView();

        if(searchView!=null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener(){
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText != null) {
                        adapter.filterByOutLet(newText);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return true;
    }


    RecyclerView recyclerView ;
    public void setUpStoreCheckDetails(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.storecheckOutletDetailsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StoreCheckOutletDetailAdapter(this.getLayoutInflater(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                StorecheckoutletItemBinding binding = DataBindingUtil.getBinding(view);
                Outlet detail = binding.getStoreCheckOutlet();

                intent = new Intent(OutletDetailsActivity.this, AddOutletActivity.class);
                intent.putExtra("outlet",detail);
                intent.putExtra("isnew",false);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AddNew:
                loadAddOutletScreen();
                return true;
            case  R.id.Refresh:
                 refreshData();
                  return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void loadAddOutletScreen() {
        AddOutletActivity activity = new AddOutletActivity();
        intent = new Intent(OutletDetailsActivity.this, AddOutletActivity.class);
        intent.putExtra("isnew",true);
        startActivity(intent);
    }


    private void refreshData() {

        adapter = new StoreCheckOutletDetailAdapter(this.getLayoutInflater(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }
}

