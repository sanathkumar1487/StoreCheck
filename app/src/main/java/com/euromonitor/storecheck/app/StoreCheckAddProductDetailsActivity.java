package com.euromonitor.storecheck.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.databinding.StorecheckProductdetailsBinding;
import com.euromonitor.storecheck.model.ProductDetail;
/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class StoreCheckAddProductDetailsActivity extends MainActivity
{
    public static int priceId;

    public static int brandId;

    DrawerLayout drawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StorecheckProductdetailsBinding binding= DataBindingUtil.setContentView(this, R.layout.storecheck_productdetails);

        setupToolbar();
        setUpNavigationView();

        ProductDetail prod=new ProductDetail();

        prod.setPrice(9.0);
        prod.setPackSize(13.0);
        binding.setProductDetails(prod);
    }

    public void setupToolbar(){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.addBrandToolBar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.addProductDetails_Drawer);

        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Export Items");

        toolbar.setTitle("Store-check details");
        toolbar.inflateMenu(R.menu.storecheck_menu);

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
}
