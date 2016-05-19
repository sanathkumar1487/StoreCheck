package com.euromonitor.storecheck.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.euromonitor.storecheck.R;

import com.euromonitor.storecheck.adapter.StoreCheckBrandAdapter;
import com.euromonitor.storecheck.adapter.StoreCheckCustomFieldsAdapter;
import com.euromonitor.storecheck.databinding.StorecheckAddbrandBinding;
import com.euromonitor.storecheck.model.BrandCustomField;
import com.euromonitor.storecheck.model.CustomField;
import com.euromonitor.storecheck.model.Market;
import com.euromonitor.storecheck.model.Option;
import com.euromonitor.storecheck.model.Product;
import com.euromonitor.storecheck.model.StoreCheckBrand;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */

public class StoreCheckAddBrandActivity extends AppCompatActivity

{
    final static String DropDown = "1";
    final static String TextBox = "2";
    final static String CustomDropDown = "3";
    final static String CustomCompanyID = "4";
    StorecheckAddbrandBinding binding;
    StoreCheckBrand storeCheckBrand;
    DatabaseHelper databaseHelper;
    RecyclerView customFieldRecyclerView;
    ArrayList<CustomField> customFields = new ArrayList<CustomField>();
    boolean isUpdated = false;
    ArrayList<Market> brand_item = new ArrayList<Market>();
    // Navigation
    DrawerLayout mDrawerLayout;
    Context context;
    ArrayAdapter<String> adapter;
    android.support.v7.widget.Toolbar toolbar;
    AutoCompleteTextView nboName;
    AutoCompleteTextView brandName;
    String[] item = new String[]{"Please search here"};
    StoreCheckCustomFieldsAdapter customFieldsAdapter;
    ArrayList<CustomField> productCustomFields ;

    boolean isExistingBrand=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.storecheck_addbrand);
        context = this;
        databaseHelper = new DatabaseHelper(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.addBrand_Drawer);
        setupToolbar();
        setUpNavigationView();
        if (databaseHelper.isDatabaseAvailable())
        {
            setBinding();
            View view = binding.getRoot();
            customFieldRecyclerView = (RecyclerView) view.findViewById(R.id.customFields);
            item = databaseHelper.getNboName();
            nboName = (AutoCompleteTextView) findViewById(R.id.nbo);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);
            nboName.setAdapter(adapter);
            nboName.setThreshold(1);
        } else {
            Toast.makeText(this, "Please import EMMA generated file to proceed!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.addbrand_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Save:
                if(databaseHelper.isDatabaseAvailable())
                {
                    validateData();
                    return true;
                }
                else
                {
                    Toast.makeText(this, "Please import EMMA generated file to proceed!", Toast.LENGTH_LONG).show();
                }
            case R.id.Clear:
                if(databaseHelper.isDatabaseAvailable())
                {
                    resetData();
                }
                else
                {
                    Toast.makeText(this, "Please import EMMA generated file to proceed!", Toast.LENGTH_LONG).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setupToolbar() {

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.addBrandToolBar);
        setSupportActionBar(toolbar);
        // actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Add Brand");

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

    private void resetData() {
        binding.brandName.setText("");
        binding.nbo.setText("");

        SearchableSpinnerForAddBrand productSpinner = (SearchableSpinnerForAddBrand) ((View) (binding.getRoot()).findViewById(R.id.products));
        if (productSpinner.getCount() > 0) {
            productSpinner.setSelection(0);
        }

        setBindingProperties();
    }

    private void validateData() {
        setBindingProperties();
        StoreCheckBrand data = binding.getStoreCheckBrand();
        boolean isValid = true;
        String errors = "Please correct the following errors: ";

        if(data.getSelectedProduct()!=null && data.getSelectedProduct().getResearched()==0)
        {
            Toast.makeText(context, data.getSelectedProduct().get_product_name() + " should not be researched!", Toast.LENGTH_LONG).show();
        }
        else
        {
            if (data.getBrand() == null || data.getBrand().equals(""))
            {
                errors += "\n Brand is required";
                isValid = false;
            }

            if (data.getNbo() == null || data.getNbo().equals(""))
            {
                errors += "\n NBO is required";
                isValid = false;
            }
            ArrayList<CustomField> customFields = data.getCustomFields();
            if (customFields != null) {
                for (CustomField cf : customFields)
                {

                    Option selectedOption = cf.getSelectedOption();
                    if (selectedOption != null) {
                        if (selectedOption.getMinimumAllowed().equals("0") && selectedOption.getMaximumAllowed().equals("0")) {
                            if (isValid) {
                                isValid = true;
                            }
                        } else if (cf.get_object_id().equals(StoreCheckAddBrandActivity.TextBox)) {
                            double minimum = 0;
                            if (selectedOption.getMinimumAllowed() != null)
                            {
                                minimum = Double.parseDouble(selectedOption.getMinimumAllowed());
                            }
                            double maximum = 0;
                            boolean noMaximum = true;
                            if (selectedOption.getMaximumAllowed() != null) {
                                maximum = Double.parseDouble(selectedOption.getMaximumAllowed());
                                noMaximum = false;
                            }
                            double cfValue = 0;
                            try
                            {

                                if (cf.getCustomFieldTextValue() != null)
                                {
                                    cfValue = Double.parseDouble(cf.getCustomFieldTextValue());
                                }

                                if (!cf.getIsNumeric())
                                {
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
                                    errors += "\n" + cf.get_label() + " value should be between " + minimum + " and " + maximum;
                                    isValid = false;
                                }
                            }
                        }
                    }
                }
            }
            if (isValid)
            {
                isUpdated = data.getSelectedMarket() != null && data.getSelectedMarket().get_id() > 0;
                long brandId = databaseHelper.saveBrand(data, isUpdated);
                if (brandId > 0) {
                    StoreCheckAddProductDetailsActivity.brandName = binding.getStoreCheckBrand().getBrand();

                    if(data.getSelectedMarket() == null){
                        Market market = new Market();
                        market.set_id(Integer.valueOf(String.valueOf(brandId)));
                        market.set_brand(data.getBrand());
                        data.setSelectMarket(market);
                    }

                    StoreCheckAddProductDetailsActivity.brandId = brandId;

                    if(data.getSelectedMarket().get_brand_market_id()!=null) {
                        StoreCheckAddProductDetailsActivity.brandMarketId = Long.valueOf(data.getSelectedMarket().get_brand_market_id());
                    }

                    StoreCheckAddProductDetailsActivity.productCode = Integer.valueOf(binding.getStoreCheckBrand().getSelectedProduct().get_product_id());
                    StoreCheckAddProductDetailsActivity.productName = binding.getStoreCheckBrand().getSelectedProduct().get_product_name();
                    StoreCheckAddProductDetailsActivity.priceId = 0;
                    StoreCheckAddProductDetailsActivity.itemId = 0;

                    Toast.makeText(this.getBaseContext(), "Saved successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, StoreCheckAddProductDetailsActivity.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(this.getBaseContext(), "Unable to save record!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this.getBaseContext(), errors, Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validateValue(double value, double minimumAllowed, double maximumAllowed, boolean noMaximum, boolean isZeroAllowed)
    {
        return value >= minimumAllowed && (value <= maximumAllowed || noMaximum || maximumAllowed == 0) || (isZeroAllowed && value == 0);
    }

    private void setBindingProperties() {
        String newValue = null;
        if (binding.brandName.getText() != null) {
            newValue = binding.brandName.getText().toString();
        }
        binding.getStoreCheckBrand().setBrand(newValue);

        newValue = null;
        if (binding.nbo.getText() != null) {
            newValue = binding.nbo.getText().toString();
        }
        binding.getStoreCheckBrand().setNbo(newValue);
    }

    private void setBinding() {
        storeCheckBrand = new StoreCheckBrand();
        storeCheckBrand.setProducts(setProducts());
        storeCheckBrand.setGeography(databaseHelper.getGeography());
        binding.setStoreCheckBrand(storeCheckBrand);
    }

    private ArrayList<Product> setProducts() {
        ArrayList<Product> products = databaseHelper.getAllProducts();

        Spinner productSpinner = (Spinner) ((View) (binding.getRoot()).findViewById(R.id.products));
        if (productSpinner != null) {
            StoreCheckAddBrandActivity.ProductAdapter adapter = new StoreCheckAddBrandActivity.ProductAdapter(products);
            productSpinner.setAdapter(adapter);

            productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                    final StoreCheckBrandAdapter brandAdapter;
                    Product selectedProduct = (Product) parent.getItemAtPosition(position);

                    if(selectedProduct.getResearched()==0){
                        Toast.makeText(context, selectedProduct.get_product_name() + " should not be researched!", Toast.LENGTH_LONG).show();
                    }

                    int productID = Integer.valueOf(selectedProduct.get_product_id());
                    storeCheckBrand.setSelectedProduct(selectedProduct);
                    binding.getStoreCheckBrand().setSelectedProduct(selectedProduct);
                    setCustomFieldByProductCode(productID);
                    storeCheckBrand.setCustomFields(customFields);

                    productCustomFields = customFields;

                    brand_item = databaseHelper.getBrandsByProductId(productID);
                    brandName = (AutoCompleteTextView) findViewById(R.id.brandName);
                    brandAdapter = new StoreCheckBrandAdapter(context, android.R.layout.simple_dropdown_item_1line, R.id.brand_name, brand_item);
                    brandName.setAdapter(brandAdapter);
                    brandName.setThreshold(1);

                    brandName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            isExistingBrand = true;
                            Market selectMarket = (Market) parent.getItemAtPosition(position);

                            storeCheckBrand.setSelectMarket(selectMarket);

                            ArrayList<String> nbo = databaseHelper.getNboByBrand(selectMarket);
                            if (nbo.size() > 0) {
                                selectMarket.set_nbo(nbo.get(0));
                                selectMarket.setEsci(nbo.get(1));
                                selectMarket.setEsbi(nbo.get(2));

                                binding.getStoreCheckBrand().setNbo(nbo.get(0));
                                binding.nbo.setText(binding.getStoreCheckBrand().getNbo());
                            }
                            ArrayList<BrandCustomField> brandCustomFields = databaseHelper.getBrandCustomFieldsByBrand(selectMarket);

                            if (brandCustomFields != null) {
                                ArrayList<CustomField> customFields = binding.getStoreCheckBrand().getCustomFields();
                                for (BrandCustomField preCustomFields : brandCustomFields) {
                                    for (CustomField cf : customFields) {
                                        if (Integer.valueOf(cf.get_group_id()) == (preCustomFields.getGroupId())) {
                                            cf.setSelectedOptionById(preCustomFields.getOptionId());
                                            if (cf.get_object_id().equals(TextBox)) {
                                                cf.setCustomFieldTextValue(preCustomFields.getOptionText());
                                            }

                                            break;
                                        }
                                    }
                                }

                                customFieldsAdapter = new StoreCheckCustomFieldsAdapter(LayoutInflater.from(context), context, customFields);
                                customFieldRecyclerView.setAdapter(customFieldsAdapter);
                            }

                            binding.getStoreCheckBrand().setSelectMarket(selectMarket);

                        }


                    });
                    if (customFieldRecyclerView != null) {
                        customFieldRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                        customFieldsAdapter = new StoreCheckCustomFieldsAdapter(LayoutInflater.from(context), context, customFields);
                        customFieldRecyclerView.setAdapter(customFieldsAdapter);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        return products;
    }

    private void setCustomFieldByProductCode(int productCode) {
        customFields = databaseHelper.getCustomFieldByProductCode(productCode, 1, customFields);
        customFields = databaseHelper.updateCustomFieldOptions(customFields);
    }

    private void setCustomDieldByBrand(int brandId) {
        //custom
    }

    public class ProductAdapter extends BaseAdapter implements SpinnerAdapter {

        private ArrayList<Product> products;

        public ProductAdapter(ArrayList<Product> products) {
            this.products = products;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int position) {
            return products.get(position);
        }

        @Override
        public long getItemId(int position) {
            return products.get(position).get_id();
        }

        @Override
        public View getView(int position, View recycle, ViewGroup parent) {
            View productItemView;
            productItemView = recycle;
            if (recycle != null) {
                productItemView = recycle;
            } else {
                productItemView = getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }
            TextView productItem = (TextView) productItemView.findViewById(R.id.productItem);

            productItem.setText(products.get(position).get_product_name());
            return productItemView;
        }
    }
}