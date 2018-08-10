package com.gandsoft.openguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Databases information
    private static final String DATABASE_NAME = "openguides.db";
    private static final int DATABASE_VERSION = 1;

    public static String TABEL_GlobalData = "tabGlobalData";
    public static String KEY_GlobalData_dbver = "dbver";
    public static String KEY_GlobalData_version_data = "version_data";

    public static String TABEL_UserData = "tabUserData";
    public static String KEY_UserData_position = "position";
    public static String KEY_UserData_listEvent = "listEvent";
    public static String KEY_UserData_birthday = "birthday";
    public static String KEY_UserData_versionData = "versionData";
    public static String KEY_UserData_accountId = "accountId";
    public static String KEY_UserData_privacyPolicy = "privacyPolicy";
    public static String KEY_UserData_imageUrl = "imageUrl";
    public static String KEY_UserData_groupCode = "groupCode";
    public static String KEY_UserData_roleName = "roleName";
    public static String KEY_UserData_checkin = "checkin";
    public static String KEY_UserData_phoneNumber = "phoneNumber";
    public static String KEY_UserData_email = "email";
    public static String KEY_UserData_gender = "gender";
    public static String KEY_UserData_fullName = "fullName";

    public static String TABEL_Event = "tabListEvent";
    public static String KEY_Event_startDate = "startDate";
    public static String KEY_Event_logo = "logo";
    public static String KEY_Event_title = "title";
    public static String KEY_Event_eventId = "eventId";
    public static String KEY_Event_groupCompany = "groupCompany";
    public static String KEY_Event_status = "status";
    public static String KEY_Event_background = "background";
    public static String KEY_Event_groupCode = "groupCode";
    public static String KEY_Event_roleName = "roleName";
    public static String KEY_Event_date = "date";
    public static String KEY_Event_walletData = "walletData";

    public static String TABEL_Wallet = "tabWallet";
    public static String KEY_Wallet_notif = "notif";
    public static String KEY_Wallet_detail = "detail";
    public static String KEY_Wallet_bodyWallet = "bodyWallet";
    public static String KEY_Wallet_sort = "sort";
    public static String KEY_Wallet_type = "type";


    private static final String query_add_table_UserData = "CREATE TABLE IF NOT EXISTS " + TABEL_UserData + "("
            + KEY_UserData_accountId + " TEXT PRIMARY KEY,"
            + KEY_UserData_position + " TEXT, "
            + KEY_UserData_listEvent + " TEXT, "
            + KEY_UserData_birthday + " TEXT, "
            + KEY_UserData_versionData + " TEXT, "
            + KEY_UserData_privacyPolicy + " TEXT, "
            + KEY_UserData_imageUrl + " TEXT, "
            + KEY_UserData_groupCode + " TEXT, "
            + KEY_UserData_roleName + " TEXT, "
            + KEY_UserData_checkin + " TEXT, "
            + KEY_UserData_phoneNumber + " TEXT, "
            + KEY_UserData_email + " TEXT, "
            + KEY_UserData_gender + " TEXT, "
            + KEY_UserData_fullName + " TEXT) ";
    private static final String query_delete_table_UserData = "DROP TABLE IF EXISTS " + TABEL_UserData;

    private static final String query_add_table_Event = "CREATE TABLE IF NOT EXISTS " + TABEL_Event + "("
            + KEY_Event_eventId + " PRIMARY KEY, "
            + KEY_Event_startDate + " TEXT, "
            + KEY_Event_logo + " TEXT, "
            + KEY_Event_title + " TEXT, "
            + KEY_Event_groupCompany + " TEXT, "
            + KEY_Event_status + " TEXT, "
            + KEY_Event_background + " TEXT, "
            + KEY_Event_groupCode + " TEXT, "
            + KEY_Event_roleName + " TEXT, "
            + KEY_Event_date + " TEXT, "
            + KEY_Event_walletData + " TEXT)";
    private static final String query_delete_table_Event = "DROP TABLE IF EXISTS " + TABEL_Event;

    private static final String query_add_table_Wallet = "CREATE TABLE IF NOT EXISTS " + TABEL_Wallet + "("
            + KEY_Wallet_notif + " TEXT, "
            + KEY_Wallet_detail + " TEXT, "
            + KEY_Wallet_bodyWallet + " TEXT, "
            + KEY_Wallet_sort + "  PRIMARY KEY , "
            + KEY_Wallet_type + " TEXT) ";
    private static final String query_delete_table_Wallet = "DROP TABLE IF EXISTS " + TABEL_Wallet;

    private static final String query_add_table_GlobalData = "CREATE TABLE IF NOT EXISTS " + TABEL_GlobalData + "("
            + KEY_Wallet_sort + "  PRIMARY KEY , "
            + KEY_Wallet_notif + " TEXT, "
            + KEY_Wallet_detail + " TEXT, "
            + KEY_Wallet_bodyWallet + " TEXT, "
            + KEY_Wallet_type + " TEXT) ";
    private static final String query_delete_table_GlobalData = "DROP TABLE IF EXISTS " + TABEL_GlobalData;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query_add_table_UserData);
        db.execSQL(query_add_table_Event);
        db.execSQL(query_add_table_Wallet);
        db.execSQL(query_add_table_GlobalData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(query_delete_table_UserData);
        db.execSQL(query_delete_table_Event);
        db.execSQL(query_delete_table_Wallet);
        db.execSQL(query_delete_table_GlobalData);
        replaceDataToNewTable(db, TABEL_UserData, "tabTempUserdata");
        replaceDataToNewTable(db, TABEL_Event, "tabTempEvent");
        replaceDataToNewTable(db, TABEL_Wallet, "tabTempWallet");
        replaceDataToNewTable(db, TABEL_GlobalData, "tabTempGlobalData");
    }

    private void replaceDataToNewTable(SQLiteDatabase db, String tableName, String tableString) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableString);

        List<String> columns = getColumns(db, tableName);
        db.execSQL("ALTER TABLE " + tableName + " RENAME TO temp_" + tableName);
        db.execSQL("CREATE TABLE " + tableString);

        columns.retainAll(getColumns(db, tableName));
        String cols = join(columns, ",");
        db.execSQL(String.format("INSERT INTO %s (%s) SELECT %s from temp_%s",
                tableName, cols, cols, tableName));
        db.execSQL("DROP TABLE temp_" + tableName);
    }

    private List<String> getColumns(SQLiteDatabase db, String tableName) {
        List<String> ar = null;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from " + tableName + " limit 1", null);
            if (c != null) {
                ar = new ArrayList<String>(Arrays.asList(c.getColumnNames()));
            }
        } catch (Exception e) {
            Log.e(tableName, e.getMessage() + e);
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }
        return ar;
    }

    private String join(List<String> list, String divider) {
        StringBuilder buf = new StringBuilder();
        int num = list.size();
        for (int i = 0; i < num; i++) {
            if (i != 0)
                buf.append(divider);
            buf.append(list.get(i));
        }
        return buf.toString();
    }

    /**/
    public void saveAccountID(String phoneNumberSaved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_UserData_accountId, phoneNumberSaved);
        db.insert(TABEL_UserData, null, contentValues);
        db.close();
    }

    public String getAccountId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABEL_UserData, new String[]{KEY_UserData_accountId}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return "Unknown";
        }
    }
}