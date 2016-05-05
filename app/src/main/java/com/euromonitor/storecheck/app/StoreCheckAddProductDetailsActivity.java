package com.euromonitor.storecheck.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.euromonitor.storecheck.adapter.StoreCheckCustomFieldsAdapter;
import com.euromonitor.storecheck.databinding.StorecheckProductdetailsBinding;
import com.euromonitor.storecheck.model.CustomField;
import com.euromonitor.storecheck.model.Market;
import com.euromonitor.storecheck.model.Option;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.PackType;
import com.euromonitor.storecheck.model.PricingDetail;
import com.euromonitor.storecheck.model.Product;
import com.euromonitor.storecheck.model.ProductDetail;
import com.euromonitor.storecheck.model.Unit;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class StoreCheckAddProductDetailsActivity extends AppCompatActivity {
    public static int priceId;

    public static int brandId;
    public static boolean brandIsNew;
    public static int productCode;
    public static String productName;
    public static String brandName;


    ArrayList<Market> markets;
    ArrayList<CustomField> customFields = new ArrayList<CustomField>();
    String errors;

    PricingDetail pricingDetail;
    StorecheckProductdetailsBinding binding;

    DatabaseHelper databaseHelper;
    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    RecyclerView customFieldRecyclerView;

    boolean isUpdate = true;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.storecheck_productdetails);

        context = this;

        drawerLayout = (DrawerLayout) findViewById(R.id.addProductDetails_Drawer);
        customFieldRecyclerView = (RecyclerView) findViewById(R.id.customFields);

        databaseHelper = new DatabaseHelper(this);

        setupToolbar();
        setUpNavigationView();

        setSpinners();

        setBinding();

        setCustomFields(productCode, 2);

        binding.setPricingDetail(pricingDetail);
    }

    private void setCustomFields(int productCode, int typeId) {
        setCustomFieldByProductCode(productCode, typeId);
        pricingDetail.setCustomFields(customFields);

        if (customFieldRecyclerView != null) {

            customFieldRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            StoreCheckCustomFieldsAdapter adapter = new StoreCheckCustomFieldsAdapter(LayoutInflater.from(context), context, customFields);

            customFieldRecyclerView.setAdapter(adapter);
        }
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
        if (validateData()) {
            if (databaseHelper.savePricingDetails(binding.getPricingDetail(), isUpdate)) {
                Toast.makeText(this.getBaseContext(), "Saved successfully!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this.getBaseContext(), "Unable to save!", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this.getBaseContext(), errors, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateData() {
        boolean isValid = true;

        errors = "Please correct the following errors!";
        PricingDetail detail = binding.getPricingDetail();
        // Multipack Size
        if (detail.getMultiPack() == null || detail.getMultiPack().equals("0")) {
            errors = errors + "\nMultipack field value is invalid";
            isValid = false;
        }

        // Pack Size
        if (detail.getPackSize() == null || detail.getPackSize().equals("0")) {
            errors = errors + "\nPack-size field value is invalid";
            isValid = false;
        }

        // Price
        if (detail.getPrice() == null || detail.getPrice().equals("0")) {
            errors = errors + "\nPrice field value is invalid";
            isValid = false;
        }

        ArrayList<CustomField> customFields = detail.getCustomFields();
        if (customFields != null) {
            for (CustomField cf : customFields) {

                Option selectedOption = cf.getSelectedOption();
                if (selectedOption != null) {
                    if (selectedOption.getMinimumAllowed().equals("0") && selectedOption.getMaximumAllowed().equals("0")) {
                        if (isValid) {
                            isValid = true;
                        }
                    } else if (cf.get_object_id().equals(StoreCheckAddBrandActivity.TextBox)) {
                        double minimum = 0;
                        if (selectedOption.getMinimumAllowed() != null) {
                            minimum = Double.parseDouble(selectedOption.getMinimumAllowed());
                        }

                        double maximum = 0;
                        boolean noMaximum = true;
                        if (selectedOption.getMaximumAllowed() != null) {
                            maximum = Double.parseDouble(selectedOption.getMaximumAllowed());
                            noMaximum = false;
                        }
                        double cfValue = 0;

                        try {

                            if (cf.getCustomFieldTextValue() != null) {
                                cfValue = Double.parseDouble(cf.getCustomFieldTextValue());
                            }

                            if (!cf.getIsNumeric()) {
                                if (cf.getCustomFieldTextValue() != null) {
                                    cfValue = cf.getCustomFieldTextValue().trim().length();
                                }
                            }

                            if (!validateValue(cfValue, minimum, maximum, noMaximum, cf.getIsZeroAllowed())) {
                                errors += "\n" + cf.get_label() + " value should be between " + minimum + " and " + maximum;
                                isValid = false;
                            }
                        } catch (NumberFormatException e) {
                            cfValue = cf.getCustomFieldTextValue().length();
                            if (!validateValue(cfValue, minimum, maximum, noMaximum, cf.getIsZeroAllowed())) {
                                errors += "\n" + cf.get_label() + " text should be between " + minimum + " and " + maximum;
                                isValid = false;
                            }
                        }
                    }
                }
            }
        }

        return isValid;
    }

    private boolean validateValue(double value, double minimumAllowed, double maximumAllowed, boolean noMaximum, boolean isZeroAllowed) {
        return value >= minimumAllowed && (value <= maximumAllowed || noMaximum || maximumAllowed == 0) || (isZeroAllowed && value == 0);
    }

    private void setBindingProperties() {
        String newValue = null;
        if (binding.multiPacks.getText() != null) {
            newValue = binding.multiPacks.getText().toString();
        }
        binding.getPricingDetail().setMultiPack(newValue != null ? Integer.valueOf(newValue) : 0);

        newValue = null;
        if (binding.packSize.getText() != null) {
            newValue = binding.packSize.getText().toString();
        }
        binding.getPricingDetail().setPackSize(newValue != null ? Integer.valueOf(newValue) : 0);

        newValue = null;
        if (binding.price.getText() != null) {
            newValue = binding.price.getText().toString();
        }
        binding.getPricingDetail().setPrice(newValue != null ? Double.valueOf(newValue) : 0.0);
    }

    public void setupToolbar() {

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.productDetailsToolbar);
        setSupportActionBar(toolbar);
        // actionbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setSubtitle("Add/Edit Price Details");

        toolbar.setTitle("Store-Check");
        toolbar.inflateMenu(R.menu.addoutlet_menu);
    }

    private void setUpNavigationView() {
        Fragment navFragment = StoreCheckNavigationFragment.newInstance(drawerLayout.getId(), toolbar.getId());
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.navDrawerFrame, navFragment, "Nav");
        ft.commit();
    }

    private void setBinding() {
        pricingDetail = databaseHelper.getPricingDetails(priceId);
        if (pricingDetail == null) {
            isUpdate = false;
            pricingDetail = new PricingDetail();
            pricingDetail.setPricingId(priceId);
            pricingDetail.setBrandName(brandName);

        }
        pricingDetail.setProductName(productName.trim());

        if (brandId > 0) {
            binding.setPricingDetail(pricingDetail);
        }
    }

    private void setSpinners() {
        setOutletSpinner();
        setUnitSpinner();
        setPackTypeSpinner();
    }

    private void setOutletSpinner() {
        Spinner outletSpinner = (Spinner) findViewById(R.id.outlets);

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

    private void setUnitSpinner() {
        Spinner unitSpinner = (Spinner) findViewById(R.id.units);
        ArrayList<Unit> units = databaseHelper.getUnits();

        StoreCheckAddProductDetailsActivity.UnitAdapter unitAdapter = new StoreCheckAddProductDetailsActivity.UnitAdapter(units);
        unitSpinner.setAdapter(unitAdapter);

    }

    private void setCustomFieldByProductCode(int productCode, int typeId) {

        customFields = databaseHelper.getCustomFieldByProductCode(productCode, typeId, customFields);
        customFields = databaseHelper.updateCustomFieldOptions(customFields);

        if (isUpdate) {
            for (CustomField preCustomFields : pricingDetail.getCustomFields()) {
                for (CustomField cf : customFields) {
                    if (cf.getUniqueID().equals(preCustomFields.getUniqueID())) {
                        cf.setSelectedOption(preCustomFields.getSelectedOption());
                        if(cf.get_object_id().equals("1")) {
                            cf.setCustomFieldTextValue(preCustomFields.getCustomFieldTextValue());
                        }
                        break;
                    }
                }
            }
        }
    }

    private void setPackTypeSpinner() {
        Spinner packTypeSpinner = (Spinner) findViewById(R.id.packType);
        ArrayList<PackType> packTypes = databaseHelper.getPackTypeByProduct(productCode);
        StoreCheckAddProductDetailsActivity.PackTypeAdapter packTypeAdapter = new StoreCheckAddProductDetailsActivity.PackTypeAdapter(packTypes);
        packTypeSpinner.setAdapter(packTypeAdapter);
    }

    public class OutletAdapter extends BaseAdapter implements SpinnerAdapter {

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
            if (convertView != null) {
                outletView = convertView;
            } else {
                outletView = getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }

            TextView outletItem = (TextView) outletView.findViewById(R.id.productItem);
            outletItem.setText(outlets.get(position).get_outlet_Name());

            return outletView;
        }
    }

    public class UnitAdapter extends BaseAdapter implements SpinnerAdapter {
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
            if (convertView != null) {
                unitView = convertView;
            } else {
                unitView = getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }

            TextView outletItem = (TextView) unitView.findViewById(R.id.productItem);
            outletItem.setText(units.get(position).get_unit_name());

            return unitView;
        }
    }

    public class PackTypeAdapter extends BaseAdapter implements SpinnerAdapter {

        ArrayList<PackType> packTypes;

        public PackTypeAdapter(ArrayList<PackType> packTypes) {
            this.packTypes = packTypes;
        }

        @Override
        public int getCount() {
            return packTypes.size();
        }

        @Override
        public Object getItem(int position) {
            return packTypes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return Long.valueOf(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View packTypeView;
            if (convertView != null) {
                packTypeView = convertView;
            } else {
                packTypeView = getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }

            TextView outletItem = (TextView) packTypeView.findViewById(R.id.productItem);
            outletItem.setText(packTypes.get(position).get_packtypename());

            return packTypeView;
        }
    }
}
