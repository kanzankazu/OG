package com.gandsoft.openguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gandsoft.openguide.API.APIresponse.Event.EventAbout;
import com.gandsoft.openguide.API.APIresponse.Event.EventCommitteeNote;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.API.APIresponse.Event.EventEmergencies;
import com.gandsoft.openguide.API.APIresponse.Event.EventFeedback;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfo;
import com.gandsoft.openguide.API.APIresponse.Event.EventPlaceList;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDateDataList;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingArea;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.Gallery.GalleryResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Databases information
    private static final String DATABASE_NAME = "openguides.db";
    private static final int DATABASE_VERSION = 5;

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
    public static String Key_Schedule_List_linkvote = "linkvote";
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

    public static String TableFeedback = "tabFeedback";
    public static String Key_Feedback_No = "number";
    public static String Key_Feedback_EventId = "eventId";
    public static String Key_Feedback_Judul = "judul";
    public static String Key_Feedback_SubJudul = "subJudul";
    public static String Key_Feedback_InputType = "inputType";
    public static String Key_Feedback_Name = "name";
    public static String Key_Feedback_SubName = "subName";
    public static String Key_Feedback_Label = "label";
    public static String Key_Feedback_PlaceHolder = "placeHolder";

    public static String TableEmergencie = "tabEmergencie";
    public static String Key_Emergencie_No = "number";
    public static String Key_Emergencie_EventId = "eventId";
    public static String Key_Emergencie_Icon = "icon";
    public static String Key_Emergencie_Title = "title";
    public static String Key_Emergencie_Keterangan = "keterangan";

    public static String TableArea = "tabArea";
    public static String Key_Area_No = "number";
    public static String Key_Area_EventId = "eventId";
    public static String Key_Area_Title = "title";
    public static String Key_Area_Description = "description";

    public static String TableCommiteNote = "tabCommiteNote";
    public static String Key_CommiteNote_No = "number";
    public static String Key_CommiteNote_EventId = "eventId";
    public static String Key_CommiteNote_Id = "id";
    public static String Key_CommiteNote_Icon = "icon";
    public static String Key_CommiteNote_Title = "title";
    public static String Key_CommiteNote_Note = "note";
    public static String Key_CommiteNote_Tanggal = "tanggal";
    public static String Key_CommiteNote_Has_been_opened = "has_been_opened";
    public static String Key_CommiteNote_Sort_inbox = "sort_inbox";

    public static String TableGallery = "tabGallery";
    public static String Key_Gallery_No = "number";
    public static String Key_Gallery_eventId = "eventId";
    public static String Key_Gallery_galleryId = "galleryId";
    public static String Key_Gallery_Like = "like";
    public static String Key_Gallery_accountId = "accountId";
    public static String Key_Gallery_totalComment = "totalComment";
    public static String Key_Gallery_statusLike = "statusLike";
    public static String Key_Gallery_Username = "username";
    public static String Key_Gallery_Caption = "caption";
    public static String Key_Gallery_imagePosted = "imagePosted";
    public static String Key_Gallery_imageIcon = "imageIcon";
    public static String Key_Gallery_imagePostedLocal = "imagePostedLocal";
    public static String Key_Gallery_imageIconLocal = "imageIconLocal";


    public static String TableHomeContent = "tabHomeContent";
    public static String Key_HomeContent_No = "number";
    public static String Key_HomeContent_EventId = "eventId";
    public static String Key_HomeContent_id = "id";
    public static String Key_HomeContent_like = "like";
    public static String Key_HomeContent_account_id = "account_id";
    public static String Key_HomeContent_total_comment = "total_comment";
    public static String Key_HomeContent_status_like = "status_like";
    public static String Key_HomeContent_username = "username";
    public static String Key_HomeContent_jabatan = "jabatan";
    public static String Key_HomeContent_date_created = "date_created";
    public static String Key_HomeContent_image_icon = "image_icon";
    public static String Key_HomeContent_image_icon_local = "image_icon_local";
    public static String Key_HomeContent_image_posted = "image_posted";
    public static String Key_HomeContent_image_posted_local = "image_posted_local";
    public static String Key_HomeContent_keterangan = "keterangan";
    public static String Key_HomeContent_event = "event";

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
            + KEY_Contact_List_Telephone + " INTEGER, "
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
            + Key_Schedule_List_linkvote + " TEXT, "
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

    private static final String query_add_table_Feedback = "CREATE TABLE IF NOT EXISTS " + TableFeedback + "("
            + Key_Feedback_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Feedback_EventId + " TEXT, "
            + Key_Feedback_Judul + " TEXT, "
            + Key_Feedback_SubJudul + " TEXT, "
            + Key_Feedback_InputType + " TEXT, "
            + Key_Feedback_Name + " TEXT, "
            + Key_Feedback_SubName + " TEXT, "
            + Key_Feedback_Label + " TEXT, "
            + Key_Feedback_PlaceHolder + " TEXT) ";
    private static final String query_delete_table_Feedback = "DROP TABLE IF EXISTS " + TableFeedback;

    private static final String query_add_table_Emergencie = "CREATE TABLE IF NOT EXISTS " + TableEmergencie + "("
            + Key_Emergencie_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Emergencie_EventId + " TEXT, "
            + Key_Emergencie_Icon + " TEXT, "
            + Key_Emergencie_Title + " TEXT, "
            + Key_Emergencie_Keterangan + " TEXT) ";
    private static final String query_delete_table_Emergencie = "DROP TABLE IF EXISTS " + TableEmergencie;

    private static final String query_add_table_Area = "CREATE TABLE IF NOT EXISTS " + TableArea + "("
            + Key_Area_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Area_EventId + " TEXT, "
            + Key_Area_Title + " TEXT, "
            + Key_Area_Description + " TEXT) ";
    private static final String query_delete_table_Area = "DROP TABLE IF EXISTS " + TableArea;

    private static final String query_add_table_CommiteNote = "CREATE TABLE IF NOT EXISTS " + TableCommiteNote + "("
            + Key_CommiteNote_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_CommiteNote_EventId + " TEXT, "
            + Key_CommiteNote_Id + " INTEGER, "
            + Key_CommiteNote_Icon + " TEXT, "
            + Key_CommiteNote_Title + " TEXT, "
            + Key_CommiteNote_Note + " TEXT, "
            + Key_CommiteNote_Tanggal + " TEXT, "
            + Key_CommiteNote_Has_been_opened + " BOOLEAN, "
            + Key_CommiteNote_Sort_inbox + " INTEGER) ";
    private static final String query_delete_table_CommiteNote = "DROP TABLE IF EXISTS " + TableCommiteNote;

    private static final String query_add_table_Gallery = "CREATE TABLE IF NOT EXISTS " + TableGallery + "("
            + Key_Gallery_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Gallery_eventId + " TEXT, "
            + Key_Gallery_galleryId + " TEXT, "
            + Key_Gallery_Like + " TEXT, "
            + Key_Gallery_accountId + " INTEGER, "
            + Key_Gallery_totalComment + " TEXT, "
            + Key_Gallery_statusLike + " TEXT, "
            + Key_Gallery_Username + " TEXT, "
            + Key_Gallery_Caption + " TEXT, "
            + Key_Gallery_imagePosted + " TEXT, "
            + Key_Gallery_imageIcon + " TEXT, "
            + Key_Gallery_imagePostedLocal + " TEXT, "
            + Key_Gallery_imageIconLocal + " TEXT) ";
    private static final String query_delete_table_Gallery = "DROP TABLE IF EXISTS " + TableGallery;

    private static final String query_add_table_HomeContent = "CREATE TABLE IF NOT EXISTS " + TableHomeContent + "("
            + Key_HomeContent_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_HomeContent_EventId + " TEXT, "
            + Key_HomeContent_id + " TEXT, "
            + Key_HomeContent_like + " INTEGER, "
            + Key_HomeContent_account_id + " TEXT, "
            + Key_HomeContent_total_comment + " INTEGER, "
            + Key_HomeContent_status_like + " INTEGER, "
            + Key_HomeContent_username + " TEXT, "
            + Key_HomeContent_jabatan + " TEXT, "
            + Key_HomeContent_date_created + " TEXT, "
            + Key_HomeContent_image_icon + " TEXT, "
            + Key_HomeContent_image_icon_local + " TEXT, "
            + Key_HomeContent_image_posted + " TEXT, "
            + Key_HomeContent_image_posted_local + " TEXT, "
            + Key_HomeContent_keterangan + " TEXT, "
            + Key_HomeContent_event + " TEXT) ";
    private static final String query_delete_table_HomeContent = "DROP TABLE IF EXISTS " + TableHomeContent;

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
        db.execSQL(query_add_table_Feedback);
        db.execSQL(query_add_table_Emergencie);
        db.execSQL(query_add_table_Area);
        db.execSQL(query_add_table_CommiteNote);
        db.execSQL(query_add_table_Gallery);
        db.execSQL(query_add_table_HomeContent);
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
        db.execSQL(query_delete_table_Feedback);
        db.execSQL(query_delete_table_Emergencie);
        db.execSQL(query_delete_table_Area);
        db.execSQL(query_delete_table_CommiteNote);
        db.execSQL(query_delete_table_Gallery);
        db.execSQL(query_delete_table_HomeContent);

        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
        db.execSQL(query_delete_table_Feedback);
        db.execSQL(query_delete_table_Emergencie);
        db.execSQL(query_delete_table_Area);
        db.execSQL(query_delete_table_CommiteNote);
        db.execSQL(query_delete_table_Gallery);
        db.execSQL(query_delete_table_HomeContent);

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
        db.execSQL(query_add_table_Feedback);
        db.execSQL(query_add_table_Emergencie);
        db.execSQL(query_add_table_Area);
        db.execSQL(query_add_table_CommiteNote);
        db.execSQL(query_add_table_Gallery);
        db.execSQL(query_add_table_HomeContent);
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
        contentValues.put(KEY_ListEvent_background, model.getBackground());
        //contentValues.put(KEY_ListEvent_background_local, model.getBackground());
        contentValues.put(KEY_ListEvent_groupCode, model.getGroup_code());
        contentValues.put(KEY_ListEvent_roleName, model.getRole_name());
        contentValues.put(KEY_ListEvent_date, model.getDate());
        contentValues.put(KEY_ListEvent_IsFirstIn, false);
        db.insert(TableListEvent, null, contentValues);
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
        db.insert(TableUserData, null, contentValues);
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

    public void saveTheEvent(EventTheEvent model, String eventId, String feedback_data, String version_data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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
        contentValues.put(Key_The_Event_addpost_status, model.getAddpost_status());
        long l = db.insert(TableTheEvent, null, contentValues);
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
        contentValues.put(Key_Schedule_List_linkvote, model.getLinkvote());
        contentValues.put(Key_Schedule_List_status, model.getStatus());
        db.insert(TableScheduleList, null, contentValues);
        db.close();
    }

    public void saveImportanInfo(EventImportanInfo model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Importan_Info_EventId, eventId);
        contentValues.put(Key_Importan_Info_title, model.getTitle());
        contentValues.put(Key_Importan_Info_info, model.getInfo());
        db.insert(TableImportantInfo, null, contentValues);
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
        db.insert(TableContactList, null, contentValues);
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
        db.insert(TableEventAbout, null, contentValues);
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
        db.insert(TablePlaceList, null, contentValues);
        db.close();
    }

    public void saveEmergency(EventEmergencies model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Emergencie_EventId, eventId);
        contentValues.put(Key_Emergencie_Icon, model.getIcon());
        contentValues.put(Key_Emergencie_Title, model.getTitle());
        contentValues.put(Key_Emergencie_Keterangan, model.getKeterangan());
        db.insert(TableEmergencie, null, contentValues);
        db.close();
    }

    public void saveArea(EventSurroundingArea model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Area_EventId, eventId);
        contentValues.put(Key_Area_Title, model.getTitle());
        contentValues.put(Key_Area_Description, model.getDescription());
        db.insert(TableArea, null, contentValues);
        db.close();
    }

    public void saveCommiteNote(EventCommitteeNote model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_CommiteNote_EventId, eventId);
        contentValues.put(Key_CommiteNote_Id, model.getId());
        contentValues.put(Key_CommiteNote_Icon, model.getIcon());
        contentValues.put(Key_CommiteNote_Title, model.getTitle());
        contentValues.put(Key_CommiteNote_Note, model.getNote());
        contentValues.put(Key_CommiteNote_Tanggal, model.getTanggal());
        contentValues.put(Key_CommiteNote_Has_been_opened, model.getHas_been_opened());
        contentValues.put(Key_CommiteNote_Sort_inbox, model.getSort_inbox());
        db.insert(TableCommiteNote, null, contentValues);
        db.close();
    }

    public void saveFeedback(EventFeedback model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Feedback_EventId, eventId);
        contentValues.put(Key_Feedback_Judul, model.getJudul());
        contentValues.put(Key_Feedback_SubJudul, model.getSubJudul());
        contentValues.put(Key_Feedback_InputType, model.getInputType());
        contentValues.put(Key_Feedback_Name, model.getName());
        contentValues.put(Key_Feedback_SubName, model.getSubName());
        contentValues.put(Key_Feedback_Label, model.getLabel());
        contentValues.put(Key_Feedback_PlaceHolder, model.getPlaceholder());
        db.insert(TableFeedback, null, contentValues);
        db.close();
    }

    public void saveGallery(GalleryResponseModel model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Gallery_eventId, eventId);
        contentValues.put(Key_Gallery_galleryId, model.getId());
        contentValues.put(Key_Gallery_Like, model.getLike());
        contentValues.put(Key_Gallery_accountId, model.getAccount_id());
        contentValues.put(Key_Gallery_totalComment, model.getTotal_comment());
        contentValues.put(Key_Gallery_statusLike, model.getStatus_like());
        contentValues.put(Key_Gallery_Username, model.getUsername());
        contentValues.put(Key_Gallery_Caption, model.getCaption());
        contentValues.put(Key_Gallery_imagePosted, model.getImage_posted());
        contentValues.put(Key_Gallery_imageIcon, model.getImage_icon());
        contentValues.put(Key_Gallery_imagePostedLocal, model.getImage_postedLocal());
        contentValues.put(Key_Gallery_imageIconLocal, model.getImage_iconLocal());
        db.insert(TableGallery, null, contentValues);
        db.close();
    }

    public void saveHomeContent(HomeContentResponseModel model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_HomeContent_EventId, eventId);
        contentValues.put(Key_HomeContent_id, model.getId());
        contentValues.put(Key_HomeContent_like, model.getLike());
        contentValues.put(Key_HomeContent_account_id, model.getAccount_id());
        contentValues.put(Key_HomeContent_total_comment, model.getTotal_comment());
        contentValues.put(Key_HomeContent_status_like, model.getStatus_like());
        contentValues.put(Key_HomeContent_username, model.getUsername());
        contentValues.put(Key_HomeContent_jabatan, model.getJabatan());
        contentValues.put(Key_HomeContent_date_created, model.getDate_created());
        contentValues.put(Key_HomeContent_image_icon, model.getImage_icon());
        //contentValues.put(Key_HomeContent_image_icon_local, model.getImage_icon_local());
        contentValues.put(Key_HomeContent_image_posted, model.getImage_posted());
        //contentValues.put(Key_HomeContent_image_posted_local, model.getSubJudul());
        contentValues.put(Key_HomeContent_keterangan, model.getKeterangan());
        contentValues.put(Key_HomeContent_event, model.getEvent());
        db.insert(TableHomeContent, null, contentValues);
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
        db.update(TableUserData, contentValues, KEY_UserData_accountId + " = ? ", new String[]{accountId});
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
        contentValues.put(KEY_ListEvent_background, model.getBackground());
        //contentValues.put(KEY_ListEvent_background_local, model.getBackground());
        contentValues.put(KEY_ListEvent_groupCode, model.getGroup_code());
        contentValues.put(KEY_ListEvent_roleName, model.getRole_name());
        contentValues.put(KEY_ListEvent_date, model.getDate());
        contentValues.put(KEY_ListEvent_IsFirstIn, false);
        int i = db.update(TableListEvent, contentValues, KEY_ListEvent_eventId + " = ? ", new String[]{model.getEvent_id()});
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
        db.update(TableWallet, contentValues, KEY_Wallet_sort + " = ? AND " + KEY_Wallet_eventId + " = ? ", new String[]{model.getSort(), eventId});
        db.close();
    }

    public void updateTheEvent(EventTheEvent model, String eventId, String feedback_data, String version_data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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
        contentValues.put(Key_The_Event_addpost_status, model.getAddpost_status());
        db.update(TableTheEvent, contentValues, Key_The_Event_EventId + " = ? ", new String[]{eventId});
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
        contentValues.put(Key_Schedule_List_linkvote, model.getLinkvote());
        contentValues.put(Key_Schedule_List_status, model.getStatus());
        db.update(TableScheduleList, contentValues, Key_Schedule_List_EventId + " = ? ", new String[]{eventId});
        db.close();
    }

    public void updateImportanInfo(EventImportanInfo model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Importan_Info_title, model.getTitle());
        contentValues.put(Key_Importan_Info_info, model.getInfo());
        db.update(TableImportantInfo, contentValues, Key_Importan_Info_EventId + " = ? ", new String[]{eventId});
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
        db.update(TableContactList, contentValues, Key_Contact_List_EventId + " = ? ", new String[]{eventId});
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
        db.update(TableEventAbout, contentValues, Key_Event_About_EventId + " = ? ", new String[]{eventId});
        db.close();
    }

    public void updatePlaceList(EventPlaceList model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Place_List_latitude, model.getLatitude());
        contentValues.put(Key_Place_List_longitude, model.getLongitude());
        contentValues.put(Key_Place_List_title, model.getTitle());
        contentValues.put(Key_Place_List_icon, model.getIcon());
        db.update(TablePlaceList, contentValues, Key_Place_List_EventId + " = ?", new String[]{eventId});
        db.close();
    }

    public void updateEmergency(EventEmergencies model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Emergencie_EventId, eventId);
        contentValues.put(Key_Emergencie_Icon, model.getIcon());
        contentValues.put(Key_Emergencie_Title, model.getTitle());
        contentValues.put(Key_Emergencie_Keterangan, model.getKeterangan());
        db.update(TableEmergencie, contentValues, Key_Emergencie_EventId + " = ? ", new String[]{eventId});
        db.close();
    }

    public void updateArea(EventSurroundingArea model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Area_EventId, eventId);
        contentValues.put(Key_Area_Title, model.getTitle());
        contentValues.put(Key_Area_Description, model.getDescription());
        db.update(TableArea, contentValues, Key_Area_EventId + " = ? ", new String[]{eventId});
        db.close();
    }

    public void updateCommiteNote(EventCommitteeNote model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_CommiteNote_Id, model.getId());
        contentValues.put(Key_CommiteNote_Icon, model.getIcon());
        contentValues.put(Key_CommiteNote_Title, model.getTitle());
        contentValues.put(Key_CommiteNote_Note, model.getNote());
        contentValues.put(Key_CommiteNote_Tanggal, model.getTanggal());
        contentValues.put(Key_CommiteNote_Has_been_opened, model.getHas_been_opened());
        contentValues.put(Key_CommiteNote_Sort_inbox, model.getSort_inbox());
        db.update(TableCommiteNote, contentValues, Key_CommiteNote_EventId + " = ?", new String[]{eventId});
        db.close();
    }

    public void updateFeedback(EventFeedback model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Feedback_Judul, model.getJudul());
        contentValues.put(Key_Feedback_SubJudul, model.getSubJudul());
        contentValues.put(Key_Feedback_InputType, model.getInputType());
        contentValues.put(Key_Feedback_Name, model.getName());
        contentValues.put(Key_Feedback_SubName, model.getSubName());
        contentValues.put(Key_Feedback_Label, model.getLabel());
        contentValues.put(Key_Feedback_PlaceHolder, model.getPlaceholder());
        db.update(TableFeedback, contentValues, Key_Feedback_EventId + " = ?", new String[]{eventId});
        db.close();
    }

    public void updateHomeContent(HomeContentResponseModel model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_HomeContent_id, model.getId());
        contentValues.put(Key_HomeContent_like, model.getLike());
        contentValues.put(Key_HomeContent_account_id, model.getAccount_id());
        contentValues.put(Key_HomeContent_total_comment, model.getTotal_comment());
        contentValues.put(Key_HomeContent_status_like, model.getStatus_like());
        contentValues.put(Key_HomeContent_username, model.getUsername());
        contentValues.put(Key_HomeContent_jabatan, model.getJabatan());
        contentValues.put(Key_HomeContent_date_created, model.getDate_created());
        contentValues.put(Key_HomeContent_image_icon, model.getImage_icon());
        //contentValues.put(Key_HomeContent_image_icon_local, model.getImage_icon_local());
        contentValues.put(Key_HomeContent_image_posted, model.getImage_posted());
        //contentValues.put(Key_HomeContent_image_posted_local, model.getSubJudul());
        contentValues.put(Key_HomeContent_keterangan, model.getKeterangan());
        contentValues.put(Key_HomeContent_event, model.getEvent());
        db.update(TableHomeContent, contentValues, Key_HomeContent_EventId + " = ?", new String[]{eventId});
        db.close();
    }

    public void updateGallery(GalleryResponseModel model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Gallery_eventId, eventId);
        contentValues.put(Key_Gallery_galleryId, model.getId());
        contentValues.put(Key_Gallery_Like, model.getLike());
        contentValues.put(Key_Gallery_accountId, model.getAccount_id());
        contentValues.put(Key_Gallery_totalComment, model.getTotal_comment());
        contentValues.put(Key_Gallery_statusLike, model.getStatus_like());
        contentValues.put(Key_Gallery_Username, model.getUsername());
        contentValues.put(Key_Gallery_Caption, model.getCaption());
        contentValues.put(Key_Gallery_imagePosted, model.getImage_posted());
        contentValues.put(Key_Gallery_imageIcon, model.getImage_icon());
        contentValues.put(Key_Gallery_imagePostedLocal, model.getImage_postedLocal());
        contentValues.put(Key_Gallery_imageIconLocal, model.getImage_iconLocal());
        db.update(TableGallery, contentValues, Key_Gallery_eventId + " = ?", new String[]{eventId});
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

    public UserDataResponseModel getOneUserData(String accountid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableUserData, null, KEY_UserData_accountId + " = ? ", new String[]{accountid}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        UserDataResponseModel model = new UserDataResponseModel();
        model.setNumber(cursor.getInt(cursor.getColumnIndex(KEY_UserData_No)));
        model.setAccount_id(cursor.getString(cursor.getColumnIndex(KEY_UserData_accountId)));
        model.setImage_url(cursor.getString(cursor.getColumnIndex(KEY_UserData_imageUrl)));
        model.setFull_name(cursor.getString(cursor.getColumnIndex(KEY_UserData_fullName)));
        model.setPosition(cursor.getString(cursor.getColumnIndex(KEY_UserData_position)));
        model.setPhone_number(cursor.getString(cursor.getColumnIndex(KEY_UserData_phoneNumber)));
        model.setEmail(cursor.getString(cursor.getColumnIndex(KEY_UserData_email)));
        model.setGender(cursor.getString(cursor.getColumnIndex(KEY_UserData_gender)));
        model.setBirthday(cursor.getString(cursor.getColumnIndex(KEY_UserData_birthday)));
        model.setCheckin(cursor.getString(cursor.getColumnIndex(KEY_UserData_checkin)));
        model.setPrivacy_policy(cursor.getString(cursor.getColumnIndex(KEY_UserData_privacyPolicy)));
        model.setVersion_data(cursor.getString(cursor.getColumnIndex(KEY_UserData_versionData)));
        model.setGroup_code(cursor.getString(cursor.getColumnIndex(KEY_UserData_groupCode)));
        model.setRole_name(cursor.getString(cursor.getColumnIndex(KEY_UserData_roleName)));
        model.setImage_url_local(cursor.getString(cursor.getColumnIndex(KEY_UserData_imageUrl_local)));
        // return model
        return model;
    }

    public ArrayList<UserListEventResponseModel> getAllListEvent(String accountid) {
        ArrayList<UserListEventResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableListEvent, null, KEY_ListEvent_accountId + " = ? ", new String[]{accountid}, null, null, KEY_ListEvent_startDate + " DESC");

        if (cursor.moveToFirst()) {
            do {
                UserListEventResponseModel model = new UserListEventResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(KEY_ListEvent_No)));
                model.setEvent_id(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_eventId)));
                model.setAccountId(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_accountId)));
                model.setStart_date(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_startDate)));
                model.setLogo(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_logo)));
                model.setLogo_local(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_logo_local)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_title)));
                model.setGroup_company(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_groupCompany)));
                model.setStatus(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_status)));
                model.setBackground(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_background)));
                model.setBackground_local(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_background_local)));
                model.setGroup_code(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_groupCode)));
                model.setRole_name(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_roleName)));
                model.setDate(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_date)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        return modelList;
    }

    public UserListEventResponseModel getOneListEvent(String eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableListEvent, null, KEY_ListEvent_eventId + " = ? ", new String[]{eventId}, null, null, KEY_ListEvent_startDate + " DESC");
        if (cursor != null)
            cursor.moveToFirst();

        UserListEventResponseModel model = new UserListEventResponseModel();
        model.setNumber(cursor.getInt(cursor.getColumnIndex(KEY_ListEvent_No)));
        model.setEvent_id(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_eventId)));
        model.setAccountId(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_accountId)));
        model.setStart_date(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_startDate)));
        model.setLogo(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_logo)));
        model.setLogo_local(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_logo_local)));
        model.setTitle(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_title)));
        model.setGroup_company(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_groupCompany)));
        model.setStatus(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_status)));
        model.setBackground(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_background)));
        model.setBackground_local(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_background_local)));
        model.setGroup_code(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_groupCode)));
        model.setRole_name(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_roleName)));
        model.setDate(cursor.getString(cursor.getColumnIndex(KEY_ListEvent_date)));
        // return model
        return model;
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

    public UserWalletDataResponseModel getWalletDataIdCard(String eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableWallet, null, KEY_Wallet_eventId + " = ? AND " + KEY_Wallet_type + " = 'idcard' ", new String[]{String.valueOf(eventId)}, KEY_Wallet_eventId, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        UserWalletDataResponseModel model = new UserWalletDataResponseModel();
        model.setNumber(cursor.getInt(cursor.getColumnIndex(KEY_Wallet_No)));
        model.setSort(cursor.getString(cursor.getColumnIndex(KEY_Wallet_sort)));
        model.setType(cursor.getString(cursor.getColumnIndex(KEY_Wallet_type)));
        model.setBody_wallet(cursor.getString(cursor.getColumnIndex(KEY_Wallet_bodyWallet)));
        model.setNotif(cursor.getString(cursor.getColumnIndex(KEY_Wallet_notif)));
        model.setDetail(cursor.getString(cursor.getColumnIndex(KEY_Wallet_detail)));
        // return model
        return model;
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
        model.setFeedback(cursor.getString(cursor.getColumnIndex(Key_The_Event_feedback)));
        model.setDate_event(cursor.getString(cursor.getColumnIndex(Key_The_Event_date_event)));
        model.setWeather(cursor.getString(cursor.getColumnIndex(Key_The_Event_weather)));
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
        Cursor cursor = db.query(TableScheduleList, null, Key_Schedule_List_EventId + " = ? ", new String[]{eventId}, Key_Schedule_List_id, null, null);

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
                model.setLinkvote(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_linkvote)));
                model.setStatus(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_status)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventScheduleListDateDataList> getScheduleListPerDate(String eventId, String date) {
        ArrayList<EventScheduleListDateDataList> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableScheduleList, null, Key_Schedule_List_EventId + " = ? AND " + Key_Schedule_List_date + " = ? ", new String[]{eventId, date}, Key_Schedule_List_id, null, Key_Schedule_List_id);

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
                model.setDetail(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_detail)));
                model.setAction(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_action)));
                model.setLink(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_link)));
                model.setLinkvote(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_linkvote)));
                model.setStatus(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_status)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<String> getScheduleListDate(String eventId) {
        ArrayList<String> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableScheduleList, new String[]{Key_Schedule_List_date}, Key_Schedule_List_EventId + " = ? ", new String[]{eventId}, Key_Schedule_List_date, null, Key_Schedule_List_No);

        if (cursor.moveToFirst()) {
            do {
                modelList.add(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_date)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventImportanInfo> getImportanInfo(String eventId) {
        ArrayList<EventImportanInfo> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableImportantInfo, null, Key_Importan_Info_EventId + " = ? ", new String[]{eventId}, Key_Importan_Info_title, null, null);

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
        Cursor cursor = db.query(TableContactList, null, Key_Contact_List_EventId + " = ? ", new String[]{eventId}, KEY_Contact_List_Telephone, null, Key_Contact_List_No);

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

    public ArrayList<EventEmergencies> getEmergencie(String eventId) {
        ArrayList<EventEmergencies> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableEmergencie, null, Key_Emergencie_EventId + " = ? ", new String[]{eventId}, Key_Emergencie_Title, null, Key_Emergencie_No);

        if (cursor.moveToFirst()) {
            do {
                EventEmergencies model = new EventEmergencies();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Emergencie_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Emergencie_EventId)));
                model.setIcon(cursor.getString(cursor.getColumnIndex(Key_Emergencie_Icon)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(Key_Emergencie_Title)));
                model.setKeterangan(cursor.getString(cursor.getColumnIndex(Key_Emergencie_Keterangan)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventSurroundingArea> getSurroundingArea(String eventId) {
        ArrayList<EventSurroundingArea> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableArea, null, Key_Area_EventId + " = ? ", new String[]{eventId}, Key_Area_Title, null, Key_Area_No);

        if (cursor.moveToFirst()) {
            do {
                EventSurroundingArea model = new EventSurroundingArea();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Area_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Area_EventId)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(Key_Area_Title)));
                model.setDescription(cursor.getString(cursor.getColumnIndex(Key_Area_Description)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventCommitteeNote> getCommiteNote(String eventId) {
        ArrayList<EventCommitteeNote> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableCommiteNote, null, Key_CommiteNote_EventId + " = ? ", new String[]{eventId}, Key_CommiteNote_Title, null, Key_CommiteNote_No);

        if (cursor.moveToFirst()) {
            do {
                EventCommitteeNote model = new EventCommitteeNote();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_CommiteNote_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_CommiteNote_EventId)));
                model.setId(cursor.getString(cursor.getColumnIndex(Key_CommiteNote_Id)));
                model.setIcon(cursor.getString(cursor.getColumnIndex(Key_CommiteNote_Icon)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(Key_CommiteNote_Title)));
                model.setNote(cursor.getString(cursor.getColumnIndex(Key_CommiteNote_Note)));
                model.setTanggal(cursor.getString(cursor.getColumnIndex(Key_CommiteNote_Tanggal)));
                model.setHas_been_opened(cursor.getString(cursor.getColumnIndex(Key_CommiteNote_Has_been_opened)));
                model.setSort_inbox(cursor.getString(cursor.getColumnIndex(Key_CommiteNote_Sort_inbox)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventFeedback> getFeedback(String eventId) {
        ArrayList<EventFeedback> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableFeedback, null, Key_Feedback_EventId + " = ? ", new String[]{eventId}, null, null, Key_Feedback_No);

        if (cursor.moveToFirst()) {
            do {
                EventFeedback model = new EventFeedback();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Feedback_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Feedback_EventId)));
                model.setJudul(cursor.getString(cursor.getColumnIndex(Key_Feedback_Judul)));
                model.setSubJudul(cursor.getString(cursor.getColumnIndex(Key_Feedback_SubJudul)));
                model.setInputType(cursor.getString(cursor.getColumnIndex(Key_Feedback_InputType)));
                model.setName(cursor.getString(cursor.getColumnIndex(Key_Feedback_Name)));
                model.setSubName(cursor.getString(cursor.getColumnIndex(Key_Feedback_SubName)));
                model.setLabel(cursor.getString(cursor.getColumnIndex(Key_Feedback_Label)));
                model.setPlaceholder(cursor.getString(cursor.getColumnIndex(Key_Feedback_PlaceHolder)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<GalleryResponseModel> getGallery(String eventId) {
        ArrayList<GalleryResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableGallery, null, Key_Gallery_eventId + " = ? ",
                new String[]{eventId}, Key_Gallery_galleryId, null, Key_Gallery_No);

        if (cursor.moveToFirst()) {
            do {
                GalleryResponseModel model = new GalleryResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Gallery_No)));
                model.setEvent_id(cursor.getString(cursor.getColumnIndex(Key_Gallery_eventId)));
                model.setId(cursor.getString(cursor.getColumnIndex(Key_Gallery_galleryId)));
                model.setLike(cursor.getString(cursor.getColumnIndex(Key_Gallery_Like)));
                model.setAccount_id(cursor.getString(cursor.getColumnIndex(Key_Gallery_accountId)));
                model.setTotal_comment(cursor.getString(cursor.getColumnIndex(Key_Gallery_totalComment)));
                model.setStatus_like(cursor.getString(cursor.getColumnIndex(Key_Gallery_statusLike)));
                model.setUsername(cursor.getString(cursor.getColumnIndex(Key_Gallery_Username)));
                model.setCaption(cursor.getString(cursor.getColumnIndex(Key_Gallery_Caption)));
                model.setImage_posted(cursor.getString(cursor.getColumnIndex(Key_Gallery_imagePosted)));
                model.setImage_icon(cursor.getString(cursor.getColumnIndex(Key_Gallery_imageIcon)));
                model.setImage_postedLocal(cursor.getString(cursor.getColumnIndex(Key_Gallery_imagePostedLocal)));
                model.setImage_iconLocal(cursor.getString(cursor.getColumnIndex(Key_Gallery_imageIconLocal)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<HomeContentResponseModel> getHomeContent(String eventId) {
        ArrayList<HomeContentResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableHomeContent, null, Key_HomeContent_EventId + " = ? ", new String[]{eventId}, Key_HomeContent_id, null, Key_HomeContent_No);

        if (cursor.moveToFirst()) {
            do {
                HomeContentResponseModel model = new HomeContentResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_HomeContent_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_HomeContent_EventId)));
                model.setId(cursor.getString(cursor.getColumnIndex(Key_HomeContent_id)));
                model.setLike(cursor.getString(cursor.getColumnIndex(Key_HomeContent_like)));
                model.setAccount_id(cursor.getString(cursor.getColumnIndex(Key_HomeContent_account_id)));
                model.setTotal_comment(cursor.getString(cursor.getColumnIndex(Key_HomeContent_total_comment)));
                model.setStatus_like(cursor.getInt(cursor.getColumnIndex(Key_HomeContent_status_like)));
                model.setUsername(cursor.getString(cursor.getColumnIndex(Key_HomeContent_username)));
                model.setJabatan(cursor.getString(cursor.getColumnIndex(Key_HomeContent_jabatan)));
                model.setDate_created(cursor.getString(cursor.getColumnIndex(Key_HomeContent_date_created)));
                model.setImage_icon(cursor.getString(cursor.getColumnIndex(Key_HomeContent_image_icon)));
                model.setImage_icon_local(cursor.getString(cursor.getColumnIndex(Key_HomeContent_image_icon_local)));
                model.setImage_posted(cursor.getString(cursor.getColumnIndex(Key_HomeContent_image_posted)));
                model.setImage_posted_local(cursor.getString(cursor.getColumnIndex(Key_HomeContent_image_posted_local)));
                model.setKeterangan(cursor.getString(cursor.getColumnIndex(Key_HomeContent_keterangan)));
                model.setEvent(cursor.getString(cursor.getColumnIndex(Key_HomeContent_event)));
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
        Cursor cursor = db.query(table, null, key + " = ? AND " + key2 + " = ? ", new String[]{value, value2}, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean isDataTableValueMultipleNull2(String table, String key, String key2, String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String selectQuery = "SELECT * FROM " + tableName + " WHERE " + targetKey + " = '" + targetValue + "'";
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(table, null, key + " = ? AND " + key2 + " IS NULL ", new String[]{value}, null, null, null);
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
        db.insert(table, null, contentValues);
        db.close();
    }

    public void updateOneKey(String table, String keyWhere, String valueWhere, String keyParam, String valueParam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(keyParam, valueParam);
        db.update(table, contentValues, keyWhere + " = ? ", new String[]{valueWhere});
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

    public String getFeedbackString(String eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableTheEvent, null, Key_The_Event_EventId + " = ? ", new String[]{eventId}, null, null, null);

        cursor.close();
        return cursor.getString(cursor.getColumnIndex(Key_The_Event_feedback));
        /*if (cursor.getCount() == 1) {
            return (cursor.getString(cursor.getColumnIndex(Key_The_Event_feedback)));
        } else {
            cursor.close();
            return "";
        }*/
    }

    public boolean isFirstIn(String eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
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