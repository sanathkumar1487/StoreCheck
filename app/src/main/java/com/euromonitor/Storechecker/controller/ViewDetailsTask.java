package com.euromonitor.Storechecker.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.euromonitor.Storechecker.app.StoreCheckDetailsActivity;
import com.euromonitor.Storechecker.controller.interfaces.AsyncPostExceuteDetails;
import com.euromonitor.Storechecker.controller.interfaces.AsyncPreExecute;
import com.euromonitor.Storechecker.controller.interfaces.AsyncProgressReport;
import com.euromonitor.Storechecker.model.StoreCheckDetail;
import com.euromonitor.Storechecker.utility.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Sanath.Kumar on 26-Apr-16.
 */
public class ViewDetailsTask extends AsyncTask<Void, Void, ArrayList<StoreCheckDetail> > {

    Context activityContext;
    StoreCheckDetailsActivity _mactivity;
    Exception backgroundException;
    public int productId;


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
        return  databaseHelper.GetDetailsByProductCode(productId);

    }
}
