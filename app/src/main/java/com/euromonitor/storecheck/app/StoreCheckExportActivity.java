package com.euromonitor.storecheck.app;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.euromonitor.storecheck.R;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class StoreCheckExportActivity extends MainActivity
{
    android.support.v7.widget.Toolbar toolbar;

    private SearchView.OnQueryTextListener queryTextListener;
    StoreCheckNavigationFragment navigationFragment;
    DrawerLayout drawerLayout;
    Fragment fragment;
    Activity activity;
    Context context;
    Intent intent;
    int activeView;
    final static int VIEW_DETAILS = 0;
    final static int VIEW_PRODUCT_DETAILS = 1;
    final static int ADD_BRAND = 2;
    final static int ADD_OUTLET = 3;
    final static int IMPORT_STORECHECK_DETAILS = 4;
    final static int EXPORT_STORECHECK_DETAILS = 5;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        StoreCheckNavigationFragment navigationFragment;
        DrawerLayout drawerLayout;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storecheck_export);
       // setupToolbar();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Save:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void  crash()
    {
        throw  new NullPointerException("Dummy Exception Thrown");
    }


}
