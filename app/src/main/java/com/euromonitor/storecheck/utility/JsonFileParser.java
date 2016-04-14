package com.euromonitor.storecheck.utility;

import android.content.Context;
import android.util.Log;

import com.euromonitor.storecheck.model.Unit;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import com.euromonitor.storecheck.model.Channel;
import com.euromonitor.storecheck.model.CustomField;
import com.euromonitor.storecheck.model.Detail;
import com.euromonitor.storecheck.model.Market;
import com.euromonitor.storecheck.model.Option;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.Product;


/**
 * Created by Sanath.Kumar on 3/30/2016.
 * Loads the .EmmaStoreCheck file and populates all the Models values to be saved to the database
 */
public  class JsonFileParser {
    Gson gsonObject = new Gson();
    StringBuilder rawJsonDataBuilder;
    String rawJsonData;

  public  void loadEmmaExtractFile(String path) throws Exception {

     Log.e("calling::","  loadEmmaExtractFile");

        File emmaExtractFile = new File(path);
        rawJsonDataBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(emmaExtractFile));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                rawJsonDataBuilder.append(line);
                rawJsonDataBuilder.append('\n');
            }
            bufferedReader.close();
            }

        catch (Exception e)
        {
           throw e;
        }

      rawJsonData = rawJsonDataBuilder.toString();

    }

    public void loadModels(List<Outlet> outlets,List<CustomField> customFields,List<Detail> details,List<Market> markets,List<Option> options ,List<Product> products,List<Channel> channels, List<Unit> units ,Context context) throws JSONException {

             JSONObject rootData = new JSONObject(rawJsonData);

             products = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("StoreCheckProduct").toString(),Product[].class));
             Log.e("products data Count::", String.valueOf(products.size()));

            JSONObject outletJson =  rootData.getJSONObject("Outlets");

                outlets  = Arrays.asList(gsonObject.fromJson(outletJson.getJSONArray("StoreCheckOutlets").toString(),Outlet[].class));
                Log.e("outlets data Count::", String.valueOf(outlets.size()));

                channels  = Arrays.asList(gsonObject.fromJson(outletJson.getJSONArray("Channels").toString(),Channel[].class));
                Log.e("channels data Count::", String.valueOf(channels.size()));

                 /* Store Check Grid data*/
                details = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("StoreChecks").toString(),Detail[].class));
                Log.e("details data Count::", String.valueOf(details.size()));

                 /* Store Check Market  data*/
                 markets = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("StoreCheckMarkets").toString(),Market[].class));
                 Log.e("markets data Count::", String.valueOf(markets.size()));

                 /* Store Check Custom Field  data*/
                customFields = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("CustomFields").toString(),CustomField[].class));
                Iterator iterator = customFields.iterator();
                while (iterator.hasNext()) {
                    CustomField customField = (CustomField) iterator.next();
                    if (customField != null) {
                        customField.generateUniqueID();
                        options.addAll(customField.get_options());
                    }
                }

                 /* Store Check Market  data*/
        units = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("Units").toString(),Unit[].class));
        Log.e("Units data Count::", String.valueOf(units.size()));




        DatabaseHelper databaseHelper = new DatabaseHelper(context);
            Log.d("Insert :", "Inserting Products... ");
            databaseHelper.loadData(products, outlets, channels, details, markets, options, customFields, units);



    }







}
