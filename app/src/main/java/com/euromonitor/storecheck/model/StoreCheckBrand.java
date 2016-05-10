package com.euromonitor.storecheck.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.euromonitor.storecheck.app.StoreCheckAddProductDetailsActivity;

import java.util.ArrayList;

/**
 * Created by Noora.Akhtar on 12/04/2016.
 */
public class StoreCheckBrand extends BaseObservable {

    private Geography geography;

    private ArrayList<Product> products;

    private ArrayList<Market> markets;
    //private int selectedProductId;

    private String brand;



    private Market selectMarket;

    private ArrayList<CustomField> customFields;

    public void setGeography(Geography geography){
        this.geography = geography;
    }

    public void setProducts(ArrayList<Product> products)
    {
        this.products = products;
    }
    public void setMarket(ArrayList<Market> markets)
    {
        this.markets=markets;
    }
    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    @Bindable
    public String getNbo() {
        return nbo;
    }

    public void setNbo(String nbo) {
        this.nbo = nbo;
    }

    private String nbo;

    public void setCustomFields(ArrayList<CustomField> customFields)
    {
        this.customFields = customFields;
    }

    public Product getSelectedProduct()
    {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct)
    {
        this.selectedProduct = selectedProduct;
    }

    private Product selectedProduct;

    public Market getSelectMarket()
    {
        return  selectMarket;
    }

    public Market getSelectedMarket()
    {
        return selectMarket;
    }
    public void setSelectMarket(Market selectMarket)
    {
        this.selectMarket=selectMarket;
    }

    @Bindable
    public Geography getGeography(){
        return geography;
    }

    public ArrayList<Product> getProducts(){
        return  products;
    }

    @Bindable
    public String getBrand(){
        return  brand;
    }



    public ArrayList<CustomField> getCustomFields(){
        return  customFields;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

}
