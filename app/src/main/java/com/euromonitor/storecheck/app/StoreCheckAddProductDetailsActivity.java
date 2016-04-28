package com.euromonitor.storecheck.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
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
import com.euromonitor.storecheck.databinding.StorecheckProductdetailsBinding;
import com.euromonitor.storecheck.model.Market;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.PricingDetail;
import com.euromonitor.storecheck.model.Product;
import com.euromonitor.storecheck.model.ProductDetail;
import com.euromonitor.storecheck.model.Unit;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class StoreCheckAddProductDetailsActivity extends MainActivity
{
    public static int priceId;

    public static int brandId;
    public static boolean brandIsNew;


    ArrayList<Market> markets;
    String errors;

    PricingDetail pricingDetail;
    StorecheckProductdetailsBinding binding;

    DatabaseHelper databaseHelper;
    DrawerLayout drawerLayout;

    Spinner brandSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.storecheck_productdetails);

        drawerLayout = (DrawerLayout) findViewById(R.id.addProductDetails_Drawer);

        databaseHelper = new DatabaseHelper(this);

        setupToolbar();
        setUpNavigationView();


        setSpinners();

        setBinding();

        binding.setPricingDetail(pricingDetail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.addoutlet_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Save:
                SavePricing();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SavePricing() {
        setBindingProperties();
        if(validateData()){
            databaseHelper.savePricingDetails(binding.getPricingDetail());
        }
        else {
            Toast.makeText(this.getBaseContext(), errors, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateData() {
        boolean isValid=true;
        errors = "Please correct the following errors!";
        PricingDetail detail = binding.getPricingDetail();
        // Multipack Size
        if(detail.getMultiPack()==null || detail.getMultiPack().equals("0")){
            errors = errors + "\nMultipack field value is invalid";
            isValid = false;
        }

        // Pack Size
        if(detail.getPackSize()==null || detail.getPackSize().equals("0")){
            errors = errors + "\nPack-size field value is invalid";
            isValid = false;
        }

        // Price
        if(detail.getPrice()==null || detail.getPrice().equals("0")){
            errors = errors + "\nPrice field value is invalid";
            isValid = false;
        }

        return isValid;
    }

    private void setBindingProperties(){
        String newValue = null;
        if(binding.multiPacks.getText()!=null){
            newValue = binding.multiPacks.getText().toString();
        }
        binding.getPricingDetail().setMultiPack(newValue!=null?Integer.valueOf(newValue):0);

        newValue = null;
        if(binding.packSize.getText()!=null){
            newValue = binding.packSize.getText().toString();
        }
        binding.getPricingDetail().setPackSize(newValue != null ? Integer.valueOf(newValue) : 0);

        newValue = null;
        if(binding.price.getText()!=null){
            newValue = binding.price.getText().toString();
        }
        binding.getPricingDetail().setPrice(newValue != null ? Double.valueOf(newValue) : 0.0);
    }

    public void setupToolbar(){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.productDetailsToolbar);
        setSupportActionBar(toolbar);
        // actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setSubtitle("Add/Edit Price Details");

        toolbar.setTitle("Store-Check");
        toolbar.inflateMenu(R.menu.addoutlet_menu);
    }

    private void setUpNavigationView() {
        try {
            Fragment navFragment = StoreCheckNavigationFragment.newInstance(drawerLayout.getId(), toolbar.getId());

            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.navDrawerFrame, navFragment, "Nav");
            ft.commit();

        } catch (Exception e) {
            Log.e("Setup Drawer", e.getMessage());
        }
    }

    private void setBinding(){
        pricingDetail = new PricingDetail();
        if(brandId>0) {
            for (int index=0;index<markets.size();index++) {
                if(brandIsNew){
                    if(markets.get(index).get_id() == brandId){
                        brandSpinner.setSelection(index);
                    }
                }else{
                    if(markets.get(index).get_brand_market_id().equals(String .valueOf(brandId))){
                        brandSpinner.setSelection(index);
                    }
                }
            }
        }
    }

    private void setSpinners(){
        setOutletSpinner();
        setUnitSpinner();
        setMarketSpinner();
    }

    private void setOutletSpinner(){
        Spinner outletSpinner = (Spinner)findViewById(R.id.outlets);

        ArrayList<Outlet> outlets = databaseHelper.getOutlets();

        StoreCheckAddProductDetailsActivity.OutletAdapter outletAdapter = new StoreCheckAddProductDetailsActivity.OutletAdapter(outlets);
        outletSpinner.setAdapter(outletAdapter);
        outletSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Outlet selectedOutlet = (Outlet) parent.getItemAtPosition(position);

                pricingDetail.setSelectedOutletId(Integer.valueOf(selectedOutlet.get_outlet_id()));
                pricingDetail.setSelectedOutletName(selectedOutlet.get_outlet_Name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setUnitSpinner(){
        Spinner unitSpinner = (Spinner)findViewById(R.id.units);
        ArrayList<Unit> units = databaseHelper.getUnits();

        StoreCheckAddProductDetailsActivity.UnitAdapter unitAdapter = new StoreCheckAddProductDetailsActivity.UnitAdapter(units);
        unitSpinner.setAdapter(unitAdapter);

    }

    private void setMarketSpinner(){
        brandSpinner =  (Spinner)findViewById(R.id.brands);

        markets = databaseHelper.getBrands();
        StoreCheckAddProductDetailsActivity.BrandAdapter brandAdapter = new StoreCheckAddProductDetailsActivity.BrandAdapter(markets);
        brandSpinner.setAdapter(brandAdapter);
    }

    private void loadPricingDetails(){
        databaseHelper.getPricingDetails(brandId, priceId);
    }

    public class OutletAdapter extends BaseAdapter implements SpinnerAdapter{

        ArrayList<Outlet> outlets = new ArrayList<>();

        public OutletAdapter(ArrayList<Outlet> outlets) {
            this.outlets = outlets;
        }

        @Override
        public int getCount() {
            return outlets.size();
        }

        @Override
        public Object getItem(int position) {
            return outlets.get(position);
        }

        @Override
        public long getItemId(int position) {
            return Long.valueOf(outlets.get(position).get_outlet_id());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View outletView;
            if(convertView!=null){
                outletView = convertView;
            }else {
                outletView = getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }

            TextView outletItem = (TextView) outletView.findViewById(R.id.productItem);
            outletItem.setText(outlets.get(position).get_outlet_Name());

            return outletView;
        }
    }

    public class UnitAdapter extends BaseAdapter implements SpinnerAdapter{
        ArrayList<Unit> units = new ArrayList<>();

        public UnitAdapter(ArrayList<Unit> units) {
            this.units = units;
        }

        @Override
        public int getCount() {
            return units.size();
        }

        @Override
        public Object getItem(int position) {
            return units.get(position);
        }

        @Override
        public long getItemId(int position) {
            return Long.valueOf(units.get(position).get_unit_id());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View unitView;
            if(convertView!=null){
                unitView = convertView;
            }else {
                unitView = getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }

            TextView outletItem = (TextView) unitView.findViewById(R.id.productItem);
            outletItem.setText(units.get(position).get_unit_name());

            return unitView;
        }
    }

    public class BrandAdapter extends BaseAdapter implements SpinnerAdapter{

        ArrayList<Market> brands;

        public BrandAdapter(ArrayList<Market> brands) {
            this.brands = brands;
        }

        @Override
        public int getCount() {
            return brands.size();
        }

        @Override
        public Object getItem(int position) {
            return brands.get(position);
        }

        @Override
        public long getItemId(int position) {
            return Long.valueOf(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View brandView;
            if(convertView!=null){
                brandView = convertView;
            }else {
                brandView = getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }

            TextView outletItem = (TextView) brandView.findViewById(R.id.productItem);
            outletItem.setText(brands.get(position).get_brand());
            outletItem.setTextColor(Color.parseColor("#42A5F5"));

            return brandView;
        }
    }
}
