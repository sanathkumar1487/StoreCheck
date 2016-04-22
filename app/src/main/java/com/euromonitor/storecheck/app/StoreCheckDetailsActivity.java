package com.euromonitor.storecheck.app;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Binder;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.adapter.StoreCheckDetailAdapter;
import com.euromonitor.storecheck.databinding.StorecheckDetailsBinding;
import com.euromonitor.storecheck.databinding.StorecheckdetailItemBinding;
import com.euromonitor.storecheck.listener.ClickListener;
import com.euromonitor.storecheck.listener.RecyclerTouchListener;
import com.euromonitor.storecheck.model.StoreCheckDetail;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;

public class StoreCheckDetailsActivity extends AppCompatActivity
{
    public StoreCheckDetailAdapter adapter;
    private StoreCheckDetail detail = new StoreCheckDetail();
    private int position;
    StorecheckDetailsBinding binding;
    Context context;
    Intent intent;
    private SearchView.OnQueryTextListener queryTextListener;
    DatabaseHelper dbHelper;
    android.support.v7.widget.Toolbar toolbar;

    StoreCheckNavigationFragment navigationFragment;
    DrawerLayout mDrawerLayout;
    Fragment fragment;
    Activity activity;

    ActionBarDrawerToggle mDrawerToggle;
    int activeView;
    final static int VIEW_DETAILS = 0;
    final static int VIEW_PRODUCT_DETAILS = 1;
    final static int ADD_BRAND = 2;
    final static int ADD_OUTLET = 3;
    final static int IMPORT_STORECHECK_DETAILS = 4;
    final static int EXPORT_STORECHECK_DETAILS = 5;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.storecheck_details);
        context = this;
        dbHelper = new DatabaseHelper(context);
        View view=binding.getRoot();
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
        setupToolbar();
        setUpNavigationView();
        if(dbHelper.isDatabaseAvailable())
        {
            setUpStoreCheckDetails(view);
        }else {
            Toast.makeText(this.getBaseContext(), "Please import EMMA generated file to proceed!", Toast.LENGTH_LONG).show();
        }
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
                        adapter.filterByProduct(newText);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.searchItem:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupToolbar(){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Export Items");

        toolbar.setTitle("Store-check details");
        toolbar.inflateMenu(R.menu.storecheck_menu);
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

    }
    public void setUpNavigationView() {
//        try {
//            navigationFragment = (StoreCheckNavigationFragment) getSupportFragmentManager().findFragmentById(R.id.storeCheckNavDrawerFragment);
//            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//            navigationFragment.setUpDrawer(R.id.storeCheckNavDrawerFragment, mDrawerLayout, toolbar);
//
//            navigationFragment.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), navigationFragment.recyclerView, new ClickListener()
//            {
//                @Override
//                public void onClick(View view, int position)
//                {
//
//                    loadView(position);
//                }
//
//                @Override
//                public void onLongClick(View view, int position) {
//
//                }
//            }
//            ));
//
//        } catch (Exception e) {
//            Log.e("Setup Drawer", e.getMessage());
//        }
    }
    private void loadView(int position){
        activeView = position;
        fragment = null;
        switch (position){
            case VIEW_DETAILS:
                // fragment = new StoreCheckDetailsFragment();
                context=this;
                intent=new Intent(context,StoreCheckDetailsActivity.class);
                startActivity(intent);
                break;
            case VIEW_PRODUCT_DETAILS:
                //fragment = new StoreCheckAddProductDetailsFragment();
                context=this;
                intent=new Intent(context,StoreCheckAddProductDetailsActivity.class);
                startActivity(intent);
                break;
            case ADD_BRAND:
                // fragment = new StoreCheckAddBrandFragment();
                context=this;
                intent=new Intent(context,StoreCheckAddBrandActivity.class);
                startActivity(intent);

                break;
            case ADD_OUTLET:
                //fragment = new StoreCheckAddOutletFragment();
                context=this;
                // intent=new Intent(context,StoreCheckAddoutletActivity.class);
                startActivity(intent);
                break;
            case IMPORT_STORECHECK_DETAILS:
                //  fragment = new StoreCheckImportFragment();
                context = this;
                intent=new Intent(context,StoreCheckImportActivity.class);
                startActivity(intent);
                break;
            case EXPORT_STORECHECK_DETAILS:
                // fragment = new StoreCheckExportFragment();
                context=this;
                intent=new Intent(context,StoreCheckExportActivity.class);
                startActivity(intent);
                break;
        }

        if(fragment!=null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.containerFrame, fragment).commit();
            navigationFragment.closeDrawer();
        }
    }

    public void setUpStoreCheckDetails(View view)
    {
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.storecheckDetailsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StoreCheckDetailAdapter(this.getLayoutInflater(),this, dbHelper.GetAllProductDetails());
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
