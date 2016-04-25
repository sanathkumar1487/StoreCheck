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
import com.euromonitor.storecheck.model.Geography;
import com.euromonitor.storecheck.model.Market;
import com.euromonitor.storecheck.model.MetaData;
import com.euromonitor.storecheck.model.Option;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.PackType;
import com.euromonitor.storecheck.model.Product;
import com.euromonitor.storecheck.model.StoreCheckBrand;
import com.euromonitor.storecheck.model.StoreCheckDetail;
import com.euromonitor.storecheck.model.Unit;


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

    private static final String TABLE_METADATA = "storecheckmetadata";
    private static final String TABLE_OUTLETS = "outlets";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CHANNELS = "channels";
    private static final String TABLE_DETAILS = "details";
    private static final String TABLE_MARKETS = "markets";
    private static final String TABLE_OPTIONS = "options";
    private static final String TABLE_CUSTOMFIELDS = "customfields";
    private static final String TABLE_UNITS = "units";
    private static final String TABLE_BRANDS = "brands";
    private static final String TABLE_BRANDCUSTOMFIELDS = "brandcustomfields";
    private static final String TABLE_PACKTYPES = "packtypes";


    private static final String KEY_ID = "id";
    private static final String KEY_BRAND = "brand";
    private static final String KEY_NBO = "nbo";

    private static final String KEY_PROJECTID = "projectid";
    private static final String KEY_UPDATED = "updated";
    private static final String KEY_NEW = "new";


    // Meta-data Table columns name


    // Outlets Table Columns names
    private static final String KEY_INDUSTRY_NAME = "industry_name";

    private static final String KEY_OUTLET_ID = "outlet_id";
    private static final String KEY_OUTLET_Name = "outlet_name";
    private static final String KEY_CHANNEL_NAME = "channel_name";
    private static final String KEY_PROJECT_ID = "project_id";
    private static final String KEY_GEO_CODE = "geo_code";
    private static final String KEY_GEO_NAME = "geo_name";
    private static final String KEY_INDUSTRY = "industry";
    private static final String KEY_OUTLET_CITY = "outlet_city";
    private static final String KEY_YEAR = "year";
    private static final String KEY_OUTLET_DATE = "outlet_date";


    //Products Table Column Names

    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_Product_Name = "product_name";
    private static final String KEY_PRODUCTNAME = "productName";

    //Channels Table Column Names


    private static final String KEY_CHCCODE = "chccode";
    private static final String KEY_CHC_NAME = "chcname";


    //Details Table Column Names

    private static final String KEY_CHANNELNAME = "channelName";
    private static final String KEY_MULTIPACKSIZE = "multipackSize";
    private static final String KEY_OUTLETID = "outletId";
    private static final String KEY_PRODUCTID = "productId";
    private static final String KEY_OUTLETNAME = "outletName";
    private static final String KEY_PACKSIZE = "packSize";
    private static final String KEY_PACKTYPE = "packType";
    private static final String KEY_PACKTYPECODE = "packTypeCode";
    private static final String KEY_PRICE = "price";
    private static final String KEY_PRICINGID = "pricingId";
    private static final String KEY_UNITCODE = "unitCode";
    private static final String KEY_UNITPRICELOCAL = "unitPriceLocal";
    private static final String KEY_UNITPRICEUS = "unitPriceUS";


    //Markets Table Column Names

    private static final String KEY_BRANDMARKETID = "brandmarketid";

    private static final String KEY_GEOGRAPHYCODE = "geographycode";
    private static final String KEY_GEOGRAPHY = "geography";

    private static final String KEY_PRODUCT = "product";
    private static final String KEY_COMPANYNAME = "companyname";

    //Options Table Column Names
    private static final String KEY_ISDROPDOWN = "isdropdown";
    private static final String KEY_OPTIONID = "optionid";
    private static final String KEY_OPTIONNAME = "optionname";
    private static final String KEY_MINIMUMALLOWED = "minimumallowed";
    private static final String KEY_MAXIMUMALLOWED = "maximumallowed";
    private static final String KEY_CUSTOMFIELDID = "customfieldid";
    private static final String KEY_ISNUMERIC = "isNumeric";
    private static final String KEY_ISZEROALLOWED = "isZeroAllowed";

    //Custom Fields Column Names


    private static final String KEY_CTTCODE = "cttcode";
    private static final String KEY_GROUPID = "groupid";
    private static final String KEY_LABEL = "label";
    private static final String KEY_OBJECTID = "objectid";
    private static final String KEY_OPTIONS = "options";
    private static final String KEY_TOOLTIP = "tooltip";
    private static final String KEY_UNIQUEID = "uniqueid";
    private static final String KEY_FRAMEGROUPID = "frameGroupId";


    // Units Table Column Names
    private static final String KEY_UNITID = "unitid";
    private static final String KEY_UNITNAME = "unitname";
    private static final String KEY_UNITBASE = "unitbase";
    private static final String KEY_UNITMULTIPLIER = "unitmultiplier";

    // Brands Column Names
    private static final String KEY_BRANDID = "brandid";
    private static final String KEY_OPTIONVALUE = "optionvalue";



    //packtype columns

    private static  final  String KEY_PACKTYPENAME ="packtypename";
    private static  final  String KEY_PRODUCTCODE ="productcodes";

    // Custom Field Control Type
    final String DropDown = "1";
    final String TextBox = "2";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        database = db;

    }


    private void createTables() {

        Log.e("createTables ::", "createTables is called");
        createMetaDataTable();
        createOutletTable();
        createProductTable();
        createChannelTable();
        createDetailTable();
        createMarketTable();
        createOptionTable();
        createCustimFieldssTable();
        createUnitTable();
        createBrandsTable();
        createBrandCustomFieldsTable();
        createPacktypeTable();
    }

    public void loadData(MetaData storeCheckMetaData, List<Product> products, List<Outlet> outlets, List<Channel> channels, List<Detail> details, List<Market> markets, List<Option> options, List<CustomField> customFields, List<Unit> units ,List<PackType> packTypes) {

        Log.e("load Data is called ::", "load data");

        dropTables();
        createTables();
        loadStoreCheckMetaDataTable(storeCheckMetaData);
        loadProdcutsTable(products);
        loadChannelsTable(channels);
        loadOutletsTable(outlets);
        loadDetailsTable(details);
        loadMarketTable(markets);
        loadOptionsTable(options);
        loadCustomFiledsTable(customFields);
        loadUnitsTable(units);
        loadpacktypeTable( packTypes);

    }

    private void loadStoreCheckMetaDataTable(MetaData storeCheckMetaData) {
        Log.e("loadStoreCheckMe::", "loadStoreCheckMetaDataTable load data");
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_GEOGRAPHYCODE, storeCheckMetaData.getGeographyCode());
        values.put(KEY_GEOGRAPHY, storeCheckMetaData.getGeographyName());
        values.put(KEY_INDUSTRY_NAME, storeCheckMetaData.getIndustryName());
        values.put(KEY_YEAR, storeCheckMetaData.getYear());
        db.insert(TABLE_METADATA, null, values);
        db.close();
    }

    private void loadProdcutsTable(List<Product> products) {
        Log.e("loadProdcutsTable::", "loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = (Product) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_PRODUCT_ID, product.get_product_id());
            values.put(KEY_Product_Name, product.get_product_name());
            db.insert(TABLE_PRODUCTS, null, values);

        }

        db.close(); // Closing database connection
    }

    public void loadChannelsTable(List<Channel> channels) {
        Log.e("loadChannelsTable::", "loadChannelsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = channels.iterator();
        while (iterator.hasNext()) {
            Channel channel = (Channel) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_CHCCODE, channel.get_chc_code());
            values.put(KEY_CHC_NAME, channel.get_chc_name());
            db.insert(TABLE_CHANNELS, null, values);


        }

        db.close(); // Closing database connection
    }

    private void loadOutletsTable(List<Outlet> outlets) {
        Log.e("loadProdcutsTable::", "loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = outlets.iterator();
        while (iterator.hasNext()) {
            Outlet outlet = (Outlet) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_OUTLET_ID, outlet.get_outlet_id());
            values.put(KEY_OUTLET_Name, outlet.get_outlet_Name());
            values.put(KEY_CHANNEL_NAME, outlet.get_channel_name());

            values.put(KEY_PROJECT_ID, outlet.get_project_id());
            values.put(KEY_GEO_CODE, outlet.get_geo_code());
            values.put(KEY_GEO_NAME, outlet.get_geo_name());
            values.put(KEY_INDUSTRY, outlet.get_industry());
            values.put(KEY_OUTLET_CITY, outlet.get_outlet_city());
            values.put(KEY_YEAR, outlet.get_year());
            values.put(KEY_CHCCODE, outlet.get_chccode());
            values.put(KEY_OUTLET_DATE, String.valueOf(outlet.get_outlet_date()));

            // Inserting Row
            db.insert(TABLE_OUTLETS, null, values);


        }

        db.close(); // Closing database connection
    }

    private void loadDetailsTable(List<Detail> details) {
        Log.e("loadProdcutsTable::", "loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = details.iterator();
        while (iterator.hasNext()) {
            Detail detail = (Detail) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_CHANNELNAME, detail.get_channel_name());
            values.put(KEY_MULTIPACKSIZE, detail.get_multipack_size());
            values.put(KEY_OUTLETID, detail.get_outlet_id());
            values.put(KEY_PRODUCTID, detail.get_product_id());
            values.put(KEY_OUTLETNAME, detail.get_outlet_name());
            values.put(KEY_PACKSIZE, detail.get_pack_size());
            values.put(KEY_PACKTYPE, detail.get_pack_type());
            values.put(KEY_PACKTYPECODE, detail.get_packtype_code());
            values.put(KEY_PRICE, detail.get_price());
            values.put(KEY_PRICINGID, detail.get_pricing_id());
            values.put(KEY_UNITCODE, detail.get_unit_code());
            values.put(KEY_UNITPRICELOCAL, detail.get_unit_price_local());
            values.put(KEY_UNITPRICEUS, detail.get_unit_price_us());
            values.put(KEY_BRAND, detail.get_brand());
            values.put(KEY_NBO, detail.get_nbo());
            // Inserting Row
            db.insert(TABLE_DETAILS, null, values);
        }

        db.close(); // Closing database connection
    }

    private void loadMarketTable(List<Market> markets) {
        Log.e("loadProdcutsTable::", "loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = markets.iterator();
        while (iterator.hasNext()) {
            Market market = (Market) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_BRANDMARKETID, market.get_brand_market_id());
            values.put(KEY_PROJECTID, market.get_project_id());
            values.put(KEY_GEOGRAPHYCODE, market.get_geography_code());
            values.put(KEY_GEOGRAPHY, market.getGeography());
            values.put(KEY_PRODUCTCODE, market.get_product_code());
            values.put(KEY_PRODUCT, market.get_product());
            values.put(KEY_BRAND, market.get_brand());
            values.put(KEY_NBO, market.get_nbo());
            values.put(KEY_COMPANYNAME, market.get_company_name());


            // Inserting Row
            db.insert(TABLE_MARKETS, null, values);
        }

        db.close(); // Closing database connection
    }

    private void loadOptionsTable(List<Option> options) {
        Log.e("loadProdcutsTable::", "loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = options.iterator();
        while (iterator.hasNext()) {
            Option option = (Option) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_ISDROPDOWN, option.is_is_dropdown());
            values.put(KEY_OPTIONID, option.getOptionId());
            values.put(KEY_OPTIONNAME, option.getOptionName());
            values.put(KEY_MINIMUMALLOWED, option.getMinimumAllowed());
            values.put(KEY_MAXIMUMALLOWED, option.getMaximumAllowed());
            values.put(KEY_CUSTOMFIELDID, option.getUniqueID());
            values.put(KEY_ISNUMERIC, option.getIsNumeric());
            values.put(KEY_ISZEROALLOWED, option.getIsZeroAllowed());

            db.insert(TABLE_OPTIONS, null, values);
        }

        db.close(); // Closing database connection
    }

    private void loadCustomFiledsTable(List<CustomField> customFields) {
        Log.e("loadProdcutsTable::", "loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = customFields.iterator();
        while (iterator.hasNext()) {
            CustomField customField = (CustomField) iterator.next();
            if (customField != null) {
                ContentValues values = new ContentValues();
                values.put(KEY_PROJECTID, customField.get_project_id());
                values.put(KEY_PRODUCTCODE, customField.get_product_code());
                values.put(KEY_CTTCODE, customField.get_ctt_code());
                values.put(KEY_GROUPID, customField.get_group_id());
                values.put(KEY_LABEL, customField.get_label());
                values.put(KEY_OBJECTID, customField.get_object_id());
                values.put(KEY_TOOLTIP, customField.get_tooltip());
                values.put(KEY_UNIQUEID, customField.getUniqueID());
                values.put(KEY_FRAMEGROUPID, customField.getFrameGroupID());


                db.insert(TABLE_CUSTOMFIELDS, null, values);
            }
        }

        db.close(); // Closing database connection
    }

    private void loadUnitsTable(List<Unit> units) {
        Log.e("loadProdcutsTable::", "loadProdcutsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = units.iterator();
        while (iterator.hasNext()) {
            Unit unit = (Unit) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_UNITID, unit.get_unit_id());
            values.put(KEY_UNITNAME, unit.get_unit_name());
            values.put(KEY_UNITBASE, unit.get_unit_base());
            values.put(KEY_UNITMULTIPLIER, unit.get_unit_multiplier());

            // Inserting Row
            db.insert(TABLE_UNITS, null, values);
        }

        db.close();
    }


    private void loadpacktypeTable(List<PackType> packTypes) {
        Log.e("loadpacktypeTable::", "loadpacktypeTable load data");

        Log.e("Start ::",String.valueOf( System.currentTimeMillis()));
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = packTypes.iterator();
        while (iterator.hasNext()) {



            PackType packType = (PackType) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_PACKTYPECODE, packType.get_packtypecode());
            values.put(KEY_PACKTYPENAME, packType.get_packtypename());

             Iterator produtcodelist =  packType.get_productcodes().iterator();

            while (produtcodelist.hasNext())
            {
                String productcode = (String) produtcodelist.next();

                values.put(KEY_PRODUCTCODE, productcode);
                db.insert(TABLE_PACKTYPES, null, values);

            }



        }
        Log.e("End ::",String.valueOf( System.currentTimeMillis()));
        db.close();
    }

    private void createMetaDataTable() {
        //
        String CREATE_METADAT_TABLE = "CREATE TABLE " + TABLE_METADATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_GEOGRAPHYCODE + " TEXT,"
                + KEY_GEOGRAPHY + " TEXT, "
                + KEY_INDUSTRY_NAME + " INT, "
                + KEY_YEAR + " TEXT"
                + ")";

        database.execSQL(CREATE_METADAT_TABLE);
    }

    private void createOutletTable() {
        String CREATE_OUTLETS_TABLE = "CREATE TABLE " + TABLE_OUTLETS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_OUTLET_ID + " TEXT,"
                + KEY_OUTLET_Name + " TEXT, "
                + KEY_CHANNEL_NAME + " TEXT, "
                + KEY_PROJECT_ID + " TEXT,"
                + KEY_GEO_CODE + " TEXT,"
                + KEY_GEO_NAME + " TEXT,"
                + KEY_INDUSTRY + " TEXT,"
                + KEY_OUTLET_CITY + " TEXT,"
                + KEY_YEAR + " TEXT,"
                + KEY_CHCCODE + " TEXT,"
                + KEY_UPDATED + " TEXT,"
                + KEY_NEW + " TEXT,"
                + KEY_OUTLET_DATE + " TEXT" + ")";

        database.execSQL(CREATE_OUTLETS_TABLE);

    }

    private void createProductTable() {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PRODUCT_ID + " TEXT,"
                + KEY_Product_Name + " TEXT" + ")";
        database.execSQL(CREATE_PRODUCTS_TABLE);

    }

    private void createChannelTable() {

        String CREATE_CHANNLES_TABLE = "CREATE TABLE " + TABLE_CHANNELS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CHCCODE + " TEXT,"
                + KEY_CHC_NAME + " TEXT" + ")";
        database.execSQL(CREATE_CHANNLES_TABLE);

    }

    private void createDetailTable() {

        String CREATE_DETAILS_TABLE = "CREATE TABLE " + TABLE_DETAILS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CHANNELNAME + " TEXT,"
                + KEY_MULTIPACKSIZE + " TEXT,"
                + KEY_OUTLETID + " TEXT,"
                + KEY_PRODUCTID + " TEXT,"
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
                + KEY_NBO + " TEXT" + ")";
        database.execSQL(CREATE_DETAILS_TABLE);

    }

    private void createMarketTable() {

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
                + KEY_COMPANYNAME + " TEXT" + ")";
        database.execSQL(CREATE_MARKETS_TABLE);

    }

    private void createOptionTable() {
        String CREATE_OPTIONS_TABLE = "CREATE TABLE " + TABLE_OPTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ISDROPDOWN + " TEXT,"
                + KEY_OPTIONID + " TEXT,"
                + KEY_OPTIONNAME + " TEXT,"
                + KEY_MINIMUMALLOWED + " TEXT,"
                + KEY_MAXIMUMALLOWED + " TEXT,"
                + KEY_ISNUMERIC + " TEXT,"
                + KEY_ISZEROALLOWED + " TEXT,"
                + KEY_CUSTOMFIELDID + " TEXT" + ")";
        database.execSQL(CREATE_OPTIONS_TABLE);

    }

    private void createCustimFieldssTable() {
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
                + KEY_FRAMEGROUPID + " TEXT,"
                + KEY_UNIQUEID + " TEXT" + ")";
        database.execSQL(CREATE_CUSTOMFIELDS_TABLE);

    }

    private void createUnitTable() {
        String CREATE_UNIT_TABLE = " CREATE TABLE " + TABLE_UNITS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_UNITID + " TEXT, "
                + KEY_UNITNAME + " TEXT, "
                + KEY_UNITBASE + " TEXT, "
                + KEY_UNITMULTIPLIER + " TEXT ) ";
        database.execSQL(CREATE_UNIT_TABLE);
    }

    private void createBrandsTable() {
        String CREATE_BRANDS_TABLE = " CREATE TABLE " + TABLE_BRANDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_BRAND + " TEXT, "
                + KEY_NBO + " TEXT)";
        database.execSQL(CREATE_BRANDS_TABLE);
    }

    private void createBrandCustomFieldsTable() {
        String CREATE_BRANDCUSTOMFIELD_TABLE = " CREATE TABLE " + TABLE_BRANDCUSTOMFIELDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_BRANDID + " TEXT, "
                + KEY_CUSTOMFIELDID + " TEXT, "
                + KEY_OPTIONID + " TEXT, "
                + KEY_OPTIONVALUE + " TEXT) ";
        database.execSQL(CREATE_BRANDCUSTOMFIELD_TABLE);
    }

    private void createPacktypeTable() {
        String CREATE_PACKTYPE_TABLE = " CREATE TABLE " + TABLE_PACKTYPES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_PACKTYPECODE+ " TEXT, "
                + KEY_PACKTYPENAME + " TEXT, "
                + KEY_PRODUCTCODE + " TEXT) ";
        database.execSQL(CREATE_PACKTYPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    private void dropTables() {
        database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_METADATA + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_OUTLETS + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_CHANNELS + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_PRODUCTS + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_DETAILS + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_MARKETS + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_OPTIONS + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_CUSTOMFIELDS + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_UNITS + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_BRANDS + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_BRANDCUSTOMFIELDS + "'");
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_PACKTYPES + "'");

    }

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
                outlet.set_outlet_id(cursor.getString(1));
                outlet.set_project_id(Integer.parseInt(cursor.getString(2)));
                // outlet.set_geo_code(cursor.getString(3));
                // Adding outlet to list
                outletList.add(outlet);
            } while (cursor.moveToNext());
        }

        // return outlet list
        return outletList;
    }


    public ArrayList<Cursor> getData(String Query) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"mesage"};

        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);
        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);
            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});
            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {
                alc.set(0, c);
                c.moveToFirst();
                return alc;
            }
            return alc;
        } catch (Exception sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

//
//    select * from details  d
//    inner join    products p on p. product_id =   d.productid   inner join   (select distinct unitid,unitname,unitbase  from units) u
//    on u.unitid = d.unitcode

    public ArrayList<StoreCheckDetail> GetAllProductDetails() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select distinct d.pricingId, d.price, d.packSize, d.multipackSize, u.unitname, p.product_Name, m.brand from details d inner join products p on p.product_id = d.productid inner join markets m on m.productcode = d.productid inner join units u on u.unitid = d.unitcode";
        Cursor cursor = database.rawQuery(query, null);
        ArrayList<StoreCheckDetail> storeCheckDetails = null;
        try {
            if (cursor.moveToFirst()) {
                storeCheckDetails = new ArrayList<>();
                do {
                    StoreCheckDetail temp = new StoreCheckDetail();

                    String priceId = cursor.getString(0);
                    if (priceId != null) {
                        temp.setPriceId(Integer.valueOf(priceId));
                    }

                    String price = cursor.getString(1);
                    if (price != null) {
                        temp.setPrice(Double.valueOf(price));
                    }

                    String packSize = cursor.getString(2);
                    if (packSize != null) {
                        temp.setPackSize(Integer.valueOf(packSize));
                    }

                    String multiPackSize = cursor.getString(3);
                    if (packSize != null) {
                        temp.setMultiPackSize(Integer.valueOf(multiPackSize));
                    }

                    String packUnit = cursor.getString(4);
                    if (packUnit != null) {
                        temp.setPackUnit(packUnit);
                    }

                    String productName = cursor.getString(5);
                    if (productName != null) {
                        temp.setProductName(productName);
                    }

                    String brand = cursor.getString(6);
                    if (brand != null) {
                        temp.setBrand(brand);
                    }

                    storeCheckDetails.add(temp);
                } while ((cursor.moveToNext()));
            }
        } catch (Exception e) {
            Log.e("db-error", e.getMessage());
        }
        return storeCheckDetails;
    }

    public ArrayList<Outlet> getOutlets() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select id, outlet_id,outlet_city,outlet_date,chccode,outlet_name,channel_name,project_id,geo_code,geo_name,year from outlets";
        Cursor cursor = database.rawQuery(query, null);
        ArrayList<Outlet> outlets = null;
        try {
            if (cursor.moveToFirst()) {
                outlets = new ArrayList<>();
                do {

                    Outlet outlet = new Outlet();
                    outlet.set_id(Integer.parseInt(cursor.getString(0)));
                    outlet.set_outlet_id(cursor.getString(1));
                    outlet.set_outlet_city(cursor.getString(2));
                    outlet.set_outlet_date(cursor.getString(3));
                    outlet.set_chccode(cursor.getString(4));
                    outlet.set_outlet_Name(cursor.getString(5));
                    outlet.set_channel_name(cursor.getString(6));
                    outlet.set_project_id(cursor.getInt(7));
                    outlet.set_geo_code(cursor.getString(8));
                    outlet.set_geo_name(cursor.getString(9));
                    outlet.set_year(cursor.getString(10));
                    outlets.add(outlet);

                } while ((cursor.moveToNext()));

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (cursor != null)
                cursor.close();
            if (database != null)
                database.close();
        }
        return outlets;
    }

    public Geography getGeography() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select geo_code, geo_name from outlets where id = 1";
        Cursor cursor = database.rawQuery(query, null);
        Geography geography = null;
        if (cursor.moveToFirst()) {
            geography = new Geography();

            String geographyCode = cursor.getString(0);
            String geographyName = cursor.getString(1);

            geography.setGeographyCode(geographyCode);
            geography.setGeographyName(geographyName);
        }

        return geography;
    }

    public ArrayList<CustomField> getCustomFieldByProductCode(int productCode, ArrayList<CustomField> customFields) {

        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {

            database = this.getReadableDatabase();
            String query = "select label,uniqueid,objectid, frameGroupId, groupid  from customfields where productcode=" + "" + productCode + "";
            Log.i("query is ::", query);
            cursor = database.rawQuery(query, null);
            customFields = new ArrayList<CustomField>();
            if (cursor.moveToFirst()) {

                do {
                    CustomField customField = new CustomField();
                    customField.set_label(cursor.getString(0));
                    customField.setUniqueID(cursor.getString((1)));
                    customField.set_object_id(cursor.getString((2)));
                    customField.setFrameGroupID(Integer.valueOf(cursor.getString((3))));
                    customField.set_group_id(cursor.getString(4));
                    customFields.add(customField);
                } while ((cursor.moveToNext()));

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            Log.i("Custom Field count::", String.valueOf(customFields.size()));
            if (cursor != null)
                cursor.close();
            if (database != null)
                database.close();
        }
        return customFields;
    }

    public ArrayList<CustomField> updateCustomFieldOptions(ArrayList<CustomField> customFields) {

        Log.i("Custom Field count ", "updateCustomField::" + String.valueOf(customFields.size()));
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {

            Iterator iterator = customFields.iterator();
            while (iterator.hasNext()) {
                CustomField customField = (CustomField) iterator.next();
                ContentValues values = new ContentValues();
                database = this.getReadableDatabase();
                String query = "select optionname,minimumallowed,maximumallowed,optionid, isnumeric, iszeroallowed from options where customfieldid='" + customField.getUniqueID() + "'";
                Log.i("query is ::", query);
                cursor = database.rawQuery(query, null);
                ArrayList<Option> options = new ArrayList<Option>();
                if (cursor.moveToFirst()) {
                    do {
                        Option option = new Option();
                        option.setOptionName(cursor.getString(0));
                        option.setMinimumAllowed(cursor.getString((1)));
                        option.setMaximumAllowed(cursor.getString((2)));
                        option.setOptionId(cursor.getString(3));
                        option.setIsNumeric(cursor.getString(4));
                        option.setIsZeroAllowed(cursor.getString(5));
                        option.setUniqueID(customField.getUniqueID());
                        options.add(option);
                    } while ((cursor.moveToNext()));

                }
                if (options.size() > 0) {
                    if (customField.get_object_id().equals(TextBox)) {
                        String isNumeric = options.get(0).getIsNumeric();
                        if (isNumeric != null && isNumeric.equals("1")) {
                            customField.setIsNumeric(true);
                        } else if (isNumeric != null && isNumeric.equals("0")) {
                            customField.setIsNumeric(false);
                        }
                        customField.setSelectedOption(options.get(0));
                    } else if (customField.get_object_id().equals(DropDown)) {
                        customField.setSelectedOption(options.get(0));
                    }
                }

                customField.set_options(options);
            }


        } catch (Exception ex) {
            throw ex;
        } finally {
            if (cursor != null)
                cursor.close();
            if (database != null)
                database.close();
        }
        return customFields;
    }

    public ArrayList<Product> getAllProducts() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select id, product_id, product_name from products";
        Cursor cursor = database.rawQuery(query, null);
        ArrayList<Product> products = null;
        try {
            if (cursor.moveToFirst()) {
                products = new ArrayList<>();
                do {
                    String productId = cursor.getString(1);
                    String productName = cursor.getString(2);

                    Product temp = new Product(productId, productName);
                    String id = cursor.getString(0);
                    if (id != null) {
                        temp.set_id(Integer.valueOf(id));
                    }
                    products.add(temp);
                } while ((cursor.moveToNext()));
            }
        } catch (Exception e) {
            Log.e("Product fetch", e.getMessage());
        }
        return products;
    }

    public boolean saveBrand(StoreCheckBrand brand) {
        boolean result = true;
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_BRAND, brand.getBrand());
            contentValues.put(KEY_NBO, brand.getBrand());

            long brandId = db.insert(TABLE_BRANDS, "", contentValues);

            if (brandId > 0) {

                for (CustomField cf : brand.getCustomFields()) {
                    if (result) {
                        contentValues = new ContentValues();

                        contentValues.put(KEY_BRANDID, brandId);
                        contentValues.put(KEY_CUSTOMFIELDID, cf.getUniqueID());
                        contentValues.put(KEY_OPTIONID, cf.getSelectedOption().getOptionId());
                        if (cf.get_object_id().equals("2")) {
                            contentValues.put(KEY_OPTIONVALUE, cf.getCustomFieldTextValue());
                        } else {
                            contentValues.put(KEY_OPTIONVALUE, cf.getSelectedOption().getOptionName());
                        }
                        result = db.insert(TABLE_BRANDCUSTOMFIELDS, "", contentValues) > 0;
                    }
                }
            } else {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            db.close();
        }
        return result;
    }

    public ArrayList<Channel> getAllChannels() {
        ArrayList<Channel> my_array = new ArrayList<Channel>();
        try {
            String selectQuery = "SELECT  id,chccode,chcname FROM " + TABLE_CHANNELS;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    Channel channel = new Channel(null, null);
                    channel.set_id(cursor.getInt(cursor.getColumnIndex("id")));
                    channel.set_chc_code(cursor.getString(cursor.getColumnIndex("chccode")));
                    channel.set_chc_name(cursor.getString(cursor.getColumnIndex("chcname")));
                    my_array.add(channel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return my_array;
    }

    // Updating single outlet
    public int updateOutlet(Outlet outlet) {

        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            ContentValues values = new ContentValues();
            values.put(KEY_OUTLET_Name, outlet.get_outlet_Name());
            values.put(KEY_OUTLET_CITY,outlet.getOutlet_city());
            values.put(KEY_OUTLET_DATE,outlet.get_outlet_date());
            values.put(KEY_CHANNEL_NAME,outlet.get_channel_name());
            Log.i("chc name", outlet.get_channel_name());
            Log.i("chc code", outlet.get_chccode());
            values.put(KEY_CHCCODE,outlet.get_chccode());
            values.put(KEY_UPDATED, "1");
            // updating row
            return db.update(TABLE_OUTLETS, values, KEY_ID + " = ?",
                    new String[] { String.valueOf(outlet.get_id()) });
        }
        catch(Exception ex)
        {
            throw  ex;
        }
        finally {
            if (db != null)
                db.close();
        }
    }

    public void insertOutlet(Outlet outlet)
    {

        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor cursor = null;

        try
        {
            String selectQuery = "SELECT  project_id,geo_code,year FROM " + TABLE_OUTLETS +" where outlet_id is not null limit 1";
            cursor = dbRead.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    outlet.set_project_id(cursor.getInt(0));
                    outlet.set_geo_code(cursor.getString(1));
                    outlet.set_year(cursor.getString(2));
                    // outlet.set_chccode(cursor.getString(3));
                } while (cursor.moveToNext());
            }


        }
        catch (Exception e)
        {
            throw e;
        }
        finally {

            if (cursor != null)
                cursor.close();
            if (dbRead != null)
                dbRead.close();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            ContentValues values = new ContentValues();
            values.put(KEY_OUTLET_Name, outlet.get_outlet_Name());
            values.put(KEY_OUTLET_CITY,outlet.getOutlet_city());
            values.put(KEY_OUTLET_DATE,outlet.get_outlet_date());
            values.put(KEY_CHANNEL_NAME,outlet.get_channel_name());
            values.put(KEY_PROJECT_ID, outlet.get_project_id());
            values.put(KEY_GEO_CODE, outlet.get_geo_code());
            values.put(KEY_YEAR, outlet.get_year());
            values.put(KEY_CHCCODE,outlet.get_chccode());
            values.put(KEY_NEW,1);
            db.insert(TABLE_OUTLETS, null, values);
        }
        catch (Exception ex)
        {
            throw  ex;
        }
        finally {
            db.close(); // Closing database connection
        }
    }

    public boolean isDatabaseAvailable() {
        boolean result = false;
        SQLiteDatabase database = this.getReadableDatabase();
        try {
            String query = "select count(1) from " + TABLE_METADATA;
            result = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_METADATA + "'", null).getCount() > 0;

        } catch (Exception e) {
        } finally {
            database.close();
        }
        return result;
    }
}