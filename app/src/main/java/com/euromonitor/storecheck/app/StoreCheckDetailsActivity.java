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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
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
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.PackType;
import com.euromonitor.storecheck.model.Product;
import com.euromonitor.storecheck.model.StoreCheckDetail;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class StoreCheckDetailsActivity extends AppCompatActivity
{
    public StoreCheckDetailAdapter adapter;
    StorecheckDetailsBinding binding;
    Context context;
    Intent intent;
    DatabaseHelper dbHelper;
    android.support.v7.widget.Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    private SearchView.OnQueryTextListener queryTextListener;
    MaterialProgressBar progressBar;
    Product selectedProduct;
    ProductTouchListener productTouchListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
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
        if (dbHelper.isDatabaseAvailable())
        {
            setSpinner();

        } else
        {
            Toast.makeText(this.getBaseContext(), "Please import EMMA generated file to proceed!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.viewdetail_menu, menu);

       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchItem:

                break;
            case R.id.refresh:
                if(productTouchListener!=null){
                    productTouchListener.loadSelectedProductItems();
                }
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
            throw e;
        }
    }

    private void setSpinner(){
        SearchableSpinner packTypeSpinner =  (SearchableSpinner)findViewById(R.id.products);
        ArrayList<Product> products = dbHelper.getAllProducts();
        StoreCheckDetailsActivity.ProductAdapter productAdapter = new StoreCheckDetailsActivity.ProductAdapter(products);
        packTypeSpinner.setAdapter(productAdapter);

        productTouchListener = new ProductTouchListener();
        packTypeSpinner.setOnItemSelectedListener(productTouchListener);
    }

    public void setUpStoreCheckDetails(List<StoreCheckDetail> storeCheckItems) {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.storecheckDetailsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StoreCheckDetailAdapter(this.getLayoutInflater(), this, storeCheckItems);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (selectedProduct != null) {
                    if (selectedProduct.getResearched() == 1) {
                        StorecheckdetailItemBinding binding = DataBindingUtil.getBinding(view);
                        StoreCheckDetail detail = binding.getStoreCheckDetail();
                        if (detail != null) {
                            StoreCheckAddProductDetailsActivity.priceId = detail.getPriceId();
                            StoreCheckAddProductDetailsActivity.itemId = detail.getItemId();
                            StoreCheckAddProductDetailsActivity.brandId = detail.getBrandId();
                            StoreCheckAddProductDetailsActivity.brandMarketId = detail.getBrandMarketId();
                            StoreCheckAddProductDetailsActivity.brandName = detail.getBrand();
                            StoreCheckAddProductDetailsActivity.productCode = detail.getProductCode();
                            StoreCheckAddProductDetailsActivity.productName = detail.getProductName();
                        }
                        StoreCheckAddProductDetailsActivity activity = new StoreCheckAddProductDetailsActivity();
                        intent = new Intent(StoreCheckDetailsActivity.this, StoreCheckAddProductDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(context, selectedProduct.get_product_name() + " is not researched!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    public class ProductAdapter extends BaseAdapter implements SpinnerAdapter {

        ArrayList<Product> products;

        public ProductAdapter(ArrayList<Product> products)
        {
            this.products = products;
        }

        @Override
        public int getCount()
        {
            return products.size();
        }

        @Override
        public Object getItem(int position)
        {
            return products.get(position);
        }

        @Override
        public long getItemId(int position) {
            return Long.valueOf(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View productView;
            if(convertView!=null){
                productView = convertView;
            }else {
                productView = getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }

            TextView outletItem = (TextView) productView.findViewById(R.id.productItem);
            outletItem.setText(products.get(position).get_product_name());

            return productView;
        }
    }



    public  class ProductTouchListener
            implements
            AdapterView.OnItemSelectedListener,
            AsyncPostExceuteDetails,
            AsyncProgressReport,
            AsyncPreExecute{

        private Product selProduct;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            this.selProduct = (Product) parent.getItemAtPosition(position);

            selectedProduct = this.selProduct;

            loadSelectedProductItems();
//            ViewDetailsTask viewDetailsTask = new ViewDetailsTask(context);
//            viewDetailsTask.productId = Integer.valueOf(selProduct.get_product_id());
//            viewDetailsTask.preExecute = this;
//            viewDetailsTask.progressReport = this;
//            viewDetailsTask.postExecute = this;
//            viewDetailsTask.execute();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

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

        public  void loadSelectedProductItems(){
            ViewDetailsTask viewDetailsTask = new ViewDetailsTask(context);
            viewDetailsTask.productId = Integer.valueOf(selProduct.get_product_id());
            viewDetailsTask.preExecute = this;
            viewDetailsTask.progressReport = this;
            viewDetailsTask.postExecute = this;
            viewDetailsTask.execute();
        }
    }
}
