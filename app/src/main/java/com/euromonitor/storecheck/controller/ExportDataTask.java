package com.euromonitor.storecheck.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.euromonitor.storecheck.app.StoreCheckExportActivity;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncPreExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncProgressReport;
import com.euromonitor.storecheck.model.Export;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.StoreCheckExtended;
import com.euromonitor.storecheck.utility.DatabaseHelper;
import com.euromonitor.storecheck.utility.JsonFileParser;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Noora.Akhtar on 4/20/2016.
 */
public class ExportDataTask extends AsyncTask<Void, Void, Void> {
    View view;
    Context context;
    String fileName;
    private Writer writer;
    private String absolutePath;
    boolean status;
    Exception backgroundException;
    StoreCheckExportActivity activity;


    public AsyncPreExecute preExecute = null;
    public AsyncProgressReport progressReport = null;
    public AsyncPostExecute postExecute = null;

    public ExportDataTask(String fileName, Context context) {
        this.context = context;
        this.fileName = fileName;
    }

    @Override
    protected Void doInBackground(Void... params) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        try{
            Gson gsonObject = new Gson();

            StoreCheckExtended storeCheckExtended = new StoreCheckExtended();
            storeCheckExtended.Outlets = databaseHelper.getOutlets();;
            storeCheckExtended.ProductDetails = databaseHelper.GetAllProductDetails();

            String jsonString =  gsonObject.toJson(storeCheckExtended);
            write(jsonString);

        }catch (Exception e){

        }
        finally {
            databaseHelper.close();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        preExecute.preExecute("Export Complete");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        progressReport.progressReport("you just called progressReport  Async Task in the UI Thread");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (  backgroundException != null)
        {
            activity.messageBox(backgroundException);
        }
        postExecute.PostExecute("Store check data export to file: "+ absolutePath );
    }

    public void write(String data) {
        File root = Environment.getExternalStorageDirectory();
        File outDir = new File(root.getAbsolutePath() + File.separator + "StoreCheck");
        if (!outDir.isDirectory()) {
            outDir.mkdir();
        }
        try {
            if (!outDir.isDirectory()) {
                status = false;
                throw new IOException(
                        "Unable to create directory StoreCheck. Maybe the SD card is mounted?");
            }
            File outputFile = new File(outDir, fileName);
            writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(data);

            status = true;
            absolutePath = outputFile.getAbsolutePath();

            writer.close();
        } catch (IOException e) {
            Log.w("IOException", e.getMessage(), e);
            status = false;
        }
    }
}