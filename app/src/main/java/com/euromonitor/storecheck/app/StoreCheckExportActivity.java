package com.euromonitor.storecheck.app;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
<<<<<<< HEAD
import android.view.MenuItem;
=======
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
>>>>>>> 99b63f44d695ee70bb86b37579340c061de44402
import android.widget.Toolbar;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncPreExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncProgressReport;
import com.euromonitor.storecheck.controller.interfaces.ExportDataTask;
import com.euromonitor.storecheck.databinding.StorecheckExportBinding;
import com.euromonitor.storecheck.model.Export;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class StoreCheckExportActivity extends MainActivity implements AsyncPostExecute,AsyncProgressReport,AsyncPreExecute {

    EditText fileName ;
    MaterialProgressBar progressBar;
    Button export;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storecheck_export);

        export = (Button)findViewById(R.id.exportFile);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportData(v);
            }
        });

        fileName = (EditText) findViewById(R.id.fileName);
        progressBar = (MaterialProgressBar) this.findViewById(R.id.exportProgress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
<<<<<<< HEAD
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


=======
        getMenuInflater().inflate(R.menu.storecheck_menu, menu);
        return true;
    }


    @Override
    public void PostExecute(String message) {
        Toast.makeText(this, "PostExecute :: " + message, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        export.setEnabled(true);
        fileName.setEnabled(true);
    }

    @Override
    public void preExecute(String message) {
        progressBar.setVisibility(View.VISIBLE);
        export.setEnabled(false);
        fileName.setEnabled(false);
    }

    @Override
    public void progressReport(String message) {

    }

    private void exportData(View v) {
        if (fileName.getText() != null && fileName.getText().toString().trim().length() > 0) {
            ExportDataTask task = new ExportDataTask(fileName.getText().toString(), this);
            task.postExecute = this;
            task.preExecute = this;
            task.progressReport = this;
            task.execute();

        } else {
            Toast.makeText(this.getBaseContext(), "Please provide a filename", Toast.LENGTH_SHORT).show();
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
>>>>>>> 99b63f44d695ee70bb86b37579340c061de44402
}
