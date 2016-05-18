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
import com.euromonitor.storecheck.model.Validation;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class StoreCheckAddProductDetailsActivity extends AppCompatActivity {
    public static int priceId;
    public static long itemId;
    public static long brandId;
    public static long brandMarketId;
    public static int productCode;
    public static String productName;
    public static String brandName;

    final static String DropDown = "1";
    final static String TextBox = "2";
    final static String CustomDropDown = "3";
    final static String CustomCompanyID = "4";
    ArrayList<Market> markets;
    ArrayList<CustomField> customFields = new ArrayList<CustomField>();
    ArrayList<Validation> validations;
    String errors;
    PricingDetail pricingDetail;
    StorecheckProductdetailsBinding binding;
    DatabaseHelper databaseHelper;
    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    RecyclerView customFieldRecyclerView;
    Spinner outletSpinner;
    ArrayList<Outlet> outlets;
    Spinner packTypeSpinner;
    ArrayList<PackType> packTypes;
    Spinner unitSpinner;
    ArrayList<Unit> units;

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

        if(databaseHelper.isDatabaseAvailable()) {
            setSpinners();

            setBinding();

            setCustomFields(productCode, 2);

            setSelectedOutletId();

            setSelectedPackType();

            setSelectedUnitCode();

            binding.setPricingDetail(pricingDetail);
        }
        else {
            Toast.makeText(this,"Please import EMMA generated file to proceed!",Toast.LENGTH_LONG).show();
        }
    }

    private void setSelectedUnitCode() {
        if (pricingDetail.getId() !=null && pricingDetail.isUpdated && itemId > 0 && units!=null) {
            for(int index = 0; index < units.size(); index++){
                if(units.get(index).get_unit_id().equals(String.valueOf(pricingDetail.getUnitId()))){
                    unitSpinner.setSelection(index);
                    break;
                }
            }

        }
    }

    private void setSelectedPackType() {
        if (pricingDetail.getId() !=null && pricingDetail.isUpdated && itemId > 0 && packTypes!=null) {
            for(int index = 0; index < packTypes.size(); index++){
                if(packTypes.get(index).get_packtypecode().equals(String.valueOf(pricingDetail.getPackTypeCode()))){
                    packTypeSpinner.setSelection(index);
                    break;
                }
            }

        }
    }

    private void setSelectedOutletId() {

        if (pricingDetail.getId() !=null && pricingDetail.isUpdated && itemId > 0 && outlets!=null) {
            for(int index = 0; index < outlets.size(); index++){
                if(outlets.get(index).get_id() == pricingDetail.getNewOutletId() ||
                        outlets.get(index).get_outlet_id().equals(String.valueOf(pricingDetail.getSelectedOutletId()))){
                    outletSpinner.setSelection(index);
                    break;
                }
            }

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

    private void setCustomFields(int productCode, int typeId) {
        setCustomFieldByProductCode(productCode, typeId);
        pricingDetail.setCustomFields(customFields);

        if (customFieldRecyclerView != null) {

            customFieldRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            StoreCheckCustomFieldsAdapter adapter = new StoreCheckCustomFieldsAdapter(LayoutInflater.from(context), context, customFields);

            customFieldRecyclerView.setAdapter(adapter);
        }
    }

    private void SavePricing() {
        setBindingProperties();
        if (validateData()) {
            PricingDetail pricingDetail = binding.getPricingDetail();
            pricingDetail.setBrandMarketId(brandMarketId);
            pricingDetail.setBrandId(brandId);

            long pricingId = databaseHelper.savePricingDetails(pricingDetail, isUpdate);
            if (pricingId > 0 ) {
                Toast.makeText(this.getBaseContext(), "Saved successfully!", Toast.LENGTH_LONG).show();
                pricingDetail.setId(String.valueOf(pricingId));
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
        Validation validation ;

        // Multipack Size
        if (detail.getMultiPack() == null){
            errors = errors + "\nMultipack field cannot be blank";
        }

        validation = getMultipackMax(productCode);
        if(validation!=null) {
            if (Integer.valueOf(detail.getMultiPack()) <= 0
                    || Integer.valueOf(detail.getMultiPack()) > validation.getMultiPackMaximum()) {
                errors = errors + "\nMultipack field value should be between " + 1
                        + " and " + validation.getMultiPackMaximum();
                isValid = false;
            }

            // Pack Size
            if (detail.getPackSize() == null || detail.getPackSize().equals("0")) {
                errors = errors + "\nPack-size field value is invalid";
                isValid = false;
            }

            if(Integer.valueOf(detail.getPackSize()) < validation.getUnitMin() || Integer.valueOf(detail.getPackSize()) >validation.getUnitMax()){
                errors = errors + "\nPack-size field value should be between " + validation.getUnitMin() + " and " + validation.getUnitMax() ;
                isValid = false;
            }
        }

        // Price
        if (detail.getPrice() == null || detail.getPrice().equals("0")) {
            errors = errors + "\nPrice field value is invalid";
            isValid = false;
        }

        validation = getValidationByColumn("scrPrice");

        if(validation!=null && (Double.valueOf(detail.getPrice()) < validation.getMinimumAllowed()
                ||Double.valueOf(detail.getPrice()) > validation.getMaximumAllowed() ) ){
            errors = errors + "\nPrice field value should be between " + validation.getMinimumAllowed() + " and " + validation.getMaximumAllowed();
            isValid = false;
        }

        // Brand
        validation = getValidationByColumn("scmBrand");
        if(validation!=null && detail.getBrandName()!=null
                && (detail.getBrandName().length() < validation.getMinimumAllowed() ||detail.getBrandName().length() > validation.getMaximumAllowed())){
            errors = errors + "\nBrand text length should be between " + validation.getMinimumAllowed() + " and " + validation.getMaximumAllowed();
            isValid = false;
        }

        // NBO
        validation = getValidationByColumn("scmNBO");
        if(validation!=null && detail.getNbo()!=null
                && (detail.getNbo().length() < validation.getMinimumAllowed() ||detail.getNbo().length() > validation.getMaximumAllowed())){
            errors = errors + "\nNBO text length should be between " + validation.getMinimumAllowed() + " and " + validation.getMaximumAllowed();
            isValid = false;
        }


        ArrayList<CustomField> customFields = detail.getCustomFields();
        if (customFields != null) {
            for (CustomField cf : customFields) {

                Option selectedOption = cf.getSelectedOption();
                if (selectedOption != null) {
                    if (selectedOption.getMinimumAllowed()!=null && selectedOption.getMaximumAllowed()!=null
                            && selectedOption.getMinimumAllowed().equals("0")
                            && selectedOption.getMaximumAllowed().equals("0")) {
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
        validations = databaseHelper.getValidations(productCode);

        if (priceId > 0 || itemId > 0) {
            pricingDetail = databaseHelper.getPricingDetails(priceId, itemId);
        }

        if (pricingDetail == null) {
            isUpdate = false;
            pricingDetail = new PricingDetail();
            pricingDetail.setPricingId(priceId);
            pricingDetail.setBrandId(brandId);
            pricingDetail.setBrandMarketId(brandMarketId);
        }

        if(pricingDetail.getBrandId() == 0 && brandId > 0) {
            pricingDetail.setBrandId(brandId);
        }

        if(pricingDetail.getBrandMarketId() == 0 && brandMarketId > 0 ){
            pricingDetail.setBrandMarketId(brandMarketId);
        }

        pricingDetail.setBrandName(brandName);

        if(pricingDetail.getProductId() == 0 && productCode > 0) {
            pricingDetail.setProductId(productCode);
        }
        pricingDetail.setProductName(productName == null ? "" : productName.trim());

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
        outletSpinner = (Spinner) findViewById(R.id.outlets);
        outlets = databaseHelper.getOutlets(false);
        StoreCheckAddProductDetailsActivity.OutletAdapter outletAdapter = new StoreCheckAddProductDetailsActivity.OutletAdapter(outlets);
        outletSpinner.setAdapter(outletAdapter);
        outletSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Outlet selectedOutlet = (Outlet) parent.getItemAtPosition(position);
                pricingDetail.setNewOutletId(selectedOutlet.get_id());

                if (selectedOutlet.get_outlet_id() != null) {
                    pricingDetail.setSelectedOutletId(Integer.valueOf(selectedOutlet.get_outlet_id()));
                }
                else {
                    pricingDetail.setSelectedOutletId(-1);
                }
                pricingDetail.setSelectedOutletName(selectedOutlet.get_outlet_Name());
                pricingDetail.setChannelName(selectedOutlet.get_channel_name());
                if (selectedOutlet.get_outlet_id() != null) {
                    binding.getPricingDetail().setSelectedOutletId(Integer.valueOf(selectedOutlet.get_outlet_id()));
                }
                binding.getPricingDetail().setSelectedOutletName(selectedOutlet.get_outlet_Name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUnitSpinner() {
        unitSpinner = (Spinner) findViewById(R.id.units);
        units = databaseHelper.getUnits(productCode);

        StoreCheckAddProductDetailsActivity.UnitAdapter unitAdapter = new StoreCheckAddProductDetailsActivity.UnitAdapter(units);
        unitSpinner.setAdapter(unitAdapter);
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Unit unit = (Unit)parent.getItemAtPosition(position);

                binding.getPricingDetail().setUnitId (Integer.valueOf(unit.get_unit_id()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setCustomFieldByProductCode(int productCode, int typeId) {

        customFields = databaseHelper.getCustomFieldByProductCode(productCode, typeId, customFields);
        customFields = databaseHelper.updateCustomFieldOptions(customFields);

        if (isUpdate && pricingDetail != null && pricingDetail.getCustomFields() != null
                && pricingDetail.getCustomFields().size() > 0) {
            for (CustomField preCustomFields : pricingDetail.getCustomFields()) {
                for (CustomField cf : customFields) {
                    if (cf.getUniqueID().equals(preCustomFields.getUniqueID())) {
                        cf.setSelectedOption(preCustomFields.getSelectedOption());
                        if (cf.get_object_id().equals(TextBox)) {
                            cf.setCustomFieldTextValue(preCustomFields.getCustomFieldTextValue());
                        }
                        break;
                    }
                }
            }
        }
    }

    private void setPackTypeSpinner() {
        packTypeSpinner = (Spinner) findViewById(R.id.packType);
        packTypes = databaseHelper.getPackTypeByProduct(productCode);
        StoreCheckAddProductDetailsActivity.PackTypeAdapter packTypeAdapter = new StoreCheckAddProductDetailsActivity.PackTypeAdapter(packTypes);
        packTypeSpinner.setAdapter(packTypeAdapter);
        packTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PackType packType = (PackType)parent.getItemAtPosition(position);
                binding.getPricingDetail().setPackTypeCode(Integer.valueOf(packType.get_packtypecode()));
                binding.getPricingDetail().setPackTypeName(packType.get_packtypename());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            return Long.valueOf(outlets.get(position).get_id());
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

    private Validation getValidationByColumn(String columnName) {
        if (validations != null) {
            for (Validation validation : validations) {
                if (validation.getColumnName().equals(columnName)) {
                    return validation;
                }
            }
        }
        return null;
    }

    private Validation getMultipackMax(int productCode){
        for(Validation validation: validations){
            if(validation.getProductCode() == productCode)
                return validation;
        }
        return null;
    }
}
