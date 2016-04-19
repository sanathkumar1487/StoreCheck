package com.euromonitor.storecheck.app;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.euromonitor.storecheck.R;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.adapter.StoreCheckCustomFieldsAdapter;
import com.euromonitor.storecheck.adapter.StoreCheckDetailAdapter;
import com.euromonitor.storecheck.databinding.StorecheckAddbrandBinding;
import com.euromonitor.storecheck.databinding.StorecheckProductdetailsBinding;
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

public class StoreCheckAddBrandActivity extends MainActivity

{
    final static String DropDown = "1";
    final static String TextBox ="2";
    final static String CustomDropDown="3";
    final static String CustomCompanyID="4";

    StorecheckAddbrandBinding binding;
    StoreCheckBrand storeCheckBrand;
    DatabaseHelper databaseHelper;
    RecyclerView customFieldRecyclerView;
    ArrayList<CustomField> customFields = new ArrayList<CustomField>() ;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storecheck_addbrand);
       // binding = DataBindingUtil.inflate(inflater, R.layout.storecheck_addbrand, container, false);
        binding=DataBindingUtil.setContentView(this,R.layout.storecheck_addbrand);

        context=this;
        databaseHelper = new DatabaseHelper(context);

        binding.setListeners(new Listeners(binding, databaseHelper));


        setBinding();

        View view = binding.getRoot();
        customFieldRecyclerView = (RecyclerView) view.findViewById(R.id.customFields);


    }
    private void setBinding(){
        storeCheckBrand = new StoreCheckBrand();
        storeCheckBrand.setProducts(setProducts());

        storeCheckBrand.setGeography(databaseHelper.getGeography());
        binding.setStoreCheckBrand(storeCheckBrand);
    }

    public static class Listeners implements View.OnClickListener {

        private StorecheckAddbrandBinding binding;
        DatabaseHelper databaseHelper;
        public Listeners(StorecheckAddbrandBinding binding,DatabaseHelper databaseHelper) {
            this.binding = binding;
            this.databaseHelper = databaseHelper;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.resetAddBrand:
                    resetData();
                    break;
                case R.id.okAddBrand:
                    validateData(v);
                    break;
            }
        }

        private void resetData(){
            binding.brandName.setText("");
            binding.nboName.setText("");

            setBindingProperties();
        }

        private void validateData(View v) {
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
                        if (isValid && selectedOption.getMinimumAllowed().equals("0") && selectedOption.getMaximumAllowed().equals("0")) {
                            isValid = true;
                        }

                        if (isValid && cf.get_object_id().equals(StoreCheckAddBrandActivity.TextBox)) {
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
                    Toast.makeText(v.getContext(), "Saved successfully!", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(v.getContext(), errors, Toast.LENGTH_SHORT).show();
            }
        }

        private boolean validateValue (double value, double minimumAllowed, double maximumAllowed, boolean noMaximum, boolean isZeroAllowed){
            return value >= minimumAllowed && (value <= maximumAllowed || noMaximum) || (isZeroAllowed && value == 0);
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
    public class ProductAdapter extends BaseAdapter implements SpinnerAdapter {

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
                //productItemView=DataBindingUtil.setContentView(,R.layout.storecheck_productitem)

               productItemView =  getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }
            TextView productItem = (TextView)productItemView.findViewById(R.id.productItem);

            productItem.setText(products.get(position).get_product_name());
           return productItemView;
        }
    }

}
