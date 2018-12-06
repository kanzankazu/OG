package com.gandsoft.openguide.view;

import android.Manifest;
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
import android.view.Gravity;
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
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfoNew;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfoNewDetail;
import com.gandsoft.openguide.API.APIresponse.Event.EventPlaceList;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDate;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDateDataList;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingArea;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingAreaNew;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingAreaNewDetail;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.UserData.GetListUserEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.VerificationStatusLoginAppUserResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.DateTimeUtil;
import com.gandsoft.openguide.support.DeviceDetailUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;
import com.gandsoft.openguide.view.main.BaseHomeActivity;
import com.gandsoft.openguide.view.services.RepeatCheckDataService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gandsoft.openguide.database.SQLiteHelper.KEY_Contact_List_Name;
import static com.gandsoft.openguide.database.SQLiteHelper.KEY_Event_About_Description;
import static com.gandsoft.openguide.database.SQLiteHelper.KEY_ListEvent_accountId;
import static com.gandsoft.openguide.database.SQLiteHelper.KEY_ListEvent_eventId;
import static com.gandsoft.openguide.database.SQLiteHelper.KEY_UserData_accountId;
import static com.gandsoft.openguide.database.SQLiteHelper.KEY_UserData_phoneNumber;
import static com.gandsoft.openguide.database.SQLiteHelper.KEY_Wallet_eventId;
import static com.gandsoft.openguide.database.SQLiteHelper.KEY_Wallet_sort;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_AreaNew_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_AreaNew_Title_image;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Area_Description;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Area_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_CommiteNote_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_CommiteNote_Id;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Contact_List_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Emergencie_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Emergencie_Title;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Event_About_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Importan_Info_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Importan_Info_title;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Important_InfoNew_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Important_InfoNew_Title_image;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Important_InfoNew_title;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Place_List_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Place_List_title;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Schedule_List_GroupCode;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Schedule_List_id;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_The_Event_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_The_Event_version_data;
import static com.gandsoft.openguide.database.SQLiteHelper.TableArea;
import static com.gandsoft.openguide.database.SQLiteHelper.TableAreaNew;
import static com.gandsoft.openguide.database.SQLiteHelper.TableCommiteNote;
import static com.gandsoft.openguide.database.SQLiteHelper.TableContactList;
import static com.gandsoft.openguide.database.SQLiteHelper.TableEmergencie;
import static com.gandsoft.openguide.database.SQLiteHelper.TableEventAbout;
import static com.gandsoft.openguide.database.SQLiteHelper.TableImportantInfo;
import static com.gandsoft.openguide.database.SQLiteHelper.TableImportantInfoNew;
import static com.gandsoft.openguide.database.SQLiteHelper.TableListEvent;
import static com.gandsoft.openguide.database.SQLiteHelper.TablePlaceList;
import static com.gandsoft.openguide.database.SQLiteHelper.TableScheduleList;
import static com.gandsoft.openguide.database.SQLiteHelper.TableTheEvent;
import static com.gandsoft.openguide.database.SQLiteHelper.TableUserData;
import static com.gandsoft.openguide.database.SQLiteHelper.TableWallet;

public class ChangeEventActivity extends AppCompatActivity implements ChangeEventPastHook {
    private static final int RP_ACCESS = 123;
    private static final int REQ_CODE_ACCOUNT = 1234;
    SQLiteHelperMethod db = new SQLiteHelperMethod(this);

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
    private boolean loadingResult;
    private Timer timerLoopCheckUser;

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
            moveToTheEvent(null);
        } else if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountid = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
            initPermission();
        } else {
            AppUtil.signOutUserOtherDevice(ChangeEventActivity.this, db, RepeatCheckDataService.class, accountid, false);
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
            // SystemUtil.showToast(ChangeEventActivity.this, "Yay, has permission", Toast.LENGTH_SHORT,Gravity.TOP);
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
                            SystemUtil.showToast(getApplicationContext(), "Izin tidak di berikan", Toast.LENGTH_LONG, Gravity.TOP);
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
                    SystemUtil.showToast(getApplicationContext(), "Izin tidak di berikan", Snackbar.LENGTH_LONG,Gravity.TOP);
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

        getAPIUserDataDoValid();

        customText(ceTVInfofvbi);
    }

    private void initRecycleView() {
        adapterOnGoing = new ChangeEventOnGoingAdapter(this, menuUi, accountid);
        adapterPast = new ChangeEventPastAdapter(this, menuUi, accountid);
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
                getAPIUserDataDoValid();
            }
        });

        ceIVUserPicfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Lihat", "onClick ChangeEventActivity NetworkUtil.isConnected: " + NetworkUtil.isConnected(ChangeEventActivity.this));

                Date firstDateOfMonth = DateTimeUtil.getStart(DateTimeUtil.stringToDate("01/01/" + DateTimeUtil.getYearCurrent(), new SimpleDateFormat("dd/MM/yyyy")));
                Date endDateOfMonth = DateTimeUtil.getEndDateOfMonth(DateTimeUtil.stringToDate("01/12/" + DateTimeUtil.getYearCurrent() + " 23:59:59", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));
                Log.d("Lihat", "onClick ChangeEventActivity : " + firstDateOfMonth);
                Log.d("Lihat", "onClick ChangeEventActivity : " + endDateOfMonth);

                Log.d("Lihat", "onClick ChangeEventActivity : " + DateTimeUtil.getDayBetween2Date(firstDateOfMonth, endDateOfMonth));
                Log.d("Lihat", "onClick ChangeEventActivity : " + DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth));

                int posDateInListDate = DateTimeUtil.getPosDateInListDate(DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth), DateTimeUtil.currentDate());
                Log.d("Lihat", "onClick ChangeEventActivity : " + posDateInListDate);
                Log.d("Lihat", "onClick ChangeEventActivity : " + DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth).get(posDateInListDate));

                String s = "<u>Registration:</u><br>Bima room (1st Floor – MICC) Group Registration area (please look for a signage for your ease reference)<br><br>Note: Upon arrival at the hotel, please make your way to the 'Group Registration Area'. You will be given your room information and IDbadge at the registration.<br><br><u>Workshop Venue:</u><br>Ballroom A&B (2nd Floor – MICC)<br><br><u>Breakfast:</u><br>Andrawina Restaurant (Hotel Lobby Area)<br><br><u>Lunch:</u><br>Grand Ballroom Foyer (2nd Floor – MICC)<br><br><u>Dinner Day 1:</u><br>Boogeys Restaurant – Grand Hyatt Hotel<br><br><u>Gala Dinner:</u><br>Ballroom B&C (2nd Floor – MICC)<br><br><u>Friday Pray:</u><br>Masjid Pendopo – Ground Floor<br><br><u>Medical:</u><br>Holding Room (2nd Floor – MICC)<br><br><span>Indoor Map - First Floor</span><img src=http://api.openguides.id:3000/get_list_image?im=1st-floor&s=event/66989c6a-0bda-11e8-8536-08002753cf51/info><br>A. Yudhistira<br>B. Bima<br>C. Smooking Room<br><span>Indoor Map - Second Floor</span><img src=http://api.openguides.id:3000/get_list_image?im=2nd-floor&s=event/66989c6a-0bda-11e8-8536-08002753cf51/info><br>A. Workshop (Ballroom A+B)<br>B. Gala Dinner (Ballroom B+C)<br>C. Smooking Area<br>D. Medic Room<br>E. Ballroom Foyer<br>F. Smooking Area (Drupadi Room)<br>G. Smooking Area (Adrawina Terrace)<br><span>Indoor Map - Zoning</span><img src=http://api.openguides.id:3000/get_list_image?im=alana-zoning&s=event/66989c6a-0bda-11e8-8536-08002753cf51/info><br>A. Alana Hotel Entrance<br>B. MICC Ballroom Drop Off Area<br>C. Secretariat Room (Yudhistira 1st Floor)<br>D. Registration Area (Bima 1st floor)<br>E. Medic Room (Holding Room 2nd Floor)<br>F. Workshop Ballroom (Ballroom A+B 2nd Floor)<br>G. Gala DInner Ballroom (Ballroom B+C 2nd Floor)<br>H. Hotel Lobby Drop Off<br>I. Adrawina Restaurant<br>J. Team Building Area 1<br>K. Team Building Area 2";
                String target = "<span>";
                String target2 = "</span>";
                Log.d("Lihat", "onClick ChangeEventActivity : " + s.indexOf(target));

                String regexSpan = "<span>(.+?)</span>";
                String regexImg = "<img src=(.+?)>";

                Log.d("Lihat", "onClick ChangeEventActivity : " + s.replaceAll(regexImg, "<a>"));
                Log.d("Lihat", "onClick ChangeEventActivity : " + s.replaceAll(regexSpan, "=#="));
                Log.d("Lihat", "onClick ChangeEventActivity : " + SystemUtil.getTagValues(s, regexSpan));
                Log.d("Lihat", "onClick ChangeEventActivity : " + SystemUtil.getTagValues(s, regexImg));
                //Log.d("Lihat", "onClick ChangeEventActivity : " + s.substring(s.indexOf(target3), s.indexOf(target3) + target3.length()));

                List<String> tagSpan = SystemUtil.getTagValues(s, regexSpan);
                List<String> tagImg = SystemUtil.getTagValues(s, regexImg);

                String s1 = s.replaceAll(regexImg, "<a>");
                String s2 = s1.replaceAll(regexSpan, "=#=");
                String s3 = s2.replaceAll("\"", "");
                String[] split = s3.split("=#=");

                for (int i = 0; i < split.length; i++) {
                    Log.d("Lihat", "onClick ChangeEventActivity : " + split[i]);
                }
                Log.d("Lihat", "onClick ChangeEventActivity : " + split.length);
                Log.d("Lihat", "onClick ChangeEventActivity : " + tagSpan.size());
            }
        });
    }

    private void getAPIUserDataDoValid() {
        if (srlChangeEventActivityfvbi.isRefreshing()) {
            srlChangeEventActivityfvbi.setRefreshing(false);
        }

        if (db.getAllUserData(accountid).size() == 0) {
            if (!loadingResult) {
                progressDialog = SystemUtil.showProgress(ChangeEventActivity.this, "Get data from server", "Please Wait...", false);
                loadingResult = true;
            }
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //code here
                    getAPIUserDataDo(accountid);
                }
            }, 1000);
        } else {
            if (!loadingResult) {
                progressDialog = SystemUtil.showProgress(ChangeEventActivity.this, "Get data from server", "Please Wait...", false);
                loadingResult = true;
            }
            updateUserDataEvent(accountid, false);

            if (NetworkUtil.isConnected(getApplicationContext())) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //code here
                        getAPIUserDataDo(accountid);
                    }
                }, 1000);
            } else {
                progressDialog.dismiss();
            }
        }
    }

    private void getAPIUserDataDo(String accountId) {
        if (NetworkUtil.isConnected(getApplicationContext())) {
            GetListUserEventRequestModel requestModel = new GetListUserEventRequestModel();
            requestModel.setAccountid(accountId);
            requestModel.setDbver(IConfig.DB_Version);

            requestModel.setVersion_data(IConfig.DB_Version);
            /*if (db.isDataTableKeyNull(TableUserData, KEY_UserData_versionData)) {
                requestModel.setVersion_data(0);
            } else {
                requestModel.setVersion_data(db.getVersionDataIdUser(accountId));
            }*/

            //code here
            API.doGetListUserEventRet(requestModel).enqueue(new Callback<List<GetListUserEventResponseModel>>() {
                @Override
                public void onResponse(Call<List<GetListUserEventResponseModel>> call, Response<List<GetListUserEventResponseModel>> response) {
                    SystemUtil.hideProgress(progressDialog, 2000);
                    loadingResult = false;
                    if (response.isSuccessful()) {
                        List<GetListUserEventResponseModel> getListUserEventResponseModels = response.body();
                        for (int i = 0; i < getListUserEventResponseModels.size(); i++) {
                            GetListUserEventResponseModel model = getListUserEventResponseModels.get(i);
                            if (model.getVersion_data().equalsIgnoreCase("last version")) {//jika bukan lastversion
                                SystemUtil.showToast(getApplicationContext(), model.getVersion_data(), Toast.LENGTH_LONG, Gravity.TOP);
                            } else if (model.getVersion_data().equalsIgnoreCase("error account")) {
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        //code here
                                        AppUtil.signOutFull(ChangeEventActivity.this, db, false, accountId);
                                    }
                                }, 2500);
                            } else {
                                getAPICheckStatusLoginUser();

                                if (db.isDataTableValueMultipleNull(TableUserData, KEY_UserData_accountId, KEY_UserData_phoneNumber, accountId, model.getPhone_number())) {
                                    db.saveUserData(model, accountId);
                                } else {
                                    db.updateUserData(model, accountId);
                                }

                                List<UserListEventResponseModel> userListEventResponseModels = model.getList_event();
                                for (int j = 0; j < userListEventResponseModels.size(); j++) {
                                    UserListEventResponseModel model1 = userListEventResponseModels.get(j);
                                    if (db.isDataTableValueMultipleNull(TableListEvent, KEY_ListEvent_eventId, KEY_ListEvent_accountId, model1.getEvent_id(), accountId)) {
                                        db.saveListEvent(model1, accountId);
                                    } else {
                                        db.updateListEvent(model1, accountId);
                                    }

                                    List<UserWalletDataResponseModel> userWalletDataResponseModels = model1.getWallet_data();
                                    for (int n = 0; n < userWalletDataResponseModels.size(); n++) {
                                        UserWalletDataResponseModel model2 = userWalletDataResponseModels.get(n);
                                        if (db.isDataTableValueMultipleNull(TableWallet, KEY_Wallet_sort, KEY_Wallet_eventId, model2.getSort(), model1.getEvent_id())) {
                                            db.saveWalletData(model2, accountId, model1.getEvent_id());
                                        } else {
                                            db.updateWalletData(model2, model1.getEvent_id());
                                        }
                                    }
                                }
                            }

                            if (i == (getListUserEventResponseModels.size() - 1)) {
                                if (db.getAllUserData(accountid).size() != 0) {
                                    updateUserDataEvent(accountId, true);
                                }
                            }
                        }
                    } else {
                        SystemUtil.showToast(getApplicationContext(), response.message(), Toast.LENGTH_LONG, Gravity.TOP);
                    }
                }

                @Override
                public void onFailure(Call<List<GetListUserEventResponseModel>> call, Throwable t) {
                    SystemUtil.hideProgress(progressDialog, 2000);
                    loadingResult = false;
                    Log.d("Lihat", "onFailure ChangeEventActivity : " + t.getMessage());
                    Snackbar.make(findViewById(android.R.id.content), "Tidak Dapat terhubung dengan server", Snackbar.LENGTH_LONG).setAction("Reload", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getAPIUserDataDoValid();
                        }
                    }).show();
                    if (!db.isDataTableValueMultipleNull(TableUserData, KEY_UserData_accountId, KEY_UserData_phoneNumber, accountId, accountId)) {
                        updateUserDataEvent(accountId, false);
                    }

                    getAPICheckStatusLoginUser();
                }
            });
        } else {
            progressDialog.dismiss();
            SystemUtil.showToast(getApplicationContext(), "Check you connection", Toast.LENGTH_SHORT, Gravity.TOP);
        }

    }

    private void updateUserDataEvent(String accountId, boolean isPreferUrl) {
        updateUserInfo(db.getOneUserData(accountId), isPreferUrl);
        updateRecycleView(db.getAllListEvent(accountId), isPreferUrl);
    }

    private void updateUserInfo(GetListUserEventResponseModel model, boolean isPreferUrl) {
        ceTVUserNamefvbi.setText(model.getFull_name());
        ceTVUserIdfvbi.setText(model.getAccount_id());

        /*Glide.with(this)
                .load(R.drawable.load)
                .asGif()
                .override(180, 180)
                .into(ceIVUserPicfvbi);*/

        String stringImageIcon = AppUtil.validationStringImageIcon(ChangeEventActivity.this, model.getImage_url(), model.getImage_url_local(), isPreferUrl);
        //code here
        Glide.with(getApplicationContext())
                .load(InputValidUtil.isLinkUrl(stringImageIcon) ? stringImageIcon : new File(stringImageIcon))
                .asBitmap()
                .error(R.drawable.template_account_og)
                .placeholder(R.drawable.ic_action_name)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ceIVUserPicfvbi.setImageBitmap(resource);
                        if (NetworkUtil.isConnected(getApplicationContext()) && isPreferUrl) {
                            String imageCachePath = PictureUtil.saveImageLogoBackIcon(getApplicationContext(), resource, "user_image" + accountid);
                            db.saveUserPicture(imageCachePath, accountid);
                        }
                    }
                });

    }

    private void updateRecycleView(List<UserListEventResponseModel> models, boolean isPreferUrl) {
        for (int i = 0; i < models.size(); i++) {
            UserListEventResponseModel model = models.get(i);
            if (model.getStatus().equalsIgnoreCase("PAST EVENT")) {
                ceLLPastEventfvbi.setVisibility(View.VISIBLE);

            } else if (model.getStatus().equalsIgnoreCase("ONGOING EVENT")) {
                ceLLOngoingEventfvbi.setVisibility(View.VISIBLE);
            }
        }

        //code here
        adapterPast.replaceData(models, isPreferUrl);
        adapterOnGoing.replaceData(models, isPreferUrl);
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
                        AppUtil.signOutUserOtherDevice(ChangeEventActivity.this, db, RepeatCheckDataService.class, accountid, false);
                    }
                } else {
                    Log.d("Lihat", "onFailure ChangeEventActivity : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<VerificationStatusLoginAppUserResponseModel>> call, Throwable t) {
                Log.d("Lihat", "onFailure ChangeEventActivity : " + t.getMessage());
                //SystemUtil.showToast(getApplicationContext(), "Failed Connection To Server", Toast.LENGTH_SHORT,Gravity.TOP);
                //Crashlytics.logException(new Exception(t.getMessage()));
            }
        });
    }

    private void Onclick(View view) {
        if (view == ceBUserAccountfvbi) {
            moveToAccount();
        }
    }

    private void moveToAccount() {
        Intent intent = new Intent(ChangeEventActivity.this, AccountActivity.class);
        startActivityForResult(intent, REQ_CODE_ACCOUNT);
    }

    private void customText(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder("To create an event, please contact our phone number: ");

        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 10, spanTxt.length(), 0);
        spanTxt.append("+62 21 53661536");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //SystemUtil.showToast(getApplicationContext(), "+62 21 53661536 Clicked", Toast.LENGTH_SHORT,Gravity.TOP);
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
                //SystemUtil.showToast(getApplicationContext(), "hello@gandsoft.com Clicked", Toast.LENGTH_SHORT,Gravity.TOP);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "hello@gandsoft.com")); //alamat email tujuan
                startActivity(Intent.createChooser(emailIntent, "Pilih Aplikasi Email"));
            }
        }, spanTxt.length() - "hello@gandsoft.com".length(), spanTxt.length(), 0);

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void goToTheEvent(String eventId) {
        getAPITheEventDataValid(eventId);
    }

    private void getAPITheEventDataValid(String eventId) {
        if (NetworkUtil.isConnected(ChangeEventActivity.this)) {
            progressDialog = SystemUtil.showProgress(ChangeEventActivity.this, "Get data from server", "Please Wait...", false);
            getAPITheEventDataDo(eventId, accountid, false);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (!db.isDataTableValueNull(TableTheEvent, Key_The_Event_EventId, eventId)) {
                        moveToTheEvent(eventId);//validation
                    } else {
                        SystemUtil.showToast(getApplicationContext(), "Empty Data", Toast.LENGTH_SHORT, Gravity.TOP);
                    }
                }
            }, 1000);
        }

        /*if (db.isDataTableValueNull(TableTheEvent, Key_The_Event_EventId, eventId)) {
            Snackbar.make(findViewById(android.R.id.content), "Event data empty", Snackbar.LENGTH_SHORT).show();
            if (NetworkUtil.isConnected(this)) {
                progressDialog = SystemUtil.showProgress(ChangeEventActivity.this, "Get data from server", "Please Wait...", false);
                getAPITheEventDataDo(eventId, accountid, false);
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Show event data", Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    moveToHomeBase(eventId);//validation
                }
            }, 1000);
        }*/
    }

    private void getAPITheEventDataDo(String eventId, String accountId, boolean isLoading) {
        EventDataRequestModel requestModel = new EventDataRequestModel();
        requestModel.setDbver("3");
        requestModel.setId_event(eventId);
        requestModel.setPass("");
        requestModel.setPhonenumber(accountId);
        requestModel.setVersion_data(IConfig.DB_Version);
        /*if (db.isDataTableValueNull(TableTheEvent, Key_The_Event_EventId, event_Id)) {
            requestModel.setVersion_data(0);
        } else {
            requestModel.setVersion_data(db.getVersionDataIdEvent(event_Id));
        }*/

        API.doEventDataRet(requestModel).enqueue(new Callback<List<EventDataResponseModel>>() {
            @Override
            public void onResponse(Call<List<EventDataResponseModel>> call, Response<List<EventDataResponseModel>> response) {
                SystemUtil.hideProgress(progressDialog, 2000);
                loadingResult = false;
                if (response.isSuccessful()) {
                    List<EventDataResponseModel> eventDataResponseModels = response.body();
                    for (int i = 0; i < eventDataResponseModels.size(); i++) {
                        EventDataResponseModel model = eventDataResponseModels.get(i);
                        if (model.getStatus().equalsIgnoreCase("ok")) {//jika status ok
                            for (int i1 = 0; i1 < model.getThe_event().size(); i1++) {
                                EventTheEvent theEvent = model.getThe_event().get(i1);
                                if (db.isDataTableValueMultipleNull(TableTheEvent, Key_The_Event_EventId, Key_The_Event_version_data, model.getEvent_id(), model.getVersion_data())) {
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
                                        if (db.isDataTableValueMultipleNull(TablePlaceList, Key_Place_List_EventId, Key_Place_List_title, model.getEvent_id(), placeList.getTitle())) {
                                            db.savePlaceList(placeList, model.getEvent_id());
                                        } else {
                                            db.updatePlaceList(placeList, model.getEvent_id());
                                        }
                                    }
                                }
                            }

                            for (int i3 = 0; i3 < model.getImportan_info().size(); i3++) {
                                EventImportanInfo importanInfo = model.getImportan_info().get(i3);
                                if (db.isDataTableValueMultipleNull(TableImportantInfo, Key_Importan_Info_EventId, Key_Importan_Info_title, model.getEvent_id(), importanInfo.getTitle())) {
                                    db.saveImportanInfo(importanInfo, model.getEvent_id());
                                } else {
                                    db.updateImportanInfo(importanInfo, model.getEvent_id());
                                }
                            }
                            for (int i3 = 0; i3 < model.getImportan_info_new().size(); i3++) {
                                EventImportanInfoNew importanInfoNew = model.getImportan_info_new().get(i3);
                                if (importanInfoNew.getDetail().size() == 0) {
                                    if (db.isDataTableValueMultipleNull(TableImportantInfoNew, Key_Important_InfoNew_EventId, Key_Important_InfoNew_title, model.getEvent_id(), importanInfoNew.getTitle())) {
                                        db.saveImportanInfoNew(importanInfoNew, model.getEvent_id());
                                    } else {
                                        db.updateImportanInfoNew(importanInfoNew, model.getEvent_id());
                                    }
                                } else {
                                    for (int i31 = 0; i31 < importanInfoNew.getDetail().size(); i31++) {
                                        EventImportanInfoNewDetail infoNewDetail = importanInfoNew.getDetail().get(i31);
                                        if (db.isDataTableValueMultipleNull(TableImportantInfoNew, Key_Important_InfoNew_EventId, Key_Important_InfoNew_Title_image, model.getEvent_id(), infoNewDetail.getTitle_image())) {
                                            db.saveImportanInfoNew(importanInfoNew, infoNewDetail, model.getEvent_id());
                                        } else {
                                            db.updateImportanInfoNew(importanInfoNew, infoNewDetail, model.getEvent_id());
                                        }
                                    }
                                }
                            }

                            for (int i4 = 0; i4 < model.getData_contact().size(); i4++) {
                                EventDataContact dataContact = model.getData_contact().get(i4);
                                for (int i41 = 0; i41 < dataContact.getContact_list().size(); i41++) {
                                    EventDataContactList dataContactList = dataContact.getContact_list().get(i41);
                                    if (db.isDataTableValueMultipleNull(TableContactList, Key_Contact_List_EventId, KEY_Contact_List_Name, model.getEvent_id(), dataContactList.getName())) {
                                        db.saveDataContactList(dataContactList, dataContact.getTitle(), model.getEvent_id());
                                    } else {
                                        db.updateDataContactList(dataContactList, dataContact.getTitle(), model.getEvent_id());
                                    }
                                }
                            }

                            for (int i5 = 0; i5 < model.getAbout().size(); i5++) {
                                EventAbout eventAbout = model.getAbout().get(i5);
                                if (db.isDataTableValueMultipleNull(TableEventAbout, Key_Event_About_EventId, KEY_Event_About_Description, model.getEvent_id(), eventAbout.getDescription())) {
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
                                            if (db.isDataTableValueMultipleNull(TableScheduleList, Key_Schedule_List_GroupCode, Key_Schedule_List_id, key, listDateDataList.getId())) {
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
                                if (db.isDataTableValueMultipleNull(TableEmergencie, Key_Emergencie_EventId, Key_Emergencie_Title, model.getEvent_id(), emergencies.getTitle())) {
                                    db.saveEmergency(emergencies, model.getEvent_id());
                                } else {
                                    db.updateEmergency(emergencies, model.getEvent_id());
                                }
                            }

                            for (int i8 = 0; i8 < model.getSurrounding_area().size(); i8++) {
                                EventSurroundingArea area = model.getSurrounding_area().get(i8);
                                if (db.isDataTableValueMultipleNull(TableArea, Key_Area_EventId, Key_Area_Description, model.getEvent_id(), area.getDescription())) {
                                    db.saveArea(area, model.getEvent_id());
                                } else {
                                    db.updateArea(area, model.getEvent_id());
                                }
                            }
                            for (int i8 = 0; i8 < model.getSurrounding_area_new().size(); i8++) {
                                EventSurroundingAreaNew area = model.getSurrounding_area_new().get(i8);
                                for (int i81 = 0; i81 < area.getDetail().size(); i81++) {
                                    EventSurroundingAreaNewDetail areaNewDetail = area.getDetail().get(i81);
                                    if (db.isDataTableValueMultipleNull(TableAreaNew, Key_AreaNew_EventId, Key_AreaNew_Title_image, model.getEvent_id(), areaNewDetail.getTitle_image())) {
                                        db.saveAreaNew(areaNewDetail, area.getTitle(), model.getEvent_id());
                                    } else {
                                        db.updateAreaNew(areaNewDetail, area.getTitle(), model.getEvent_id());
                                    }
                                }
                            }

                            for (int i9 = 0; i9 < model.getCommittee_note().size(); i9++) {
                                EventCommitteeNote note = model.getCommittee_note().get(i9);
                                if (db.isDataTableValueMultipleNull(TableCommiteNote, Key_CommiteNote_EventId, Key_CommiteNote_Id, model.getEvent_id(), note.getId())) {
                                    db.saveCommiteNote(note, model.getEvent_id());
                                } else {
                                    db.updateCommiteNote(note, model.getEvent_id());
                                }
                            }

                        } else {
                            SystemUtil.showToast(getApplicationContext(), model.getStatus(), Toast.LENGTH_LONG, Gravity.TOP);
                        }

                        if (i == (eventDataResponseModels.size() - 1)) {
                            moveToTheEvent(eventId);//on response
                        }
                    }
                } else {
                    Log.d("Lihat", "onResponse ChangeEventActivity : " + response.message());
                    SystemUtil.showToast(getApplicationContext(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                }
            }

            @Override
            public void onFailure(Call<List<EventDataResponseModel>> call, Throwable t) {
                SystemUtil.hideProgress(progressDialog, 2000);
                loadingResult = false;
                Log.d("Lihat", "onFailure ChangeEventActivity : " + t.getMessage());
                SystemUtil.showToast(getApplicationContext(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                /*SystemUtil.showToast(getApplicationContext(), "Failed Connection To Server", Snackbar.LENGTH_SHORT).setAction("Reload", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();*/
                //Crashlytics.logException(new Exception(t.getMessage()));

            }
        });
    }

    private void moveToTheEvent(@Nullable String eventId) {
        if (!TextUtils.isEmpty(eventId)) {
            SessionUtil.setStringPreferences(ISeasonConfig.KEY_EVENT_ID, eventId);
        }
        Intent intent = new Intent(ChangeEventActivity.this, BaseHomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ACCOUNT && resultCode == RESULT_OK) {
            loadingResult = true;
            progressDialog = SystemUtil.showProgress(ChangeEventActivity.this, "Get data from server", "Please Wait...", false);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //code here
                    getAPIUserDataDoValid();
                }
            }, 2000);

        }
    }

    private void initLoopCheck() {
        if (db.getAllUserData(accountid).size() != 0) {
            timerLoopCheckUser = new Timer();
            timerLoopCheckUser.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!db.isUserStillIn(accountid)) {
                                AppUtil.signOutUserOtherDevice(ChangeEventActivity.this, db, RepeatCheckDataService.class, accountid, true); //check eveery 30sec
                            }
                        }
                    });
                }
            }, 0, DateTimeUtil.SECOND_MILLIS * 10);
        }
    }
}
