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
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.euromonitor.storecheck.controller.ViewDetailsTask;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExceuteDetails;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExceuteDetails;
import com.euromonitor.storecheck.controller.interfaces.AsyncPreExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncProgressReport;
import com.euromonitor.storecheck.databinding.StorecheckDetailsBinding;
import com.euromonitor.storecheck.databinding.StorecheckdetailItemBinding;
import com.euromonitor.storecheck.listener.ClickListener;
import com.euromonitor.storecheck.listener.RecyclerTouchListener;
import com.euromonitor.storecheck.model.StoreCheckDetail;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;

<<<<<<< HEAD
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class StoreCheckDetailsActivity extends AppCompatActivity implements AsyncPostExceuteDetails,AsyncProgressReport,AsyncPreExecute
{
=======
public class StoreCheckDetailsActivity extends AppCompatActivity {
>>>>>>> origin/master
    public StoreCheckDetailAdapter adapter;

    StorecheckDetailsBinding binding;
    Context context;
    Intent intent;

    DatabaseHelper dbHelper;
    android.support.v7.widget.Toolbar toolbar;

    DrawerLayout mDrawerLayout;
    private SearchView.OnQueryTextListener queryTextListener;

    MaterialProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.storecheck_details);
        context = this;
        dbHelper = new DatabaseHelper(context);
        View view = binding.getRoot();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.details_drawer);

        progressBar = (MaterialProgressBar) this.findViewById(R.id.progbar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#42A5F5"), PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#42A5F5"), PorterDuff.Mode.SRC_IN);


        setupToolbar();
        setUpNavigationView();

<<<<<<< HEAD
        if(dbHelper.isDatabaseAvailable())
        {

            ViewDetailsTask viewDetailsTask = new ViewDetailsTask(this);
            viewDetailsTask.preExecute = this;
            viewDetailsTask.progressReport= this;
            viewDetailsTask.postExecute = this;
            viewDetailsTask.execute();
            //setUpStoreCheckDetails(view);
        }else {
=======
        if (dbHelper.isDatabaseAvailable()) {
            setUpStoreCheckDetails(view);
        } else {
>>>>>>> origin/master
            Toast.makeText(this.getBaseContext(), "Please import EMMA generated file to proceed!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.storecheck_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.searchItem).getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
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
        switch (item.getItemId()) {
            case R.id.searchItem:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupToolbar() {

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.detailsToolbar);
        setSupportActionBar(toolbar);
        // actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Item Details");

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
            Log.e("Setup Drawer", e.getMessage());
        }
    }

<<<<<<< HEAD



    public void setUpStoreCheckDetails(ArrayList<StoreCheckDetail> productDetails)
    {
        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.storecheckDetailsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new StoreCheckDetailAdapter(this.getLayoutInflater(),this, productDetails);
=======
    public void setUpStoreCheckDetails(View view) {
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.storecheckDetailsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StoreCheckDetailAdapter(this.getLayoutInflater(), this, dbHelper.GetAllProductDetails());
>>>>>>> origin/master
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                StorecheckdetailItemBinding binding = DataBindingUtil.getBinding(view);
                StoreCheckDetail detail = binding.getStoreCheckDetail();
                if (detail != null) {

                    StoreCheckAddProductDetailsActivity.priceId = detail.getPriceId();
                    StoreCheckAddProductDetailsActivity.brandId = detail.getBrandId();
                }
                StoreCheckAddProductDetailsActivity activity = new StoreCheckAddProductDetailsActivity();
                intent = new Intent(StoreCheckDetailsActivity.this, StoreCheckAddProductDetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

<<<<<<< HEAD
    @Override
    public void preExecute(String message) {

        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void progressReport(String message) {

    }

    @Override
    public void PostExecute(ArrayList<StoreCheckDetail> data) {
        setUpStoreCheckDetails(data);
        progressBar.setVisibility(View.GONE);

    }
=======
>>>>>>> origin/master
}
