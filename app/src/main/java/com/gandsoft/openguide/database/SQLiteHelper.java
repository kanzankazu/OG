package com.gandsoft.openguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Databases information
    private static final String DATABASE_NAME = "openguides.db";
    private static final int DATABASE_VERSION = 1;

    public static String TableGlobalData = "tabGlobalData";
    public static String KEY_GlobalData_dbver = "dbver";
    public static String KEY_GlobalData_version_data_user = "version_data_user";
    public static String KEY_GlobalData_version_data_event = "version_data_event";

    public static String TableUserData = "tabUserEvent";
    public static String KEY_UserData_position = "position";
    public static String KEY_UserData_birthday = "birthday";
    public static String KEY_UserData_versionData = "versionData";
    public static String KEY_UserData_No = "number";
    public static String KEY_UserData_accountId = "accountId";
    public static String KEY_UserData_privacyPolicy = "privacyPolicy";
    public static String KEY_UserData_imageUrl = "imageUrl";
    public static String KEY_UserData_imageUrl_local = "imageUrlLocal";
    public static String KEY_UserData_groupCode = "groupCode";
    public static String KEY_UserData_roleName = "roleName";
    public static String KEY_UserData_checkin = "checkin";
    public static String KEY_UserData_phoneNumber = "phoneNumber";
    public static String KEY_UserData_email = "email";
    public static String KEY_UserData_gender = "gender";
    public static String KEY_UserData_fullName = "fullName";

    public static String TableListEvent = "tabListEvent";
    public static String KEY_ListEvent_No = "number";
    public static String KEY_ListEvent_eventId = "eventId";
    public static String KEY_ListEvent_accountId = "accountId";
    public static String KEY_ListEvent_version_data = "versionData";
    public static String KEY_ListEvent_startDate = "startDate";
    public static String KEY_ListEvent_logo = "logo";
    public static String KEY_ListEvent_logo_local = "logo_local";
    public static String KEY_ListEvent_title = "title";
    public static String KEY_ListEvent_groupCompany = "groupCompany";
    public static String KEY_ListEvent_status = "status";
    public static String KEY_ListEvent_background = "background";
    public static String KEY_ListEvent_background_local = "background_local";
    public static String KEY_ListEvent_groupCode = "groupCode";
    public static String KEY_ListEvent_roleName = "roleName";
    public static String KEY_ListEvent_date = "date";
    public static String KEY_ListEvent_IsFirstIn = "isFirstIn";

    public static String TableWallet = "tabWallet";
    public static String KEY_Wallet_No = "number";
    public static String KEY_Wallet_eventId = "eventId";
    public static String KEY_Wallet_accountId = "accountId";
    public static String KEY_Wallet_notif = "notif";
    public static String KEY_Wallet_detail = "detail";
    public static String KEY_Wallet_bodyWallet = "bodyWallet";
    public static String KEY_Wallet_sort = "sort";
    public static String KEY_Wallet_type = "type";

    private static final String query_add_table_UserData = "CREATE TABLE IF NOT EXISTS " + TableUserData + "("
            + KEY_UserData_No + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_UserData_accountId + " TEXT,"
            + KEY_UserData_position + " TEXT, "
            + KEY_UserData_birthday + " TEXT, "
            + KEY_UserData_versionData + " INTEGER, "
            + KEY_UserData_privacyPolicy + " TEXT, "
            + KEY_UserData_imageUrl + " BLOB, "
            + KEY_UserData_imageUrl_local + " BLOB, "
            + KEY_UserData_groupCode + " TEXT, "
            + KEY_UserData_roleName + " TEXT, "
            + KEY_UserData_checkin + " TEXT, "
            + KEY_UserData_phoneNumber + " TEXT, "
            + KEY_UserData_email + " TEXT, "
            + KEY_UserData_gender + " TEXT, "
            + KEY_UserData_fullName + " TEXT) ";
    private static final String query_delete_table_UserData = "DROP TABLE IF EXISTS " + TableUserData;


    private static final String query_add_table_ListEvent = "CREATE TABLE IF NOT EXISTS " + TableListEvent + "("
            + KEY_ListEvent_No + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ListEvent_eventId + " TEXT, "
            + KEY_ListEvent_accountId + " TEXT,"
            + KEY_ListEvent_version_data + " INTEGER,"
            + KEY_ListEvent_startDate + " TEXT, "
            + KEY_ListEvent_logo + " BLOB, "
            + KEY_ListEvent_logo_local + " BLOB, "
            + KEY_ListEvent_title + " TEXT, "
            + KEY_ListEvent_groupCompany + " TEXT, "
            + KEY_ListEvent_status + " TEXT, "
            + KEY_ListEvent_background + " BLOB, "
            + KEY_ListEvent_background_local + " BLOB, "
            + KEY_ListEvent_groupCode + " TEXT, "
            + KEY_ListEvent_roleName + " TEXT, "
            + KEY_ListEvent_date + " TEXT,"
            + KEY_ListEvent_IsFirstIn + " BOOLEAN)";
    private static final String query_delete_table_ListEvent = "DROP TABLE IF EXISTS " + TableListEvent;

    private static final String query_add_table_Wallet = "CREATE TABLE IF NOT EXISTS " + TableWallet + "("
            + KEY_Wallet_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_Wallet_sort + " INTEGER, "
            + KEY_Wallet_accountId + " TEXT, "
            + KEY_Wallet_eventId + " TEXT, "
            + KEY_Wallet_bodyWallet + " BLOB, "
            + KEY_Wallet_notif + " TEXT, "
            + KEY_Wallet_detail + " TEXT, "
            + KEY_Wallet_type + " TEXT) ";
    private static final String query_delete_table_Wallet = "DROP TABLE IF EXISTS " + TableWallet;

    private static final String query_add_table_GlobalData = "CREATE TABLE IF NOT EXISTS " + TableGlobalData + "("
            + KEY_GlobalData_dbver + " TEXT PRIMARY KEY , "
            + KEY_GlobalData_version_data_user + " TEXT, "
            + KEY_GlobalData_version_data_event + " TEXT) ";
    private static final String query_delete_table_GlobalData = "DROP TABLE IF EXISTS " + TableGlobalData;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query_add_table_UserData);
        db.execSQL(query_add_table_ListEvent);
        db.execSQL(query_add_table_Wallet);
        db.execSQL(query_add_table_GlobalData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(query_delete_table_UserData);
        db.execSQL(query_delete_table_ListEvent);
        db.execSQL(query_delete_table_Wallet);
        db.execSQL(query_delete_table_GlobalData);
        replaceDataToNewTable(db, TableUserData, "tabTempUserEvent");
        replaceDataToNewTable(db, TableListEvent, "tabTempEvent");
        replaceDataToNewTable(db, TableWallet, "tabTempWallet");
        replaceDataToNewTable(db, TableGlobalData, "tabTempGlobalData");
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
        db.insert(TableUserData, null, contentValues);
        db.close();
    }

    public String getAccountId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableUserData, new String[]{KEY_UserData_accountId}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return "Unknown";
        }
    }

    public int getVersionDataIdUser(String accountId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableUserData, new String[]{KEY_UserData_versionData}, KEY_UserData_accountId + " = ? ", new String[]{accountId}, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(KEY_UserData_versionData));
        } else {
            return 0;
        }
    }

    public int getVersionDataIdEvent(String eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableListEvent, new String[]{KEY_ListEvent_version_data}, KEY_ListEvent_eventId + " = ? ", new String[]{eventId}, null, null, null);
        if (cursor.moveToFirst()) {
            return Integer.parseInt(cursor.getString(0));
        } else {
            return 0;
        }
    }

    /**/
    public void saveUserData(UserDataResponseModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_UserData_accountId, model.getAccount_id());
        contentValues.put(KEY_UserData_position, model.getPosition());
        contentValues.put(KEY_UserData_birthday, model.getBirthday());
        contentValues.put(KEY_UserData_versionData, model.getVersion_data());
        contentValues.put(KEY_UserData_privacyPolicy, model.getPrivacy_policy());
        contentValues.put(KEY_UserData_imageUrl, model.getImage_url());
        //contentValues.put(KEY_UserData_imageUrl_local, model.getImageUrl());
        contentValues.put(KEY_UserData_groupCode, model.getGroup_code());
        contentValues.put(KEY_UserData_roleName, model.getRole_name());
        contentValues.put(KEY_UserData_checkin, model.getCheckin());
        contentValues.put(KEY_UserData_phoneNumber, model.getPhone_number());
        contentValues.put(KEY_UserData_email, model.getEmail());
        contentValues.put(KEY_UserData_gender, model.getGender());
        contentValues.put(KEY_UserData_fullName, model.getFull_name());
        db.insert(TableUserData, null, contentValues);
        db.close();
    }

    public void saveListEvent(UserListEventResponseModel model, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ListEvent_eventId, model.getEvent_id());
        contentValues.put(KEY_ListEvent_accountId, accountId);
        contentValues.put(KEY_ListEvent_startDate, model.getStart_date());
        contentValues.put(KEY_ListEvent_logo, model.getLogo());
        //contentValues.put(KEY_ListEvent_logo_local, model.getLogo());
        contentValues.put(KEY_ListEvent_title, model.getTitle());
        contentValues.put(KEY_ListEvent_groupCompany, model.getGroup_company());
        contentValues.put(KEY_ListEvent_status, model.getStatus());
        //contentValues.put(KEY_ListEvent_background_local, model.getBackground());
        contentValues.put(KEY_ListEvent_groupCode, model.getGroup_code());
        contentValues.put(KEY_ListEvent_roleName, model.getRole_name());
        contentValues.put(KEY_ListEvent_date, model.getDate());
        contentValues.put(KEY_ListEvent_IsFirstIn, false);
        long i = db.insert(TableListEvent, null, contentValues);
        db.close();
    }

    public void saveWalletData(UserWalletDataResponseModel model, String accountId, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_Wallet_sort, model.getSort());
        contentValues.put(KEY_Wallet_accountId, accountId);
        contentValues.put(KEY_Wallet_eventId, eventId);
        contentValues.put(KEY_Wallet_bodyWallet, model.getBody_wallet());
        contentValues.put(KEY_Wallet_notif, model.getNotif());
        contentValues.put(KEY_Wallet_detail, model.getDetail());
        contentValues.put(KEY_Wallet_type, model.getType());
        db.insert(TableWallet, null, contentValues);
        db.close();
    }

    public void updateUserData(UserDataResponseModel model, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_UserData_accountId, model.getAccount_id());
        contentValues.put(KEY_UserData_position, model.getPosition());
        contentValues.put(KEY_UserData_birthday, model.getBirthday());
        contentValues.put(KEY_UserData_versionData, model.getVersion_data());
        contentValues.put(KEY_UserData_privacyPolicy, model.getPrivacy_policy());
        contentValues.put(KEY_UserData_imageUrl, model.getImage_url());
        //contentValues.put(KEY_UserData_imageUrl_local, model.getImageUrl());
        contentValues.put(KEY_UserData_groupCode, model.getGroup_code());
        contentValues.put(KEY_UserData_roleName, model.getRole_name());
        contentValues.put(KEY_UserData_checkin, model.getCheckin());
        contentValues.put(KEY_UserData_phoneNumber, model.getPhone_number());
        contentValues.put(KEY_UserData_email, model.getEmail());
        contentValues.put(KEY_UserData_gender, model.getGender());
        contentValues.put(KEY_UserData_fullName, model.getFull_name());
        int q = db.update(TableUserData, contentValues, KEY_UserData_accountId + " = ? ", new String[]{accountId});
        db.close();
    }

    public void updateListEvent(UserListEventResponseModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ListEvent_eventId, model.getEvent_id());
        //contentValues.put(KEY_ListEvent_accountId, eventId);
        contentValues.put(KEY_ListEvent_startDate, model.getStart_date());
        contentValues.put(KEY_ListEvent_logo, model.getLogo());
        //contentValues.put(KEY_ListEvent_logo_local, model.getLogo());
        contentValues.put(KEY_ListEvent_title, model.getTitle());
        contentValues.put(KEY_ListEvent_groupCompany, model.getGroup_company());
        contentValues.put(KEY_ListEvent_status, model.getStatus());
        //contentValues.put(KEY_ListEvent_background_local, model.getBackground());
        contentValues.put(KEY_ListEvent_groupCode, model.getGroup_code());
        contentValues.put(KEY_ListEvent_roleName, model.getRole_name());
        contentValues.put(KEY_ListEvent_date, model.getDate());
        contentValues.put(KEY_ListEvent_IsFirstIn, false);
        int q = db.update(TableListEvent, contentValues, KEY_ListEvent_eventId + " = ? ", new String[]{model.getEvent_id()});
        db.close();
    }

    public void updateWalletData(UserWalletDataResponseModel model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_Wallet_sort, model.getSort());
        //contentValues.put(KEY_Wallet_accountId, accountId);
        //contentValues.put(KEY_Wallet_eventId, eventId);
        contentValues.put(KEY_Wallet_bodyWallet, model.getBody_wallet());
        contentValues.put(KEY_Wallet_notif, model.getNotif());
        contentValues.put(KEY_Wallet_detail, model.getDetail());
        contentValues.put(KEY_Wallet_type, model.getType());
        int q = db.update(TableWallet, contentValues, KEY_Wallet_sort + " = ? AND " + KEY_Wallet_eventId + " = ? ", new String[]{model.getSort(), eventId});
        db.close();
    }

    public void deleteAllDataUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TableUserData);
        db.close();
    }

    public void deleteAllDataListEvent() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TableListEvent);
        db.close();
    }

    public void deleteAllDataWallet() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TableWallet);
        db.close();
    }

    public void deleleDataByKey(String table, String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, key + " = ? ", new String[]{value});
        db.close();
    }

    public void deleleDataByKeyMultiple(String table, String key, String key2, String value, String value2) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, key + " = ? AND " + key2 + " = ? ", new String[]{value, value2});
        db.close();
    }


    /**/
    public ArrayList<UserDataResponseModel> getUserData(String accountid) {
        ArrayList<UserDataResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableUserData, null, KEY_UserData_accountId + " = ? ", new String[]{accountid}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                UserDataResponseModel model = new UserDataResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(KEY_UserData_No)));
                model.setAccount_id(cursor.getString(cursor.getColumnIndex(KEY_UserData_accountId)));
                model.setPosition(cursor.getString(cursor.getColumnIndex(KEY_UserData_position)));
                model.setBirthday(cursor.getString(cursor.getColumnIndex(KEY_UserData_birthday)));
                model.setVersion_data(cursor.getString(cursor.getColumnIndex(KEY_UserData_versionData)));
                model.setPrivacy_policy(cursor.getString(cursor.getColumnIndex(KEY_UserData_privacyPolicy)));
                model.setImage_url(cursor.getString(cursor.getColumnIndex(KEY_UserData_imageUrl)));
                model.setImage_url_local(cursor.getString(cursor.getColumnIndex(KEY_UserData_imageUrl_local)));
                model.setGroup_code(cursor.getString(cursor.getColumnIndex(KEY_UserData_groupCode)));
                model.setRole_name(cursor.getString(cursor.getColumnIndex(KEY_UserData_roleName)));
                model.setCheckin(cursor.getString(cursor.getColumnIndex(KEY_UserData_checkin)));
                model.setPhone_number(cursor.getString(cursor.getColumnIndex(KEY_UserData_phoneNumber)));
                model.setEmail(cursor.getString(cursor.getColumnIndex(KEY_UserData_email)));
                model.setGender(cursor.getString(cursor.getColumnIndex(KEY_UserData_gender)));
                model.setFull_name(cursor.getString(cursor.getColumnIndex(KEY_UserData_fullName)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        return modelList;
    }

    public ArrayList<UserListEventResponseModel> getAllListEvent(String accountid) {
        ArrayList<UserListEventResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        /*String query = " SELECT * FROM " + TableListEvent + " Where " + KEY_ListEvent_accountId + " = '" + accountid + "'";
        Cursor cursor = db.rawQuery(query, null);*/
        Cursor cursor = db.query(TableListEvent, null, KEY_ListEvent_accountId + " = ? ", new String[]{accountid}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                UserListEventResponseModel model = new UserListEventResponseModel();
                model.setNumber(cursor.getInt(0));
                model.setEvent_id(cursor.getString(1));
                model.setAccountId(cursor.getString(2));
                model.setStart_date(cursor.getString(3));
                model.setLogo(cursor.getString(4));
                model.setLogo_local(cursor.getString(5));
                model.setTitle(cursor.getString(6));
                model.setGroup_company(cursor.getString(7));
                model.setStatus(cursor.getString(8));
                model.setBackground(cursor.getString(9));
                model.setBackground_local(cursor.getString(10));
                model.setGroup_code(cursor.getString(11));
                model.setRole_name(cursor.getString(12));
                model.setDate(cursor.getString(13));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        return modelList;
    }

    public ArrayList<UserWalletDataResponseModel> getListWalletData(String eventId) {
        ArrayList<UserWalletDataResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        /*String query = " SELECT * FROM " + TableWallet + " Where " + KEY_Wallet_eventId + " = '" + eventId + "'";
        Cursor cursor = db.rawQuery(query, null);*/
        Cursor cursor = db.query(TableWallet, null, KEY_Wallet_eventId + " = ? ", new String[]{eventId}, null, null, KEY_Wallet_sort);

        if (cursor.moveToFirst()) {
            do {
                UserWalletDataResponseModel model = new UserWalletDataResponseModel();
                model.setNumber(cursor.getInt(0));
                model.setSort(cursor.getString(1));
                model.setAccountId(cursor.getString(2));
                model.setEventId(cursor.getString(3));
                model.setBody_wallet(cursor.getString(4));
                model.setNotif(cursor.getString(5));
                model.setDetail(cursor.getString(6));
                model.setType(cursor.getString(7));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public boolean checkDataTable(String tableName, String targetKey, String targetValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String selectQuery = "SELECT * FROM " + tableName + " WHERE " + targetKey + " = '" + targetValue + "'";
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(tableName, null, targetKey + " = ? ", new String[]{targetValue}, null, null, null);
        if (cursor != null) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean checkDataTableNull(String tableName, String targetKey) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, null, targetKey + " IS NULL ", null, null, null, null);
        if (cursor == null) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean checkDataTableKeyMultiple(String table, String key, String key2, String value, String value2) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String selectQuery = "SELECT * FROM " + tableName + " WHERE " + targetKey + " = '" + targetValue + "'";
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(table, null, key + " = ? AND " + key2 + " = ? ", new String[]{value, value2}, null, null, null);
        if (cursor != null) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void setOneKey(String table, String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(key, value);
        db.insert(table, null, contentValues);
        db.close();
    }

    public boolean getIsFirstIn(String eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String selectQuery = "SELECT * FROM " + TableListEvent + " WHERE " + KEY_ListEvent_eventId + " = '" + eventId + "' AND " + KEY_ListEvent_IsFirstIn + " = " + true;
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TableListEvent, null, KEY_ListEvent_eventId + " = ? AND " + KEY_ListEvent_IsFirstIn + " = ? ", new String[]{eventId, String.valueOf(true)}, null, null, null);
        if (cursor != null) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void saveImageUserToLocal(String imgCachePath, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_UserData_imageUrl_local, imgCachePath);
        db.update(TableUserData, contentValues, KEY_UserData_accountId + " = ? ", new String[]{accountId});
        db.close();
    }

    public void saveImageListEventToLocal(String backgroundCachePath, String logoCachePath, UserListEventResponseModel model1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ListEvent_background_local, backgroundCachePath);
        contentValues.put(KEY_ListEvent_logo_local, logoCachePath);
        db.update(TableListEvent, contentValues, KEY_ListEvent_eventId + " = ? ", new String[]{model1.getEvent_id()});
        db.close();
    }
}