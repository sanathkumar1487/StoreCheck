package com.euromonitor.Storechecker.app;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.euromonitor.Storechecker.R;
import com.euromonitor.Storechecker.controller.ImportDataTask;
import com.euromonitor.Storechecker.controller.interfaces.AsyncPostExecute;
import com.euromonitor.Storechecker.controller.interfaces.AsyncPreExecute;
import com.euromonitor.Storechecker.controller.interfaces.AsyncProgressReport;
import com.github.developerpaul123.filepickerlibrary.FilePickerActivity;
import com.github.developerpaul123.filepickerlibrary.enums.Request;
import com.github.developerpaul123.filepickerlibrary.enums.Scope;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
public class StoreCheckImportActivity extends AppCompatActivity implements AsyncPostExecute,AsyncProgressReport,AsyncPreExecute
{

    ImportDataTask importDataController;
    Button browseFile;
    Button sqlLiteMonitor;
    MaterialProgressBar progressBar;
    DrawerLayout mDrawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    private static final int REQUEST_PICK_FILE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storecheck_import);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.import_Drawer);
        setupToolbar();
        setUpNavigationView();
        progressBar = (MaterialProgressBar) this.findViewById(R.id.progbar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#42A5F5"), PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#42A5F5"), PorterDuff.Mode.SRC_IN);
        browseFile = (Button) this.findViewById(R.id.browseFile);
        browseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(v);
            }
        });
        sqlLiteMonitor = (Button) this.findViewById(R.id.sqlMonitor);
        sqlLiteMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddbmanager(v);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 10) && (resultCode == this.RESULT_OK)) {
            Toast.makeText(this.getBaseContext(), "Importing file", Toast.LENGTH_LONG).show();
            try {

                importDataController = new ImportDataTask(data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH), this, this);
                importDataController.postExecute = this;
                importDataController.preExecute = this;
                importDataController.progressReport = this;
                importDataController.execute();
            } catch (Exception e) {
                messageBox(e);
            }
        }
    }


    @Override
    public void PostExecute(String message)
    {

        if (message.startsWith("Error"))
        {
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        }
        progressBar.setVisibility(View.GONE);
        browseFile.setEnabled(true);
        sqlLiteMonitor.setEnabled(true);
    }

    @Override
    public void progressReport(String message)
    {

        // Toast.makeText(this, "progressReport  :: " + message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void preExecute(String message) {

        progressBar.setVisibility(View.VISIBLE);
        browseFile.setEnabled(false);
        sqlLiteMonitor.setEnabled(false);
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

    public void setupToolbar()
    {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.importToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Import File");
        toolbar.setTitle("Storechecker details");
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
            throw e;
        }
    }

    public void selectFile(View view) {

        Intent filePicker = new Intent(this.getBaseContext(), FilePickerActivity.class);
        filePicker.putExtra(FilePickerActivity.SCOPE, Scope.ALL);
        filePicker.putExtra(FilePickerActivity.REQUEST, Request.FILE);
        filePicker.putExtra(FilePickerActivity.INTENT_EXTRA_COLOR_ID, android.R.color.holo_blue_dark);
        startActivityForResult(filePicker, 10);
    }

    public void loaddbmanager(View view) {
        Intent dbmanager = new Intent(this, AndroidDatabaseManager.class);
        startActivity(dbmanager);
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
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"StoreCheckAppSupport@Euromonitor.com",});
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

    @Override
    public void onBackPressed()
    {
        Intent intent= new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}