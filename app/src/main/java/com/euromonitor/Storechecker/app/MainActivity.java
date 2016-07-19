package com.euromonitor.Storechecker.app;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.euromonitor.Storechecker.R;
import com.euromonitor.Storechecker.utility.DatabaseHelper;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    DatabaseHelper db;
    boolean isLaunch = true;
    StoreCheckNavigationFragment storeCheckNavigationFragment;

    Button manageOutlet;
    Button viewDetails;
    Button addBrand;
    Button importData;
    Button export;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            Fabric.with(this, new Crashlytics());
            setContentView(R.layout.activity_main);

            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            setupToolbar();
            //setUpNavigationView();
            db = new DatabaseHelper(this);

            manageOutlet = (Button) findViewById(R.id.manageOutlet);
            viewDetails = (Button) findViewById(R.id.viewDetails);
            addBrand = (Button) findViewById(R.id.addBrand);
            importData = (Button) findViewById(R.id.importData);
            export = (Button) findViewById(R.id.export);


            manageOutlet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), OutletDetailsActivity.class);
                    startActivity(intent);
                }
            });

            viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), StoreCheckDetailsActivity.class);
                    startActivity(intent);
                }
            });

            addBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), StoreCheckAddBrandActivity.class);
                    startActivity(intent);
                }
            });

            importData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), StoreCheckImportActivity.class);
                    startActivity(intent);
                }
            });

            export.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), StoreCheckExportActivity.class);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        toolbar.setSubtitle("");

        toolbar.setTitle("StoreChecker");
        toolbar.inflateMenu(R.menu.menu_main);

    }


    private void setUpNavigationView() {
        try {
            Fragment navFragment = StoreCheckNavigationFragment.newInstance(R.id.drawer_layout, toolbar.getId());

            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.navDrawerFrame, navFragment, "Nav");
            ft.commit();

        } catch (Exception e) {
            throw e;
        }
    }



    public void messageBox(Exception e) {
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
                }
        );
        messageBox.show();
    }
}