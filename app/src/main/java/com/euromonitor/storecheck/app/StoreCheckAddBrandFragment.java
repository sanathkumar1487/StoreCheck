package com.euromonitor.storecheck.app;

import android.app.Fragment;
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
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

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

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckAddBrandFragment extends Fragment {

    final int DropDown = 1;
    final int TextBox = 2;
    final int CustomDropDown=3;
    final int CustomCompanyID=4;

    StorecheckAddbrandBinding binding;
    StoreCheckBrand storeCheckBrand;
    DatabaseHelper databaseHelper;
    RecyclerView customFieldRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.storecheck_addbrand, container, false);
        setBinding();

        View view = binding.getRoot();
        customFieldRecyclerView = (RecyclerView) view.findViewById(R.id.customFields);

        return view;
    }

    private void setBinding(){
        storeCheckBrand = new StoreCheckBrand();
        storeCheckBrand.setProducts(setProducts());

        storeCheckBrand.setGeography(databaseHelper.getGeography());
        storeCheckBrand.setBrand("Coke");


        binding.setStoreCheckBrand(storeCheckBrand);
    }

    private ArrayList<Product> setProducts() {
        databaseHelper = new DatabaseHelper(getActivity());

        ArrayList<Product> products = databaseHelper.getAllProducts();

        Spinner productSpinner = (Spinner) ((View) (binding.getRoot()).findViewById(R.id.products));
        if (productSpinner != null) {
            ProductAdapter adapter = new ProductAdapter(products);
            productSpinner.setAdapter(adapter);
            productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Product selectedProduct = (Product)parent.getItemAtPosition(position);
                    int productID = Integer.valueOf(selectedProduct.get_product_id());
                    storeCheckBrand.setSelectedProductId(productID);
                    ArrayList<CustomField> customFields =setCustomFieldByProductCode(productID);
                    storeCheckBrand.setCustomFields(customFields);


                    if(customFieldRecyclerView!=null) {
                        customFieldRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        StoreCheckCustomFieldsAdapter adapter = new StoreCheckCustomFieldsAdapter(getActivity().getLayoutInflater(), getActivity(), customFields);
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



    private ArrayList<CustomField> setCustomFieldByProductCode(int productCode) {
        ArrayList<CustomField> customFields = new ArrayList<>();

        for (int index = 0; index < 5; index++) {
            CustomField temp = new CustomField();
            temp.setUniqueID(String.valueOf(index));
            temp.set_label(index + "Label text");
            temp.set_tooltip(index + "Tooltip text");
            temp.set_product_code(String.valueOf(index));
            temp.set_object_id(String.valueOf(DropDown));
            Option[] options = null;
            if (index % 2 == 0) {
                options  = new Option[5];
                for (int sub = 0; sub < 5; sub++) {
                    Option tempOption = new Option(true, String.valueOf(sub), sub + "Available mainly off-trade", "0", "0");
                    options[sub] = tempOption;
                }
                temp.set_options(options);
            }
            else {
                options = new Option[1];
                Option tempOption = new Option(false, "10", "Text box", "0", "0");
                options[0] = tempOption;
                temp.set_options(options);
            }
            customFields.add(temp);
        }

        return customFields;
    }

    public class ProductAdapter extends BaseAdapter implements SpinnerAdapter{

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
            if (recycle != null){
                productItemView = recycle;
            } else {
                productItemView = getActivity().getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }
            TextView productItem = (TextView)productItemView.findViewById(R.id.productItem);

            productItem.setText(products.get(position).get_product_name());
            return productItemView;
        }
    }


}
