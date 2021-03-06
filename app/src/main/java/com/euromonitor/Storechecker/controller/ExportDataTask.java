package com.euromonitor.Storechecker.controller;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.view.View;

import com.euromonitor.Storechecker.app.StoreCheckExportActivity;
import com.euromonitor.Storechecker.controller.interfaces.AsyncPostExecute;
import com.euromonitor.Storechecker.controller.interfaces.AsyncPreExecute;
import com.euromonitor.Storechecker.controller.interfaces.AsyncProgressReport;
import com.euromonitor.Storechecker.model.StoreCheckExtended;
import com.euromonitor.Storechecker.utility.DatabaseHelper;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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
            storeCheckExtended.Outlets = databaseHelper.getOutlets(true);
            storeCheckExtended.details = databaseHelper.getAllUpdatedDetails();
            storeCheckExtended.markets = databaseHelper.getAllUpdatedMarkets();
            storeCheckExtended.brandCustomFields = databaseHelper.getAllUpdatedCustomFields();

            String jsonString =  gsonObject.toJson(storeCheckExtended);
            write(jsonString);

        }catch(Exception e) {
            backgroundException = e;
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

    public void write(String data) throws IOException {

        try {

            File outputFile = new File(fileName);
            writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(data);

            status = true;
            absolutePath = outputFile.getAbsolutePath();
            MediaScannerConnection.scanFile(context, new String[]{absolutePath.toString()}, null, null);
        }
        catch (IOException e) {
            status = false;
            throw e;
        }

        finally {
            if (writer != null)
            writer.close();
        }
    }
}
