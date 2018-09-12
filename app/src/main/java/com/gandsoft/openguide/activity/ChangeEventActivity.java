package com.gandsoft.openguide.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.Event.EventDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserData.UserDataRequestModel;
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
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.BaseHomeActivity;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeEventActivity extends AppCompatActivity implements ChangeEventPastHook {
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
    private int version_data_user, version_data_event;
    private List<UserListEventResponseModel> menuUi = new ArrayList<>();
    private ChangeEventOnGoingAdapter adapterOnGoing;
    private ChangeEventPastAdapter adapterPast;
    private boolean isRefresh;
    private boolean isMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_event);

        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountid = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }

        initComponent();
        initContent();
        initListener();
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
        initRecycleView();
        getAPIUserDataValidation();
        initVersionDataUser();
        customText(ceTVInfofvbi);

        //getversion
        try {
            appVersionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersionCode = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appName = getResources().getString(R.string.app_name);
        appPkg = this.getBaseContext().getPackageName();
        ceTVVersionAppfvbi.setText(getString(R.string.app_name) + " - v " + appVersionName + "." + appVersionCode + "" +
                "\n Powered by Gandsoft");
    }

    private void initListener() {
        ceBUserAccountfvbi.setOnClickListener(this::Onclick);

        srlChangeEventActivityfvbi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                getAPIUserDataValidation();
            }
        });
    }

    private void getAPIUserDataValidation() {
        if (!isRefresh) {
            if (srlChangeEventActivityfvbi.isRefreshing()) {
                srlChangeEventActivityfvbi.setRefreshing(false);
            }

            if (db.isDataTableValueMultipleNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, SQLiteHelper.KEY_UserData_phoneNumber, accountid, accountid)) {
                Snackbar.make(findViewById(android.R.id.content), "Belum ada data", Snackbar.LENGTH_SHORT).show();
                if (NetworkUtil.isConnected(this)) {
                    getAPIUserDataDo(accountid);
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Memunculkan Data", Snackbar.LENGTH_SHORT).show();
                updateRecycleView(db.getAllListEvent(accountid));
                updateUserInfo(db.getUserData(accountid));
                if (NetworkUtil.isConnected(this)) {
                    getAPIUserDataDo(accountid);
                }
            }


            /*if (db.isDataTableValueMultipleNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, SQLiteHelper.KEY_UserData_phoneNumber, accountid, accountid)) {
                Snackbar.make(findViewById(android.R.id.content), "Data Kosong, bersiap mengmbil models", Snackbar.LENGTH_LONG).show();
                if (NetworkUtil.isConnected(this)) {
                    getAPIUserDataDo(accountid);
                }
            } else {
                updateRecycleView(db.getAllListEvent(accountid));
                updateUserInfo(db.getUserData(accountid));
                Snackbar.make(findViewById(android.R.id.content), "Memunculkan Data Offline", Snackbar.LENGTH_LONG).show();
            }*/
        } else {
            if (srlChangeEventActivityfvbi.isRefreshing()) {
                srlChangeEventActivityfvbi.setRefreshing(false);
            }

            if (NetworkUtil.isConnected(this)) {
                getAPIUserDataDo(accountid);
            }
        }

    }

    private void getAPIUserDataDo(String accountid) {

        UserDataRequestModel requestModel = new UserDataRequestModel();
        requestModel.setAccountid(accountid);
        requestModel.setDbver(IConfig.DB_Version);
        if (db.isDataTableKeyNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_versionData)) {
            requestModel.setVersion_data(IConfig.DB_Version);
        } else {
            requestModel.setVersion_data(db.getVersionDataIdUser(accountid));
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Get data from server");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        //ProgressDialog progressDialog = ProgressDialog.show(ChangeEventActivity.this, "Loading...", "Please Wait..", true, false);

        //code here
        API.doUserDataRet(requestModel).enqueue(new Callback<List<UserDataResponseModel>>() {
            @Override
            public void onResponse(Call<List<UserDataResponseModel>> call, Response<List<UserDataResponseModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<UserDataResponseModel> userDataResponseModels = response.body();
                    for (int i = 0; i < userDataResponseModels.size(); i++) {
                        UserDataResponseModel model = userDataResponseModels.get(i);
                        if (!model.getVersion_data().equalsIgnoreCase("last version")) {//jika bukan lastversion
                            if (db.isDataTableValueNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, accountid)) {
                                db.saveUserData(model);
                            } else {
                                db.updateUserData(model, accountid);
                            }
                            updateUserInfo(userDataResponseModels);

                            List<UserListEventResponseModel> userListEventResponseModels = model.getList_event();
                            for (int j = 0; j < userListEventResponseModels.size(); j++) {
                                UserListEventResponseModel model1 = userListEventResponseModels.get(j);
                                if (db.isDataTableValueNull(SQLiteHelper.TableListEvent, SQLiteHelper.KEY_ListEvent_eventId, model1.getEvent_id())) {
                                    db.saveListEvent(model1, accountid);
                                } else {
                                    db.updateListEvent(model1);
                                }
                                updateRecycleView(userListEventResponseModels);

                                List<UserWalletDataResponseModel> userWalletDataResponseModels = model1.getWallet_data();
                                for (int n = 0; n < userWalletDataResponseModels.size(); n++) {
                                    UserWalletDataResponseModel model2 = userWalletDataResponseModels.get(n);
                                    if (db.isDataTableValueMultipleNull(SQLiteHelper.TableWallet, SQLiteHelper.KEY_Wallet_sort, SQLiteHelper.KEY_Wallet_eventId, model2.getSort(), model1.getEvent_id())) {
                                        db.saveWalletData(model2, accountid, model1.getEvent_id());
                                    } else {
                                        db.updateWalletData(model2, model1.getEvent_id());
                                    }
                                }
                            }
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), model.getVersion_data(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserDataResponseModel>> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Tidak Dapat terhubung dengan server", Snackbar.LENGTH_LONG).setAction("Reload", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getAPIUserDataValidation();
                    }
                }).show();
                if (!db.isDataTableValueMultipleNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, SQLiteHelper.KEY_UserData_phoneNumber, accountid, accountid)) {
                    updateRecycleView(db.getAllListEvent(accountid));
                    updateUserInfo(db.getUserData(accountid));
                }
            }
        });
    }

    private void getAPIEventDataValid(String eventId) {

        if (db.isDataTableValueNull(SQLiteHelper.TableTheEvent, SQLiteHelper.Key_The_Event_EventId, eventId)) {
            Snackbar.make(findViewById(android.R.id.content), "Belum ada data", Snackbar.LENGTH_SHORT).show();
            if (NetworkUtil.isConnected(this)) {
                getAPIEventDataDo(eventId, accountid);
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Memunculkan Data", Snackbar.LENGTH_SHORT).show();
            gotoBaseHome();//validation
        }
    }

    private void getAPIEventDataDo(String eventId, String accountId) {
        EventDataRequestModel requestModel = new EventDataRequestModel();
        requestModel.setDbver(String.valueOf(IConfig.DB_Version));
        requestModel.setId_event(eventId);
        requestModel.setPass("");
        requestModel.setPhonenumber(accountId);
        requestModel.setVersion_data(0);
        /*if (db.isDataTableValueNull(SQLiteHelper.TableTheEvent, SQLiteHelper.Key_The_Event_EventId, eventId)) {
            requestModel.setVersion_data(0);
        } else {
            requestModel.setVersion_data(db.getVersionDataIdEvent(eventId));
        }*/

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Get data from server");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        API.doEventDataRet(requestModel).enqueue(new Callback<List<EventDataResponseModel>>() {
            @Override
            public void onResponse(Call<List<EventDataResponseModel>> call, Response<List<EventDataResponseModel>> response) {
                //progressDialog.dismiss();
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
                                    if (db.isDataTableValueMultipleNull(SQLiteHelper.TableContactList, SQLiteHelper.Key_Contact_List_EventId, SQLiteHelper.KEY_Contact_List_Telephone, model.getEvent_id(), dataContactList.getTelephone())) {
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
                                            if (db.isDataTableValueMultipleNull(SQLiteHelper.TableScheduleList, SQLiteHelper.Key_Schedule_List_EventId, SQLiteHelper.Key_Schedule_List_id, model.getEvent_id(), listDateDataList.getId())) {
                                                db.saveScheduleList(listDateDataList, listDate.getDate(), model.getEvent_id());
                                            } else {
                                                db.updateScheduleList(listDateDataList, listDate.getDate(), model.getEvent_id());
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
                                if (db.isDataTableValueMultipleNull(SQLiteHelper.TableArea, SQLiteHelper.Key_Area_EventId, SQLiteHelper.Key_Area_Title, model.getEvent_id(), area.getTitle())) {
                                    db.saveArea(area, model.getEvent_id());
                                } else {
                                    db.updateArea(area, model.getEvent_id());
                                }
                            }

                            for (int i9 = 0; i9 < model.getCommittee_note().size(); i9++) {
                                EventCommitteeNote note = model.getCommittee_note().get(i9);
                                if (db.isDataTableValueMultipleNull(SQLiteHelper.TableCommiteNote, SQLiteHelper.Key_CommiteNote_EventId, SQLiteHelper.Key_CommiteNote_Title, model.getEvent_id(), note.getTitle())) {
                                    db.saveCommiteNote(note, model.getEvent_id());
                                } else {
                                    db.updateCommiteNote(note, model.getEvent_id());
                                }
                            }

                            if (i == (eventDataResponseModels.size() - 1)) {
                                gotoBaseHome();//on response
                            }
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), model.getStatus(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventDataResponseModel>> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void gotoBaseHome() {
        Intent intent = new Intent(ChangeEventActivity.this, BaseHomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void initVersionDataUser() {
        if (db.isDataTableValueNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, accountid)) {
            version_data_user = 0;
        } else {
            version_data_user = db.getVersionDataIdUser(accountid);
        }
    }

    private void initVersionDataEvent() {
        if (db.isDataTableValueNull(SQLiteHelper.TableListEvent, SQLiteHelper.KEY_ListEvent_eventId, eventIdPub)) {
            version_data_event = 0;
        } else {
            version_data_event = db.getVersionDataIdEvent(eventIdPub);
        }
    }

    private void initRecycleView() {
        adapterOnGoing = new ChangeEventOnGoingAdapter(this, menuUi);
        adapterPast = new ChangeEventPastAdapter(this, menuUi);
        ceRVOngoingEventfvbi.setNestedScrollingEnabled(false);
        ceRVPastEventfvbi.setNestedScrollingEnabled(false);
        ceRVOngoingEventfvbi.setAdapter(adapterOnGoing);
        ceRVPastEventfvbi.setAdapter(adapterPast);
        ceRVOngoingEventfvbi.setLayoutManager(new LinearLayoutManager(this));
        ceRVPastEventfvbi.setLayoutManager(new LinearLayoutManager(this));
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
        adapterPast.replaceData(models);
        adapterOnGoing.replaceData(models);
    }

    private void updateUserInfo(List<UserDataResponseModel> models) {
        if (models.size() == 1) {
            for (int i = 0; i < models.size(); i++) {
                UserDataResponseModel model = models.get(i);
                ceTVUserNamefvbi.setText(model.getFull_name());
                ceTVUserIdfvbi.setText(model.getAccount_id());
                Glide.with(getApplicationContext())
                        .load(model.getImage_url())
                        .placeholder(R.drawable.template_account_og)
                        .error(R.drawable.template_account_og)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(ceIVUserPicfvbi);
            }
        }
    }

    @SuppressLint("NewApi")
    private void Onclick(View view) {
        if (view == ceBUserAccountfvbi) {
            startActivity(AccountActivity.getActIntent(ChangeEventActivity.this));
        }
    }

    @Override
    public void gotoEvent(String eventId) {
        getAPIEventDataValid(eventId);
        this.eventIdPub = eventId;
        SessionUtil.setStringPreferences(ISeasonConfig.KEY_EVENT_ID, eventId);
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
}
