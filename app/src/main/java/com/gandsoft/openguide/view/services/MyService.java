package com.gandsoft.openguide.view.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.Event.EventDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserData.GetListUserEventRequestModel;
import com.gandsoft.openguide.API.APIrequest.VerificationStatusLoginAppUserRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventAbout;
import com.gandsoft.openguide.API.APIresponse.Event.EventCommitteeNote;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContact;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventEmergencies;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfo;
import com.gandsoft.openguide.API.APIresponse.Event.EventPlaceList;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDate;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDateDataList;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingArea;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.UserData.GetListUserEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.VerificationStatusLoginAppUserResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.DateTimeUtil;
import com.gandsoft.openguide.support.DeviceDetailUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service {

    SQLiteHelper db = new SQLiteHelper(this);

    private String eventId;
    private String accountId;
    private Timer timer = new Timer();
    private int count;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(), "start service", Toast.LENGTH_SHORT).show();// Set your own toast  message

        initParam(intent);
        initSession();
        initContent();

        return super.onStartCommand(intent, flags, startId);
    }

    private void initParam(Intent intent) {

    }

    private void initSession() {
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
    }

    private void initContent() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (NetworkUtil.isConnected(getApplicationContext())) {
                    Log.d("Lihat", "run MyService : " + eventId + "," + accountId);
                    Log.d("Lihat", "run MyService : " + "connected" + count);
                    Log.d("Lihat", "run MyService : " + count++);

                    getAPIUserDataDo(accountId);
                    getAPIEventDataDo(eventId, accountId);
                    getAPICheckUser();
                }
            }
        }, 0, DateTimeUtil.MINUTE_MILLIS);
    }

    private void getAPIUserDataDo(String accountId) {

        GetListUserEventRequestModel requestModel = new GetListUserEventRequestModel();
        requestModel.setAccountid(accountId);
        requestModel.setDbver(IConfig.DB_Version);
        if (db.isDataTableValueNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, accountId)) {
            requestModel.setVersion_data(0);
        } else {
            requestModel.setVersion_data(db.getVersionDataIdUser(accountId));
        }
        Log.d("Lihat", "getAPIUserDataDo MyService : " + db.getVersionDataIdUser(accountId));

        API.doGetListUserEventRet(requestModel).enqueue(new Callback<List<GetListUserEventResponseModel>>() {
            @Override
            public void onResponse(Call<List<GetListUserEventResponseModel>> call, Response<List<GetListUserEventResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<GetListUserEventResponseModel> getListUserEventResponseModels = response.body();
                    for (int i = 0; i < getListUserEventResponseModels.size(); i++) {
                        GetListUserEventResponseModel model = getListUserEventResponseModels.get(i);
                        if (!model.getVersion_data().equalsIgnoreCase("last version")) {//jika bukan lastversion
                            Log.d("Lihat", "onResponse MyService getAPIUserDataDo: " + "ok");
                            if (db.isDataTableValueNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, accountId)) {
                                db.saveUserData(model, accountId);
                            } else {
                                db.updateUserData(model, accountId);
                            }

                            List<UserListEventResponseModel> userListEventResponseModels = model.getList_event();
                            for (int j = 0; j < userListEventResponseModels.size(); j++) {
                                UserListEventResponseModel model1 = userListEventResponseModels.get(j);
                                if (db.isDataTableValueNull(SQLiteHelper.TableListEvent, SQLiteHelper.KEY_ListEvent_eventId, model1.getEvent_id())) {
                                    db.saveListEvent(model1, accountId);
                                } else {
                                    db.updateListEvent(model1, accountId);
                                }

                                List<UserWalletDataResponseModel> userWalletDataResponseModels = model1.getWallet_data();
                                for (int n = 0; n < userWalletDataResponseModels.size(); n++) {
                                    UserWalletDataResponseModel model2 = userWalletDataResponseModels.get(n);
                                    if (db.isDataTableValueMultipleNull(SQLiteHelper.TableWallet, SQLiteHelper.KEY_Wallet_sort, SQLiteHelper.KEY_Wallet_eventId, model2.getSort(), model1.getEvent_id())) {
                                        db.saveWalletData(model2, accountId, model1.getEvent_id());
                                    } else {
                                        db.updateWalletData(model2, model1.getEvent_id());
                                    }
                                }
                            }
                        } else {
                            Log.d("Lihat", "onResponse MyService : " + model.getVersion_data());
                        }
                    }
                } else {
                    Log.d("Lihat", "onResponse MyService : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<GetListUserEventResponseModel>> call, Throwable t) {
                Log.d("Lihat", "onFailure MyService : " + t.getMessage());
            }
        });
    }

    private void getAPIEventDataDo(String eventId, String accountId) {
        EventDataRequestModel requestModel = new EventDataRequestModel();
        requestModel.setDbver("3");
        requestModel.setId_event(eventId);
        requestModel.setPass("");
        requestModel.setPhonenumber(accountId);
        //requestModel.setVersion_data(0);
        if (db.isDataTableValueNull(SQLiteHelper.TableTheEvent, SQLiteHelper.Key_The_Event_EventId, eventId)) {
            requestModel.setVersion_data(0);
        } else {
            requestModel.setVersion_data(db.getVersionDataIdEvent(eventId));
        }

        Log.d("Lihat", "getAPIEventDataDo MyService : " + db.getVersionDataIdEvent(eventId));

        API.doEventDataRet(requestModel).enqueue(new Callback<List<EventDataResponseModel>>() {
            @Override
            public void onResponse(Call<List<EventDataResponseModel>> call, Response<List<EventDataResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<EventDataResponseModel> eventDataResponseModels = response.body();
                    for (int i = 0; i < eventDataResponseModels.size(); i++) {
                        EventDataResponseModel model = eventDataResponseModels.get(i);
                        if (model.getStatus().equalsIgnoreCase("ok")) {//jika status ok
                            for (int i1 = 0; i1 < model.getThe_event().size(); i1++) {
                                EventTheEvent theEvent = model.getThe_event().get(i1);
                                if (db.isDataTableValueMultipleNull(SQLiteHelper.TableTheEvent, SQLiteHelper.Key_The_Event_EventId, SQLiteHelper.Key_The_Event_version_data, model.getEvent_id(), model.getVersion_data())) {
                                    db.saveTheEvent(theEvent, eventId, model.getFeedback_data(), model.getVersion_data());
                                } else {
                                    db.updateTheEvent(theEvent, model.getEvent_id(), model.getFeedback_data(), model.getVersion_data());
                                }
                            }

                            for (int i2 = 0; i2 < model.getPlace_list().size(); i2++) {
                                Map<Integer, List<EventPlaceList>> stringListMap = model.getPlace_list().get(i2);
                                for (Map.Entry<Integer, List<EventPlaceList>> entry : stringListMap.entrySet()) {
                                    Integer key = entry.getKey();
                                    List<EventPlaceList> values = entry.getValue();
                                    for (int i22 = 0; i22 < values.size(); i22++) {
                                        EventPlaceList placeList = values.get(i22);
                                        if (db.isDataTableValueMultipleNull(SQLiteHelper.TablePlaceList, SQLiteHelper.Key_Place_List_EventId, SQLiteHelper.Key_Place_List_title, model.getEvent_id(), placeList.getTitle())) {
                                            db.savePlaceList(placeList, model.getEvent_id());
                                        } else {
                                            db.updatePlaceList(placeList, model.getEvent_id());
                                        }
                                    }
                                }
                            }

                            for (int i3 = 0; i3 < model.getImportan_info().size(); i3++) {
                                EventImportanInfo importanInfo = model.getImportan_info().get(i3);
                                if (db.isDataTableValueMultipleNull(SQLiteHelper.TableImportantInfo, SQLiteHelper.Key_Importan_Info_EventId, SQLiteHelper.Key_Importan_Info_title, model.getEvent_id(), importanInfo.getTitle())) {
                                    db.saveImportanInfo(importanInfo, model.getEvent_id());
                                } else {
                                    db.updateImportanInfo(importanInfo, model.getEvent_id());
                                }
                            }

                            for (int i4 = 0; i4 < model.getData_contact().size(); i4++) {
                                EventDataContact dataContact = model.getData_contact().get(i4);
                                for (int i41 = 0; i41 < dataContact.getContact_list().size(); i41++) {
                                    EventDataContactList dataContactList = dataContact.getContact_list().get(i41);
                                    if (db.isDataTableValueMultipleNull(SQLiteHelper.TableContactList, SQLiteHelper.Key_Contact_List_EventId, SQLiteHelper.KEY_Contact_List_Name, model.getEvent_id(), dataContactList.getName())) {
                                        db.saveDataContactList(dataContactList, dataContact.getTitle(), model.getEvent_id());
                                    } else {
                                        db.updateDataContactList(dataContactList, dataContact.getTitle(), model.getEvent_id());
                                    }
                                }
                            }

                            for (int i5 = 0; i5 < model.getAbout().size(); i5++) {
                                EventAbout eventAbout = model.getAbout().get(i5);
                                if (db.isDataTableValueMultipleNull(SQLiteHelper.TableEventAbout, SQLiteHelper.Key_Event_About_EventId, SQLiteHelper.KEY_Event_About_Description, model.getEvent_id(), eventAbout.getDescription())) {
                                    db.saveAbout(eventAbout, model.getEvent_id());
                                } else {
                                    db.updateAbout(eventAbout, model.getEvent_id());
                                }
                            }

                            for (int i6 = 0; i6 < model.getSchedule_list().size(); i6++) {
                                Map<String, List<EventScheduleListDate>> scheduleList = model.getSchedule_list().get(i6);
                                for (Map.Entry<String, List<EventScheduleListDate>> entry : scheduleList.entrySet()) {
                                    String key = entry.getKey();
                                    List<EventScheduleListDate> value = entry.getValue();
                                    for (int i61 = 0; i61 < value.size(); i61++) {
                                        EventScheduleListDate listDate = value.get(i61);
                                        List<EventScheduleListDateDataList> value2 = listDate.getData();
                                        for (int i62 = 0; i62 < value2.size(); i62++) {
                                            EventScheduleListDateDataList listDateDataList = value2.get(i62);
                                            if (db.isDataTableValueMultipleNull(SQLiteHelper.TableScheduleList, SQLiteHelper.Key_Schedule_List_GroupCode, SQLiteHelper.Key_Schedule_List_id, key, listDateDataList.getId())) {
                                                db.saveScheduleList(listDateDataList, listDate.getDate(), model.getEvent_id(), key);
                                            } else {
                                                db.updateScheduleList(listDateDataList, listDate.getDate(), model.getEvent_id(), key);
                                            }
                                        }
                                    }
                                }
                            }

                            for (int i7 = 0; i7 < model.getEmergencies().size(); i7++) {
                                EventEmergencies emergencies = model.getEmergencies().get(i7);
                                if (db.isDataTableValueMultipleNull(SQLiteHelper.TableEmergencie, SQLiteHelper.Key_Emergencie_EventId, SQLiteHelper.Key_Emergencie_Title, model.getEvent_id(), emergencies.getTitle())) {
                                    db.saveEmergency(emergencies, model.getEvent_id());
                                } else {
                                    db.updateEmergency(emergencies, model.getEvent_id());
                                }
                            }

                            for (int i8 = 0; i8 < model.getSurrounding_area().size(); i8++) {
                                EventSurroundingArea area = model.getSurrounding_area().get(i8);
                                if (db.isDataTableValueMultipleNull(SQLiteHelper.TableArea, SQLiteHelper.Key_Area_EventId, SQLiteHelper.Key_Area_Description, model.getEvent_id(), area.getDescription())) {
                                    db.saveArea(area, model.getEvent_id());
                                } else {
                                    db.updateArea(area, model.getEvent_id());
                                }
                            }

                            for (int i9 = 0; i9 < model.getCommittee_note().size(); i9++) {
                                EventCommitteeNote note = model.getCommittee_note().get(i9);
                                if (db.isDataTableValueMultipleNull(SQLiteHelper.TableCommiteNote, SQLiteHelper.Key_CommiteNote_EventId, SQLiteHelper.Key_CommiteNote_Id, model.getEvent_id(), note.getId())) {
                                    db.saveCommiteNote(note, model.getEvent_id());
                                } else {
                                    db.updateCommiteNote(note, model.getEvent_id());
                                }
                            }
                        } else {
                            Log.d("Lihat", "onResponse MyService getAPIEventDataDo: " + "skip");
                        }
                    }
                } else {
                    Log.d("Lihat", "onResponse MyService : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<EventDataResponseModel>> call, Throwable t) {
                Log.d("Lihat", "onFailure MyService : " + t.getMessage());
            }
        });
    }

    private void getAPICheckUser() {
        VerificationStatusLoginAppUserRequestModel requestModel = new VerificationStatusLoginAppUserRequestModel();
        requestModel.setAccount_id(accountId);
        requestModel.setDevice_app(DeviceDetailUtil.getAllDataPhone2(MyService.this));
        requestModel.setDbver(String.valueOf(IConfig.DB_Version));

        API.doVerificationStatusLoginAppUserRet(new VerificationStatusLoginAppUserRequestModel(accountId, DeviceDetailUtil.getAllDataPhone2(this), String.valueOf(IConfig.DB_Version))).enqueue(new Callback<List<VerificationStatusLoginAppUserResponseModel>>() {
            @Override
            public void onResponse(Call<List<VerificationStatusLoginAppUserResponseModel>> call, Response<List<VerificationStatusLoginAppUserResponseModel>> response) {
                //progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<VerificationStatusLoginAppUserResponseModel> model = response.body();
                    if (model.size() != 0) {
                        db.setUserStillIn(accountId, true);
                    } else {
                        db.setUserStillIn(accountId, false);
                    }
                } else {
                    Log.d("Lihat", "onFailure ChangeEventActivity : " + response.message());
                    //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                    //Crashlytics.logException(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<VerificationStatusLoginAppUserResponseModel>> call, Throwable t) {
                //progressDialog.dismiss();
                Log.d("Lihat", "onFailure MyService : " + t.getMessage());
                //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                //Crashlytics.logException(new Exception(t.getMessage()));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Lihat", "onDestroy MyService : " + "destroy");
        timer.cancel();
    }
}
