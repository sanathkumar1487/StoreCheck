package com.euromonitor.storecheck.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.listener.ClickListener;
import com.euromonitor.storecheck.listener.RecyclerTouchListener;

public class MainActivity extends ActionBarActivity {

    Toolbar toolbar;
    private SearchView.OnQueryTextListener queryTextListener;
    StoreCheckNavigationFragment navigationFragment;
    DrawerLayout drawerLayout;
    Fragment fragment;
    int activeView;

    final static int VIEW_DETAILS = 0;
    final static int VIEW_PRODUCT_DETAILS = 1;
    final static int ADD_BRAND = 2;
    final static int ADD_OUTLET = 3;
    final static int IMPORT_STORECHECK_DETAILS = 4;
    final static int EXPORT_STORECHECK_DETAILS = 5;

    boolean isLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            setupToolbar();
            setUpNavigationView();

            if(isLaunch)
            {
                isLaunch = false;
                loadView(VIEW_DETAILS);
            }

        } catch (Exception e) {
            Log.i("Main Activity Exception", e.getMessage());
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.searchItem:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("View Products");

        toolbar.setTitle("Store-check details");
        toolbar.inflateMenu(R.menu.storecheck_menu);
    }

    private void setUpNavigationView() {
        try {
            navigationFragment = (StoreCheckNavigationFragment) getSupportFragmentManager().findFragmentById(R.id.storeCheckNavDrawerFragment);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            navigationFragment.setUpDrawer(R.id.storeCheckNavDrawerFragment, drawerLayout, toolbar);

            navigationFragment.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), navigationFragment.recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    loadView(position);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }
            ));

        } catch (Exception e) {
            Log.e("Setup Drawer", e.getMessage());
        }
    }

    private void loadView(int position){
        activeView = position;
        fragment = null;
        switch (position){
            case VIEW_DETAILS:
                fragment = new StoreCheckDetailsFragment();
                break;
            case VIEW_PRODUCT_DETAILS:
                fragment = new StoreCheckAddProductDetailsFragment();
            case ADD_BRAND:
                fragment = new StoreCheckAddBrandFragment();
                break;
            case ADD_OUTLET:
                fragment = new StoreCheckAddOutletFragment();
                break;
            case IMPORT_STORECHECK_DETAILS:
                fragment = new StoreCheckImportFragment();
                break;
            case EXPORT_STORECHECK_DETAILS:
                fragment = new StoreCheckExportFragment();
                break;
        }

        if(fragment!=null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.containerFrame, fragment).commit();
            navigationFragment.closeDrawer();
        }
    }

}
