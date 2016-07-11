package com.euromonitor.storecheck.app;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.controller.ImportDataTask;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncPreExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncProgressReport;
import com.euromonitor.storecheck.controller.ExportDataTask;
import com.euromonitor.storecheck.utility.DatabaseHelper;
import com.github.developerpaul123.filepickerlibrary.FilePickerActivity;
import com.github.developerpaul123.filepickerlibrary.enums.Request;
import com.github.developerpaul123.filepickerlibrary.enums.Scope;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class StoreCheckExportActivity extends MainActivity implements AsyncPostExecute,AsyncProgressReport,AsyncPreExecute {

    MaterialProgressBar progressBar;
    Button export;
    String fileName;
    TextView selectedFile;
    Button exportFile;
    DrawerLayout mDrawerLayout;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storecheck_export);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.exportDrawer_layout);
        setupToolbar();
        setUpNavigationView();
        DatabaseHelper db = new DatabaseHelper(this);
        if(db.isDatabaseAvailable()) {
            export = (Button) findViewById(R.id.browseFolder);
            export.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    browseFolder();
                }
            });
            progressBar = (MaterialProgressBar) this.findViewById(R.id.exportProgress);
            int color = 0xFF00FF00;
            progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#42A5F5"), PorterDuff.Mode.SRC_IN);
            progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#42A5F5"), PorterDuff.Mode.SRC_IN);
            selectedFile = (TextView) findViewById(R.id.selectedFile);
            selectedFile.setVisibility(View.VISIBLE);
            exportFile = (Button) findViewById(R.id.exportFile);
            exportFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exportData();
                }
            });
        }
        else {
            Toast.makeText(this.getBaseContext(),"Please import EMMA generated file to proceed!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setSubtitle("Export ");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Save:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void PostExecute(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        export.setEnabled(true);
    }

    @Override
    public void preExecute(String message) {
        progressBar.setVisibility(View.VISIBLE);
        export.setEnabled(false);
    }

    @Override
    public void progressReport(String message) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            final String directoryPath = data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH);
            if ((requestCode == 10) && (resultCode == this.RESULT_OK)) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Filename: ");
                    final EditText input = new EditText(this);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fileName = directoryPath + "/" + input.getText().toString() + ".storecheck";
                            selectedFile.setText(fileName);
                            selectedFile.setVisibility(View.VISIBLE);
                            exportFile.setEnabled(true);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
                catch (Exception e) {
                    messageBox(e);
                }
            }
        }
    }

    private void browseFolder(){
        Intent filePicker = new Intent(this.getBaseContext(), FilePickerActivity.class);
        filePicker.putExtra(FilePickerActivity.SCOPE, Scope.ALL);
        filePicker.putExtra(FilePickerActivity.REQUEST, Request.DIRECTORY);
        filePicker.putExtra(FilePickerActivity.INTENT_EXTRA_COLOR_ID, android.R.color.holo_blue_dark);
        startActivityForResult(filePicker, 10);
    }



    private void exportData() {
        if (fileName != null && fileName.trim().length() > 0) {
            ExportDataTask task = new ExportDataTask(fileName.trim(), this);
            task.postExecute = this;
            task.preExecute = this;
            task.progressReport = this;
            task.execute();

        } else {
            Toast.makeText(this.getBaseContext(), "Please provide a filename", Toast.LENGTH_SHORT).show();
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


    public void setupToolbar(){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.exportToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Export file");

        toolbar.setTitle("Store-check details");
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

    @Override
    public void onBackPressed()
    {
        Intent intent= new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
