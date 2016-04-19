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
        getMenuInflater().inflate(R.menu.storecheck_menu, menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView)menu.findItem(R.id.searchItem).getActionView();

        if(searchView!=null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener(){
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText != null) {
                        switch (activeView){
                            case VIEW_DETAILS:
                                ((StoreCheckDetailsFragment)fragment).adapter.filterByProduct(newText);
                                break;
                            case VIEW_PRODUCT_DETAILS:

                                break;
                            case ADD_BRAND:

                                break;
                            case ADD_OUTLET:

                                break;
                            case IMPORT_STORECHECK_DETAILS:

                                break;
                            case EXPORT_STORECHECK_DETAILS:

                                break;
                        }
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return true;
    }
    private void setupToolbar(){
        //  toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Export");

        toolbar.setTitle("Store-check details");
        toolbar.inflateMenu(R.menu.storecheck_menu);
    }

}
