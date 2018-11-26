package com.gandsoft.openguide.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gandsoft.openguide.support.SessionUtil;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Databases information
    public static final String DB_NM = "openguides.db";
    public static final int DB_VER = 11;

    public static String TableGlobalData = "tabGlobalData";
    private static final String query_delete_table_GlobalData = "DROP TABLE IF EXISTS " + TableGlobalData;
    public static String KEY_GlobalData_dbver = "dbver";
    public static String KEY_GlobalData_eventId = "event_Id";
    public static String KEY_GlobalData_accountId = "accountId";
    public static String KEY_GlobalData_version_data_user = "version_data_user";
    public static String KEY_GlobalData_version_data_event = "version_data_event";
    private static final String query_add_table_GlobalData = "CREATE TABLE IF NOT EXISTS " + TableGlobalData + "("
            + KEY_GlobalData_dbver + " TEXT PRIMARY KEY , "
            + KEY_GlobalData_eventId + " TEXT, "
            + KEY_GlobalData_accountId + " TEXT, "
            + KEY_GlobalData_version_data_user + " TEXT, "
            + KEY_GlobalData_version_data_event + " TEXT) ";
    public static String TableNotif = "tabNotif";
    private static final String query_delete_table_Notif = "DROP TABLE IF EXISTS " + TableNotif;
    public static String Key_Notif_No = "number";
    public static String Key_Notif_eventId = "event_Id";
    public static String Key_Notif_galleryId = "galleryId";
    public static String Key_Notif_Like = "like";
    public static String Key_Notif_accountId = "accountId";
    public static String Key_Notif_totalComment = "totalComment";
    public static String Key_Notif_statusLike = "statusLike";
    public static String Key_Notif_Username = "username";
    public static String Key_Notif_Caption = "caption";
    public static String Key_Notif_imagePosted = "imagePosted";
    public static String Key_Notif_imageIcon = "imageIcon";
    public static String Key_Notif_imagePostedLocal = "imagePostedLocal";
    public static String Key_Notif_imageIconLocal = "imageIconLocal";
    private static final String query_add_table_Notif = "CREATE TABLE IF NOT EXISTS " + TableNotif + "("
            + Key_Notif_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Notif_eventId + " TEXT, "
            + Key_Notif_galleryId + " TEXT, "
            + Key_Notif_Like + " TEXT, "
            + Key_Notif_accountId + " INTEGER, "
            + Key_Notif_totalComment + " TEXT, "
            + Key_Notif_statusLike + " TEXT, "
            + Key_Notif_Username + " TEXT, "
            + Key_Notif_Caption + " TEXT, "
            + Key_Notif_imagePosted + " TEXT, "
            + Key_Notif_imageIcon + " TEXT, "
            + Key_Notif_imagePostedLocal + " TEXT, "
            + Key_Notif_imageIconLocal + " TEXT) ";
    /**/
    public static String TableListEvent = "tabListEvent";
    private static final String query_delete_table_ListEvent = "DROP TABLE IF EXISTS " + TableListEvent;
    public static String KEY_ListEvent_No = "number";
    public static String KEY_ListEvent_eventId = "event_Id";

    /*Version data*/
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
            + KEY_ListEvent_IsFirstIn + " INTEGER) ";
    /**/
    public static String TableUserData = "tabUserEvent";
    private static final String query_delete_table_UserData = "DROP TABLE IF EXISTS " + TableUserData;
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
    public static String KEY_UserData_isStillIn = "isStillIn";
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
            + KEY_UserData_fullName + " TEXT, "
            + KEY_UserData_isStillIn + " INTEGER) ";
    /**/
    public static String TableWallet = "tabWallet";
    private static final String query_delete_table_Wallet = "DROP TABLE IF EXISTS " + TableWallet;
    public static String KEY_Wallet_No = "number";
    public static String KEY_Wallet_eventId = "event_Id";
    public static String KEY_Wallet_accountId = "accountId";
    public static String KEY_Wallet_notif = "notif";
    public static String KEY_Wallet_detail = "detail";
    public static String KEY_Wallet_bodyWallet = "bodyWallet";
    public static String KEY_Wallet_sort = "sort";
    public static String KEY_Wallet_idCardLocal = "id_card_local";
    public static String KEY_Wallet_type = "type";
    private static final String query_add_table_Wallet = "CREATE TABLE IF NOT EXISTS " + TableWallet + "("
            + KEY_Wallet_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_Wallet_sort + " INTEGER, "
            + KEY_Wallet_accountId + " TEXT, "
            + KEY_Wallet_eventId + " TEXT, "
            + KEY_Wallet_bodyWallet + " BLOB, "
            + KEY_Wallet_notif + " TEXT, "
            + KEY_Wallet_detail + " TEXT, "
            + KEY_Wallet_idCardLocal + " TEXT, "
            + KEY_Wallet_type + " TEXT) ";
    /**/
    public static String TableTheEvent = "tabTheEvent";
    private static final String query_delete_table_TheEvent = "DROP TABLE IF EXISTS " + TableTheEvent;
    public static String Key_The_Event_No = "number";
    public static String Key_The_Event_EventId = "event_Id";
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
    /**/
    public static String TableScheduleList = "tabScheduleList";
    private static final String query_delete_table_ScheduleList = "DROP TABLE IF EXISTS " + TableScheduleList;
    public static String Key_Schedule_List_No = "number";
    public static String Key_Schedule_List_EventId = "event_Id";
    public static String Key_Schedule_List_GroupCode = "groupcode";
    public static String Key_Schedule_List_date = "date";
    public static String Key_Schedule_List_id = "id";
    public static String Key_Schedule_List_waktu = "waktu";
    public static String Key_Schedule_List_schedule_title = "schedule_title";
    public static String Key_Schedule_List_location = "location";
    public static String Key_Schedule_List_detail = "detail";
    public static String Key_Schedule_List_action = "action";
    public static String Key_Schedule_List_link = "link";
    public static String Key_Schedule_List_linkvote = "linkvote";
    public static String Key_Schedule_List_name = "name";
    public static String Key_Schedule_List_link_external = "link_external";
    public static String Key_Schedule_List_external = "external";
    public static String Key_Schedule_List_status = "status";
    private static final String query_add_table_ScheduleList = "CREATE TABLE IF NOT EXISTS " + TableScheduleList + "("
            + Key_Schedule_List_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Schedule_List_EventId + " TEXT, "
            + Key_Schedule_List_GroupCode + " TEXT, "
            + Key_Schedule_List_date + " TEXT, "
            + Key_Schedule_List_id + " INTEGER, "
            + Key_Schedule_List_waktu + " TEXT, "
            + Key_Schedule_List_schedule_title + " TEXT, "
            + Key_Schedule_List_location + " TEXT, "
            + Key_Schedule_List_detail + " TEXT, "
            + Key_Schedule_List_action + " TEXT, "
            + Key_Schedule_List_link + " TEXT, "
            + Key_Schedule_List_linkvote + " TEXT, "
            + Key_Schedule_List_name + " TEXT, "
            + Key_Schedule_List_link_external + " TEXT, "
            + Key_Schedule_List_external + " TEXT, "
            + Key_Schedule_List_status + " TEXT) ";
    /**/
    public static String TableImportantInfo = "tabImportantInfo";
    private static final String query_delete_table_ImportantInfo = "DROP TABLE IF EXISTS " + TableImportantInfo;
    public static String Key_Importan_Info_No = "number";
    public static String Key_Importan_Info_EventId = "event_Id";
    public static String Key_Importan_Info_title = "title";
    public static String Key_Importan_Info_info = "info";
    private static final String query_add_table_ImportantInfo = "CREATE TABLE IF NOT EXISTS " + TableImportantInfo + "("
            + Key_Importan_Info_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Importan_Info_EventId + " TEXT, "
            + Key_Importan_Info_title + " TEXT, "
            + Key_Importan_Info_info + " TEXT) ";
    /**/
    public static String TableImportantInfoNew = "tabImportantInfoNew";
    private static final String query_delete_table_ImportantInfoNew = "DROP TABLE IF EXISTS " + TableImportantInfoNew;
    public static String Key_Important_InfoNew_No = "number";
    public static String Key_Important_InfoNew_EventId = "event_Id";
    public static String Key_Important_InfoNew_title = "title";
    public static String Key_Important_InfoNew_Info = "Info";
    public static String Key_Important_InfoNew_Title_image = "title_image";
    public static String Key_Important_InfoNew_Name_image = "name_image";
    public static String Key_Important_InfoNew_Name_image_local = "name_image_local";
    public static String Key_Important_InfoNew_Detail_image = "detail_image";
    private static final String query_add_table_ImportantInfoNew = "CREATE TABLE IF NOT EXISTS " + TableImportantInfoNew + "("
            + Key_Important_InfoNew_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Important_InfoNew_EventId + " TEXT, "
            + Key_Important_InfoNew_title + " TEXT, "
            + Key_Important_InfoNew_Info + " TEXT, "
            + Key_Important_InfoNew_Title_image + " TEXT, "
            + Key_Important_InfoNew_Name_image + " TEXT, "
            + Key_Important_InfoNew_Name_image_local + " TEXT, "
            + Key_Important_InfoNew_Detail_image + " TEXT) ";
    /**/
    public static String TableArea = "tabArea";
    private static final String query_delete_table_Area = "DROP TABLE IF EXISTS " + TableArea;
    public static String Key_Area_No = "number";
    public static String Key_Area_EventId = "event_Id";
    public static String Key_Area_Title = "title";
    public static String Key_Area_Description = "description";
    private static final String query_add_table_Area = "CREATE TABLE IF NOT EXISTS " + TableArea + "("
            + Key_Area_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Area_EventId + " TEXT, "
            + Key_Area_Title + " TEXT, "
            + Key_Area_Description + " TEXT) ";
    /**/
    public static String TableAreaNew = "tabAreaNew";
    private static final String query_delete_table_AreaNew = "DROP TABLE IF EXISTS " + TableAreaNew;
    public static String Key_AreaNew_No = "number";
    public static String Key_AreaNew_EventId = "event_Id";
    public static String Key_AreaNew_Title = "title";
    public static String Key_AreaNew_Name_image = "name_image";
    public static String Key_AreaNew_Name_image_local = "name_image_local";
    public static String Key_AreaNew_Title_image = "title_image";
    public static String Key_AreaNew_Detail_image = "detail_image";
    public static String Key_AreaNew_Icon_walk = "icon_walk";
    public static String Key_AreaNew_Icon_car = "icon_car";
    public static String Key_AreaNew_Icon_direction = "icon_direction";
    public static String Key_AreaNew_Minute_walk = "minute_walk";
    public static String Key_AreaNew_Minute_car = "minute_car";
    public static String Key_AreaNew_Google_direction = "google_direction";
    private static final String query_add_table_AreaNew = "CREATE TABLE IF NOT EXISTS " + TableAreaNew + "("
            + Key_AreaNew_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_AreaNew_EventId + " TEXT, "
            + Key_AreaNew_Title + " TEXT, "
            + Key_AreaNew_Name_image + " TEXT, "
            + Key_AreaNew_Name_image_local + " TEXT, "
            + Key_AreaNew_Title_image + " TEXT, "
            + Key_AreaNew_Detail_image + " TEXT, "
            + Key_AreaNew_Icon_walk + " TEXT, "
            + Key_AreaNew_Icon_car + " TEXT, "
            + Key_AreaNew_Icon_direction + " TEXT, "
            + Key_AreaNew_Minute_walk + " TEXT, "
            + Key_AreaNew_Minute_car + " TEXT, "
            + Key_AreaNew_Google_direction + " TEXT) ";
    /**/
    public static String TableContactList = "tabContactList";
    private static final String query_delete_table_ContactList = "DROP TABLE IF EXISTS " + TableContactList;
    public static String Key_Contact_List_No = "number";
    public static String Key_Contact_List_EventId = "event_Id";
    public static String Key_Contact_List_Title = "title";
    public static String Key_Contact_List_Icon = "icon";
    public static String KEY_Contact_List_Telephone = "telephone";
    public static String KEY_Contact_List_Email = "email";
    public static String KEY_Contact_List_Name = "name";
    private static final String query_add_table_ContactList = "CREATE TABLE IF NOT EXISTS " + TableContactList + "("
            + Key_Contact_List_No + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Key_Contact_List_EventId + " TEXT, "
            + Key_Contact_List_Title + " TEXT, "
            + KEY_Contact_List_Name + " TEXT, "
            + KEY_Contact_List_Email + " TEXT, "
            + KEY_Contact_List_Telephone + " INTEGER, "
            + Key_Contact_List_Icon + " TEXT) ";
    /**/
    public static String TableEventAbout = "tabAbout";
    private static final String query_delete_table_EventAbout = "DROP TABLE IF EXISTS " + TableEventAbout;
    public static String Key_Event_About_No = "number";
    public static String Key_Event_About_EventId = "event_Id";
    public static String KEY_Event_About_Background = "background";
    public static String KEY_Event_About_Background_Local = "background_local";
    public static String KEY_Event_About_Logo = "logo";
    public static String KEY_Event_About_Logo_Local = "logo_local";
    public static String KEY_Event_About_Description = "description";
    private static final String query_add_table_EventAbout = "CREATE TABLE IF NOT EXISTS " + TableEventAbout + "("
            + Key_Event_About_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Event_About_EventId + " TEXT, "
            + KEY_Event_About_Background + " TEXT, "
            + KEY_Event_About_Background_Local + " TEXT, "
            + KEY_Event_About_Logo + " TEXT, "
            + KEY_Event_About_Logo_Local + " TEXT, "
            + KEY_Event_About_Description + " TEXT) ";
    /**/
    public static String TablePlaceList = "tabPlaceList";
    private static final String query_delete_table_PlaceList = "DROP TABLE IF EXISTS " + TablePlaceList;
    public static String Key_Place_List_No = "number";
    public static String Key_Place_List_EventId = "event_Id";
    public static String Key_Place_List_latitude = "latitude";
    public static String Key_Place_List_longitude = "longitude";
    public static String Key_Place_List_title = "title";
    public static String Key_Place_List_staticMap = "static_map";
    public static String Key_Place_List_icon = "icon";
    private static final String query_add_table_PlaceList = "CREATE TABLE IF NOT EXISTS " + TablePlaceList + "("
            + Key_Place_List_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Place_List_EventId + " TEXT, "
            + Key_Place_List_latitude + " TEXT, "
            + Key_Place_List_longitude + " TEXT, "
            + Key_Place_List_title + " TEXT, "
            + Key_Place_List_staticMap + " TEXT, "
            + Key_Place_List_icon + " TEXT) ";
    /**/
    public static String TableEmergencie = "tabEmergencie";
    private static final String query_delete_table_Emergencie = "DROP TABLE IF EXISTS " + TableEmergencie;
    public static String Key_Emergencie_No = "number";
    public static String Key_Emergencie_EventId = "event_Id";
    public static String Key_Emergencie_Icon = "icon";
    public static String Key_Emergencie_Title = "title";
    public static String Key_Emergencie_Keterangan = "keterangan";
    private static final String query_add_table_Emergencie = "CREATE TABLE IF NOT EXISTS " + TableEmergencie + "("
            + Key_Emergencie_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Emergencie_EventId + " TEXT, "
            + Key_Emergencie_Icon + " TEXT, "
            + Key_Emergencie_Title + " TEXT, "
            + Key_Emergencie_Keterangan + " TEXT) ";
    /**/
    public static String TableCommiteNote = "tabCommiteNote";
    private static final String query_delete_table_CommiteNote = "DROP TABLE IF EXISTS " + TableCommiteNote;
    public static String Key_CommiteNote_No = "number";
    public static String Key_CommiteNote_EventId = "event_Id";
    public static String Key_CommiteNote_Id = "id";
    public static String Key_CommiteNote_Icon = "icon";
    public static String Key_CommiteNote_Title = "title";
    public static String Key_CommiteNote_Note = "note";
    public static String Key_CommiteNote_Tanggal = "tanggal";
    public static String Key_CommiteNote_Has_been_opened = "has_been_opened";
    public static String Key_CommiteNote_Sort_inbox = "sort_inbox";
    private static final String query_add_table_CommiteNote = "CREATE TABLE IF NOT EXISTS " + TableCommiteNote + "("
            + Key_CommiteNote_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_CommiteNote_EventId + " TEXT, "
            + Key_CommiteNote_Id + " INTEGER, "
            + Key_CommiteNote_Icon + " TEXT, "
            + Key_CommiteNote_Title + " TEXT, "
            + Key_CommiteNote_Note + " TEXT, "
            + Key_CommiteNote_Tanggal + " TEXT, "
            + Key_CommiteNote_Has_been_opened + " INTEGER, "
            + Key_CommiteNote_Sort_inbox + " INTEGER) ";
    /**/
    public static String TableFeedback = "tabFeedback";
    private static final String query_delete_table_Feedback = "DROP TABLE IF EXISTS " + TableFeedback;
    public static String Key_Feedback_No = "number";
    public static String Key_Feedback_EventId = "event_Id";
    public static String Key_Feedback_Judul = "judul";
    public static String Key_Feedback_SubJudul = "subJudul";
    public static String Key_Feedback_InputType = "inputType";
    public static String Key_Feedback_Name = "name";
    public static String Key_Feedback_SubName = "subName";
    public static String Key_Feedback_Label = "label";
    public static String Key_Feedback_PlaceHolder = "placeHolder";
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
    /**/
    public static String TableGallery = "tabGallery";
    private static final String query_delete_table_Gallery = "DROP TABLE IF EXISTS " + TableGallery;
    public static String Key_Gallery_No = "number";
    public static String Key_Gallery_eventId = "event_Id";
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
    /**/
    public static String TableHomeContent = "tabHomeContent";
    private static final String query_delete_table_HomeContent = "DROP TABLE IF EXISTS " + TableHomeContent;
    public static String Key_HomeContent_No = "number";
    public static String Key_HomeContent_EventId = "event_Id";
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
    public static String Key_HomeContent_event = "new_event";
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
    /**/
    public static String TableComment = "tabComment";
    private static final String query_delete_table_Comment = "DROP TABLE IF EXISTS " + TableComment;
    public static String Key_Comment_No = "number";
    public static String Key_Comment_Id = "id";
    public static String Key_Comment_Account_id = "account_id";
    public static String Key_Comment_Total_comment = "total_comment";
    public static String Key_Comment_Image_icon = "image_icon";
    public static String Key_Comment_Image_icon_local = "image_icon_local";
    public static String Key_Comment_Full_name = "full_name";
    public static String Key_Comment_Role_name = "role_name";
    public static String Key_Comment_Post_time = "post_time";
    public static String Key_Comment_Post_content = "post_content";
    public static String Key_Comment_EventId = "eventId";
    public static String Key_Comment_PostId = "postId";
    public static String Key_Comment_UserId = "userId";
    private static final String query_add_table_Comment = "CREATE TABLE IF NOT EXISTS " + TableComment + "("
            + Key_Comment_No + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Key_Comment_Id + " TEXT, "
            + Key_Comment_Account_id + " TEXT, "
            + Key_Comment_Total_comment + " INTEGER, "
            + Key_Comment_Image_icon + " TEXT, "
            + Key_Comment_Image_icon_local + " TEXT, "
            + Key_Comment_Full_name + " TEXT, "
            + Key_Comment_Role_name + " TEXT, "
            + Key_Comment_Post_time + " TEXT, "
            + Key_Comment_Post_content + " TEXT, "
            + Key_Comment_EventId + " TEXT, "
            + Key_Comment_PostId + " TEXT, "
            + Key_Comment_UserId + " TEXT) ";

    public SQLiteHelper(Context context) {
        super(context, DB_NM, null, DB_VER);
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
        db.execSQL(query_add_table_ImportantInfoNew);
        db.execSQL(query_add_table_ScheduleList);
        db.execSQL(query_add_table_TheEvent);
        db.execSQL(query_add_table_PlaceList);
        db.execSQL(query_add_table_Feedback);
        db.execSQL(query_add_table_Emergencie);
        db.execSQL(query_add_table_Area);
        db.execSQL(query_add_table_AreaNew);
        db.execSQL(query_add_table_CommiteNote);
        db.execSQL(query_add_table_Gallery);
        db.execSQL(query_add_table_HomeContent);
        db.execSQL(query_add_table_Comment);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SessionUtil.removeAllSharedPreferences();

        db.execSQL(query_delete_table_UserData);
        db.execSQL(query_delete_table_ListEvent);
        db.execSQL(query_delete_table_Wallet);
        db.execSQL(query_delete_table_GlobalData);
        db.execSQL(query_delete_table_EventAbout);
        db.execSQL(query_delete_table_ContactList);
        db.execSQL(query_delete_table_ImportantInfo);
        db.execSQL(query_delete_table_ImportantInfoNew);
        db.execSQL(query_delete_table_ScheduleList);
        db.execSQL(query_delete_table_TheEvent);
        db.execSQL(query_delete_table_PlaceList);
        db.execSQL(query_delete_table_Feedback);
        db.execSQL(query_delete_table_Emergencie);
        db.execSQL(query_delete_table_Area);
        db.execSQL(query_delete_table_AreaNew);
        db.execSQL(query_delete_table_CommiteNote);
        db.execSQL(query_delete_table_Gallery);
        db.execSQL(query_delete_table_HomeContent);
        db.execSQL(query_delete_table_Comment);

        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SessionUtil.removeAllSharedPreferences();

        db.execSQL(query_delete_table_UserData);
        db.execSQL(query_delete_table_ListEvent);
        db.execSQL(query_delete_table_Wallet);
        db.execSQL(query_delete_table_GlobalData);
        db.execSQL(query_delete_table_EventAbout);
        db.execSQL(query_delete_table_ContactList);
        db.execSQL(query_delete_table_ImportantInfo);
        db.execSQL(query_delete_table_ImportantInfoNew);
        db.execSQL(query_delete_table_ScheduleList);
        db.execSQL(query_delete_table_TheEvent);
        db.execSQL(query_delete_table_PlaceList);
        db.execSQL(query_delete_table_Feedback);
        db.execSQL(query_delete_table_Emergencie);
        db.execSQL(query_delete_table_Area);
        db.execSQL(query_delete_table_AreaNew);
        db.execSQL(query_delete_table_CommiteNote);
        db.execSQL(query_delete_table_Gallery);
        db.execSQL(query_delete_table_HomeContent);
        db.execSQL(query_delete_table_Comment);

        onCreate(db);

    }
}