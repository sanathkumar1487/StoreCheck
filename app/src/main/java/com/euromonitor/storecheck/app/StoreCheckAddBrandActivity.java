package com.euromonitor.storecheck.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.euromonitor.storecheck.Constants.NavigationConstants;
import com.euromonitor.storecheck.R;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.adapter.StoreCheckCustomFieldsAdapter;
import com.euromonitor.storecheck.adapter.StoreCheckDetailAdapter;
import com.euromonitor.storecheck.databinding.StorecheckAddbrandBinding;
import com.euromonitor.storecheck.databinding.StorecheckProductdetailsBinding;
import com.euromonitor.storecheck.listener.ClickListener;
import com.euromonitor.storecheck.listener.RecyclerTouchListener;
import com.euromonitor.storecheck.model.CustomField;
import com.euromonitor.storecheck.model.Option;
import com.euromonitor.storecheck.model.Product;
import com.euromonitor.storecheck.model.StoreCheckBrand;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */

public class StoreCheckAddBrandActivity extends AppCompatActivity

{
    final static String DropDown = "1";
    final static String TextBox ="2";
    final static String CustomDropDown="3";
    final static String CustomCompanyID="4";
    StorecheckAddbrandBinding binding;
    StoreCheckBrand storeCheckBrand;
    RecyclerView customFieldRecyclerView;
    DatabaseHelper databaseHelper;
    ArrayList<CustomField> customFields = new ArrayList<CustomField>() ;
    // Navigation
    DrawerLayout mDrawerLayout;
    Context context;
    ArrayAdapter<String> adapter;
    android.support.v7.widget.Toolbar toolbar;
    AutoCompleteTextView nbo_name;
    String[] item=new String[]{"Please search here"};
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.storecheck_addbrand);
        context = this;
        databaseHelper = new DatabaseHelper(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.addBrand_Drawer);
        setupToolbar();
        setUpNavigationView();
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> parent of 3981de9... Pricing screen
        setBinding();
        View view = binding.getRoot();
        customFieldRecyclerView = (RecyclerView) view.findViewById(R.id.customFields);
        item=databaseHelper.getNboName();
        nbo_name=(AutoCompleteTextView)findViewById(R.id.nboName);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,item);
        nbo_name.setAdapter(adapter);
    }
=======
        if(databaseHelper.isDatabaseAvailable())
        {
            setBinding();
            View view = binding.getRoot();
            customFieldRecyclerView = (RecyclerView) view.findViewById(R.id.customFields);
            item=databaseHelper.getNboName();
            nbo_name=(AutoCompleteTextView)findViewById(R.id.nboName);
            adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,item);
            nbo_name.setAdapter(adapter);
            nbo_name.setThreshold(1);
        }
        else
        {
            Toast.makeText(this,"Please import EMMA generated file to proceed!",Toast.LENGTH_LONG).show();
        }
>>>>>>> c6a452318bb6df254bd2303f424664fe7be132b5

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.addbrand_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Save:
                if(databaseHelper.isDatabaseAvailable()) {
                    validateData();
                    return true;
                }
                else
                {
                    Toast.makeText(this,"Please import EMMA generated file to proceed!",Toast.LENGTH_LONG).show();
                }
            case R.id.Clear:
                if(databaseHelper.isDatabaseAvailable()) {
                    resetData();
                }
                else
                {
                    Toast.makeText(this,"Please import EMMA generated file to proceed!",Toast.LENGTH_LONG).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setupToolbar(){

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
            Log.e("Setup Drawer", e.getMessage());
        }
    }

    private void resetData(){
        binding.brandName.setText("");
        binding.nboName.setText("");

        Spinner productSpinner = (Spinner) ((View) (binding.getRoot()).findViewById(R.id.products));
        if(productSpinner.getCount()>0) {
            productSpinner.setSelection(0);
        }

        setBindingProperties();
    }
    private void validateData() {
        setBindingProperties();
        StoreCheckBrand data = binding.getStoreCheckBrand();
        boolean isValid = true;
        String errors = "Please correct the following errors: ";
        if (data.getBrand() == null || data.getBrand().equals("")) {
            errors += "\n Brand is required";
            isValid = false;
        }
        if (data.getNBO() == null || data.getNBO().equals("")) {
            errors += "\n NBO is required";
            isValid = false;
        }
        ArrayList<CustomField> customFields = data.getCustomFields();
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

                            if (!validateValue(cfValue, minimum, maximum, noMaximum,  cf.getIsZeroAllowed())) {
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

        if (isValid) {
            if(databaseHelper.saveBrand(data)){
                binding.brandName.setText(null);
                binding.nboName.setText(null);
                Spinner productSpinner = (Spinner) ((View) (binding.getRoot()).findViewById(R.id.products));
                if(productSpinner.getCount()>0) {
                    productSpinner.setSelection(0);
                }
                setBindingProperties();


                Toast.makeText(this.getBaseContext(), "Saved successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,StoreCheckAddProductDetailsActivity.class);
                context.startActivity(intent);
            }
        }
        else{
            Toast.makeText(this.getBaseContext(), errors, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateValue (double value, double minimumAllowed, double maximumAllowed, boolean noMaximum, boolean isZeroAllowed){
        return value >= minimumAllowed && (value <= maximumAllowed || noMaximum || maximumAllowed == 0) || (isZeroAllowed && value == 0);
    }

    private void setBindingProperties(){
        String newValue = null;
        if(binding.brandName.getText()!=null){
            newValue = binding.brandName.getText().toString();
        }
        binding.getStoreCheckBrand().setBrand(newValue);

        newValue = null;
        if(binding.nboName.getText()!=null){
            newValue = binding.nboName.getText().toString();
        }
        binding.getStoreCheckBrand().setNbo(newValue);
    }

    private void setBinding(){
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
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Product selectedProduct = (Product)parent.getItemAtPosition(position);
                    int productID = Integer.valueOf(selectedProduct.get_product_id());
                    storeCheckBrand.setSelectedProductId(productID);
                    setCustomFieldByProductCode(productID);
                    storeCheckBrand.setCustomFields(customFields);


                    if(customFieldRecyclerView!=null) {

                        customFieldRecyclerView.setLayoutManager(new LinearLayoutManager(context));

                        StoreCheckCustomFieldsAdapter adapter = new StoreCheckCustomFieldsAdapter(LayoutInflater.from(context), context, customFields);
                        customFieldRecyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    storeCheckBrand.setSelectedProductId(0);
                }
            });
        }
        return products;
    }

    private void setCustomFieldByProductCode(int productCode) {
        customFields = databaseHelper.getCustomFieldByProductCode(productCode, customFields);
        customFields = databaseHelper.updateCustomFieldOptions(customFields);
    }

    public class ProductAdapter extends BaseAdapter implements SpinnerAdapter
    {
        private ArrayList<Product> products;
        public  ProductAdapter(ArrayList<Product> products){
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
            if (recycle != null)
            {
                productItemView = recycle;
            }
            else
            {
                productItemView =  getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }
            TextView productItem = (TextView)productItemView.findViewById(R.id.productItem);

            productItem.setText(products.get(position).get_product_name());
            return productItemView;
        }
    }



}
