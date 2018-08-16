package com.gandsoft.openguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gandsoft.openguide.API.APIresponse.Event.EventAbout;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfo;
import com.gandsoft.openguide.API.APIresponse.Event.EventPlaceList;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDateDataList;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Databases information
    private static final String DATABASE_NAME = "openguides.db";
    private static final int DATABASE_VERSION = 2;

    public static String TableGlobalData = "tabGlobalData";
    public static String KEY_GlobalData_dbver = "dbver";
    public static String KEY_GlobalData_eventId = "eventId";
    public static String KEY_GlobalData_accountId = "accountId";
    public static String KEY_GlobalData_version_data_user = "version_data_user";
    public static String KEY_GlobalData_version_data_event = "version_data_event";

    public static String TableUserData = "tabUserEvent";
    public static String KEY_UserData_No = "number";
    public static String KEY_UserData_accountId = "accountId";
    public static String KEY_UserData_position = "position";
    public static String KEY_UserData_birthday = "birthday";
    public static String KEY_UserData_versionData = "versionData";
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

    public static String TableEventAbout = "tabAbout";
    public static String Key_Event_About_No = "number";
    public static String Key_Event_About_EventId = "eventId";
    public static String KEY_Event_About_Background = "background";
    public static String KEY_Event_About_Background_Local = "background_local";
    public static String KEY_Event_About_Logo = "logo";
    public static String KEY_Event_About_Logo_Local = "logo_local";
    public static String KEY_Event_About_Description = "description";

    public static String TableContactList = "tabContactList";
    public static String Key_Contact_List_No = "number";
    public static String Key_Contact_List_EventId = "eventId";
    public static String Key_Contact_List_Title = "title";
    public static String Key_Contact_List_Icon = "icon";
    public static String KEY_Contact_List_Telephone = "telephone";
    public static String KEY_Contact_List_Email = "email";
    public static String KEY_Contact_List_Name = "name";

    public static String TableImportantInfo = "tabImportantInfo";
    public static String Key_Importan_Info_No = "number";
    public static String Key_Importan_Info_EventId = "eventId";
    public static String Key_Importan_Info_title = "title";
    public static String Key_Importan_Info_info = "info";

    public static String TableScheduleList = "tabScheduleList";
    public static String Key_Schedule_List_No = "number";
    public static String Key_Schedule_List_EventId = "eventId";
    public static String Key_Schedule_List_date = "date";
    public static String Key_Schedule_List_id = "id";
    public static String Key_Schedule_List_waktu = "waktu";
    public static String Key_Schedule_List_schedule_title = "schedule_title";
    public static String Key_Schedule_List_location = "location";
    public static String Key_Schedule_List_detail = "detail";
    public static String Key_Schedule_List_action = "action";
    public static String Key_Schedule_List_link = "link";
    public static String Key_Schedule_List_status = "status";

    public static String TableTheEvent = "tabTheEvent";
    public static String Key_The_Event_No = "number";
    public static String Key_The_Event_EventId = "eventId";
    public static String Key_The_Event_background = "background";
    public static String Key_The_Event_logo = "logo";
    public static String Key_The_Event_event_name = "event_name";
    public static String Key_The_Event_event_location = "event_location";
    public static String Key_The_Event_version_data = "versionData";
    public static String Key_The_Event_date_event = "date_event";
    public static String Key_The_Event_weather = "weather";
    public static String Key_The_Event_title_contact = "title_contact";
    public static String Key_The_Event_api_weather = "api_weather";
    public static String Key_The_Event_welcome_note = "welcome_note";
    public static String Key_The_Event_commentpost_status = "commentpost_status";
    public static String Key_The_Event_deletepost_status = "deletepost_status";
    public static String Key_The_Event_addpost_status = "addpost_status";
    public static String Key_The_Event_feedback = "feedback_data";

    public static String TablePlaceList = "tabPlaceList";
    public static String Key_Place_List_No = "number";
    public static String Key_Place_List_EventId = "eventId";
    public static String Key_Place_List_latitude = "latitude";
    public static String Key_Place_List_longitude = "longitude";
    public static String Key_Place_List_title = "title";
    public static String Key_Place_List_icon = "icon";

    private static final String query_add_table_UserData = "CREATE TABLE IF NOT EXISTS " + TableUserData + "("
            + KEY_UserData_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_UserData_accountId + " TEXT, "
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
            + KEY_ListEvent_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ListEvent_eventId + " TEXT, "
            + KEY_ListEvent_accountId + " TEXT, "
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
            + KEY_ListEvent_date + " TEXT, "
            + KEY_ListEvent_IsFirstIn + " BOOLEAN) ";
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
            + KEY_GlobalData_eventId + " TEXT, "
            + KEY_GlobalData_accountId + " TEXT, "
            + KEY_GlobalData_version_data_user + " TEXT, "
            + KEY_GlobalData_version_data_event + " TEXT) ";
    private static final String query_delete_table_GlobalData = "DROP TABLE IF EXISTS " + TableGlobalData;

    private static final String query_add_table_EventAbout = "CREATE TABLE IF NOT EXISTS " + TableEventAbout + "("
            + Key_Event_About_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Event_About_EventId + " TEXT, "
            + KEY_Event_About_Background + " TEXT, "
            + KEY_Event_About_Background_Local + " TEXT, "
            + KEY_Event_About_Logo + " TEXT, "
            + KEY_Event_About_Logo_Local + " TEXT, "
            + KEY_Event_About_Description + " TEXT) ";
    private static final String query_delete_table_EventAbout = "DROP TABLE IF EXISTS " + TableEventAbout;

    private static final String query_add_table_ContactList = "CREATE TABLE IF NOT EXISTS " + TableContactList + "("
            + Key_Contact_List_No + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Key_Contact_List_EventId + " TEXT, "
            + Key_Contact_List_Title + " TEXT, "
            + KEY_Contact_List_Name + " TEXT, "
            + KEY_Contact_List_Email + " TEXT, "
            + KEY_Contact_List_Telephone + " TEXT, "
            + Key_Contact_List_Icon + " TEXT) ";
    private static final String query_delete_table_ContactList = "DROP TABLE IF EXISTS " + TableContactList;

    private static final String query_add_table_ImportantInfo = "CREATE TABLE IF NOT EXISTS " + TableImportantInfo + "("
            + Key_Importan_Info_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Importan_Info_EventId + " TEXT, "
            + Key_Importan_Info_title + " TEXT, "
            + Key_Importan_Info_info + " TEXT) ";
    private static final String query_delete_table_ImportantInfo = "DROP TABLE IF EXISTS " + TableImportantInfo;

    private static final String query_add_table_ScheduleList = "CREATE TABLE IF NOT EXISTS " + TableScheduleList + "("
            + Key_Schedule_List_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Schedule_List_EventId + " TEXT, "
            + Key_Schedule_List_date + " TEXT, "
            + Key_Schedule_List_id + " INTEGER, "
            + Key_Schedule_List_waktu + " TEXT, "
            + Key_Schedule_List_schedule_title + " TEXT, "
            + Key_Schedule_List_location + " TEXT, "
            + Key_Schedule_List_detail + " TEXT, "
            + Key_Schedule_List_action + " TEXT, "
            + Key_Schedule_List_link + " TEXT, "
            + Key_Schedule_List_status + " TEXT) ";
    private static final String query_delete_table_ScheduleList = "DROP TABLE IF EXISTS " + TableScheduleList;

    private static final String query_add_table_TheEvent = "CREATE TABLE IF NOT EXISTS " + TableTheEvent + "("
            + Key_The_Event_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_The_Event_EventId + " TEXT, "
            + Key_The_Event_background + " TEXT, "
            + Key_The_Event_logo + " TEXT, "
            + Key_The_Event_event_name + " TEXT, "
            + Key_The_Event_event_location + " TEXT, "
            + Key_The_Event_version_data + " INTEGER, "
            + Key_The_Event_feedback + " TEXT, "
            + Key_The_Event_date_event + " TEXT, "
            + Key_The_Event_weather + " TEXT, "
            + Key_The_Event_title_contact + " TEXT, "
            + Key_The_Event_api_weather + " TEXT, "
            + Key_The_Event_welcome_note + " TEXT, "
            + Key_The_Event_commentpost_status + " BOOLEAN, "
            + Key_The_Event_deletepost_status + " BOOLEAN, "
            + Key_The_Event_addpost_status + " BOOLEAN) ";
    private static final String query_delete_table_TheEvent = "DROP TABLE IF EXISTS " + TableTheEvent;

    private static final String query_add_table_PlaceList = "CREATE TABLE IF NOT EXISTS " + TablePlaceList + "("
            + Key_Place_List_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Place_List_EventId + " TEXT, "
            + Key_Place_List_latitude + " TEXT, "
            + Key_Place_List_longitude + " TEXT, "
            + Key_Place_List_title + " TEXT, "
            + Key_Place_List_icon + " TEXT) ";
    private static final String query_delete_table_PlaceList = "DROP TABLE IF EXISTS " + TablePlaceList;


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
        db.execSQL(query_add_table_EventAbout);
        db.execSQL(query_add_table_ContactList);
        db.execSQL(query_add_table_ImportantInfo);
        db.execSQL(query_add_table_ScheduleList);
        db.execSQL(query_add_table_TheEvent);
        db.execSQL(query_add_table_PlaceList);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(query_delete_table_UserData);
        db.execSQL(query_delete_table_ListEvent);
        db.execSQL(query_delete_table_Wallet);
        db.execSQL(query_delete_table_GlobalData);
        db.execSQL(query_delete_table_EventAbout);
        db.execSQL(query_delete_table_ContactList);
        db.execSQL(query_delete_table_ImportantInfo);
        db.execSQL(query_delete_table_ScheduleList);
        db.execSQL(query_delete_table_TheEvent);
        db.execSQL(query_delete_table_PlaceList);

        db.execSQL(query_add_table_UserData);
        db.execSQL(query_add_table_ListEvent);
        db.execSQL(query_add_table_Wallet);
        db.execSQL(query_add_table_GlobalData);
        db.execSQL(query_add_table_EventAbout);
        db.execSQL(query_add_table_ContactList);
        db.execSQL(query_add_table_ImportantInfo);
        db.execSQL(query_add_table_ScheduleList);
        db.execSQL(query_add_table_TheEvent);
        db.execSQL(query_add_table_PlaceList);
    }

    private void replaceDataToNewTable(SQLiteDatabase db, String tableName, String tableString) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableString);

        List<String> columns = getColumns(db, tableName);
        db.execSQL("ALTER TABLE " + tableName + " RENAME TO temp_" + tableName);
        db.execSQL("CREATE TABLE " + tableString);

        columns.retainAll(getColumns(db, tableName));
        String cols = join(columns, ", ");
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
        db.insertWithOnConflict(TableUserData, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
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

    /*Version data*/

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
        Cursor cursor = db.query(TableTheEvent, new String[]{Key_The_Event_version_data}, Key_The_Event_EventId + " = ? ", new String[]{eventId}, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(Key_The_Event_version_data));
        } else {
            return 0;
        }
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
        db.insertWithOnConflict(TableListEvent, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void saveUserData(UserDataResponseModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_UserData_accountId, model.getAccount_id());
        contentValues.put(KEY_UserData_position, model.getPosition());
        contentValues.put(KEY_UserData_birthday, model.getBirthday());
        contentValues.put(KEY_UserData_versionData, model.getVersion_data());
        contentValues.put(KEY_UserData_privacyPolicy, model.getPrivacy_policy());
        contentValues.put(KEY_UserData_imageUrl, model.getImage_url());
        contentValues.put(KEY_UserData_groupCode, model.getGroup_code());
        contentValues.put(KEY_UserData_roleName, model.getRole_name());
        contentValues.put(KEY_UserData_checkin, model.getCheckin());
        contentValues.put(KEY_UserData_phoneNumber, model.getPhone_number());
        contentValues.put(KEY_UserData_email, model.getEmail());
        contentValues.put(KEY_UserData_gender, model.getGender());
        contentValues.put(KEY_UserData_fullName, model.getFull_name());
        db.insertWithOnConflict(TableUserData, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
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
        db.insertWithOnConflict(TableWallet, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void saveTheEvent(EventTheEvent model, String eventId, String feedback_data, String version_data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d("Lihat", "saveTheEvent SQLiteHelper : " + model.event_name);
        Log.d("Lihat", "saveTheEvent SQLiteHelper : " + eventId);
        Log.d("Lihat", "saveTheEvent SQLiteHelper : " + feedback_data);
        Log.d("Lihat", "saveTheEvent SQLiteHelper : " + version_data);
        contentValues.put(Key_The_Event_EventId, eventId);
        contentValues.put(Key_The_Event_version_data, version_data);
        contentValues.put(Key_The_Event_feedback, feedback_data);
        contentValues.put(Key_The_Event_background, model.getBackground());
        contentValues.put(Key_The_Event_logo, model.getLogo());
        contentValues.put(Key_The_Event_event_name, model.getEvent_name());
        contentValues.put(Key_The_Event_event_location, model.getEvent_location());
        contentValues.put(Key_The_Event_date_event, model.getDate_event());
        contentValues.put(Key_The_Event_weather, model.getWeather());
        contentValues.put(Key_The_Event_title_contact, model.getTitle_contact());
        contentValues.put(Key_The_Event_api_weather, model.getApi_weather());
        contentValues.put(Key_The_Event_welcome_note, model.getWelcome_note());
        contentValues.put(Key_The_Event_commentpost_status, model.getCommentpost_status());
        contentValues.put(Key_The_Event_deletepost_status, model.getDeletepost_status());
        long l = db.insertWithOnConflict(TableTheEvent, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("Lihat", "saveTheEvent SQLiteHelper : " + l);
        db.close();
    }

    public void saveScheduleList(EventScheduleListDateDataList model, String date, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Schedule_List_EventId, eventId);
        contentValues.put(Key_Schedule_List_date, date);
        contentValues.put(Key_Schedule_List_id, model.getId());
        contentValues.put(Key_Schedule_List_waktu, model.getWaktu());
        contentValues.put(Key_Schedule_List_schedule_title, model.getSchedule_title());
        contentValues.put(Key_Schedule_List_location, model.getLocation());
        contentValues.put(Key_Schedule_List_detail, model.getDetail());
        contentValues.put(Key_Schedule_List_action, model.getAction());
        contentValues.put(Key_Schedule_List_link, model.getLink());
        contentValues.put(Key_Schedule_List_status, model.getStatus());
        db.insertWithOnConflict(TableScheduleList, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void saveImportanInfo(EventImportanInfo model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Importan_Info_EventId, eventId);
        contentValues.put(Key_Importan_Info_title, model.getInfo());
        contentValues.put(Key_Importan_Info_info, model.getInfo());
        db.insertWithOnConflict(TableImportantInfo, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void saveDataContactList(EventDataContactList model, String title, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Contact_List_EventId, eventId);
        contentValues.put(Key_Contact_List_Title, title);
        contentValues.put(KEY_Contact_List_Name, model.getName());
        contentValues.put(KEY_Contact_List_Email, model.getEmail());
        contentValues.put(KEY_Contact_List_Telephone, model.getTelephone());
        contentValues.put(Key_Contact_List_Icon, model.getIcon());
        db.insertWithOnConflict(TableContactList, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void saveAbout(EventAbout model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Event_About_EventId, eventId);
        contentValues.put(KEY_Event_About_Background, model.getBackground());
        //contentValues.put(KEY_Event_About_Background_Local, model.);
        contentValues.put(KEY_Event_About_Logo, model.getLogo());
        //contentValues.put(KEY_Event_About_Logo_Local, model.);
        contentValues.put(KEY_Event_About_Description, model.getDescription());
        db.insertWithOnConflict(TableEventAbout, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void savePlaceList(EventPlaceList model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Place_List_EventId, eventId);
        contentValues.put(Key_Place_List_latitude, model.getLatitude());
        contentValues.put(Key_Place_List_longitude, model.getLongitude());
        contentValues.put(Key_Place_List_title, model.getTitle());
        contentValues.put(Key_Place_List_icon, model.getIcon());
        db.insertWithOnConflict(TablePlaceList, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
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
        db.updateWithOnConflict(TableUserData, contentValues, KEY_UserData_accountId + " = ? ", new String[]{accountId}, SQLiteDatabase.CONFLICT_REPLACE);
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
        int i = db.updateWithOnConflict(TableListEvent, contentValues, KEY_ListEvent_eventId + " = ? ", new String[]{model.getEvent_id()}, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("Lihat", "updateListEvent SQLiteHelper : " + i);
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
        db.updateWithOnConflict(TableWallet, contentValues, KEY_Wallet_sort + " = ? AND " + KEY_Wallet_eventId + " = ? ", new String[]{model.getSort(), eventId}, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void updateTheEvent(EventTheEvent model, String eventId, String feedback_data, String version_data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d("Lihat", "updateTheEvent SQLiteHelper : " + model.getEvent_name());
        Log.d("Lihat", "updateTheEvent SQLiteHelper : " + eventId);
        Log.d("Lihat", "updateTheEvent SQLiteHelper : " + version_data);
        contentValues.put(Key_The_Event_background, model.getBackground());
        contentValues.put(Key_The_Event_logo, model.getLogo());
        contentValues.put(Key_The_Event_event_name, model.getEvent_name());
        contentValues.put(Key_The_Event_event_location, model.getEvent_location());
        contentValues.put(Key_The_Event_version_data, version_data);
        contentValues.put(Key_The_Event_feedback, feedback_data);
        contentValues.put(Key_The_Event_date_event, model.getDate_event());
        contentValues.put(Key_The_Event_weather, model.getWeather());
        contentValues.put(Key_The_Event_title_contact, model.getTitle_contact());
        contentValues.put(Key_The_Event_api_weather, model.getApi_weather());
        contentValues.put(Key_The_Event_welcome_note, model.getWelcome_note());
        contentValues.put(Key_The_Event_commentpost_status, model.getCommentpost_status());
        contentValues.put(Key_The_Event_deletepost_status, model.getDeletepost_status());
        db.updateWithOnConflict(TableTheEvent, contentValues, Key_The_Event_EventId + " = ? ", new String[]{eventId}, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void updateScheduleList(EventScheduleListDateDataList model, String date, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Schedule_List_date, date);
        contentValues.put(Key_Schedule_List_id, model.getId());
        contentValues.put(Key_Schedule_List_waktu, model.getWaktu());
        contentValues.put(Key_Schedule_List_schedule_title, model.getSchedule_title());
        contentValues.put(Key_Schedule_List_location, model.getLocation());
        contentValues.put(Key_Schedule_List_detail, model.getDetail());
        contentValues.put(Key_Schedule_List_action, model.getAction());
        contentValues.put(Key_Schedule_List_link, model.getLink());
        contentValues.put(Key_Schedule_List_status, model.getStatus());
        db.updateWithOnConflict(TableScheduleList, contentValues, Key_Schedule_List_EventId + " = ? ", new String[]{eventId}, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void updateImportanInfo(EventImportanInfo model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Importan_Info_title, model.getInfo());
        contentValues.put(Key_Importan_Info_info, model.getInfo());
        db.updateWithOnConflict(TableImportantInfo, contentValues, Key_Importan_Info_EventId + " = ? ", new String[]{eventId}, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void updateDataContactList(EventDataContactList model, String title, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Contact_List_Title, title);
        contentValues.put(KEY_Contact_List_Name, model.getName());
        contentValues.put(KEY_Contact_List_Email, model.getEmail());
        contentValues.put(KEY_Contact_List_Telephone, model.getTelephone());
        contentValues.put(Key_Contact_List_Icon, model.getIcon());
        db.updateWithOnConflict(TableContactList, contentValues, Key_Contact_List_EventId + " = ? ", new String[]{eventId}, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void updateAbout(EventAbout model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_Event_About_Background, model.getBackground());
        //contentValues.put(KEY_Event_About_Background_Local, model.);
        contentValues.put(KEY_Event_About_Logo, model.getLogo());
        //contentValues.put(KEY_Event_About_Logo_Local, model.);
        contentValues.put(KEY_Event_About_Description, model.getDescription());
        db.updateWithOnConflict(TableEventAbout, contentValues, Key_Event_About_EventId + " = ? ", new String[]{eventId}, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void updatePlaceList(EventPlaceList model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Place_List_latitude, model.getLatitude());
        contentValues.put(Key_Place_List_longitude, model.getLongitude());
        contentValues.put(Key_Place_List_title, model.getTitle());
        contentValues.put(Key_Place_List_icon, model.getIcon());
        db.updateWithOnConflict(TablePlaceList, contentValues, Key_Place_List_EventId + " = ?", new String[]{eventId}, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public ArrayList<UserDataResponseModel> getUserData(String accountid) {
        ArrayList<UserDataResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableUserData, null, KEY_UserData_accountId + " = ? ", new String[]{accountid}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                UserDataResponseModel model = new UserDataResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_UserData_No)));
                model.setAccount_id(cursor.getString(cursor.getColumnIndexOrThrow(KEY_UserData_accountId)));
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
        Cursor cursor = db.query(TableWallet, null, KEY_Wallet_eventId + " = ? ", new String[]{eventId}, KEY_Wallet_sort, null, KEY_Wallet_sort);

        if (cursor.moveToFirst()) {
            do {
                UserWalletDataResponseModel model = new UserWalletDataResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(KEY_Wallet_No)));
                model.setSort(cursor.getString(cursor.getColumnIndex(KEY_Wallet_sort)));
                model.setAccountId(cursor.getString(cursor.getColumnIndex(KEY_Wallet_accountId)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(KEY_Wallet_eventId)));
                model.setBody_wallet(cursor.getString(cursor.getColumnIndex(KEY_Wallet_bodyWallet)));
                model.setNotif(cursor.getString(cursor.getColumnIndex(KEY_Wallet_notif)));
                model.setDetail(cursor.getString(cursor.getColumnIndex(KEY_Wallet_detail)));
                model.setType(cursor.getString(cursor.getColumnIndex(KEY_Wallet_type)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventTheEvent> getTheEvents(String eventId) {
        ArrayList<EventTheEvent> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableTheEvent, null, Key_The_Event_EventId + " = ? ", new String[]{eventId}, Key_The_Event_EventId, null, null);

        if (cursor.moveToFirst()) {
            do {
                EventTheEvent model = new EventTheEvent();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_The_Event_No)));
                model.setBackground(cursor.getString(cursor.getColumnIndex(Key_The_Event_background)));
                model.setLogo(cursor.getString(cursor.getColumnIndex(Key_The_Event_logo)));
                model.setDate_event(cursor.getString(cursor.getColumnIndex(Key_The_Event_date_event)));
                model.setEvent_name(cursor.getString(cursor.getColumnIndex(Key_The_Event_event_name)));
                model.setEvent_location(cursor.getString(cursor.getColumnIndex(Key_The_Event_event_location)));
                model.setWeather(cursor.getString(cursor.getColumnIndex(Key_The_Event_weather)));
                model.setTitle_contact(cursor.getString(cursor.getColumnIndex(Key_The_Event_title_contact)));
                model.setApi_weather(cursor.getString(cursor.getColumnIndex(Key_The_Event_api_weather)));
                model.setWelcome_note(cursor.getString(cursor.getColumnIndex(Key_The_Event_welcome_note)));
                model.setCommentpost_status(cursor.getString(cursor.getColumnIndex(Key_The_Event_commentpost_status)));
                model.setDeletepost_status(cursor.getString(cursor.getColumnIndex(Key_The_Event_deletepost_status)));
                model.setAddpost_status(cursor.getString(cursor.getColumnIndex(Key_The_Event_addpost_status)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public EventTheEvent getTheEvent(String eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableTheEvent, null, Key_The_Event_EventId + " = ?", new String[]{String.valueOf(eventId)}, Key_The_Event_EventId, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        EventTheEvent model = new EventTheEvent();
        model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_The_Event_No)));
        model.setBackground(cursor.getString(cursor.getColumnIndex(Key_The_Event_background)));
        model.setLogo(cursor.getString(cursor.getColumnIndex(Key_The_Event_logo)));
        model.setEvent_name(cursor.getString(cursor.getColumnIndex(Key_The_Event_event_name)));
        model.setEvent_location(cursor.getString(cursor.getColumnIndex(Key_The_Event_event_location)));
        model.setWeather(cursor.getString(cursor.getColumnIndex(Key_The_Event_weather)));
        model.setDate_event(cursor.getString(cursor.getColumnIndex(Key_The_Event_date_event)));
        model.setTitle_contact(cursor.getString(cursor.getColumnIndex(Key_The_Event_title_contact)));
        model.setApi_weather(cursor.getString(cursor.getColumnIndex(Key_The_Event_api_weather)));
        model.setWelcome_note(cursor.getString(cursor.getColumnIndex(Key_The_Event_welcome_note)));
        model.setCommentpost_status(cursor.getString(cursor.getColumnIndex(Key_The_Event_commentpost_status)));
        model.setDeletepost_status(cursor.getString(cursor.getColumnIndex(Key_The_Event_deletepost_status)));
        model.setAddpost_status(cursor.getString(cursor.getColumnIndex(Key_The_Event_addpost_status)));
        // return model
        return model;
    }

    public ArrayList<EventScheduleListDateDataList> getScheduleList(String eventId) {
        ArrayList<EventScheduleListDateDataList> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableScheduleList, null, Key_Schedule_List_EventId + " = ? ", new String[]{eventId}, null, null, Key_Schedule_List_date);

        if (cursor.moveToFirst()) {
            do {
                EventScheduleListDateDataList model = new EventScheduleListDateDataList();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Schedule_List_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_EventId)));
                model.setDate(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_date)));
                model.setId(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_id)));
                model.setWaktu(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_waktu)));
                model.setSchedule_title(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_schedule_title)));
                model.setLocation(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_location)));
                model.setAction(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_action)));
                model.setLink(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_link)));
                model.setStatus(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_status)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventImportanInfo> getImportanInfo(String eventId) {
        ArrayList<EventImportanInfo> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableImportantInfo, null, Key_Importan_Info_EventId + " = ? ", new String[]{eventId}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                EventImportanInfo model = new EventImportanInfo();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Importan_Info_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Importan_Info_EventId)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(Key_Importan_Info_title)));
                model.setInfo(cursor.getString(cursor.getColumnIndex(Key_Importan_Info_info)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventDataContactList> getDataContactList(String eventId) {
        ArrayList<EventDataContactList> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableContactList, null, Key_Contact_List_EventId + " = ? ", new String[]{eventId}, null, null, KEY_Wallet_sort);

        if (cursor.moveToFirst()) {
            do {
                EventDataContactList model = new EventDataContactList();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Contact_List_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Contact_List_EventId)));
                model.setName(cursor.getString(cursor.getColumnIndex(KEY_Contact_List_Name)));
                model.setTelephone(cursor.getString(cursor.getColumnIndex(KEY_Contact_List_Telephone)));
                model.setEmail(cursor.getString(cursor.getColumnIndex(KEY_Contact_List_Email)));
                model.setIcon(cursor.getString(cursor.getColumnIndex(Key_Contact_List_Icon)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(Key_Contact_List_Title)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventAbout> getAbout(String eventId) {
        ArrayList<EventAbout> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableEventAbout, null, Key_Event_About_EventId + " = ? ", new String[]{eventId}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                EventAbout model = new EventAbout();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Event_About_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Event_About_EventId)));
                model.setBackground(cursor.getString(cursor.getColumnIndex(KEY_Event_About_Background)));
                model.setLogo(cursor.getString(cursor.getColumnIndex(KEY_Event_About_Logo)));
                model.setDescription(cursor.getString(cursor.getColumnIndex(KEY_Event_About_Description)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventPlaceList> getPlaceList(String eventId) {
        ArrayList<EventPlaceList> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TablePlaceList, null, Key_Place_List_EventId + " = ? ", new String[]{eventId}, null, null, Key_Place_List_No);

        if (cursor.moveToFirst()) {
            do {
                EventPlaceList model = new EventPlaceList();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Place_List_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Place_List_EventId)));
                model.setLatitude(cursor.getString(cursor.getColumnIndex(Key_Place_List_latitude)));
                model.setLongitude(cursor.getString(cursor.getColumnIndex(Key_Place_List_longitude)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(Key_Place_List_title)));
                model.setIcon(cursor.getString(cursor.getColumnIndex(Key_Place_List_icon)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
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


    /*CHECK DATA*/
    public boolean isDataTableKeyNull(String tableName, String targetKey) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, null, targetKey + " IS NULL ", null, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean isDataTableKeyMultipleNull(String table, String key, String key2) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String selectQuery = "SELECT * FROM " + tableName + " WHERE " + targetKey + " = '" + targetValue + "'";
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(table, null, key + " IS NULL AND " + key2 + " IS NULL ", null, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean isDataTableValueNull(String tableName, String targetKey, String targetValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, null, targetKey + " = ? ", new String[]{targetValue}, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean isDataTableValueMultipleNull(String table, String key, String key2, String value, String value2) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String selectQuery = "SELECT * FROM " + tableName + " WHERE " + targetKey + " = '" + targetValue + "'";
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(table, null, key + " = ? AND " + key2 + " = ? ", new String[]{value, value2}, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    /**/
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

    public void insertOneKey(String table, String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(key, value);
        db.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void updateOneKey(String table, String keyWhere, String valueWhere, String keyParam, String valueParam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(keyParam, valueParam);
        db.updateWithOnConflict(table, contentValues, keyWhere + " = ? ", new String[]{valueWhere}, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public String getOneKey(String table, String keyWhere, String valueWhere, String keyParam) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, null, keyWhere + " = ? ", new String[]{valueWhere}, null, null, null);

        if (cursor.getCount() == 1) {
            return (cursor.getString(cursor.getColumnIndex(keyParam)));
        } else {
            cursor.close();
            return null;
        }
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
        db.updateWithOnConflict(TableUserData, contentValues, KEY_UserData_accountId + " = ? ", new String[]{accountId}, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void saveImageListEventToLocal(String backgroundCachePath, String logoCachePath, UserListEventResponseModel model1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ListEvent_background_local, backgroundCachePath);
        contentValues.put(KEY_ListEvent_logo_local, logoCachePath);
        db.updateWithOnConflict(TableListEvent, contentValues, KEY_ListEvent_eventId + " = ? ", new String[]{model1.getEvent_id()}, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }
}