package com.gandsoft.openguide.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.DeviceDetailUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;
import com.gandsoft.openguide.view.main.BaseHomeActivity;
import com.gandsoft.openguide.view.services.MyService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gandsoft.openguide.database.SQLiteHelper.KEY_UserData_accountId;
import static com.gandsoft.openguide.database.SQLiteHelper.TableUserData;

public class ChangeEventActivity extends AppCompatActivity implements ChangeEventPastHook {
    private static final int RP_ACCESS = 123;
    private static final int REQ_CODE_ACCOUNT = 1234;
    SQLiteHelper db = new SQLiteHelper(this);

    private ImageView ceIVUserPicfvbi;
    private TextView ceTVUserNamefvbi, ceTVUserIdfvbi, ceTVInfofvbi, ceTVVersionAppfvbi;
    private LinearLayout ceLLOngoingEventfvbi, ceLLPastEventfvbi;
    private RecyclerView ceRVOngoingEventfvbi, ceRVPastEventfvbi;
    private String appVersionName, appVersionCode, appName, appPkg;
    private Button ceBUserAccountfvbi;
    private String accountid, eventIdPub;
    private SwipeRefreshLayout srlChangeEventActivityfvbi;
    /**/
    private List<UserListEventResponseModel> menuUi = new ArrayList<>();
    private ChangeEventOnGoingAdapter adapterOnGoing;
    private ChangeEventPastAdapter adapterPast;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_event);

        initSession();

    }

    private void initSession() {
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID) && !SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            accountid = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
            initPermission();
        } else if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID) && SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            accountid = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
            moveToHomeBase(null);
        } else {
            AppUtil.signOutUserOtherDevice(ChangeEventActivity.this, db, MyService.class, accountid, false);
        }
    }

    private void initPermission() {
        // cek apakah sudah memiliki permission untuk access fine location
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                ) {
            // cek apakah perlu menampilkan info kenapa membutuhkan access fine location
            if (
                    ActivityCompat.shouldShowRequestPermissionRationale(ChangeEventActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(ChangeEventActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(ChangeEventActivity.this, Manifest.permission.READ_PHONE_STATE)
                    ) {
                String[] perm = {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                };
                ActivityCompat.requestPermissions(ChangeEventActivity.this, perm, RP_ACCESS);
            } else {
                // request permission untuk access fine location
                String[] perm = {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                };
                ActivityCompat.requestPermissions(ChangeEventActivity.this, perm, RP_ACCESS);
            }
        } else {
            // permission access fine location didapat
            // Toast.makeText(ChangeEventActivity.this, "Yay, has permission", Toast.LENGTH_SHORT).show();
            initComponent();
            initContent();
            initListener();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RP_ACCESS: //private final int = 1
                Log.d("Lihat", "onRequestPermissionsResult ChangeEventActivity grantResults.length : " + grantResults.length);
                Log.d("Lihat", "onRequestPermissionsResult ChangeEventActivity permissions.length : " + permissions.length);
                boolean isPerpermissionForAllGranted = false;
                ArrayList<String> listStat = new ArrayList<>();
                if (grantResults.length > 0 && permissions.length == grantResults.length) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            isPerpermissionForAllGranted = true;
                            listStat.add("1");
                        } else {
                            isPerpermissionForAllGranted = false;
                            listStat.add("0");
                        }
                    }
                } else {
                    isPerpermissionForAllGranted = true;
                }

                int frequency0 = Collections.frequency(listStat, "0");
                int frequency1 = Collections.frequency(listStat, "1");

                if (frequency1 != grantResults.length) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage("you denied some permission, you must give all permission to next proccess?");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            initPermission();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                            Snackbar.make(findViewById(android.R.id.content), "Izin tidak di berikan", Snackbar.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    initComponent();
                    initContent();
                    initListener();
                }

                /*if (!isPerpermissionForAllGranted) {
                    finish();
                    Snackbar.make(findViewById(android.R.id.content), "Izin tidak di berikan", Snackbar.LENGTH_LONG).show();
                } else {
                    initComponent();
                    initContent();
                    initListener();
                }*/
                break;
        }
    }

    private void initComponent() {
        srlChangeEventActivityfvbi = (SwipeRefreshLayout) findViewById(R.id.srlChangeEventActivity);
        ceIVUserPicfvbi = (ImageView) findViewById(R.id.ceIVUserPic);
        ceTVUserNamefvbi = (TextView) findViewById(R.id.ceTVUserName);
        ceTVUserIdfvbi = (TextView) findViewById(R.id.ceTVUserId);
        ceTVInfofvbi = (TextView) findViewById(R.id.ceTVInfo);
        ceTVVersionAppfvbi = (TextView) findViewById(R.id.ceTVVersionApp);
        ceBUserAccountfvbi = (Button) findViewById(R.id.ceBUserAccount);
        ceLLOngoingEventfvbi = (LinearLayout) findViewById(R.id.ceLLOngoingEvent);
        ceLLPastEventfvbi = (LinearLayout) findViewById(R.id.ceLLPastEvent);
        ceRVOngoingEventfvbi = (RecyclerView) findViewById(R.id.ceRVOngoingEvent);
        ceRVPastEventfvbi = (RecyclerView) findViewById(R.id.ceRVPastEvent);
    }

    private void initContent() {
        try {
            appVersionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersionCode = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appName = getResources().getString(R.string.app_name);
        appPkg = this.getBaseContext().getPackageName();
        ceTVVersionAppfvbi.setText(getString(R.string.app_name) + " - v " + appVersionName + "." + appVersionCode + "" + "\n Powered by Gandsoft");

        initRecycleView();
        getAPIUserDataValidation();
        customText(ceTVInfofvbi);
    }

    private void initRecycleView() {
        adapterOnGoing = new ChangeEventOnGoingAdapter(this, menuUi, accountid, false);
        adapterPast = new ChangeEventPastAdapter(this, menuUi, accountid, false);
        ceRVOngoingEventfvbi.setNestedScrollingEnabled(false);
        ceRVPastEventfvbi.setNestedScrollingEnabled(false);
        ceRVOngoingEventfvbi.setAdapter(adapterOnGoing);
        ceRVPastEventfvbi.setAdapter(adapterPast);
        ceRVOngoingEventfvbi.setLayoutManager(new LinearLayoutManager(this));
        ceRVPastEventfvbi.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListener() {
        ceBUserAccountfvbi.setOnClickListener(this::Onclick);

        srlChangeEventActivityfvbi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAPIUserDataValidation();
            }
        });

        ceIVUserPicfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnectedIsOnline(ChangeEventActivity.this)) {
                    getAPICheckStatusLoginUser();
                }
            }
        });
    }

    private void getAPIUserDataValidation() {
        if (srlChangeEventActivityfvbi.isRefreshing()) {
            srlChangeEventActivityfvbi.setRefreshing(false);
        }

        if (db.getAllUserData(accountid).size() == 0) {
            progressDialog = SystemUtil.showProgress(ChangeEventActivity.this, "Get data from server", "Please Wait...");
            getAPIUserDataDo(accountid);
        } else {
            getAPIUserDataDoLocal(accountid);

            if (NetworkUtil.isConnected(getApplicationContext())) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //code here
                        progressDialog = SystemUtil.showProgress(ChangeEventActivity.this, "Get data from server", "Please Wait...");
                        getAPIUserDataDo(accountid);
                    }
                }, 1000);
            }

        }

    }

    private void getAPIUserDataDo(String accountId) {
        if (NetworkUtil.isConnected(getApplicationContext())) {
            GetListUserEventRequestModel requestModel = new GetListUserEventRequestModel();
            requestModel.setAccountid(accountId);
            requestModel.setDbver(IConfig.DB_Version);

            requestModel.setVersion_data(IConfig.DB_Version);
            /*if (db.isDataTableKeyNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_versionData)) {
                requestModel.setVersion_data(IConfig.DB_Version);
            } else {
                requestModel.setVersion_data(db.getVersionDataIdUser(accountId));
            }*/

            //code here
            API.doGetListUserEventRet(requestModel).enqueue(new Callback<List<GetListUserEventResponseModel>>() {
                @Override
                public void onResponse(Call<List<GetListUserEventResponseModel>> call, Response<List<GetListUserEventResponseModel>> response) {
                    SystemUtil.hideProgress(progressDialog, 0);
                    if (response.isSuccessful()) {
                        List<GetListUserEventResponseModel> getListUserEventResponseModels = response.body();
                        for (int i = 0; i < getListUserEventResponseModels.size(); i++) {
                            GetListUserEventResponseModel model = getListUserEventResponseModels.get(i);
                            if (!model.getVersion_data().equalsIgnoreCase("last version")) {//jika bukan lastversion
                                if (db.isDataTableValueNull(TableUserData, KEY_UserData_accountId, accountId)) {
                                    db.saveUserData(model, accountId);
                                } else {
                                    db.updateUserData(model, accountId);
                                }

                                List<UserListEventResponseModel> userListEventResponseModels = model.getList_event();
                                for (int j = 0; j < userListEventResponseModels.size(); j++) {
                                    UserListEventResponseModel model1 = userListEventResponseModels.get(j);
                                    if (db.isDataTableValueMultipleNull(SQLiteHelper.TableListEvent, SQLiteHelper.KEY_ListEvent_eventId, SQLiteHelper.KEY_ListEvent_accountId, model1.getEvent_id(), accountId)) {
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
                                Snackbar.make(findViewById(android.R.id.content), model.getVersion_data(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                        getAPIUserDataDoLocal(accountId);
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<GetListUserEventResponseModel>> call, Throwable t) {
                    SystemUtil.hideProgress(progressDialog, 0);
                    Log.d("Lihat", "onFailure ChangeEventActivity : " + t.getMessage());
                    Snackbar.make(findViewById(android.R.id.content), "Tidak Dapat terhubung dengan server", Snackbar.LENGTH_INDEFINITE).setAction("Reload", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getAPIUserDataValidation();
                        }
                    }).show();
                    if (!db.isDataTableValueMultipleNull(TableUserData, KEY_UserData_accountId, SQLiteHelper.KEY_UserData_phoneNumber, accountId, accountId)) {
                        getAPIUserDataDoLocal(accountId);
                    }
                }
            });
        } else {
            progressDialog.dismiss();
            Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void getAPIUserDataDoLocal(String accountId) {
        updateUserInfo(db.getOneUserData(accountId));
        updateRecycleView(db.getAllListEvent(accountId));
    }

    private void getAPITheEventDataValid(String eventId) {

        if (db.isDataTableValueNull(SQLiteHelper.TableTheEvent, SQLiteHelper.Key_The_Event_EventId, eventId)) {
            Snackbar.make(findViewById(android.R.id.content), "Event data empty", Snackbar.LENGTH_SHORT).show();
            if (NetworkUtil.isConnected(this)) {
                progressDialog = SystemUtil.showProgress(ChangeEventActivity.this, "Please Wait...", "Get data from server");
                getAPITheEventDataDo(eventId, accountid, false);
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Show event data", Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    moveToHomeBase(eventId);//validation
                }
            }, 1000);
        }
    }

    private void getAPITheEventDataDo(String eventId, String accountId, boolean isLoading) {
        EventDataRequestModel requestModel = new EventDataRequestModel();
        requestModel.setDbver("3");
        requestModel.setId_event(eventId);
        requestModel.setPass("");
        requestModel.setPhonenumber(accountId);
        requestModel.setVersion_data(IConfig.DB_Version);
        /*if (db.isDataTableValueNull(SQLiteHelper.TableTheEvent, SQLiteHelper.Key_The_Event_EventId, event_Id)) {
            requestModel.setVersion_data(0);
        } else {
            requestModel.setVersion_data(db.getVersionDataIdEvent(event_Id));
        }*/

        API.doEventDataRet(requestModel).enqueue(new Callback<List<EventDataResponseModel>>() {
            @Override
            public void onResponse(Call<List<EventDataResponseModel>> call, Response<List<EventDataResponseModel>> response) {
                SystemUtil.hideProgress(progressDialog, 0);
                if (response.isSuccessful()) {
                    List<EventDataResponseModel> eventDataResponseModels = response.body();
                    for (int i = 0; i < eventDataResponseModels.size(); i++) {
                        EventDataResponseModel model = eventDataResponseModels.get(i);
                        if (model.getStatus().equalsIgnoreCase("ok")) {//jika status ok
                            for (int i1 = 0; i1 < model.getThe_event().size(); i1++) {
                                EventTheEvent theEvent = model.getThe_event().get(i1);
                                if (db.isDataTableValueMultipleNull(SQLiteHelper.TableTheEvent, SQLiteHelper.Key_The_Event_EventId, SQLiteHelper.Key_The_Event_version_data, model.getEvent_id(), model.getVersion_data())) {
                                    db.saveTheEvent(theEvent, model.getEvent_id(), model.getFeedback_data(), model.getVersion_data());
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
                            Snackbar.make(findViewById(android.R.id.content), model.getStatus(), Snackbar.LENGTH_LONG).show();
                        }

                        if (i == (eventDataResponseModels.size() - 1)) {
                            moveToHomeBase(eventId);//on response
                        }
                    }
                } else {
                    Log.d("Lihat", "onResponse ChangeEventActivity : " + response.message());
                    Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventDataResponseModel>> call, Throwable t) {
                SystemUtil.hideProgress(progressDialog, 0);
                Log.d("Lihat", "onFailure ChangeEventActivity : " + t.getMessage());
                Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                /*Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).setAction("Reload", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();*/
                //Crashlytics.logException(new Exception(t.getMessage()));

            }
        });
    }

    private void getAPICheckStatusLoginUser() {
        VerificationStatusLoginAppUserRequestModel requestModel = new VerificationStatusLoginAppUserRequestModel();
        requestModel.setAccount_id(accountid);
        requestModel.setDevice_app(DeviceDetailUtil.getAllDataPhone2(ChangeEventActivity.this));
        requestModel.setDbver(String.valueOf(IConfig.DB_Version));

        API.doVerificationStatusLoginAppUserRet(requestModel).enqueue(new Callback<List<VerificationStatusLoginAppUserResponseModel>>() {
            @Override
            public void onResponse(Call<List<VerificationStatusLoginAppUserResponseModel>> call, Response<List<VerificationStatusLoginAppUserResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<VerificationStatusLoginAppUserResponseModel> model = response.body();
                    if (model.size() == 0) {
                        AppUtil.signOutUserOtherDevice(ChangeEventActivity.this, db, MyService.class, accountid, false);
                    }
                } else {
                    Log.d("Lihat", "onFailure ChangeEventActivity : " + response.message());
                    //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                    //Crashlytics.logException(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<VerificationStatusLoginAppUserResponseModel>> call, Throwable t) {
                Log.d("Lihat", "onFailure ChangeEventActivity : " + t.getMessage());
                //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                //Crashlytics.logException(new Exception(t.getMessage()));
            }
        });
    }

    private void updateUserInfo(GetListUserEventResponseModel model) {
        ceTVUserNamefvbi.setText(model.getFull_name());
        ceTVUserIdfvbi.setText(model.getAccount_id());

        String stringImageIcon = AppUtil.validationStringImageIcon(ChangeEventActivity.this, model.getImage_url(), model.getImage_url_local(), true);

        Glide.with(this)
                .load(R.drawable.load)
                .asGif()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .into(ceIVUserPicfvbi);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //code here
                Glide.with(getApplicationContext())
                        .load(InputValidUtil.isLinkUrl(stringImageIcon) ? stringImageIcon : new File(stringImageIcon))
                        .asBitmap()
                        .error(R.drawable.template_account_og)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(false)
                        .dontAnimate()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                ceIVUserPicfvbi.setImageBitmap(resource);
                                if (NetworkUtil.isConnected(getApplicationContext())) {
                                    String imageCachePath = PictureUtil.saveImageLogoBackIcon(getApplicationContext(), resource, "user_image" + accountid);
                                    db.saveUserPicture(imageCachePath, accountid);
                                }
                            }
                        });
            }
        }, 500);



    }

    private void updateRecycleView(List<UserListEventResponseModel> models) {
        for (int i = 0; i < models.size(); i++) {
            UserListEventResponseModel model = models.get(i);
            if (model.getStatus().equalsIgnoreCase("PAST EVENT")) {
                ceLLPastEventfvbi.setVisibility(View.VISIBLE);

            } else if (model.getStatus().equalsIgnoreCase("ONGOING EVENT")) {
                ceLLOngoingEventfvbi.setVisibility(View.VISIBLE);
            }
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //code here
                adapterPast.replaceData(models);
                adapterOnGoing.replaceData(models);
            }
        }, 500);
    }

    @SuppressLint("NewApi")
    private void Onclick(View view) {
        if (view == ceBUserAccountfvbi) {
            moveToAccount();
        }
    }

    private void moveToHomeBase(@Nullable String eventId) {
        if (!TextUtils.isEmpty(eventId)) {
            SessionUtil.setStringPreferences(ISeasonConfig.KEY_EVENT_ID, eventId);
        }
        Intent intent = new Intent(ChangeEventActivity.this, BaseHomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void moveToAccount() {
        Intent intent = new Intent(ChangeEventActivity.this, AccountActivity.class);
        startActivityForResult(intent, REQ_CODE_ACCOUNT);
    }

    @Override
    public void gotoEvent(String eventId) {
        getAPITheEventDataValid(eventId);
    }

    private void customText(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder("To create an event, please contact our phone number: ");

        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 10, spanTxt.length(), 0);
        spanTxt.append("+62 21 53661536");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "+62 21 53661536 Clicked", Toast.LENGTH_SHORT).show();
                String phone = "+622153661536";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        }, spanTxt.length() - "+62 21 53661536".length(), spanTxt.length(), 0);

        spanTxt.append(" or by email at: ");

        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 10, spanTxt.length(), 0);
        spanTxt.append("hello@gandsoft.com");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "hello@gandsoft.com Clicked", Toast.LENGTH_SHORT).show();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "hello@gandsoft.com")); //alamat email tujuan
                startActivity(Intent.createChooser(emailIntent, "Pilih Aplikasi Email"));
            }
        }, spanTxt.length() - "hello@gandsoft.com".length(), spanTxt.length(), 0);

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, ChangeEventActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ACCOUNT && resultCode == RESULT_OK) {
            getAPIUserDataValidation();
        }
    }
}
