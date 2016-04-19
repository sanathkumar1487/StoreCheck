package com.euromonitor.storecheck.app;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.controller.ImportDataTask;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncPreExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncProgressReport;
import com.github.developerpaul123.filepickerlibrary.FilePickerActivity;
import com.github.developerpaul123.filepickerlibrary.enums.Request;
import com.github.developerpaul123.filepickerlibrary.enums.Scope;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
public class StoreCheckImportActivity extends MainActivity implements AsyncPostExecute,AsyncProgressReport,AsyncPreExecute
{

    ImportDataTask importDataController ;
    Button browseFile;
    Button sqlLiteMonitor;
    MaterialProgressBar progressBar;

    private static final int REQUEST_PICK_FILE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // final View view =(View)findViewById(R.id.storecheckimport);
        setContentView(R.layout.storecheck_import);
        progressBar = (MaterialProgressBar) this.findViewById(R.id.progbar);

        int color = 0xFF00FF00;
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1565C0"), PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#1565C0"), PorterDuff.Mode.SRC_IN);

        browseFile = (Button)this.findViewById(R.id.browseFile);
        browseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(v);
            }
        });

        sqlLiteMonitor = (Button)this.findViewById(R.id.sqlMonitor);
        sqlLiteMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddbmanager(v);
            }
        });


    }
    public void selectFile(View view) {

        Intent filePicker = new Intent(this.getBaseContext(), FilePickerActivity.class);
        filePicker.putExtra(FilePickerActivity.SCOPE, Scope.ALL);
        filePicker.putExtra(FilePickerActivity.REQUEST, Request.FILE);
        filePicker.putExtra(FilePickerActivity.INTENT_EXTRA_COLOR_ID, android.R.color.holo_blue_dark);
        startActivityForResult(filePicker, 10);
    }



    public  void loaddbmanager(View view)
    {
        Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 10) && (resultCode == this.RESULT_OK)) {
            Toast.makeText(this.getBaseContext(), "File Selected: " + data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH), Toast.LENGTH_LONG).show();
            try
            {

                importDataController = new ImportDataTask(data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH),this,this);
                importDataController.postExecute = this;
                importDataController.preExecute = this;
                importDataController.progressReport = this;
                importDataController.execute();
            } catch (Exception e)
            {
                messageBox(e);
            }
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
    @Override
    public void PostExecute(String message) {
        Toast.makeText(this, "PostExecute :: " + message, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        browseFile.setEnabled(true);
        sqlLiteMonitor.setEnabled(true);
    }

    @Override
    public void progressReport(String message) {

        // Toast.makeText(this, "progressReport  :: " + message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void preExecute(String message) {

        progressBar.setVisibility(View.VISIBLE);
        browseFile.setEnabled(false);
        sqlLiteMonitor.setEnabled(false);
    }
}
