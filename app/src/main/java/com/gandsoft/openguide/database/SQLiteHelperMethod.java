package com.gandsoft.openguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gandsoft.openguide.API.APIresponse.Event.EventAbout;
import com.gandsoft.openguide.API.APIresponse.Event.EventCommitteeNote;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.API.APIresponse.Event.EventEmergencies;
import com.gandsoft.openguide.API.APIresponse.Event.EventFeedback;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfo;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfoNew;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfoNewDetail;
import com.gandsoft.openguide.API.APIresponse.Event.EventPlaceList;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDateDataList;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListExternalframe;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingArea;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingAreaNew;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingAreaNewDetail;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentGetResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.GetListUserEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteHelperMethod extends SQLiteHelper {

    public SQLiteHelperMethod(Context context) {
        super(context);
    }

    /**/
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
        contentValues.put(KEY_ListEvent_IsFirstIn, 0);
        db.insert(TableListEvent, null, contentValues);
        db.close();
    }

    public void updateListEvent(UserListEventResponseModel model, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ListEvent_eventId, model.getEvent_id());
        //contentValues.put(KEY_ListEvent_accountId, event_Id);
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
        db.update(TableListEvent, contentValues, KEY_ListEvent_eventId + " = ? AND " + KEY_ListEvent_accountId + " = ?", new String[]{model.getEvent_id(), accountId});
        db.close();
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

    public UserListEventResponseModel getOneListEvent(String eventId, String accountId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableListEvent, null, KEY_ListEvent_eventId + " = ? AND " + KEY_ListEvent_accountId + " = ?", new String[]{eventId, accountId}, null, null, KEY_ListEvent_startDate + " DESC");
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

    /**/
    public void saveUserData(GetListUserEventResponseModel model, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_UserData_accountId, accountId);
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
        contentValues.put(KEY_UserData_isStillIn, 1);
        db.insert(TableUserData, null, contentValues);
        db.close();
    }

    public void updateUserData(GetListUserEventResponseModel model, String accountId) {
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

    public ArrayList<GetListUserEventResponseModel> getAllUserData(String accountid) {
        ArrayList<GetListUserEventResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableUserData, null, KEY_UserData_accountId + " = ? ", new String[]{accountid}, KEY_UserData_accountId, null, null);
        if (cursor.moveToFirst()) {
            do {
                GetListUserEventResponseModel model = new GetListUserEventResponseModel();
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

    public GetListUserEventResponseModel getOneUserData(String accountid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableUserData, null, KEY_UserData_accountId + " = ? ", new String[]{accountid}, KEY_UserData_accountId, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        GetListUserEventResponseModel model = new GetListUserEventResponseModel();
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

    public void setUserStillIn(String accountid, boolean isStillIn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (isStillIn) {
            contentValues.put(KEY_UserData_isStillIn, 1);
        } else {
            contentValues.put(KEY_UserData_isStillIn, 0);
        }
        db.update(TableUserData, contentValues, KEY_UserData_accountId + " = ? ", new String[]{accountid});
        db.close();
    }

    public boolean isUserStillIn(String accountid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableUserData, null, KEY_UserData_accountId + " = ? AND " + KEY_UserData_isStillIn + " = 1", new String[]{accountid}, null, null, null);
        if (cursor.getCount() == 1) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    /**/
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

    public void updateWalletData(UserWalletDataResponseModel model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_Wallet_sort, model.getSort());
        //contentValues.put(KEY_Wallet_accountId, accountId);
        //contentValues.put(KEY_Wallet_eventId, event_Id);
        contentValues.put(KEY_Wallet_bodyWallet, model.getBody_wallet());
        contentValues.put(KEY_Wallet_notif, model.getNotif());
        contentValues.put(KEY_Wallet_detail, model.getDetail());
        contentValues.put(KEY_Wallet_type, model.getType());
        db.update(TableWallet, contentValues, KEY_Wallet_sort + " = ? AND " + KEY_Wallet_eventId + " = ? ", new String[]{model.getSort(), eventId});
        db.close();
    }

    public ArrayList<UserWalletDataResponseModel> getAllWalletData(String eventId, String accountId) {
        ArrayList<UserWalletDataResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableWallet, null, KEY_Wallet_eventId + " = ? AND " + KEY_Wallet_accountId + " = ?", new String[]{eventId, accountId}, null, null, KEY_Wallet_sort);

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
                model.setId_card_local(cursor.getString(cursor.getColumnIndex(KEY_Wallet_idCardLocal)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public UserWalletDataResponseModel getWalletDataIdCard(String eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableWallet, null, KEY_Wallet_eventId + " = ? AND " + KEY_Wallet_type + " = 'idcard' ", new String[]{String.valueOf(eventId)}, KEY_Wallet_sort, null, KEY_Wallet_sort);
        if (cursor != null)
            cursor.moveToFirst();

        UserWalletDataResponseModel model = new UserWalletDataResponseModel();
        model.setNumber(cursor.getInt(cursor.getColumnIndex(KEY_Wallet_No)));
        model.setSort(cursor.getString(cursor.getColumnIndex(KEY_Wallet_sort)));
        model.setType(cursor.getString(cursor.getColumnIndex(KEY_Wallet_type)));
        model.setBody_wallet(cursor.getString(cursor.getColumnIndex(KEY_Wallet_bodyWallet)));
        model.setNotif(cursor.getString(cursor.getColumnIndex(KEY_Wallet_notif)));
        model.setDetail(cursor.getString(cursor.getColumnIndex(KEY_Wallet_detail)));
        model.setId_card_local(cursor.getString(cursor.getColumnIndex(KEY_Wallet_idCardLocal)));
        // return model
        return model;
    }

    /**/
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
        db.update(TableTheEvent, contentValues, Key_The_Event_EventId + " = ? AND " + Key_The_Event_version_data + " = ?", new String[]{eventId, version_data});
        db.close();
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

    /**/
    public void saveScheduleList(EventScheduleListDateDataList model, String date, String eventId, String groupCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Schedule_List_EventId, eventId);
        contentValues.put(Key_Schedule_List_GroupCode, groupCode);
        contentValues.put(Key_Schedule_List_date, date);
        contentValues.put(Key_Schedule_List_id, model.getId());
        contentValues.put(Key_Schedule_List_waktu, model.getWaktu());
        contentValues.put(Key_Schedule_List_schedule_title, model.getSchedule_title());
        contentValues.put(Key_Schedule_List_location, model.getLocation());
        contentValues.put(Key_Schedule_List_detail, model.getDetail());
        contentValues.put(Key_Schedule_List_action, model.getAction());
        contentValues.put(Key_Schedule_List_link, model.getLink());
        contentValues.put(Key_Schedule_List_linkvote, model.getLinkvote());
        if (model.getExternalframe() != null) {
            contentValues.put(Key_Schedule_List_name, model.getExternalframe().getName());
            contentValues.put(Key_Schedule_List_link_external, model.getExternalframe().getLink());
            contentValues.put(Key_Schedule_List_external, model.getExternalframe().getExternal());
        }
        contentValues.put(Key_Schedule_List_status, model.getStatus());
        db.insert(TableScheduleList, null, contentValues);
        db.close();
    }

    public void updateScheduleList(EventScheduleListDateDataList model, String date, String eventId, String groupCode) {
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
        if (model.getExternalframe() != null) {
            contentValues.put(Key_Schedule_List_name, model.getExternalframe().getName());
            contentValues.put(Key_Schedule_List_link_external, model.getExternalframe().getLink());
            contentValues.put(Key_Schedule_List_external, model.getExternalframe().getExternal());
        }
        contentValues.put(Key_Schedule_List_status, model.getStatus());
        db.update(TableScheduleList, contentValues, Key_Schedule_List_GroupCode + " = ? AND " + Key_Schedule_List_id + " = ? ", new String[]{groupCode, model.getId()});
        db.close();
    }

    public ArrayList<EventScheduleListDateDataList> getScheduleList(String eventId) {
        ArrayList<EventScheduleListDateDataList> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableScheduleList, null, Key_Schedule_List_GroupCode + " = ? ", new String[]{eventId}, Key_Schedule_List_id, null, null);

        if (cursor.moveToFirst()) {
            do {
                EventScheduleListDateDataList model = new EventScheduleListDateDataList();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Schedule_List_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_EventId)));
                model.setGroupCode(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_GroupCode)));
                model.setDate(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_date)));
                model.setId(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_id)));
                model.setWaktu(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_waktu)));
                model.setSchedule_title(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_schedule_title)));
                model.setLocation(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_location)));
                model.setDetail(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_detail)));
                model.setAction(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_action)));
                model.setLink(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_link)));
                model.setLinkvote(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_linkvote)));
                model.setExternalframe(new EventScheduleListExternalframe(
                        cursor.getString(cursor.getColumnIndex(Key_Schedule_List_name)),
                        cursor.getString(cursor.getColumnIndex(Key_Schedule_List_link_external)),
                        cursor.getString(cursor.getColumnIndex(Key_Schedule_List_external))
                ));
                model.setStatus(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_status)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventScheduleListDateDataList> getScheduleListPerDate(String param1, String date) {
        ArrayList<EventScheduleListDateDataList> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableScheduleList, null, Key_Schedule_List_GroupCode + " = ? AND " + Key_Schedule_List_date + " = ? ", new String[]{param1, date}, Key_Schedule_List_id, null, Key_Schedule_List_id);

        if (cursor.moveToFirst()) {
            do {
                EventScheduleListDateDataList model = new EventScheduleListDateDataList();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Schedule_List_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_EventId)));
                model.setGroupCode(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_GroupCode)));
                model.setDate(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_date)));
                model.setId(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_id)));
                model.setWaktu(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_waktu)));
                model.setSchedule_title(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_schedule_title)));
                model.setLocation(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_location)));
                model.setDetail(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_detail)));
                model.setAction(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_action)));
                model.setLink(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_link)));
                model.setLinkvote(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_linkvote)));
                model.setExternalframe(new EventScheduleListExternalframe(
                        cursor.getString(cursor.getColumnIndex(Key_Schedule_List_name)),
                        cursor.getString(cursor.getColumnIndex(Key_Schedule_List_link_external)),
                        cursor.getString(cursor.getColumnIndex(Key_Schedule_List_external))
                ));
                model.setStatus(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_status)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<String> getScheduleListDate(String param) {
        ArrayList<String> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableScheduleList, new String[]{Key_Schedule_List_date}, Key_Schedule_List_GroupCode + " = ? ", new String[]{param}, Key_Schedule_List_date, null, Key_Schedule_List_No);

        if (cursor.moveToFirst()) {
            do {
                modelList.add(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_date)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<String> getScheduleListTime(String param, String date) {
        ArrayList<String> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableScheduleList, new String[]{Key_Schedule_List_waktu}, Key_Schedule_List_GroupCode + " = ? AND " + Key_Schedule_List_date + " = ?", new String[]{param, date}, null, null, Key_Schedule_List_No);

        if (cursor.moveToFirst()) {
            do {
                modelList.add(cursor.getString(cursor.getColumnIndex(Key_Schedule_List_waktu)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    /**/
    public void saveImportanInfo(EventImportanInfo model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Importan_Info_EventId, eventId);
        contentValues.put(Key_Importan_Info_title, model.getTitle());
        contentValues.put(Key_Importan_Info_info, model.getInfo());
        db.insert(TableImportantInfo, null, contentValues);
        db.close();
    }

    public void updateImportanInfo(EventImportanInfo model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Importan_Info_title, model.getTitle());
        contentValues.put(Key_Importan_Info_info, model.getInfo());
        db.update(TableImportantInfo, contentValues, Key_Importan_Info_EventId + " = ? AND " + Key_Importan_Info_title + " = ?", new String[]{eventId, model.getTitle()});
        db.close();
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

    /**/
    public void saveImportanInfoNew(EventImportanInfoNew model, EventImportanInfoNewDetail model1, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Important_InfoNew_EventId, eventId);
        contentValues.put(Key_Important_InfoNew_title, model.getTitle());
        contentValues.put(Key_Important_InfoNew_Info, model.getInfo());
        contentValues.put(Key_Important_InfoNew_Title_image, model1.getTitle_image());
        contentValues.put(Key_Important_InfoNew_Name_image, model1.getName_image());
        contentValues.put(Key_Important_InfoNew_Name_image_local, model1.getName_image_local());
        contentValues.put(Key_Important_InfoNew_Detail_image, model1.getDetail_image());
        db.insert(TableImportantInfoNew, null, contentValues);
        db.close();
    }

    public void saveImportanInfoNew(EventImportanInfoNew model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Important_InfoNew_EventId, eventId);
        contentValues.put(Key_Important_InfoNew_title, model.getTitle());
        contentValues.put(Key_Important_InfoNew_Info, model.getInfo());
        db.insert(TableImportantInfoNew, null, contentValues);
        db.close();
    }

    public void updateImportanInfoNew(EventImportanInfoNew model, EventImportanInfoNewDetail model1, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Important_InfoNew_title, model.getTitle());
        contentValues.put(Key_Important_InfoNew_Info, model.getInfo());
        contentValues.put(Key_Important_InfoNew_Title_image, model1.getTitle_image());
        contentValues.put(Key_Important_InfoNew_Name_image, model1.getName_image());
        contentValues.put(Key_Important_InfoNew_Name_image_local, model1.getName_image_local());
        contentValues.put(Key_Important_InfoNew_Detail_image, model1.getDetail_image());
        db.update(TableImportantInfoNew, contentValues, Key_Important_InfoNew_EventId + " = ? AND " + Key_Important_InfoNew_title + " = ? AND " + Key_Important_InfoNew_Title_image + " = ? ", new String[]{eventId, model.getTitle(), model1.getTitle_image()});
        db.close();
    }

    public void updateImportanInfoNew(EventImportanInfoNew model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Important_InfoNew_title, model.getTitle());
        contentValues.put(Key_Important_InfoNew_Info, model.getInfo());
        db.update(TableImportantInfoNew, contentValues, Key_Important_InfoNew_EventId + " = ? AND " + Key_Important_InfoNew_title + " = ?", new String[]{eventId, model.getTitle()});
        db.close();
    }

    public ArrayList<EventImportanInfoNew> getImportanInfoNewTitle(String eventId) {
        ArrayList<EventImportanInfoNew> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableImportantInfoNew, null, Key_Important_InfoNew_EventId + " = ? ", new String[]{eventId}, Key_Important_InfoNew_title, null, Key_Important_InfoNew_title);

        if (cursor.moveToFirst()) {
            do {
                EventImportanInfoNew model = new EventImportanInfoNew();
                model.setTitle(cursor.getString(cursor.getColumnIndex(Key_Important_InfoNew_title)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventImportanInfoNew> getImportanInfoNew(String eventId) {
        ArrayList<EventImportanInfoNew> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableImportantInfoNew, null, Key_Important_InfoNew_EventId + " = ? ", new String[]{eventId}, Key_Important_InfoNew_title, null, null);

        if (cursor.moveToFirst()) {
            do {
                EventImportanInfoNew model = new EventImportanInfoNew();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Important_InfoNew_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Important_InfoNew_EventId)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(Key_Important_InfoNew_title)));
                model.setInfo(cursor.getString(cursor.getColumnIndex(Key_Important_InfoNew_Info)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventImportanInfoNewDetail> getImportanInfoNewDetail(String eventId, String title) {
        ArrayList<EventImportanInfoNewDetail> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableImportantInfoNew, null, Key_Important_InfoNew_EventId + " = ? AND " + Key_Important_InfoNew_title + " = ? ", new String[]{eventId, title}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                EventImportanInfoNewDetail model = new EventImportanInfoNewDetail();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Important_InfoNew_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Important_InfoNew_EventId)));
                model.setTitle_image(cursor.getString(cursor.getColumnIndex(Key_Important_InfoNew_Title_image)));
                model.setName_image(cursor.getString(cursor.getColumnIndex(Key_Important_InfoNew_Name_image)));
                model.setName_image_local(cursor.getString(cursor.getColumnIndex(Key_Important_InfoNew_Name_image_local)));
                model.setDetail_image(cursor.getString(cursor.getColumnIndex(Key_Important_InfoNew_Detail_image)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    /**/
    public void saveArea(EventSurroundingArea model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Area_EventId, eventId);
        contentValues.put(Key_Area_Title, model.getTitle());
        contentValues.put(Key_Area_Description, model.getDescription());
        db.insert(TableArea, null, contentValues);
        db.close();
    }

    public void updateArea(EventSurroundingArea model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Area_EventId, eventId);
        contentValues.put(Key_Area_Title, model.getTitle());
        contentValues.put(Key_Area_Description, model.getDescription());
        db.update(TableArea, contentValues, Key_Area_EventId + " = ? " + Key_Area_Description + " = ? ", new String[]{eventId, model.getDescription()});
        db.close();
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

    /**/
    public void saveAreaNew(EventSurroundingAreaNewDetail model, String title, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_AreaNew_EventId, eventId);
        contentValues.put(Key_AreaNew_Title, title);
        contentValues.put(Key_AreaNew_Name_image, model.getName_image());
        contentValues.put(Key_AreaNew_Name_image_local, model.getName_image_local());
        contentValues.put(Key_AreaNew_Title_image, model.getTitle_image());
        contentValues.put(Key_AreaNew_Detail_image, model.getDetail_image());
        contentValues.put(Key_AreaNew_Icon_walk, model.getIcon_walk());
        contentValues.put(Key_AreaNew_Icon_car, model.getIcon_car());
        contentValues.put(Key_AreaNew_Icon_direction, model.getIcon_direction());
        contentValues.put(Key_AreaNew_Minute_walk, model.getMinute_walk());
        contentValues.put(Key_AreaNew_Minute_car, model.getMinute_car());
        contentValues.put(Key_AreaNew_Google_direction, model.getGoogle_direction());
        db.insert(TableAreaNew, null, contentValues);
        db.close();
    }

    public void updateAreaNew(EventSurroundingAreaNewDetail model, String title, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_AreaNew_Title, title);
        contentValues.put(Key_AreaNew_Name_image, model.getName_image());
        contentValues.put(Key_AreaNew_Name_image_local, model.getName_image_local());
        contentValues.put(Key_AreaNew_Title_image, model.getTitle_image());
        contentValues.put(Key_AreaNew_Detail_image, model.getDetail_image());
        contentValues.put(Key_AreaNew_Icon_walk, model.getIcon_walk());
        contentValues.put(Key_AreaNew_Icon_car, model.getIcon_car());
        contentValues.put(Key_AreaNew_Icon_direction, model.getIcon_direction());
        contentValues.put(Key_AreaNew_Minute_walk, model.getMinute_walk());
        contentValues.put(Key_AreaNew_Minute_car, model.getMinute_car());
        contentValues.put(Key_AreaNew_Google_direction, model.getGoogle_direction());
        db.update(TableAreaNew, contentValues, Key_AreaNew_EventId + " = ? AND " + Key_AreaNew_Title + " = ? AND " + Key_AreaNew_Title_image + " = ?", new String[]{eventId, title, model.getTitle_image()});
        db.close();
    }

    public ArrayList<EventSurroundingAreaNew> getSurroundingAreaNew(String eventId) {
        ArrayList<EventSurroundingAreaNew> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableAreaNew, null, Key_AreaNew_EventId + " = ? ", new String[]{eventId}, Key_AreaNew_Title, null, null);

        if (cursor.moveToFirst()) {
            do {
                EventSurroundingAreaNew model = new EventSurroundingAreaNew();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_AreaNew_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_AreaNew_EventId)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Title)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<EventSurroundingAreaNewDetail> getSurroundingAreaNewDetail(String eventId, String title) {
        ArrayList<EventSurroundingAreaNewDetail> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableAreaNew, null, Key_AreaNew_EventId + " = ? AND " + Key_AreaNew_Title + " = ? ", new String[]{eventId, title}, null, null, Key_AreaNew_No + " ASC");

        if (cursor.moveToFirst()) {
            do {
                EventSurroundingAreaNewDetail model = new EventSurroundingAreaNewDetail();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_AreaNew_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_AreaNew_EventId)));
                model.setName_image(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Name_image)));
                model.setName_image_local(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Name_image_local)));
                model.setTitle_image(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Title_image)));
                model.setDetail_image(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Detail_image)));
                model.setIcon_walk(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Icon_walk)));
                model.setIcon_car(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Icon_car)));
                model.setIcon_direction(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Icon_direction)));
                model.setMinute_walk(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Minute_walk)));
                model.setMinute_car(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Minute_car)));
                model.setGoogle_direction(cursor.getString(cursor.getColumnIndex(Key_AreaNew_Google_direction)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    /**/
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

    public void updateDataContactList(EventDataContactList model, String title, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Contact_List_Title, title);
        contentValues.put(KEY_Contact_List_Name, model.getName());
        contentValues.put(KEY_Contact_List_Email, model.getEmail());
        contentValues.put(KEY_Contact_List_Telephone, model.getTelephone());
        contentValues.put(Key_Contact_List_Icon, model.getIcon());
        db.update(TableContactList, contentValues, Key_Contact_List_EventId + " = ? AND " + KEY_Contact_List_Name + " = ? ", new String[]{eventId, model.getName()});
        db.close();
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

    /**/
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

    public ArrayList<EventAbout> getAbout(String eventId) {
        ArrayList<EventAbout> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableEventAbout, null, Key_Event_About_EventId + " = ? ", new String[]{eventId}, Key_Event_About_EventId, null, null);

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

    /**/
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

    public void updatePlaceList(EventPlaceList model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Place_List_latitude, model.getLatitude());
        contentValues.put(Key_Place_List_longitude, model.getLongitude());
        contentValues.put(Key_Place_List_title, model.getTitle());
        contentValues.put(Key_Place_List_icon, model.getIcon());
        db.update(TablePlaceList, contentValues, Key_Place_List_EventId + " = ? AND " + Key_Place_List_title + " = ? ", new String[]{eventId, model.getTitle()});
        db.close();
    }

    public void savePlaceListStaticMaps(String eventId, String staticMaps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Place_List_staticMap, staticMaps);
        db.update(TablePlaceList, contentValues, Key_Place_List_EventId + " = ? ", new String[]{eventId});
        db.close();
    }

    public ArrayList<EventPlaceList> getPlaceList(String eventId) {
        ArrayList<EventPlaceList> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TablePlaceList, null, Key_Place_List_EventId + " = ? ", new String[]{eventId}, Key_Place_List_No, null, Key_Place_List_No);

        if (cursor.moveToFirst()) {
            do {
                EventPlaceList model = new EventPlaceList();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Place_List_No)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Place_List_EventId)));
                model.setLatitude(cursor.getString(cursor.getColumnIndex(Key_Place_List_latitude)));
                model.setLongitude(cursor.getString(cursor.getColumnIndex(Key_Place_List_longitude)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(Key_Place_List_title)));
                model.setIcon(cursor.getString(cursor.getColumnIndex(Key_Place_List_icon)));
                model.setStaticMaps(cursor.getString(cursor.getColumnIndex(Key_Place_List_staticMap)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    /**/
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

    public void updateEmergency(EventEmergencies model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Emergencie_EventId, eventId);
        contentValues.put(Key_Emergencie_Icon, model.getIcon());
        contentValues.put(Key_Emergencie_Title, model.getTitle());
        contentValues.put(Key_Emergencie_Keterangan, model.getKeterangan());
        db.update(TableEmergencie, contentValues, Key_Emergencie_EventId + " = ? AND " + Key_Emergencie_Title + " = ? ", new String[]{eventId, model.getTitle()});
        db.close();
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

    /**/
    public void saveCommiteNote(EventCommitteeNote model, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_CommiteNote_EventId, eventId);
        contentValues.put(Key_CommiteNote_Id, model.getId());
        contentValues.put(Key_CommiteNote_Icon, model.getIcon());
        contentValues.put(Key_CommiteNote_Title, model.getTitle());
        contentValues.put(Key_CommiteNote_Note, model.getNote());
        contentValues.put(Key_CommiteNote_Tanggal, model.getTanggal());
        //contentValues.put(Key_CommiteNote_Has_been_opened, model.getHas_been_opened());
        contentValues.put(Key_CommiteNote_Sort_inbox, model.getSort_inbox());
        db.insert(TableCommiteNote, null, contentValues);
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
        //contentValues.put(Key_CommiteNote_Has_been_opened, model.getHas_been_opened());
        contentValues.put(Key_CommiteNote_Sort_inbox, model.getSort_inbox());
        db.update(TableCommiteNote, contentValues, Key_CommiteNote_EventId + " = ? AND " + Key_CommiteNote_Id + " = ? ", new String[]{eventId, model.getId()});
        db.close();
    }

    public ArrayList<EventCommitteeNote> getCommiteNote(String eventId) {
        ArrayList<EventCommitteeNote> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableCommiteNote, null, Key_CommiteNote_EventId + " = ? ", new String[]{eventId}, Key_CommiteNote_Id, null, Key_CommiteNote_No);

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

    /**/
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
        db.update(TableFeedback, contentValues, Key_Feedback_EventId + " = ? AND " + Key_Feedback_Judul + " = ? ", new String[]{eventId, model.getJudul()});
        db.close();
    }

    public ArrayList<EventFeedback> getFeedback(String eventId) {
        ArrayList<EventFeedback> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableFeedback, null, Key_Feedback_EventId + " = ? ", new String[]{eventId}, Key_Feedback_EventId, null, Key_Feedback_No);

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

    /**/
    public void saveGallery(HomeContentResponseModel model, String eventId) {
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
        contentValues.put(Key_Gallery_imagePostedLocal, model.getImage_posted());
        contentValues.put(Key_Gallery_imageIcon, model.getImage_icon());
        contentValues.put(Key_Gallery_imageIconLocal, model.getImage_icon_local());
        db.insert(TableGallery, null, contentValues);
        db.close();
    }

    public void updateGallery(HomeContentResponseModel model, String eventId) {
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
        contentValues.put(Key_Gallery_imagePostedLocal, model.getImage_posted_local());
        contentValues.put(Key_Gallery_imageIcon, model.getImage_icon());
        contentValues.put(Key_Gallery_imageIconLocal, model.getImage_icon_local());
        db.update(TableGallery, contentValues, Key_Gallery_eventId + " = ? AND " + Key_Gallery_galleryId + " = ? ", new String[]{eventId, model.getId()});
        db.close();
    }

    public ArrayList<HomeContentResponseModel> getGallery(String eventId) {
        ArrayList<HomeContentResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableGallery, null, Key_Gallery_eventId + " = ? ", new String[]{eventId}, Key_Gallery_galleryId, null, Key_Gallery_No + " ASC");

        if (cursor.moveToFirst()) {
            do {
                HomeContentResponseModel model = new HomeContentResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Gallery_No)));
                model.setEvent_Id(cursor.getString(cursor.getColumnIndex(Key_Gallery_eventId)));
                model.setId(cursor.getString(cursor.getColumnIndex(Key_Gallery_galleryId)));
                model.setLike(cursor.getString(cursor.getColumnIndex(Key_Gallery_Like)));
                model.setAccount_id(cursor.getString(cursor.getColumnIndex(Key_Gallery_accountId)));
                model.setTotal_comment(cursor.getString(cursor.getColumnIndex(Key_Gallery_totalComment)));
                model.setStatus_like(cursor.getInt(cursor.getColumnIndex(Key_Gallery_statusLike)));
                model.setUsername(cursor.getString(cursor.getColumnIndex(Key_Gallery_Username)));
                model.setCaption(cursor.getString(cursor.getColumnIndex(Key_Gallery_Caption)));
                model.setImage_posted(cursor.getString(cursor.getColumnIndex(Key_Gallery_imagePosted)));
                model.setImage_posted_local(cursor.getString(cursor.getColumnIndex(Key_Gallery_imagePostedLocal)));
                model.setImage_icon(cursor.getString(cursor.getColumnIndex(Key_Gallery_imageIcon)));
                model.setImage_icon_local(cursor.getString(cursor.getColumnIndex(Key_Gallery_imageIconLocal)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<HomeContentResponseModel> getGallery18(String eventId) {
        ArrayList<HomeContentResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableGallery, null, Key_Gallery_eventId + " = ? ", new String[]{eventId}, Key_Gallery_galleryId, null, Key_Gallery_No + " ASC", "18");

        if (cursor.moveToFirst()) {
            do {
                HomeContentResponseModel model = new HomeContentResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Gallery_No)));
                model.setEvent_Id(cursor.getString(cursor.getColumnIndex(Key_Gallery_eventId)));
                model.setId(cursor.getString(cursor.getColumnIndex(Key_Gallery_galleryId)));
                model.setLike(cursor.getString(cursor.getColumnIndex(Key_Gallery_Like)));
                model.setAccount_id(cursor.getString(cursor.getColumnIndex(Key_Gallery_accountId)));
                model.setTotal_comment(cursor.getString(cursor.getColumnIndex(Key_Gallery_totalComment)));
                model.setStatus_like(cursor.getInt(cursor.getColumnIndex(Key_Gallery_statusLike)));
                model.setUsername(cursor.getString(cursor.getColumnIndex(Key_Gallery_Username)));
                model.setCaption(cursor.getString(cursor.getColumnIndex(Key_Gallery_Caption)));
                model.setImage_posted(cursor.getString(cursor.getColumnIndex(Key_Gallery_imagePosted)));
                model.setImage_posted_local(cursor.getString(cursor.getColumnIndex(Key_Gallery_imagePostedLocal)));
                model.setImage_icon(cursor.getString(cursor.getColumnIndex(Key_Gallery_imageIcon)));
                model.setImage_icon_local(cursor.getString(cursor.getColumnIndex(Key_Gallery_imageIconLocal)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    /**/
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
        contentValues.put(Key_HomeContent_image_posted, model.getImage_posted());
        contentValues.put(Key_HomeContent_keterangan, model.getKeterangan());
        contentValues.put(Key_HomeContent_event, model.getNew_event());
        db.insert(TableHomeContent, null, contentValues);
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
        contentValues.put(Key_HomeContent_event, model.getNew_event());
        db.update(TableHomeContent, contentValues, Key_HomeContent_EventId + " = ? AND " + Key_HomeContent_id + " = ? ", new String[]{eventId, model.getId()});
        db.close();
    }

    public ArrayList<HomeContentResponseModel> getHomeContent(String eventId) {
        ArrayList<HomeContentResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableHomeContent, null, Key_HomeContent_EventId + " = ? ", new String[]{eventId}, Key_HomeContent_id, null, Key_HomeContent_No + " ASC");

        if (cursor.moveToFirst()) {
            do {
                HomeContentResponseModel model = new HomeContentResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_HomeContent_No)));
                model.setEvent_Id(cursor.getString(cursor.getColumnIndex(Key_HomeContent_EventId)));
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
                model.setNew_event(cursor.getString(cursor.getColumnIndex(Key_HomeContent_event)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<HomeContentResponseModel> getHomeContent10(String eventId) {
        ArrayList<HomeContentResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableHomeContent, null, Key_HomeContent_EventId + " = ? ", new String[]{eventId}, Key_HomeContent_id, null, Key_HomeContent_No + " ASC", "10");

        if (cursor.moveToFirst()) {
            do {
                HomeContentResponseModel model = new HomeContentResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_HomeContent_No)));
                model.setEvent_Id(cursor.getString(cursor.getColumnIndex(Key_HomeContent_EventId)));
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
                model.setNew_event(cursor.getString(cursor.getColumnIndex(Key_HomeContent_event)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    /**/
    public void saveComment(HomeContentPostCommentGetResponseModel model, String eventId, String postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Comment_Id, model.getId());
        contentValues.put(Key_Comment_Account_id, model.getAccount_id());
        contentValues.put(Key_Comment_Total_comment, model.getTotal_comment());
        contentValues.put(Key_Comment_Image_icon, model.getImage_icon());
        contentValues.put(Key_Comment_Image_icon_local, model.getImage_icon_local());
        contentValues.put(Key_Comment_Full_name, model.getFull_name());
        contentValues.put(Key_Comment_Role_name, model.getRole_name());
        contentValues.put(Key_Comment_Post_time, model.getPost_time());
        contentValues.put(Key_Comment_Post_content, model.getPost_content());
        contentValues.put(Key_Comment_EventId, eventId);
        contentValues.put(Key_Comment_PostId, postId);
        db.insert(TableComment, null, contentValues);
        db.close();
    }

    public void updateComment(HomeContentPostCommentGetResponseModel model, String eventId, String postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Comment_Id, model.getId());
        contentValues.put(Key_Comment_Account_id, model.getAccount_id());
        contentValues.put(Key_Comment_Total_comment, model.getTotal_comment());
        contentValues.put(Key_Comment_Image_icon, model.getImage_icon());
        contentValues.put(Key_Comment_Image_icon_local, model.getImage_icon_local());
        contentValues.put(Key_Comment_Full_name, model.getFull_name());
        contentValues.put(Key_Comment_Role_name, model.getRole_name());
        contentValues.put(Key_Comment_Post_time, model.getPost_time());
        contentValues.put(Key_Comment_Post_content, model.getPost_content());
        contentValues.put(Key_Comment_EventId, eventId);
        contentValues.put(Key_Comment_PostId, postId);
        db.update(TableComment, contentValues, Key_Comment_EventId + " = ? AND " + Key_Comment_PostId + " = ? AND " + Key_Comment_Id + " = ? ", new String[]{eventId, postId, model.getId()});
        db.close();
    }

    public ArrayList<HomeContentPostCommentGetResponseModel> getComment(String eventId, String postId) {
        ArrayList<HomeContentPostCommentGetResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableComment, null, Key_Comment_EventId + " = ? AND " + Key_Comment_PostId + " = ? ", new String[]{eventId, postId}, Key_Comment_Id, null, Key_Comment_No);

        if (cursor.moveToFirst()) {
            do {
                HomeContentPostCommentGetResponseModel model = new HomeContentPostCommentGetResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Comment_No)));
                model.setId(cursor.getString(cursor.getColumnIndex(Key_Comment_Id)));
                model.setAccount_id(cursor.getString(cursor.getColumnIndex(Key_Comment_Account_id)));
                model.setTotal_comment(cursor.getString(cursor.getColumnIndex(Key_Comment_Total_comment)));
                model.setImage_icon(cursor.getString(cursor.getColumnIndex(Key_Comment_Image_icon)));
                model.setImage_icon_local(cursor.getString(cursor.getColumnIndex(Key_Comment_Image_icon_local)));
                model.setFull_name(cursor.getString(cursor.getColumnIndex(Key_Comment_Full_name)));
                model.setRole_name(cursor.getString(cursor.getColumnIndex(Key_Comment_Role_name)));
                model.setPost_time(cursor.getString(cursor.getColumnIndex(Key_Comment_Post_time)));
                model.setPost_content(cursor.getString(cursor.getColumnIndex(Key_Comment_Post_content)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Comment_EventId)));
                model.setPostId(cursor.getString(cursor.getColumnIndex(Key_Comment_PostId)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    public ArrayList<HomeContentPostCommentGetResponseModel> getComment10(String eventId, String postId) {
        ArrayList<HomeContentPostCommentGetResponseModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableComment, null, Key_Comment_EventId + " = ? AND " + Key_Comment_PostId + " = ? ", new String[]{eventId, postId}, Key_Comment_Id, null, Key_Comment_No + " ASC", "10");

        if (cursor.moveToFirst()) {
            do {
                HomeContentPostCommentGetResponseModel model = new HomeContentPostCommentGetResponseModel();
                model.setNumber(cursor.getInt(cursor.getColumnIndex(Key_Comment_No)));
                model.setId(cursor.getString(cursor.getColumnIndex(Key_Comment_Id)));
                model.setAccount_id(cursor.getString(cursor.getColumnIndex(Key_Comment_Account_id)));
                model.setTotal_comment(cursor.getString(cursor.getColumnIndex(Key_Comment_Total_comment)));
                model.setImage_icon(cursor.getString(cursor.getColumnIndex(Key_Comment_Image_icon)));
                model.setImage_icon_local(cursor.getString(cursor.getColumnIndex(Key_Comment_Image_icon_local)));
                model.setFull_name(cursor.getString(cursor.getColumnIndex(Key_Comment_Full_name)));
                model.setRole_name(cursor.getString(cursor.getColumnIndex(Key_Comment_Role_name)));
                model.setPost_time(cursor.getString(cursor.getColumnIndex(Key_Comment_Post_time)));
                model.setPost_content(cursor.getString(cursor.getColumnIndex(Key_Comment_Post_content)));
                model.setEventId(cursor.getString(cursor.getColumnIndex(Key_Comment_EventId)));
                model.setPostId(cursor.getString(cursor.getColumnIndex(Key_Comment_PostId)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return modelList;
    }

    /**/
    public int getCommiteHasBeenOpened(String eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableCommiteNote, null, Key_CommiteNote_EventId + " = ? AND " + Key_CommiteNote_Has_been_opened + " = 1", new String[]{eventId}, Key_CommiteNote_Id, null, null);
        int total;
        if (cursor.getCount() > 0) {
            total = cursor.getCount();
            cursor.close();
            return total;
        } else {
            total = 0;
            cursor.close();
            return total;
        }
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

    public int getVersionDataIdUser(String accountId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableUserData, new String[]{KEY_UserData_versionData}, KEY_UserData_accountId + " = ? ", new String[]{accountId}, KEY_UserData_accountId, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(KEY_UserData_versionData));
        } else {
            return 0;
        }
    }

    public int getVersionDataIdEvent(String eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableTheEvent, new String[]{Key_The_Event_version_data}, Key_The_Event_EventId + " = ? ", new String[]{eventId}, Key_The_Event_EventId, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(Key_The_Event_version_data));
        } else {
            return 0;
        }
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

    public boolean isDataTableKeyNull(String table, String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, null, key + " IS NULL ", null, null, null, null);
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

    public boolean isDataTableValueNull(String tableName, String key, String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, null, key + " = ? ", new String[]{value}, null, null, null);
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

    public boolean isDataTableValueMultipleNotNull(String table, String key, String key2, String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        //String selectQuery = "SELECT * FROM " + tableName + " WHERE " + targetKey + " = '" + targetValue + "'";
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(table, null, key + " = ? AND " + key2 + " IS NOT NULL ", new String[]{value}, null, null, null);
        if (cursor.getCount() != 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
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

    public void insertOneKey(String table, String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(key, value);
        db.insert(table, null, contentValues);
        db.close();
    }

    public void updateOneKey(String table, String keyWhere, String valueWhere, String keyParam, String valueParam) {
        Log.d("Lihat", "updateOneKey SQLiteHelper : " + keyParam + "," + valueParam);
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
        Cursor cursor = db.query(TableTheEvent, null, Key_The_Event_EventId + " = ? ", new String[]{eventId}, Key_The_Event_EventId, null, null);

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
        Cursor cursor = db.query(TableListEvent, null, KEY_ListEvent_eventId + " = ? AND " + KEY_ListEvent_IsFirstIn + " = 1 ", new String[]{eventId}, KEY_ListEvent_eventId, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void saveImageListEventToLocal(String backgroundCachePath, String logoCachePath, UserListEventResponseModel model1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ListEvent_background_local, backgroundCachePath);
        contentValues.put(KEY_ListEvent_logo_local, logoCachePath);
        db.update(TableListEvent, contentValues, KEY_ListEvent_eventId + " = ? ", new String[]{model1.getEvent_id()});
        db.close();
    }

    public boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public void saveUserPicture(String pathImage, String accountId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_UserData_imageUrl_local, pathImage);
        db.update(TableUserData, contentValues, KEY_UserData_accountId + " = ? ", new String[]{accountId});
        db.close();
    }

    public void saveEventLogoPicture(String pathLogo, String accountId, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ListEvent_logo_local, pathLogo);
        db.update(TableListEvent, contentValues, KEY_ListEvent_accountId + " = ? AND " + KEY_ListEvent_eventId + " = ? ", new String[]{accountId, eventId});
        db.close();
    }

    public void saveEventBackgroundPicture(String pathBack, String accountId, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ListEvent_background_local, pathBack);
        db.update(TableListEvent, contentValues, KEY_ListEvent_accountId + " = ? AND " + KEY_ListEvent_eventId + " = ? ", new String[]{accountId, eventId});
        db.close();
    }

    public void saveHomeContentImage(String imageCachePath, String accountId, String eventId, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_HomeContent_image_posted_local, imageCachePath);
        db.update(TableHomeContent, contentValues, Key_HomeContent_EventId + " = ? AND " + Key_HomeContent_id + " = ? ", new String[]{eventId, id});
        db.close();
    }

    public void saveHomeContentIcon(String imageCachePath, String accountId, String eventId, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_HomeContent_image_icon_local, imageCachePath);
        db.update(TableHomeContent, contentValues, Key_HomeContent_EventId + " = ? AND " + Key_HomeContent_id + " = ? ", new String[]{eventId, id});
        db.close();
    }

    public void saveGalleryImage(String imageCachePath, String accountId, String eventId, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Gallery_imagePostedLocal, imageCachePath);
        db.update(TableGallery, contentValues, Key_Gallery_eventId + " = ? AND " + Key_Gallery_galleryId + " = ? ", new String[]{eventId, id});
        db.close();
    }

    public void saveGalleryIcon(String imageCachePath, String accountId, String eventId, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Gallery_imageIconLocal, imageCachePath);
        db.update(TableGallery, contentValues, Key_Gallery_eventId + " = ? AND " + Key_Gallery_galleryId + " = ? ", new String[]{eventId, id});
        db.close();
    }

    public void saveCommentIcon(String imageCachePath, String accountId, String eventId, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Comment_Image_icon_local, imageCachePath);
        db.update(TableComment, contentValues, Key_Comment_EventId + " = ? AND " + Key_Comment_Id + " = ? ", new String[]{eventId, id});
        db.close();
    }

    public void saveSurroundAreaImage(String imageCachePath, String accountId, String eventId, String titleImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_AreaNew_Name_image_local, imageCachePath);
        db.update(TableAreaNew, contentValues, Key_AreaNew_EventId + " = ? AND " + Key_AreaNew_Title_image + " = ? ", new String[]{eventId, titleImage});
        db.close();
    }

    public void saveImportantInfoImage(String imageCachePath, String accountId, String eventId, String titleImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key_Important_InfoNew_Name_image_local, imageCachePath);
        db.update(TableImportantInfoNew, contentValues, Key_Important_InfoNew_EventId + " = ? AND " + Key_Important_InfoNew_Title_image + " = ? ", new String[]{eventId, titleImage});
        db.close();
    }

    public void deleteAllDataHomeContent(String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableHomeContent, Key_HomeContent_EventId + " = ? ", new String[]{eventId});
        db.close();
    }

    public void deleteAllDataComment(String eventId, String postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableComment, Key_Comment_EventId + " = ? AND " + Key_Comment_PostId + " = ?", new String[]{eventId, postId});
        db.close();
    }

    public void saveEventWalletIDPicture(String imageCachePath, String accountId, String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_Wallet_idCardLocal, imageCachePath);
        db.update(TableWallet, contentValues, KEY_Wallet_accountId + " = ? AND " + KEY_Wallet_eventId + " = ? ", new String[]{accountId, eventId});
        db.close();
    }

    public void deleteAllDataGallery(String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableGallery, Key_Gallery_eventId + " = ? ", new String[]{eventId});
        db.close();
    }
}
