package com.euromonitor.storecheck.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.melnykov.fab.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.euromonitor.storecheck.utility.DatabaseHelper;

/**
 * Created by Sanath.Kumar on 4/20/2016.
 */
public class OutletDetailsActivity extends AppCompatActivity {
    public StoreCheckOutletDetailAdapter adapter;
    private StoreCheckDetail detail = new StoreCheckDetail();
    StorecheckoutletDetailsBinding binding;
    Intent intent;
    private SearchView.OnQueryTextListener queryTextListener;
    DrawerLayout mDrawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    DatabaseHelper db;
    FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.storecheckoutlet_details);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.manageOutlet_Drawer);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setColorNormal(getResources().getColor(R.color.blue_300));
        db = new DatabaseHelper(this);
        setupToolbar();
        setUpNavigationView();
        if (db.isDatabaseAvailable()) {

            View view = binding.getRoot();
            setUpStoreCheckDetails(view);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loadAddOutletScreen();
                }
            });
        } else {

            Toast.makeText(this, "Please import EMMA generated file to proceed!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.outletdetails_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchItem).getActionView();
        if (searchView != null) {
            if (db.isDatabaseAvailable()) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                queryTextListener = new SearchView.OnQueryTextListener() {
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
            } else {
                //  Toast.makeText(this, "Please import EMMA generated file to proceed!", Toast.LENGTH_LONG).show();

            }
        }
        return true;
    }

    RecyclerView recyclerView;

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
                intent.putExtra("outlet", detail);
                intent.putExtra("isnew", false);
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
                if (db.isDatabaseAvailable()) {
                    loadAddOutletScreen();
                    return true;
                } else {
                    Toast.makeText(this, "Please import EMMA generated file to proceed!", Toast.LENGTH_LONG).show();

                }
            case R.id.Refresh:
                refreshData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadAddOutletScreen() {
        AddOutletActivity activity = new AddOutletActivity();
        intent = new Intent(OutletDetailsActivity.this, AddOutletActivity.class);
        intent.putExtra("isnew", true);
        startActivity(intent);
    }

    private void refreshData() {
        db = new DatabaseHelper(this);
        if (db.isDatabaseAvailable()) {
            adapter = new StoreCheckOutletDetailAdapter(this.getLayoutInflater(), this);
            recyclerView.setAdapter(adapter);
            recyclerView.invalidate();
        } else {
            //Toast.makeText(this,"ssss",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    public void setupToolbar() {

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.manageOutletToolBar);
        setSupportActionBar(toolbar);
        // actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Outlet Details");
        toolbar.setTitle("Store-check details");
        toolbar.inflateMenu(R.menu.storecheck_menu);
    }

    private void setUpNavigationView() {
        try {
            Fragment navFragment = StoreCheckNavigationFragment.newInstance(mDrawerLayout.getId(), toolbar.getId());

            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.navDrawerFrame, navFragment, "Nav");
            ft.commit();

        } catch (Exception e) {
            throw e;
        }
    }
}

