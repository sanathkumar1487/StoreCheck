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
import com.euromonitor.storecheck.model.PricingDetail;
import com.euromonitor.storecheck.model.PackType;
import com.euromonitor.storecheck.model.Product;
import com.euromonitor.storecheck.model.StoreCheckBrand;
import com.euromonitor.storecheck.model.StoreCheckDetail;
import com.euromonitor.storecheck.model.Unit;
import com.euromonitor.storecheck.model.Validation;


/**
 * Created by Sanath.Kumar on 3/30/2016.
 */
public class DatabaseHelper extends  SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_VALIDATION = "validations";

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
    ;


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
    private static final String KEY_Researched = "researched";

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
    private static final String KEY_TYPEID = "typeId";


    // Units Table Column Names
    private static final String KEY_UNITID = "unitid";
    private static final String KEY_UNITNAME = "unitname";
    private static final String KEY_UNITBASE = "unitbase";
    private static final String KEY_UNITMULTIPLIER = "unitmultiplier";

    // Brands Column Names
    private static final String KEY_BRANDID = "brandid";
    private static final String KEY_OPTIONVALUE = "optionvalue";


    //packtype columns

    private static final String KEY_PACKTYPENAME = "packtypename";
    private static final String KEY_PRODUCTCODE = "productcodes";

    // Custom Field Control Type
    final String DropDown = "1";
    final String CustomDropDown = "3";
    final String TextBox = "2";

    // Validation Table
    private static final String KEY_IndustryCode = "industryCode";
    private static final String KEY_ColumnName = "columnName";
    private static final String KEY_DecimalPlaces = "decimalPlace";


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
        createValidationTable();
    }

    public void loadData(MetaData storeCheckMetaData, List<Product> products, List<Outlet> outlets, List<Channel> channels,
                         List<Detail> details, List<Market> markets, List<Option> options, List<CustomField> customFields,
                         List<Unit> units, List<PackType> packTypes, List<Validation> validations) {

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
        loadpacktypeTable(packTypes);
        loadValidationTable(validations);
    }

    private void loadStoreCheckMetaDataTable(MetaData storeCheckMetaData) {
        Log.e("loadStoreCheckMe::", "loadStoreCheckMetaDataTable load data");
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_GEOGRAPHYCODE, storeCheckMetaData.getGeographyCode());
        values.put(KEY_GEOGRAPHY, storeCheckMetaData.getGeographyName());
        values.put(KEY_INDUSTRY_NAME, storeCheckMetaData.getIndustryName());
        values.put(KEY_YEAR, storeCheckMetaData.getYear());
        values.put(KEY_PROJECTID, storeCheckMetaData.getProjectId());
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
            values.put(KEY_Researched, product.getResearched());
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
        Log.e("loadOutletsTable::", "loadOutletsTable load data");
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
        Log.e("loadDetailsTable::", "loadDetailsTable load data");
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
            values.put(KEY_BRANDMARKETID, detail.getBrandMarketId());
            values.put(KEY_NBO, detail.get_nbo());
            // Inserting Row
            db.insert(TABLE_DETAILS, null, values);
        }

        db.close(); // Closing database connection
    }

    private void loadMarketTable(List<Market> markets) {
        Log.e("loadMarketTable::", "loadMarketTable load data");
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
        Log.e("loadOptionsTable::", "loadOptionsTable load data");
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
        Log.e("loadCustomFiledsTable::", "loadCustomFiledsTable load data");
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
                values.put(KEY_TYPEID, customField.getTypeId());


                db.insert(TABLE_CUSTOMFIELDS, null, values);
            }
        }

        db.close(); // Closing database connection
    }

    private void loadUnitsTable(List<Unit> units) {
        Log.e("loadUnitsTable::", "loadUnitsTable load data");
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = units.iterator();
        while (iterator.hasNext()) {
            Unit unit = (Unit) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_UNITID, unit.get_unit_id());
            values.put(KEY_UNITNAME, unit.get_unit_name());
            values.put(KEY_UNITBASE, unit.get_unit_base());
            values.put(KEY_UNITMULTIPLIER, unit.get_unit_multiplier());

            Iterator produtcodelist = unit.get_productcodes().iterator();

            while (produtcodelist.hasNext()) {
                String productcode = (String) produtcodelist.next();

                values.put(KEY_PRODUCTCODE, productcode);
                // Inserting Row
                db.insert(TABLE_UNITS, null, values);

            }


        }

        db.close();
    }

    private void loadpacktypeTable(List<PackType> packTypes) {
        Log.e("loadpacktypeTable::", "loadpacktypeTable load data");

        Log.e("Start ::", String.valueOf(System.currentTimeMillis()));
        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = packTypes.iterator();
        while (iterator.hasNext()) {


            PackType packType = (PackType) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_PACKTYPECODE, packType.get_packtypecode());
            values.put(KEY_PACKTYPENAME, packType.get_packtypename());

            Iterator produtcodelist = packType.get_productcodes().iterator();

            while (produtcodelist.hasNext()) {
                String productcode = (String) produtcodelist.next();

                values.put(KEY_PRODUCTCODE, productcode);
                db.insert(TABLE_PACKTYPES, null, values);

            }


        }
        Log.e("End ::", String.valueOf(System.currentTimeMillis()));
        db.close();
    }

    private void loadValidationTable(List<Validation> validations) {
        Log.e("loadValidationTable::", "loadValidationTable load data");

        SQLiteDatabase db = this.getWritableDatabase();

        Iterator iterator = validations.iterator();
        while (iterator.hasNext()) {
            Validation validation = (Validation) iterator.next();

            ContentValues values = new ContentValues();
            values.put(KEY_IndustryCode, validation.getIndustryCode());
            values.put(KEY_ColumnName, validation.getColumnName());
            values.put(KEY_MINIMUMALLOWED, validation.getMinimumAllowed());
            values.put(KEY_MAXIMUMALLOWED, validation.getMaximumAllowed());
            values.put(KEY_DecimalPlaces, validation.getDecimalPlace());
            values.put(KEY_ISNUMERIC, validation.getIsNumeric());

            db.insert(TABLE_VALIDATION, null, values);
        }
        db.close();
    }

    private void createMetaDataTable() {
        //
        String CREATE_METADAT_TABLE = "CREATE TABLE " + TABLE_METADATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_GEOGRAPHYCODE + " TEXT,"
                + KEY_GEOGRAPHY + " TEXT, "
                + KEY_INDUSTRY_NAME + " INT, "
                + KEY_PROJECTID + " INT, "
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
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_PRODUCT_ID + " TEXT, "
                + KEY_Researched + " INT, "
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
                + KEY_BRANDID + " TEXT,"
                + KEY_BRAND + " TEXT,"
                + KEY_BRANDMARKETID + " TEXT,"
                + KEY_UPDATED + " TEXT,"
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
                + KEY_UNIQUEID + " TEXT,"
                + KEY_TYPEID + " INT"
                + ")";
        database.execSQL(CREATE_CUSTOMFIELDS_TABLE);

    }

    private void createUnitTable() {
        String CREATE_UNIT_TABLE = " CREATE TABLE " + TABLE_UNITS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_UNITID + " TEXT, "
                + KEY_UNITNAME + " TEXT, "
                + KEY_UNITBASE + " TEXT, "
                + KEY_PRODUCTCODE + " TEXT, "
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
                + KEY_PRICINGID + " TEXT, "
                + KEY_CUSTOMFIELDID + " TEXT, "
                + KEY_OPTIONID + " TEXT, "
                + KEY_OPTIONVALUE + " TEXT) ";
        database.execSQL(CREATE_BRANDCUSTOMFIELD_TABLE);
    }

    private void createPacktypeTable() {
        String CREATE_PACKTYPE_TABLE = " CREATE TABLE " + TABLE_PACKTYPES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_PACKTYPECODE + " TEXT, "
                + KEY_PACKTYPENAME + " TEXT, "
                + KEY_PRODUCTCODE + " TEXT) ";
        database.execSQL(CREATE_PACKTYPE_TABLE);
    }

    private void createValidationTable() {
        String CREATE_VALIDATION_TABLE = " CREATE TABLE " + TABLE_VALIDATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_IndustryCode + " TEXT, "
                + KEY_ColumnName + " TEXT, "
                + KEY_MINIMUMALLOWED + " TEXT, "
                + KEY_MAXIMUMALLOWED + " TEXT, "
                + KEY_ISNUMERIC + " INT, "
                + KEY_DecimalPlaces + " INT "
                + ") ";
        database.execSQL(CREATE_VALIDATION_TABLE);
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
        database.execSQL("DROP TABLE IF EXISTS '" + TABLE_VALIDATION + "'");
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

    public ArrayList<StoreCheckDetail> GetAllProductDetails() {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "select  d.pricingid,d.price,d.packsize,d.multipackSize, u.unitname, p.product_Name, d.brand from details  d inner join    products p on p. product_id =   d.productid   inner join   (select distinct unitid,unitname,unitbase  from units) u on u.unitid = d.unitcode";

        Cursor cursor = database.rawQuery(query, null);
        ArrayList<StoreCheckDetail> storeCheckDetails = null;

        int countloop = 0;
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
                        temp.setPackSize(Double.valueOf(packSize));
                    }

                    String multiPackSize = cursor.getString(3);
                    if (packSize != null) {
                        temp.setMultiPackSize(Double.valueOf(multiPackSize));
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

                    int brandId = cursor.getInt(7);
                    if (brand != null) {
                        temp.setBrandId(brandId);
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

    public ArrayList<CustomField> getCustomFieldByProductCode(int productCode, int typeId, ArrayList<CustomField> customFields) {

        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {

            database = this.getReadableDatabase();
            String query = "select label,uniqueid,objectid, frameGroupId, groupid  from customfields where productcodes=" + "" + productCode + ""
                    + " and typeId = " + typeId;
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
                        if (!(cursor.getInt(1) == 1 && cursor.getInt(2) == 0)) {
                            Option option = new Option();
                            option.setOptionName(cursor.getString(0));
                            option.setMinimumAllowed(cursor.getString((1)));
                            option.setMaximumAllowed(cursor.getString((2)));
                            option.setOptionId(cursor.getString(3));
                            option.setIsNumeric(cursor.getString(4));
                            option.setIsZeroAllowed(cursor.getString(5));
                            option.setUniqueID(customField.getUniqueID());
                            options.add(option);
                        }
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
                    } else if (customField.get_object_id().equals(DropDown) && customField.get_object_id().equals(CustomDropDown)) {
                        customField.setSelectedOption(options.get(0));
                    }
                }

                customField.set_options(options);
            }


        } catch (Exception ex) {
            Log.e("Update CF", ex.getMessage());
            //throw ex;
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
        String query = "select "
                + KEY_ID + ", "
                + KEY_PRODUCT_ID + ", "
                + KEY_Product_Name + ", "
                + KEY_Researched + " from products";
        Cursor cursor = database.rawQuery(query, null);
        ArrayList<Product> products = null;
        try {
            if (cursor.moveToFirst()) {
                products = new ArrayList<>();
                do {
                    Product temp = new Product();
                    temp.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    temp.set_product_id(cursor.getString(cursor.getColumnIndex(KEY_PRODUCT_ID)));
                    temp.set_product_name(cursor.getString(cursor.getColumnIndex(KEY_Product_Name)));
                    temp.setResearched(cursor.getInt(cursor.getColumnIndex(KEY_Researched)));

                    products.add(temp);
                } while ((cursor.moveToNext()));
            }
        } catch (Exception e) {
            Log.e("Product fetch", e.getMessage());
        }
        return products;
    }

    public long saveBrand(StoreCheckBrand brand, boolean isUpdate) {
        boolean result = true;
        long brandId = 0;

        MetaData metaData = getMetadata();

        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_BRANDMARKETID, "-1 ");
            contentValues.put(KEY_PROJECTID, String.valueOf(metaData.getProjectId() ));
            contentValues.put(KEY_GEOGRAPHYCODE, metaData.getGeographyCode() );
            contentValues.put(KEY_GEOGRAPHY, metaData.getGeographyName() );
            contentValues.put(KEY_PRODUCTCODE,String.valueOf(brand.getSelectedProduct().get_product_id()));
            contentValues.put(KEY_PRODUCT, brand.getSelectedProduct().get_product_name());
            contentValues.put(KEY_BRAND, brand.getBrand());
            contentValues.put(KEY_NBO, brand.getNBO());
            contentValues.put(KEY_COMPANYNAME, "");

            if (isUpdate) {
                brandId = db.update(TABLE_MARKETS, contentValues, KEY_ID + " = ?",
                        new String[]{String.valueOf(brand.getId())});
            } else {
                brandId = db.insert(TABLE_MARKETS, "", contentValues);
            }
            if (brandId > 0) {
                for (CustomField cf : brand.getCustomFields()) {
                    if (result) {
                        contentValues = new ContentValues();

                        contentValues.put(KEY_BRANDID, brandId);
                        contentValues.put(KEY_CUSTOMFIELDID, cf.getUniqueID());
                        contentValues.put(KEY_OPTIONID, cf.getSelectedOption().getOptionId());
                        if (cf.get_object_id().equals("2")) {
                            contentValues.put(KEY_OPTIONVALUE, cf.getCustomFieldTextValue());
                        } else if ((cf.get_object_id().equals("1") || cf.get_object_id().equals("3"))
                                && cf.getSelectedOption() != null) {
                            contentValues.put(KEY_OPTIONVALUE, cf.getSelectedOption().getOptionName());
                        }
                        if (isUpdate) {
                            result = db.update(TABLE_BRANDCUSTOMFIELDS, contentValues, KEY_ID + " = ?",
                                    new String[]{String.valueOf(cf.get_id())}) > 0;
                        } else {
                            result = db.insert(TABLE_BRANDCUSTOMFIELDS, "", contentValues) > 0;
                        }
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
        return result ? brandId : 0;
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
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_OUTLET_Name, outlet.get_outlet_Name());
            values.put(KEY_OUTLET_CITY, outlet.getOutlet_city());
            values.put(KEY_OUTLET_DATE, outlet.get_outlet_date());
            values.put(KEY_CHANNEL_NAME, outlet.get_channel_name());
            values.put(KEY_OUTLET_CITY, outlet.getOutlet_city());
            values.put(KEY_OUTLET_DATE, outlet.get_outlet_date());
            values.put(KEY_CHANNEL_NAME, outlet.get_channel_name());
            Log.i("chc name", outlet.get_channel_name());
            Log.i("chc code", outlet.get_chccode());
            values.put(KEY_CHCCODE, outlet.get_chccode());
            values.put(KEY_UPDATED, "1");
            // updating row
            return db.update(TABLE_OUTLETS, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(outlet.get_id())});
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void insertOutlet(Outlet outlet) {

        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String selectQuery = "SELECT  project_id,geo_code,year FROM " + TABLE_OUTLETS + " where outlet_id is not null limit 1";
            cursor = dbRead.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    outlet.set_project_id(cursor.getInt(0));
                    outlet.set_geo_code(cursor.getString(1));
                    outlet.set_year(cursor.getString(2));
                    // outlet.set_chccode(cursor.getString(3));
                } while (cursor.moveToNext());
            }


        } catch (Exception e) {
            throw e;
        } finally {

            if (cursor != null)
                cursor.close();
            if (dbRead != null)
                dbRead.close();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_OUTLET_Name, outlet.get_outlet_Name());
            values.put(KEY_OUTLET_CITY, outlet.getOutlet_city());
            values.put(KEY_OUTLET_DATE, outlet.get_outlet_date());
            values.put(KEY_CHANNEL_NAME, outlet.get_channel_name());
            values.put(KEY_PROJECT_ID, outlet.get_project_id());
            values.put(KEY_GEO_CODE, outlet.get_geo_code());
            values.put(KEY_YEAR, outlet.get_year());
            values.put(KEY_CHCCODE, outlet.get_chccode());
            values.put(KEY_NEW, 1);
            db.insert(TABLE_OUTLETS, null, values);
        } catch (Exception ex) {
            throw ex;
        } finally {
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

    public MetaData getMetadata() {
        MetaData data = null;
        SQLiteDatabase database = this.getReadableDatabase();
        try {
            String query = "select "
                    + KEY_GEOGRAPHYCODE + ", "
                    + KEY_GEOGRAPHY + ", "
                    + KEY_INDUSTRY_NAME + ", "
                    + KEY_PROJECTID + ", "
                    + KEY_YEAR
                    + " from " + TABLE_METADATA;
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                data = new MetaData();
                data.setGeographyCode(cursor.getString(cursor.getColumnIndex(KEY_GEOGRAPHYCODE)));
                data.setGeographyName(cursor.getString(cursor.getColumnIndex(KEY_GEOGRAPHY)));
                data.setIndustryName(cursor.getString(cursor.getColumnIndex(KEY_INDUSTRY_NAME)));
                data.setYear(cursor.getInt(cursor.getColumnIndex(KEY_YEAR)));
                data.setProjectId(cursor.getInt(cursor.getColumnIndex(KEY_PROJECTID)));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            database.close();
        }
        return data;
    }

    public PricingDetail getPricingDetails(int pricingId) {

        PricingDetail pricingDetails = null;
        try {
            String selectQuery = "SELECT id, "
                    + KEY_BRAND + ", "
                    + KEY_OUTLETID + ", "
                    + KEY_PACKSIZE + ", "
                    + KEY_MULTIPACKSIZE + ", "
                    + KEY_PACKTYPECODE + ", "
                    + KEY_UNITCODE + ", "
                    + KEY_PRICE + ", "
                    + KEY_PRICINGID + ", "
                    + KEY_UPDATED
                    + " FROM " + TABLE_DETAILS
                    + " where " + KEY_PRICINGID + " = " + pricingId
                    + " and " + KEY_UPDATED + " = " + 1;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    pricingDetails = new PricingDetail();
                    pricingDetails.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                    pricingDetails.setBrandName(cursor.getString(cursor.getColumnIndex(KEY_BRAND)));
                    pricingDetails.setSelectedOutletId(cursor.getInt(cursor.getColumnIndex(KEY_OUTLETID)));
                    pricingDetails.setPackSize(cursor.getInt(cursor.getColumnIndex(KEY_PACKSIZE)));
                    pricingDetails.setMultiPack(cursor.getInt(cursor.getColumnIndex(KEY_MULTIPACKSIZE)));
                    pricingDetails.setPackTypeCode(cursor.getInt(cursor.getColumnIndex(KEY_PACKTYPECODE)));
                    pricingDetails.setUnitId(cursor.getInt(cursor.getColumnIndex(KEY_UNITCODE)));
                    pricingDetails.setPrice(cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)));
                    pricingDetails.setPricingId(cursor.getInt(cursor.getColumnIndex(KEY_PRICINGID)));

                    pricingDetails.isUpdated = cursor.getInt(cursor.getColumnIndex(KEY_UPDATED)) == 1 ? true : false;


                } while (cursor.moveToNext());

                cursor.close();
            }

            if (pricingDetails != null && pricingDetails.isUpdated) {
                selectQuery = "select "
                        + KEY_ID + ", "
                        + KEY_CUSTOMFIELDID + ", "
                        + KEY_OPTIONID + ", "
                        + KEY_OPTIONVALUE
                        + " from " + TABLE_BRANDCUSTOMFIELDS
                        + " where " + KEY_PRICINGID + " = " + pricingId;

                cursor = db.rawQuery(selectQuery, null);

                if (cursor.moveToFirst()) {
                    ArrayList<CustomField> customFields = new ArrayList<CustomField>();

                    do {
                        CustomField customField = new CustomField();
                        customField.setUniqueID(cursor.getString(cursor.getColumnIndex(KEY_CUSTOMFIELDID)));

                        Option option = new Option();
                        option.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                        option.setUniqueID(customField.getUniqueID());
                        option.setOptionId(cursor.getString(cursor.getColumnIndex(KEY_OPTIONID)));
                        option.setOptionValue(cursor.getString(cursor.getColumnIndex(KEY_OPTIONVALUE)));
                        customField.setSelectedOption(option);

                        customFields.add(customField);

                    } while (cursor.moveToNext());

                    pricingDetails.setCustomFields(customFields);
                }
                cursor.close();
            }


            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pricingDetails;
    }

    public ArrayList<Unit> getUnits() {
        ArrayList<Unit> my_array = new ArrayList<>();
        try {
            String selectQuery = "SELECT id, unitid, unitname, unitbase, unitmultiplier FROM " + TABLE_UNITS;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    Unit unit = new Unit(null, null, null, null);
                    unit.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    unit.set_unit_id(cursor.getString(cursor.getColumnIndex(KEY_UNITID)));
                    unit.set_unit_name(cursor.getString(cursor.getColumnIndex(KEY_UNITNAME)));
                    unit.set_unit_base(cursor.getString(cursor.getColumnIndex(KEY_UNITBASE)));
                    unit.set_unit_multiplier(cursor.getString(cursor.getColumnIndex(KEY_UNITMULTIPLIER)));
                    my_array.add(unit);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return my_array;
    }

    public ArrayList<Market> getBrands(int productCode) {
        ArrayList<Market> my_array = new ArrayList<>();
        try {
            String selectQuery = "SELECT id, brandMarketId, brand FROM " + TABLE_MARKETS
                    + " where " + KEY_PRODUCTCODE + " = " + productCode;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    Market market = new Market();
                    market.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    market.set_brand_market_id(cursor.getString(cursor.getColumnIndex(KEY_BRANDMARKETID)));
                    market.set_brand(cursor.getString(cursor.getColumnIndex(KEY_BRAND)));

                    my_array.add(market);

                } while (cursor.moveToNext());
            }
            cursor.close();

            selectQuery = "select id, brand from " + TABLE_BRANDS;
            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    Market market = new Market();
                    market.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));

                    market.set_brand(cursor.getString(cursor.getColumnIndex(KEY_BRAND)));
                    market.setIsNew(true);

                    my_array.add(market);

                } while (cursor.moveToNext());
            }
            cursor.close();


            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return my_array;
    }

    public boolean savePricingDetails(PricingDetail pricingDetail, boolean isUpdate) {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();


            long result = 0;
            if(pricingDetail.getPricingId()>0) {
                values.put(KEY_MULTIPACKSIZE, pricingDetail.getMultiPack());
                values.put(KEY_OUTLETID, pricingDetail.getSelectedOutletId());
                values.put(KEY_PRODUCTID, pricingDetail.getProductId());
                values.put(KEY_OUTLETNAME, pricingDetail.getSelectedOutletName());
                values.put(KEY_PACKSIZE, pricingDetail.getPackSize());
                values.put(KEY_PACKTYPE, pricingDetail.getPackTypeName());
                values.put(KEY_PACKTYPECODE, pricingDetail.getPackTypeCode());
                values.put(KEY_PRICE, pricingDetail.getPrice());
                values.put(KEY_PRICINGID, pricingDetail.getPricingId());
                values.put(KEY_UNITCODE, pricingDetail.getUnitId());
                values.put(KEY_BRANDID, pricingDetail.getBrandId());
                values.put(KEY_BRAND, pricingDetail.getBrandName());
                values.put(KEY_BRANDMARKETID, pricingDetail.getBrandMarketId());
                values.put(KEY_NBO, pricingDetail.getNbo());
                values.put(KEY_UPDATED, "1");

                result = db.update(TABLE_DETAILS, values, KEY_ID + " = ?", new String[]{String.valueOf(pricingDetail.getId())});
            }
            else {
                values.put(KEY_MULTIPACKSIZE, pricingDetail.getMultiPack());
                values.put(KEY_OUTLETID, pricingDetail.getSelectedOutletId());
                values.put(KEY_PRODUCTID, pricingDetail.getProductId());
                values.put(KEY_OUTLETNAME, pricingDetail.getSelectedOutletName());
                values.put(KEY_PACKSIZE, pricingDetail.getPackSize());
                values.put(KEY_PACKTYPE, pricingDetail.getPackTypeName());
                values.put(KEY_PACKTYPECODE, pricingDetail.getPackTypeCode());
                values.put(KEY_PRICE, pricingDetail.getPrice());
                values.put(KEY_PRICINGID, pricingDetail.getPricingId());
                values.put(KEY_UNITCODE, pricingDetail.getUnitId());
                values.put(KEY_BRANDID, pricingDetail.getBrandId());
                values.put(KEY_BRAND, pricingDetail.getBrandName());
                values.put(KEY_NBO, pricingDetail.getNbo());
                values.put(KEY_UPDATED, "1");

                result = db.insert(TABLE_DETAILS, null, values);

            }
            if (result > 0) {
                status = saveCustomFields(pricingDetail.getPricingId(), pricingDetail.getCustomFields(), db, isUpdate);
            }

        } catch (Exception ex) {
            status = false;
            throw ex;
        } finally {
            if (db != null)
                db.close();
        }
        return status;
    }

    private boolean saveCustomFields(int pricingId, ArrayList<CustomField> customFields, SQLiteDatabase db, boolean isUpdate) {
        boolean result = true;
        if (isUpdate) {
            result = db.delete(TABLE_BRANDCUSTOMFIELDS, KEY_PRICINGID + " = ? ", new String[]{String.valueOf(pricingId)}) > 0;
        }
        if (result) {
            for (CustomField cf : customFields) {
                ContentValues contentValues = new ContentValues();
                if (result) {
                    contentValues = new ContentValues();
                    contentValues.put(KEY_PRICINGID, pricingId);
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
        }
        return result;
    }

    public ArrayList<PackType> getPackTypeByProduct(int productCode) {
        ArrayList<PackType> my_array = new ArrayList<>();
        try {
            String selectQuery = "SELECT id, packTypeCode, packTypeName, productCodes FROM " + TABLE_PACKTYPES
                    + " where productCodes = " + productCode;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    PackType packType = new PackType();
                    packType.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    packType.set_packtypecode(cursor.getString(cursor.getColumnIndex(KEY_PACKTYPECODE)));
                    packType.set_packtypename(cursor.getString(cursor.getColumnIndex(KEY_PACKTYPENAME)));

                    my_array.add(packType);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return my_array;
    }

    public ArrayList<StoreCheckDetail> GetDetailsByProductCode(int productCode) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select  d.pricingid, d.price,d.packsize,d.multipackSize, u.unitname, p.product_Name, d.brand, d.brandmarketid, p.product_id from details  d inner join    products p on p. product_id =   d.productid   inner join   (select distinct unitid,unitname,unitbase  from units) u on u.unitid = d.unitcode"
                + " where d.productId = " + productCode;
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
                        temp.setPackSize(Double.valueOf(packSize));
                    }

                    String multiPackSize = cursor.getString(3);
                    if (packSize != null) {
                        temp.setMultiPackSize(Double.valueOf(multiPackSize));
                    }

                    String packUnit = cursor.getString(4);
                    if (packUnit != null) {
                        temp.setPackUnit(packUnit);
                    }

                    String productName = cursor.getString(5);
                    if (productName != null) {
                        temp.setProductName(productName);
                    }

                    String brand = cursor.getString(cursor.getColumnIndex(KEY_BRAND));
                    if (brand != null) {
                        temp.setBrand(brand);
                    }

                    int brandId = cursor.getInt(7);
                    if (brandId > 0) {
                        temp.setBrandId(brandId);
                    }

                    int productId = cursor.getInt(8);
                    if (productId > 0) {
                        temp.setProductCode(productId);
                    }

                    storeCheckDetails.add(temp);
                } while ((cursor.moveToNext()));
            }
        } catch (Exception e) {
            Log.e("db-error", e.getMessage());
        }
        return storeCheckDetails;
    }

    //AutoSuggestion for outlet Name
    public String[] getOutletName() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select outlet_name from outlets";
        Cursor cursor = database.rawQuery(query, null);
        String[] outlets = new String[cursor.getCount()];
        try {
            if (cursor.moveToFirst()) {
                int i = 0;
                do {
                    outlets[i] = cursor.getString(cursor.getColumnIndex(KEY_OUTLET_Name));
                    i++;

                } while ((cursor.moveToNext()));
            }
            return outlets;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (cursor != null)
                cursor.close();
            if (database != null)
                database.close();
        }
    }

    //AutoSuggestion for nbo name
    public String[] getNboName() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select nbo from details";
        Cursor cursor = database.rawQuery(query, null);
        String[] nbo_s = new String[cursor.getCount()];
        try {
            if (cursor.moveToFirst()) {
                int i = 0;
                do {
                    nbo_s[i] = cursor.getString(cursor.getColumnIndex(KEY_NBO));
                    i++;

                } while ((cursor.moveToNext()));
            }
            return nbo_s;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (cursor != null)
                cursor.close();
            if (database != null)
                database.close();
        }
    }
    public String[] brandName(int productCode)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        String  selectQuery = "SELECT brand FROM " + TABLE_MARKETS
                + " where " + KEY_PRODUCTCODE + " = " + productCode;
        Cursor cursor = database.rawQuery(selectQuery, null);
        String[] brand_s = new String[cursor.getCount()];
        try {
            if (cursor.moveToFirst()) {
                int i = 0;
                do {
                    brand_s[i] = cursor.getString(cursor.getColumnIndex(KEY_BRAND));
                    i++;

                } while ((cursor.moveToNext()));
            }
            return brand_s;
        } catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            if (cursor != null)
                cursor.close();
            if (database != null)
                database.close();
        }
}
    public ArrayList<Market> getBrandsByProductId(int productCode) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT "
                + KEY_ID + ", "
                //+ KEY_BRANDID + ", "
                + KEY_BRANDMARKETID + ", "
                + KEY_BRAND
                + " FROM " + TABLE_MARKETS
                + " where " + KEY_PRODUCTCODE + " = " + productCode;
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<Market> brands = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {

                do {
                    Market market = new Market();
                    market.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    market.set_brand_market_id(cursor.getString(cursor.getColumnIndex(KEY_BRANDMARKETID)));
                    //market.setBrandId(cursor.getInt(cursor.getColumnIndex(KEY_BRANDID)));
                    market.set_brand(cursor.getString(cursor.getColumnIndex(KEY_BRAND)));
                    brands.add(market);

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
        return brands;
    }

    public StoreCheckBrand getBrandDetaildById(int brandId) {
        return null;
    }
}