package com.euromonitor.storecheck.utility;

import android.content.Context;
import android.util.Log;

import com.euromonitor.storecheck.model.BrandCustomField;
import com.euromonitor.storecheck.model.MetaData;
import com.euromonitor.storecheck.model.PackType;
import com.euromonitor.storecheck.model.Unit;
import com.euromonitor.storecheck.model.Validation;
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

    public void loadEmmaExtractFile(String path) throws Exception
    {

        Log.e("calling::", "  loadEmmaExtractFile");

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
        } catch (Exception e) {
            throw e;
        }

        rawJsonData = rawJsonDataBuilder.toString();

    }

    public void loadModels(MetaData storeCheckMetaData, List<Outlet> outlets, List<CustomField> customFields, List<Detail> details,
                           List<Market> markets, List<Option> options, List<Product> products, List<Channel> channels,
                           List<Unit> units ,List<PackType> packTypes, List<Validation> validations,
                           List<BrandCustomField> brandCustomFields, Context context) throws JSONException {

        JSONObject rootData = new JSONObject(rawJsonData);

        products = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("StoreCheckProduct").toString(), Product[].class));


        JSONObject outletJson = rootData.getJSONObject("Outlets");

        outlets = Arrays.asList(gsonObject.fromJson(outletJson.getJSONArray("StoreCheckOutlets").toString(), Outlet[].class));

        channels = Arrays.asList(gsonObject.fromJson(outletJson.getJSONArray("Channels").toString(), Channel[].class));


                 /* Store Check Grid data*/
        details = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("StoreChecks").toString(), Detail[].class));


                 /* Store Check Market  data*/
        markets = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("StoreCheckMarkets").toString(), Market[].class));


                 /* Store Check Custom Field  data*/
        Log.e("customfield ::", rootData.getJSONArray("CustomFields").toString());
        customFields = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("CustomFields").toString(), CustomField[].class));
        Iterator iterator = customFields.iterator();
        while (iterator.hasNext()) {
            CustomField customField = (CustomField) iterator.next();
            if (customField != null) {
                customField.generateUniqueID();
                options.addAll(customField.get_options());
            }
        }

                 /* Store Check Market  data*/
        units = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("Units").toString(), Unit[].class));
        packTypes = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("PackTypes").toString(), PackType[].class));
        storeCheckMetaData = gsonObject.fromJson(rootData.getJSONObject("StoreCheckMetaData").toString(), MetaData.class);

        validations = Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("Validations").toString(), Validation[].class));

        brandCustomFields =  Arrays.asList(gsonObject.fromJson(rootData.getJSONArray("BrandSelectedCustomFields").toString(), BrandCustomField[].class));

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.loadData(storeCheckMetaData, products, outlets, channels, details, markets, options, customFields,
                units,packTypes, validations, brandCustomFields);


    }


}