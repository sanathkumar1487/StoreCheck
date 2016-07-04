package com.euromonitor.storecheck.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.euromonitor.storecheck.BR;
import com.euromonitor.storecheck.app.StoreCheckImportActivity;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncPreExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncProgressReport;
import com.euromonitor.storecheck.model.BrandCustomField;
import com.euromonitor.storecheck.model.Channel;
import com.euromonitor.storecheck.model.CustomField;
import com.euromonitor.storecheck.model.Detail;
import com.euromonitor.storecheck.model.Market;
import com.euromonitor.storecheck.model.MetaData;
import com.euromonitor.storecheck.model.Option;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.PackType;
import com.euromonitor.storecheck.model.Product;
import com.euromonitor.storecheck.model.Unit;
import com.euromonitor.storecheck.model.Validation;
import com.euromonitor.storecheck.utility.JsonFileParser;

import org.json.JSONException;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sanath.Kumar on 3/31/2016.
 */



public class ImportDataTask extends AsyncTask<Void,Void,Void> {

    private String filePath;
    JsonFileParser fileParser = new JsonFileParser();
    Context activityContext;
    StoreCheckImportActivity _mactivity;
    Exception backgroundException;

    List<Detail> details = new ArrayList<Detail>();
    List<Market> markets = new ArrayList<Market>();
    List<Outlet> outlets = new ArrayList<Outlet>();
    List<Option> options = new ArrayList<Option>();
    List<Product> products = new ArrayList<Product>();
    List<Channel> channels = new ArrayList<Channel>();
    List<Unit> units = new ArrayList<Unit>();
    List<PackType> packTypes = new ArrayList<PackType>();
    List<CustomField> customFields = new ArrayList<CustomField>();
    MetaData storeCheckMetaData = new MetaData();
    List<Validation> validations = new ArrayList<Validation>();
    List<BrandCustomField> brandCustomFields = new ArrayList<BrandCustomField>();

    public AsyncPreExecute preExecute = null;
    public AsyncProgressReport progressReport = null;
    public AsyncPostExecute postExecute = null;

    @Override
    protected void onPreExecute() {
        preExecute.preExecute("you just called preExecute Async Task in the UI Thread");
    }



    @Override
    protected void onProgressUpdate(Void... values) {
        progressReport.progressReport("you just called progressReport  Async Task in the UI Thread");
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        if (backgroundException != null) {
            _mactivity.messageBox(backgroundException);
            postExecute.PostExecute("Error importing file");

        }
        else {
            postExecute.PostExecute("Import completed successfully ");
        }
    }

    public ImportDataTask(String filePath, Context context, StoreCheckImportActivity activity) {
        this.filePath = filePath;
        activityContext = context;
        _mactivity = activity;

    }


    public void  processFile() throws Exception {

        loadFileIntoMemory();
        loadModels();

    }


    private  void loadFileIntoMemory() throws Exception {

            fileParser.loadEmmaExtractFile(filePath);

    }


    private void loadModels() throws JSONException {
        fileParser.loadModels(storeCheckMetaData, outlets, customFields, details, markets, options, products, channels, units,
                packTypes, validations, brandCustomFields, activityContext);
    }


    @Override
    protected Void doInBackground(Void... params) {
        try
        {
            processFile();
        }
        catch (Exception e)
        {
            backgroundException = e;
        }
        return null;

    }
}


