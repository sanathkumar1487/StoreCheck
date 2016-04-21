package com.euromonitor.storecheck.app;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.listener.ClickListener;
import com.euromonitor.storecheck.listener.RecyclerTouchListener;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private SearchView.OnQueryTextListener queryTextListener;
    StoreCheckNavigationFragment navigationFragment;
    DrawerLayout drawerLayout;
    Fragment fragment;
    int activeView;
    Context context;
    Intent intent;
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
            Fabric.with(this, new Crashlytics());
            setContentView(R.layout.activity_main);

            setupToolbar();
            setUpNavigationView();

            if (isLaunch) {
                isLaunch = false;
                // loadView(IMPORT_STORECHECK_DETAILS);
            }

        } catch (Exception e) {
            Log.i("Main Activity Exception", e.getMessage());
          //  messageBox(e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.storecheck_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchItem:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
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

    private void loadView(int position) {
        activeView = position;
        navigationFragment.closeDrawer();
        switch (position) {
            case VIEW_DETAILS:
                context = this;
                intent = new Intent(context, StoreCheckDetailsActivity.class);
                startActivity(intent);
                break;
            case VIEW_PRODUCT_DETAILS:
                context = this;
                intent = new Intent(context, StoreCheckAddProductDetailsActivity.class);
                startActivity(intent);
                break;
            case ADD_BRAND:
                context = this;
                intent = new Intent(context, StoreCheckAddBrandActivity.class);
                startActivity(intent);
                break;
            case ADD_OUTLET:
                context = this;
                intent = new Intent(context, OutletDetailsActivity.class);
                startActivity(intent);
                break;
            case IMPORT_STORECHECK_DETAILS:
                context = this;
                intent = new Intent(context, StoreCheckImportActivity.class);
                startActivity(intent);
                break;
            case EXPORT_STORECHECK_DETAILS:
                context = this;
                intent = new Intent(context, StoreCheckExportActivity.class);
                startActivity(intent);
                break;
        }


    }

    public void messageBox(Exception e)
    {


        AlertDialog.Builder messageBox = new AlertDialog.Builder(this);
        messageBox.setTitle("Error occurred");
        final String errordata = Log.getStackTraceString(e);
        messageBox.setNegativeButton("cancel", null);
        messageBox.setPositiveButton("Email error details", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"sanath.kumar@euromonitor.com", "Fritze.George@euromonitor.com",});
                i.putExtra(Intent.EXTRA_SUBJECT, "Storecheck app error Details");
                i.putExtra(Intent.EXTRA_TEXT, errordata);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getBaseContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        messageBox.show();
    }

}
