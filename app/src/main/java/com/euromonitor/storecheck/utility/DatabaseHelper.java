package com.euromonitor.storecheck.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
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
 */
public class DatabaseHelper extends  SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    SQLiteDatabase database;

    // Database Name
    private static final String DATABASE_NAME = "storeCheckDatabase";

    // Tables name
    private static final String TABLE_OUTLETS = "outlets";
    private static final String TABLE_PRODUCTS = "products";
    private  static final String TABLE_CHANNELS ="channels";
    private  static final String TABLE_DETAILS ="details";
    private  static final String TABLE_MARKETS ="markets";
    private  static final String TABLE_OPTIONS ="options";
    private  static final String TABLE_CUSTOMFIELDS ="customfields";


    private static final String KEY_ID = "id";
    private  static final  String KEY_BRAND= "brand";
    private  static final  String KEY_NBO = "nbo";
    private  static final  String KEY_PRODUCTCODE = "productcode";
    private  static final  String KEY_PROJECTID= "projectid";



    // Outlets Table Columns names

    private static final String KEY_OUTLET_ID = "outlet_id";
    private static final String KEY_PROJECT_ID = "project_id";
    private static final String KEY_GEO_CODE = "geo_code";
    private static final String KEY_GEO_NAME = "geo_name";
    private static final String KEY_INDUSTRY = "industry";
    private static final String KEY_OUTLET_CITY = "outlet_city";
    private static final String KEY_YEAR = "year";
    private static final String KEY_OUTLET_DATE = "outlet_date";


    //Products Table Column Names

    private  static final  String KEY_PRODUCT_ID  = "product_id";
    private  static final  String KEY_Product_Name = "product_name";

    //Channels Table Column Names


    private  static final  String KEY_CHCCODE = "chccode";
    private  static final  String KEY_CHC_NAME = "chcname";


    //Details Table Column Names

    private  static final  String KEY_CHANNELNAME = "channelName";
    private  static final  String KEY_MULTIPACKSIZE= "multipackSize";
    private  static final  String KEY_OUTLETID = "outletId";
    private  static final  String KEY_OUTLETNAME = "outletName";
    private  static final  String KEY_PACKSIZE = "packSize";
    private  static final  String KEY_PACKTYPE= "packType";
    private  static final  String KEY_PACKTYPECODE = "packTypeCode";
    private  static final  String KEY_PRICE = "price";
    private  static final  String KEY_PRICINGID = "pricingId";
    private  static final  String KEY_UNITCODE = "unitCode";
    private  static final  String KEY_UNITPRICELOCAL = "unitPriceLocal";
    private  static final  String KEY_UNITPRICEUS = "unitPriceUS";


    //Markets Table Column Names

    private  static final  String KEY_BRANDMARKETID = "brandmarketid";

    private  static final  String KEY_GEOGRAPHYCODE = "geographycode";
    private  static final  String KEY_GEOGRAPHY = "geography";

    private  static final  String KEY_PRODUCT= "product";
    private  static final  String KEY_COMPANYNAME = "companyname";

    //Options Table Column Names
    private  static final  String KEY_ISDROPDOWN = "isdropdown";
    private  static final  String KEY_OPTIONID= "optionid";
    private  static final  String KEY_OPTIONNAME= "optionname";
    private  static final  String KEY_MINIMUMALLOWED= "minimumallowed";
    private  static final  String KEY_MAXIMUMALLOWED = "maximumallowed";
    private  static final  String KEY_CUSTOMFIELDID= "customfieldid";


    //Custom Fields Column Names


    private  static final  String KEY_CTTCODE= "cttcode";
    private  static final  String KEY_GROUPID= "groupid";
    private  static final  String KEY_LABEL = "label";
    private  static final  String KEY_OBJECTID= "objectid";
    private  static final  String KEY_OPTIONS = "options";
    private  static final  String KEY_TOOLTIP= "tooltip";
    private  static final  String KEY_UNIQUEID= "uniqueid";






    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        database = db;

    }


    private  void  createTables()
    {

         Log.e("createTables ::", "createTables is called");
            createOutletTable();
            createProductTable();
            createChannelTable();
            createDetailTable();
            createMarketTable();
            createOptionTable();
            createCustimFieldssTable();
    }

    public  void  loadData(List<Product> products , List<Outlet> outlets , List<Channel> channels,List<Detail> details,List<Market> markets ,List<Option> options,List<CustomField> customFields)
    {

        Log.e("load Data is called ::", "load data");

        dropTables();
        createTables();
        loadProdcutsTable(products);
        loadChannelsTable(channels);
        loadOutletsTable(outlets);
        loadDetailsTable(details);
        loadMarketTable(markets);
        loadOptionsTable(options);
        loadCustomFiledsTable(customFields);

    }


    private void loadProdcutsTable(List<Product> products)
    {
        Log.e("loadProdcutsTable::" ,"loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = products.iterator();
        while (iterator.hasNext())
        {
            Product product = (Product)iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_PRODUCT_ID,product.get_product_id());
            values.put(KEY_Product_Name,product.get_product_name());
            db.insert(TABLE_PRODUCTS, null, values);

        }

        db.close(); // Closing database connection
    }

    private void loadChannelsTable(List<Channel> channels)
    {
        Log.e("loadChannelsTable::" ,"loadChannelsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = channels.iterator();
        while (iterator.hasNext())
        {
            Channel channel = (Channel)iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_CHCCODE,channel.get_chc_code());
            values.put(KEY_CHC_NAME, channel.get_chc_name());
            db.insert(TABLE_CHANNELS, null, values);


        }

        db.close(); // Closing database connection
    }


    private void loadOutletsTable(List<Outlet> outlets)
    {
        Log.e("loadProdcutsTable::" ,"loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = outlets.iterator();
        while (iterator.hasNext())
        {
            Outlet outlet = (Outlet)iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_OUTLET_ID, outlet.get_outlet_id());
            values.put(KEY_PROJECT_ID, outlet.get_project_id());
            values.put(KEY_GEO_CODE, outlet.get_geo_code());
            values.put(KEY_GEO_NAME, outlet.get_geo_name());
            values.put(KEY_INDUSTRY, outlet.get_industry());
            values.put(KEY_OUTLET_CITY, outlet.get_outlet_city());
            values.put(KEY_YEAR, outlet.get_year());
            values.put(KEY_OUTLET_DATE, String.valueOf(outlet.get_outlet_date()));

            // Inserting Row
            db.insert(TABLE_OUTLETS, null, values);


        }

        db.close(); // Closing database connection
    }

    private void loadDetailsTable(List<Detail> details)
    {
        Log.e("loadProdcutsTable::" ,"loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = details.iterator();
        while (iterator.hasNext())
        {
            Detail detail = (Detail)iterator.next();

            ContentValues values = new ContentValues();
           values.put(KEY_CHANNELNAME,detail.get_channel_name());
            values.put(KEY_MULTIPACKSIZE,detail.get_multipack_size());
            values.put(KEY_OUTLETID,detail.get_outlet_id());
            values.put(KEY_OUTLETNAME,detail.get_outlet_name());
            values.put(KEY_PACKSIZE,detail.get_pack_size());
            values.put(KEY_PACKTYPE,detail.get_pack_type());
            values.put(KEY_PACKTYPECODE,detail.get_packtype_code());
            values.put(KEY_PRICE,detail.get_price());
            values.put(KEY_PRICINGID,detail.get_pricing_id());
            values.put(KEY_UNITCODE,detail.get_unit_code());
            values.put(KEY_UNITPRICELOCAL,detail.get_unit_price_local());
            values.put(KEY_UNITPRICEUS,detail.get_unit_price_us());
            values.put(KEY_BRAND,detail.get_brand());
            values.put(KEY_NBO,detail.get_nbo());
            // Inserting Row
            db.insert(TABLE_DETAILS, null, values);
        }

        db.close(); // Closing database connection
    }


    private void loadMarketTable(List<Market> markets)
    {
        Log.e("loadProdcutsTable::" ,"loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = markets.iterator();
        while (iterator.hasNext())
        {
            Market market = (Market)iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_BRANDMARKETID,market.get_brand_market_id());
            values.put(KEY_PROJECTID,market.get_project_id());
            values.put(KEY_GEOGRAPHYCODE,market.get_geography_code());
            values.put(KEY_GEOGRAPHY,market.getGeography());
            values.put(KEY_PRODUCTCODE,market.get_product_code());
            values.put(KEY_PRODUCT,market.get_product());
            values.put(KEY_BRAND,market.get_brand());
            values.put(KEY_NBO,market.get_nbo());
            values.put(KEY_COMPANYNAME,market.get_company_name());


            // Inserting Row
            db.insert(TABLE_MARKETS, null, values);
        }

        db.close(); // Closing database connection
    }


    private void loadOptionsTable(List<Option> options)
    {
        Log.e("loadProdcutsTable::" ,"loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = options.iterator();
        while (iterator.hasNext())
        {
            Option option = (Option)iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_ISDROPDOWN,option.is_is_dropdown());
            values.put(KEY_OPTIONID,option.getOptionId());
            values.put(KEY_OPTIONNAME,option.getOptionName());
            values.put(KEY_MINIMUMALLOWED,option.getMinimumAllowed());
            values.put(KEY_MAXIMUMALLOWED,option.getMaximumAllowed());
            values.put(KEY_CUSTOMFIELDID,option.getUniqueID());

            // Inserting Row
            db.insert(TABLE_OPTIONS, null, values);
        }

        db.close(); // Closing database connection
    }

    private void loadCustomFiledsTable(List<CustomField> customFields)
    {
        Log.e("loadProdcutsTable::" ,"loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = customFields.iterator();
        while (iterator.hasNext())
        {
            CustomField customField = (CustomField)iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_PROJECTID,customField.get_project_id());
            values.put(KEY_PRODUCTCODE,customField.get_product_code());
            values.put(KEY_CTTCODE,customField.get_ctt_code());
            values.put(KEY_GROUPID,customField.get_group_id());
            values.put(KEY_LABEL,customField.get_label());
            values.put(KEY_OBJECTID,customField.get_object_id());
            values.put(KEY_TOOLTIP,customField.get_tooltip());
            values.put(KEY_UNIQUEID,customField.getUniqueID());


            // Inserting Row
            db.insert(TABLE_CUSTOMFIELDS, null, values);
        }

        db.close(); // Closing database connection
    }


    private void  createOutletTable()
    {
        String CREATE_OUTLETS_TABLE = "CREATE TABLE " + TABLE_OUTLETS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_OUTLET_ID + " TEXT,"
                + KEY_PROJECT_ID + " TEXT,"
                + KEY_GEO_CODE + " TEXT,"
                + KEY_GEO_NAME + " TEXT,"
                + KEY_INDUSTRY + " TEXT,"
                + KEY_OUTLET_CITY + " TEXT,"
                + KEY_YEAR + " TEXT,"
                + KEY_OUTLET_DATE + " TEXT"+ ")";

        database.execSQL(CREATE_OUTLETS_TABLE);

    }

    private void  createProductTable()
    {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PRODUCT_ID + " TEXT,"
                + KEY_Product_Name + " TEXT"+ ")";
        database.execSQL(CREATE_PRODUCTS_TABLE);

    }


    private void  createChannelTable()
    {

        String CREATE_CHANNLES_TABLE = "CREATE TABLE " + TABLE_CHANNELS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CHCCODE + " TEXT,"
                + KEY_CHC_NAME + " TEXT"+ ")";
        database.execSQL(CREATE_CHANNLES_TABLE);

    }

    private void  createDetailTable()
    {

        String CREATE_DETAILS_TABLE = "CREATE TABLE " + TABLE_DETAILS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CHANNELNAME + " TEXT,"
                + KEY_MULTIPACKSIZE + " TEXT,"
                + KEY_OUTLETID + " TEXT,"
                + KEY_OUTLETNAME + " TEXT,"
                + KEY_PACKSIZE + " TEXT,"
                + KEY_PACKTYPE + " TEXT,"
                + KEY_PACKTYPECODE + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_PRICINGID + " TEXT,"
                + KEY_UNITCODE + " TEXT,"
                + KEY_UNITPRICELOCAL + " TEXT,"
                + KEY_UNITPRICEUS + " TEXT,"
                + KEY_BRAND + " TEXT,"
                + KEY_NBO + " TEXT"+ ")";
        database.execSQL(CREATE_DETAILS_TABLE);

    }

    private void  createMarketTable()
    {

        String CREATE_MARKETS_TABLE = "CREATE TABLE " + TABLE_MARKETS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_BRANDMARKETID + " TEXT,"
                + KEY_PROJECTID + " TEXT,"
                + KEY_GEOGRAPHYCODE + " TEXT,"
                + KEY_GEOGRAPHY + " TEXT,"
                + KEY_PRODUCTCODE + " TEXT,"
                + KEY_PRODUCT + " TEXT,"
                + KEY_BRAND + " TEXT,"
                + KEY_NBO + " TEXT,"
                + KEY_COMPANYNAME + " TEXT"+ ")";
        database.execSQL(CREATE_MARKETS_TABLE);

    }


    private void  createOptionTable()
    {
        String CREATE_OPTIONS_TABLE = "CREATE TABLE " + TABLE_OPTIONS+ "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ISDROPDOWN + " TEXT,"
                + KEY_OPTIONID + " TEXT,"
                + KEY_OPTIONNAME + " TEXT,"
                + KEY_MINIMUMALLOWED + " TEXT,"
                + KEY_MAXIMUMALLOWED + " TEXT,"
                + KEY_CUSTOMFIELDID + " TEXT"+ ")";
        database.execSQL(CREATE_OPTIONS_TABLE);

    }

    private void  createCustimFieldssTable()
    {
        String CREATE_CUSTOMFIELDS_TABLE = "CREATE TABLE " + TABLE_CUSTOMFIELDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PROJECTID + " TEXT,"
                + KEY_PRODUCTCODE + " TEXT,"
                + KEY_CTTCODE + " TEXT,"
                + KEY_GROUPID + " TEXT,"
                + KEY_LABEL + " TEXT,"
                + KEY_OBJECTID + " TEXT,"
                + KEY_OPTIONS + " TEXT,"
                + KEY_TOOLTIP + " TEXT,"
                + KEY_UNIQUEID + " TEXT"+ ")";
        database.execSQL(CREATE_CUSTOMFIELDS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);

    }


    private  void  dropTables()
    {
        database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_OUTLETS+"'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_CHANNELS+"'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_PRODUCTS+"'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_DETAILS+"'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_MARKETS+"'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_OPTIONS+"'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_CUSTOMFIELDS+"'");
    }



    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new outlet
   public void addOutlet(Outlet outlet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OUTLET_ID, outlet.get_outlet_id());
        values.put(KEY_PROJECT_ID, outlet.get_project_id());
       /* values.put(KEY_GEO_CODE, outlet.get_geo_code());
        values.put(KEY_GEO_NAME, outlet.get_geo_name());
        values.put(KEY_INDUSTRY, outlet.get_industry());
        values.put(KEY_OUTLET_CITY, outlet.get_outlet_city());
        values.put(KEY_YEAR, outlet.get_year());
        values.put(KEY_OUTLET_DATE, String.valueOf(outlet.get_outlet_date()));*/

        // Inserting Row
        db.insert(TABLE_OUTLETS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single outlet
//    Outlet getOutlet(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_OUTLETS, new String[] { KEY_ID,
//                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Outlet outlet = new Outlet(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        // return outlet
//        return outlet;
//    }

    // Getting All Outlets
    public List<Outlet> getAllOutlets() {
        List<Outlet> outletList = new ArrayList<Outlet>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OUTLETS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Outlet outlet = new Outlet();
                outlet.set_outlet_id(Integer.parseInt(cursor.getString(1)));
                outlet.set_project_id(Integer.parseInt(cursor.getString(2)));
               // outlet.set_geo_code(cursor.getString(3));
                // Adding outlet to list
                outletList.add(outlet);
            } while (cursor.moveToNext());
        }

        // return outlet list
        return outletList;
    }

//    // Updating single outlet
//    public int updateOutlet(Outlet outlet) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, outlet.getName());
//        values.put(KEY_PH_NO, outlet.getPhoneNumber());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(outlet.getID()) });
//    }
//
//    // Deleting single outlet
//    public void deleteOutlet(Outlet outlet) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(outlet.getID()) });
//        db.close();
//    }
//
//
//    // Getting outlets Count
//    public int getOutletsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(Exception sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
}