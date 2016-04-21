package com.euromonitor.storecheck.app;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncPreExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncProgressReport;
import com.euromonitor.storecheck.controller.ExportDataTask;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class StoreCheckExportActivity extends MainActivity implements AsyncPostExecute,AsyncProgressReport,AsyncPreExecute {

    EditText fileName;
    MaterialProgressBar progressBar;
    Button export;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storecheck_export);

        export = (Button) findViewById(R.id.exportFile);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportData(v);
            }
        });

        fileName = (EditText) findViewById(R.id.fileName);
        progressBar = (MaterialProgressBar) this.findViewById(R.id.exportProgress);
        int color = 0xFF00FF00;
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#42A5F5"), PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#42A5F5"), PorterDuff.Mode.SRC_IN);
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

    public void crash() {
        throw new NullPointerException("Dummy Exception Thrown");
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
        });
        messageBox.show();
    }
}
