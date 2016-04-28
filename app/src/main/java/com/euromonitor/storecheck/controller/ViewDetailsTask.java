package com.euromonitor.storecheck.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.euromonitor.storecheck.app.StoreCheckDetailsActivity;
import com.euromonitor.storecheck.app.StoreCheckImportActivity;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExceuteDetails;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncPreExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncProgressReport;
import com.euromonitor.storecheck.model.StoreCheckDetail;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Sanath.Kumar on 26-Apr-16.
 */
public class ViewDetailsTask extends AsyncTask<Void, Void, ArrayList<StoreCheckDetail> > {

    Context activityContext;
    StoreCheckDetailsActivity _mactivity;
    Exception backgroundException;


    public AsyncPreExecute preExecute = null;
    public AsyncProgressReport progressReport = null;
    public AsyncPostExceuteDetails postExecute = null;

    public ViewDetailsTask(Context activityContext) {
        this.activityContext = activityContext;
    }

    @Override
    protected void onPreExecute() {

        preExecute.preExecute("you just called preExecute Async Task in the UI Thread");
    }





    @Override
    protected void onProgressUpdate(Void... values) {
        progressReport.progressReport("you just called progressReport  Async Task in the UI Thread");
    }


    @Override
    protected void onPostExecute(ArrayList<StoreCheckDetail> storeCheckDetails) {

        postExecute.PostExecute(storeCheckDetails);
        super.onPostExecute(storeCheckDetails);
    }

    @Override
    protected ArrayList<StoreCheckDetail>  doInBackground(Void... params) {

        DatabaseHelper databaseHelper = new DatabaseHelper(activityContext);
        return  databaseHelper.GetAllProductDetails();

    }
}
